package Rushy_v7;

import battlecode.common.*;

public class Soldier extends Robot {

    public Soldier(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        Com.armyInterchange();

        // Try to attack someone
        if (nearby_enemy_units.length > 0) {
            combat();
        } else {
            safeMovement();
        }
    }

    public void combat() throws GameActionException {

        if (rc.isMovementReady()) {
            SoldierMicroInfo[] micro_infos = new SoldierMicroInfo[9];
            for (int i = 0; i < 8; i++) {
                micro_infos[i] = new SoldierMicroInfo(rc.adjacentLocation(directions[i]));
            }
            micro_infos[8] = new SoldierMicroInfo(rc.getLocation());

            for (RobotInfo nearby_enemy_unit : nearby_enemy_units) {
                for (SoldierMicroInfo micro_info : micro_infos) {
                    micro_info.update(nearby_enemy_unit);
                }
            }

            int best_dex = 8;
            for (int i = 0; i < 8; i++) {
                if (!rc.canMove(directions[i])) continue;
                if (micro_infos[i].isBetter(micro_infos[best_dex])) {
                    best_dex = i;
                }
            }

            if (best_dex == 8) {
                RobotInfo toAttack = chooseAttackTarget(nearby_enemy_units);
                if (toAttack != null) rc.attack(toAttack.location);
            } else if (micro_infos[best_dex].rubble < micro_infos[8].rubble) {
                nav.moveWrapper(directions[best_dex]);
                RobotInfo toAttack = chooseAttackTarget(nearby_enemy_units);
                if (toAttack != null) rc.attack(toAttack.location);
            } else {
                RobotInfo toAttack = chooseAttackTarget(nearby_enemy_units);
                if (toAttack != null) rc.attack(toAttack.location);
                nav.moveWrapper(directions[best_dex]);
            }

        } else {
            RobotInfo toAttack = chooseAttackTarget(nearby_enemy_units);
            if (toAttack != null) rc.attack(toAttack.location);
        }

    }

    static class SoldierMicroInfo {
        float potential_dmg; // assuming all known enemy that can attack me attacks me, how much health do i lose of average
        int min_dist_to_enemy;
        MapLocation loc;
        boolean on_map;
        int rubble;
        double cost_mult;

        public SoldierMicroInfo(MapLocation loc) {
            this.loc = loc;
            try {
                on_map = rc.onTheMap(loc);
                if (on_map) {
                    rubble = rc.senseRubble(loc);
                    cost_mult = Math.pow(1+rubble/10f,2);
                    potential_dmg = 0;
                    min_dist_to_enemy = 10000;
                }
            } catch (GameActionException e) {
                e.printStackTrace(); // never happening as sense is always radius 1 away
            }
        }

        public void update(RobotInfo ri) {
            if (!on_map) return;
            int d = ri.getLocation().distanceSquaredTo(loc);
            try {
                if (d <= ri.getType().actionRadiusSquared)
                    potential_dmg += Math.abs(ri.type.getDamage(ri.level) / (1 + rc.senseRubble(ri.getLocation()) / 10));
            } catch (GameActionException e) {
                e.printStackTrace(); // never happening since all input are within vision range.
            }
            if (d < min_dist_to_enemy) min_dist_to_enemy = d;
        }

        public boolean canAttack() {
            return rc.getType().actionRadiusSquared >= min_dist_to_enemy;
        }

        public boolean isBetter(SoldierMicroInfo mi) {
            // on map is better
            if (!mi.on_map) return true;
            if (!on_map) return false;

            // low potential_damage is better. however, there is a buffer.
            if ((potential_dmg + 3) * cost_mult < (mi.potential_dmg + 1) * mi.cost_mult) return true;
            if ((potential_dmg + 1) * cost_mult > (mi.potential_dmg + 3) * mi.cost_mult) return false;

            // can attack is better
            if (rc.isActionReady()) {
                if (canAttack() && !mi.canAttack()) return true;
                if (!canAttack() && mi.canAttack()) return false;
            }

            // low rubble is better
            return rubble < mi.rubble;
        }
    }

    public void safeMovement() throws GameActionException {
        if (consistent_target != null) {
            consistent_rounds++;
        }

        if (!rc.isMovementReady()) return;

        if (consistent_target != null && debugOn) {
            if (debugOn) rc.setIndicatorLine(rc.getLocation(), consistent_target, 200, 0, 0);
        }

        // check if there is any mission by communication
        if (consistent_target != null) {
            if (rc.getLocation().isWithinDistanceSquared(consistent_target, 13) ||
                    consistent_rounds >= 30 ||
                    is_target_from_com && (Com.getFlags(consistent_target) & 0b101) == 0) {
                consistent_target = null;
                consistent_rounds = 0;
                is_target_from_com = false;
            }
        }

        if (consistent_target == null) {
            consistent_target = Com.getTarget(0b001, 0b001, 6); // military support
            if (consistent_target != null) {
                is_target_from_com = true;
            }
        }

        if (consistent_target == null) {
            consistent_target = Com.getTarget(0b100, 0b100, 12); // pioneer
            if (consistent_target != null) {
                is_target_from_com = true;
            }
        }

        if (consistent_target != null) {
            nav.navigate(consistent_target);
        }
    }
}
