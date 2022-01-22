package Rushy_v9;

import battlecode.common.*;

public class Sage extends Robot {
    private enum SAGE_ACTION_STATE {FIGHT, HEAL, STALL}

    public SAGE_ACTION_STATE current_state = SAGE_ACTION_STATE.STALL;

    public Sage(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();


        // Try to attack someone
        if (nearby_enemy_units.length > 0) {
            combat();
        } else {
            safeMovement();
        }
    }

    public void combat() throws GameActionException {

        if (rc.isMovementReady()) {
            SageMicroInfo[] micro_infos = new SageMicroInfo[9];
            for (int i = 0; i < 8; i++) {
                micro_infos[i] = new SageMicroInfo(rc.adjacentLocation(directions[i]));
            }
            micro_infos[8] = new SageMicroInfo(rc.getLocation());

            for (int i = nearby_enemy_units.length - 1; --i >= 0; ) {
                RobotInfo nearby_enemy_unit = nearby_enemy_units[i];
                float dmg = Math.abs(nearby_enemy_unit.type.getDamage(nearby_enemy_unit.level) / (1 + rc.senseRubble(nearby_enemy_unit.getLocation()) / 10f));
                for (int j = 7; --j >= 0; ) {
                    micro_infos[j].updateEnemy(nearby_enemy_unit, dmg);
                }
            }

            int best_dex = 8;
            for (int i = 0; i < 8; i++) {
                if (!rc.canMove(directions[i])) continue;
                if (micro_infos[i].isBetter(micro_infos[best_dex])) {
                    best_dex = i;
                }
            }

            if (best_dex != 8) {
                nav.moveWrapper(directions[best_dex]);
            }
        }

        if(rc.isActionReady()) {
            if (rc.getHealth() < 60) {
                nearby_enemy_units = rc.senseNearbyRobots(20, rc.getTeam().opponent());
                nearby_ally_units = rc.senseNearbyRobots(20, rc.getTeam());

                int charge_counter = 0;
                int fury_counter = 0;

                for (RobotInfo nearby_enemy_unit : nearby_enemy_units) {
                    switch (nearby_enemy_unit.type) {
                        case ARCHON:
                        case WATCHTOWER:
                        case LABORATORY:
                            if (nearby_enemy_unit.mode == RobotMode.TURRET)
                                fury_counter += 0.1 * nearby_enemy_unit.type.getMaxHealth(nearby_enemy_unit.level);
                        case SAGE:
                        case SOLDIER:
                        case BUILDER:
                        case MINER:
                            charge_counter += 0.22 * nearby_enemy_unit.type.getMaxHealth(1);
                    }
                }
                for (RobotInfo nearby_ally_unit : nearby_enemy_units) {
                    switch (nearby_ally_unit.type) {
                        case ARCHON:
                        case WATCHTOWER:
                        case LABORATORY:
                            if (nearby_ally_unit.mode == RobotMode.TURRET)
                                fury_counter -= 0.1 * nearby_ally_unit.type.getMaxHealth(nearby_ally_unit.level);
                    }
                }

                rc.setIndicatorString(" " + charge_counter + " " + fury_counter);

                if (charge_counter > fury_counter) {
                    rc.envision(AnomalyType.CHARGE);
                } else {
                    rc.envision(AnomalyType.FURY);
                }
            }
        }
    }

    static class SageMicroInfo {
        float potential_dmg; // assuming all known enemy that can attack me attacks me, how much health do I lose of average
        int min_dist_to_enemy;
        MapLocation loc;
        boolean on_map;
        int padded_rubble;
        double cost_multi;

        public SageMicroInfo(MapLocation loc) {
            this.loc = loc;
            try {
                on_map = rc.onTheMap(loc);
                if (on_map) {
                    padded_rubble = 10+rc.senseRubble(loc);
                    cost_multi = Math.pow(padded_rubble, 2)/100;
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

        public boolean isBetter(SageMicroInfo mi) {
            // on map is better
            if (!mi.on_map) return true;
            if (!on_map) return false;

            // low potential_damage is better. however, there is a buffer.
            if(rc.isActionReady()) {
                return potential_dmg / cost_multi  > mi.potential_dmg/ mi.cost_multi;
            }else{
                return min_dist_to_enemy > mi.min_dist_to_enemy;
            }
        }
    }

    public void safeMovement() throws GameActionException {
        // debug signal
        if (debugOn && consistent_target != null) rc.setIndicatorLine(rc.getLocation(), consistent_target, 200, 0, 0);

        // return if it can't move any ways.
        if (!rc.isMovementReady()) return;

        // check if there is any mission by communication
        if (rc.getHealth() < 15 ) {
            current_state = SAGE_ACTION_STATE.HEAL;
            consistent_target = Com.getMainArchonLoc();
        }

        switch (current_state) {
            case FIGHT:
                if ((Com.getFlags(consistent_target) & 0b001) == 0) {
                    current_state = SAGE_ACTION_STATE.STALL;
                    consistent_target = null;
                }
                break;
            case HEAL:
                if (rc.getHealth() == 50) {
                    current_state = SAGE_ACTION_STATE.STALL;
                    consistent_target = null;
                }
        }

        if (current_state == SAGE_ACTION_STATE.STALL) {
            consistent_target = Com.getTarget(0b001, 0b001, 12,4); // military support
            if (consistent_target != null) {
                current_state = SAGE_ACTION_STATE.FIGHT;
            }
        }

        if (current_state != SAGE_ACTION_STATE.STALL && consistent_target != null) {
            nav.navigate(consistent_target);
        }
    }
}
