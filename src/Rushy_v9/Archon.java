package Rushy_v9;

import battlecode.common.*;

public class Archon extends Robot {

    public Archon(RobotController rc) throws GameActionException {
        super(rc);

        Com.archonInterchange();

        Com.partialResetExploration();
        Com.partialResetExploration();
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        if (debugOn) rc.setIndicatorLine(rc.getLocation(), Com.getArmyLoc(), 200, 200, 200);

        // stuff that this type of bot does.
        if (rc.getMode() == RobotMode.TURRET) {
            RobotType tb_built = decideNext();
            if (tb_built != null) {
                int lowest_rubble = 100;
                Direction lowest_rubble_direction = null;
                for (int i = 0; i < 8; i++) {
                    Direction dir = directions[i];
                    if (rc.canBuildRobot(tb_built, dir)) {
                        int n_rubble = rc.senseRubble(rc.adjacentLocation(dir));
                        if (n_rubble < lowest_rubble) {
                            lowest_rubble = n_rubble;
                            lowest_rubble_direction = dir;
                        }
                    }
                }
                if (lowest_rubble_direction != null) {
                    rc.buildRobot(tb_built, lowest_rubble_direction);
                }
            } else if (!rc.getLocation().isWithinDistanceSquared(Com.getMainArchonLoc(), 24) && rc.getRoundNum() > 50) {
                if (rc.canTransform()) {
                    rc.transform();
                    consistent_target = Com.getMainArchonLoc();
                }
            } else {
                for (RobotInfo unit : nearby_ally_units) {
                    if (!rc.isActionReady()) break;
                    if (unit.health < unit.type.getMaxHealth(unit.level) && unit.location.isWithinDistanceSquared(rc.getLocation(), 20)) {
                        if (rc.canRepair(unit.location)) {
                            rc.repair(unit.location);
                        }
                    }
                }
            }
        } else {
            if (debugOn) rc.setIndicatorLine(rc.getLocation(), consistent_target, 200, 200, 200);
            if (!rc.getLocation().isWithinDistanceSquared(consistent_target, 24)) {
                nav.navigate(consistent_target);
            } else {
                int best_dex = 8;
                int lowest_rubble = rc.senseRubble(rc.getLocation());
                for (int i = 0; i < 8; i++) {
                    if (rc.onTheMap(rc.adjacentLocation(directions[i])) && rc.canMove(directions[i]) && rc.adjacentLocation(directions[i]).isWithinDistanceSquared(consistent_target, 24)) {
                        int rubble_count = rc.senseRubble(rc.adjacentLocation(directions[i]));
                        if (rubble_count < lowest_rubble) {
                            lowest_rubble = rubble_count;
                            best_dex = i;
                        }
                    }
                }
                if (best_dex != 8 && rc.canMove(directions[best_dex])) {
                    nav.moveWrapper(directions[best_dex]);
                } else {
                    if (rc.canTransform()) {
                        rc.transform();
                        Com.archonInterchange();
                    }
                }
            }
        }

        if (rc.getRoundNum() % 100 == 99) {
            Com.partialResetExploration();
        }
    }

    private RobotType decideNext() throws GameActionException {
        MapLocation an_enemy = Com.getTarget(0b001, 0b001, 6, 1);

        int built_miner_count = Com.getHeadcount(RobotType.MINER);
        int built_soldier_count = Com.getHeadcount(RobotType.SOLDIER);
        int built_builder_count = Com.getHeadcount(RobotType.BUILDER);
        int built_watch_tower_count = Com.getHeadcount(RobotType.WATCHTOWER);

        RobotType ret = null;
        float progression = 10;

        if (built_miner_count < 2 || built_miner_count < ideal_miner_count && (an_enemy == null || !an_enemy.isWithinDistanceSquared(rc.getLocation(),40))) {
            float miner_progression = built_miner_count / (float) ideal_miner_count;
            if (miner_progression < progression) {
                ret = RobotType.MINER;
                progression = miner_progression;
            }
        }

        if (built_soldier_count+built_watch_tower_count < ideal_army_count && (rc.getRoundNum() > 10 || built_miner_count >= 2 * rc.getArchonCount())) {
            float soldier_progression = (built_soldier_count+built_watch_tower_count) / (float) ideal_army_count;
            if (soldier_progression < progression) {
                if(built_builder_count>0 && built_soldier_count+built_watch_tower_count>=20){
                    ret = RobotType.WATCHTOWER;
                }else {
                    ret = RobotType.SOLDIER;
                }
                progression = soldier_progression;
            }
        }

        if (built_builder_count < ideal_builder_count) {
            float builder_progression = built_builder_count / (float) ideal_builder_count;
            if (builder_progression < progression) {
                ret = RobotType.BUILDER;
                progression = builder_progression;
            }
        }

        if (ret != null) {
            int team_lead = rc.getTeamLeadAmount(rc.getTeam());

            switch (ret) {
                case MINER:
                    if (team_lead < 50) ret = null;
                    break;
                case SOLDIER:
                    if (team_lead < 75) ret = null;
                    break;
                case BUILDER:
                    if (team_lead < 40) ret = null;
                    break;
                case WATCHTOWER:
                    ret=null;
            }
        }


        if (rc.getTeamGoldAmount(rc.getTeam()) > 20 + rc.getTeamGoldAmount(rc.getTeam().opponent())) {
            ret = RobotType.SAGE;
        }

        return ret;
    }

}
