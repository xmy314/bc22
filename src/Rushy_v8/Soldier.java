package Rushy_v8;

import battlecode.common.*;

public class Soldier extends Robot {

    private enum SOLDIER_ACTION_STATE {FIGHT, EXPLORE, HEAL, SACRIFICE, STALL}

    public SOLDIER_ACTION_STATE current_state = SOLDIER_ACTION_STATE.STALL;
    public int soldier_cap;

    public Soldier(RobotController rc) throws GameActionException {
        super(rc);
        soldier_cap=Math.max(10,max_X*max_Y/25);
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
                float dmg = Math.abs(nearby_enemy_unit.type.getDamage(nearby_enemy_unit.level) / (1 + rc.senseRubble(nearby_enemy_unit.getLocation()) / 10f));
                for (SoldierMicroInfo micro_info : micro_infos) {
                    micro_info.updateEnemy(nearby_enemy_unit, dmg);
                }
            }

            int best_dex = 8;
            int health = (Com.getHeadcount(RobotType.SOLDIER)<soldier_cap)?rc.getHealth():50; // so it doesn't try to get away
            for (int i = 0; i < 8; i++) {
                if (!rc.canMove(directions[i])) continue;
                if (micro_infos[i].isBetter(micro_infos[best_dex], health,ally_dmg > 5 * enemy_dmg||rc.getRoundNum()>=1700||Com.getHeadcount(RobotType.WATCHTOWER)>soldier_cap)) {
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
        float potential_dmg; // assuming all known enemy that can attack me attacks me, how much health do I lose of average
        int min_dist_to_enemy;
        MapLocation loc;
        boolean on_map;
        int rubble;
        double cost_multi;

        public SoldierMicroInfo(MapLocation loc) {
            this.loc = loc;
            try {
                on_map = rc.onTheMap(loc);
                if (on_map) {
                    rubble = rc.senseRubble(loc);
                    cost_multi = Math.pow(1 + rubble / 10f, 2);
                    potential_dmg = 0;
                    min_dist_to_enemy = 10000;
                }
            } catch (GameActionException e) {
                e.printStackTrace(); // never happening as sense is always radius 1 away
            }
        }

        public void updateEnemy(RobotInfo ri, float dmg) {
            if (!on_map) return;
            int d = ri.getLocation().distanceSquaredTo(loc);
            if (d <= ri.getType().actionRadiusSquared) potential_dmg += dmg;
            if (d < min_dist_to_enemy) min_dist_to_enemy = d;
        }

        public boolean canAttack() {
            return rc.getType().actionRadiusSquared >= min_dist_to_enemy;
        }

        public boolean isBetter(SoldierMicroInfo mi, int health, boolean just_push) {
            // on map is better
            if (!mi.on_map) return true;
            if (!on_map) return false;

            // low potential_damage is better. however, there is a buffer.
            if(!just_push) {
                if ((potential_dmg + 3) * cost_multi < (mi.potential_dmg + 1) * mi.cost_multi) return true;
                if ((potential_dmg + 1) * cost_multi > (mi.potential_dmg + 3) * mi.cost_multi) return false;

                if (health < 12) {
                    if (min_dist_to_enemy <= 13) {
                        return min_dist_to_enemy / cost_multi > mi.min_dist_to_enemy / mi.cost_multi;
                    } else {
                        return min_dist_to_enemy > mi.min_dist_to_enemy;
                    }
                }

                // low rubble is better
                return rubble < mi.rubble;
            }else{
                return min_dist_to_enemy*cost_multi < mi.min_dist_to_enemy * mi.cost_multi;
            }
        }
    }

    public void safeMovement() throws GameActionException {
        // debug signal
        if (debugOn && consistent_target != null) rc.setIndicatorLine(rc.getLocation(), consistent_target, 200, 0, 0);

        // return if it can't move any ways.
        if (!rc.isMovementReady()) return;

        // check if there is any mission by communication
        if (rc.getHealth() < 15 && Com.getHeadcount(RobotType.SOLDIER)<soldier_cap) {
            current_state = SOLDIER_ACTION_STATE.HEAL;
            consistent_target = Com.getMainArchonLoc();
        }

        switch (current_state) {
            case FIGHT:
                if ((Com.getFlags(consistent_target) & 0b001) == 0) {
                    current_state = SOLDIER_ACTION_STATE.STALL;
                    consistent_target = null;
                }
                break;
            case EXPLORE:
                if ((Com.getFlags(consistent_target) & 0b100) == 0) {
                    current_state = SOLDIER_ACTION_STATE.STALL;
                    consistent_target = null;
                }
                break;
            case HEAL:
                if (rc.getHealth() == 50) {
                    current_state = SOLDIER_ACTION_STATE.STALL;
                    consistent_target = null;
                }else if((Com.getHeadcount(RobotType.SOLDIER)>soldier_cap || nearby_ally_units.length>30) && rc.getHealth()<15 && nearby_enemy_units.length==0 && rc.senseLead(rc.getLocation())==0){
                    rc.disintegrate();
                }
        }

        if (current_state == SOLDIER_ACTION_STATE.STALL) {
            consistent_target = Com.getTarget(0b001, 0b001, 6); // military support
            if (consistent_target != null) {
                current_state = SOLDIER_ACTION_STATE.FIGHT;
            }
        }

        if (current_state == SOLDIER_ACTION_STATE.STALL) {
            consistent_target = Com.getTarget(0b100, 0b100, 12); // pioneer
            if (consistent_target != null) {
                current_state = SOLDIER_ACTION_STATE.EXPLORE;
            }
        }

        if (current_state != SOLDIER_ACTION_STATE.STALL && consistent_target != null) {
            nav.navigate(consistent_target);
        }
    }
}
