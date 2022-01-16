package Rushy_v5;

import battlecode.common.*;

public class Archon extends Robot {
    static int ideal_miner_count;
    static int ideal_builder_count;
    static int ideal_soldier_count;

    public Archon(RobotController rc) throws GameActionException {
        super(rc);
        ideal_miner_count = Math.max(10, (max_X * max_Y) / 25); // 16 for 13/turn/square.
        ideal_builder_count = rc.getArchonCount() * (max_Y + max_X) / 10;

        Com.archonInterchange();
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        // stuff that this type of bot does.
        if (rc.getMode() == RobotMode.PORTABLE) {
            rc.setIndicatorLine(rc.getLocation(), Com.getMainArchonLoc(), 200, 200, 200);
            if (!rc.getLocation().isWithinDistanceSquared(Com.getMainArchonLoc(), 20)) {
                nav.navigate(Com.getMainArchonLoc());
            } else {
                int best_dex = 8;
                int lowest_rubble = rc.senseRubble(rc.getLocation());
                for (int i = 0; i < 8; i++) {
                    if (rc.onTheMap(rc.adjacentLocation(directions[i])) && rc.adjacentLocation(directions[i]).isWithinDistanceSquared(Com.getMainArchonLoc(), 20)) {
                        int rubble_count = rc.senseRubble(rc.adjacentLocation(directions[i]));
                        if (rubble_count < lowest_rubble) {
                            lowest_rubble = rubble_count;
                            best_dex = i;
                        }
                    }
                }
                if (best_dex != 8) {
                    nav.moveWrapper(directions[best_dex]);
                } else {
                    if (rc.canTransform()) {
                        rc.transform();
                    }
                }
            }
        } else {
            RobotType tb_built = decideNext();
            if (tb_built != null && rc.getTeamLeadAmount(rc.getTeam()) > tb_built.buildCostLead) {
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
            } else if (!rc.getLocation().isWithinDistanceSquared(Com.getMainArchonLoc(), 100) && rc.getRoundNum() > 20) {
                if (rc.canTransform()) {
                    rc.transform();
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
        }

        if (rc.getRoundNum() % 200 == 199) {
            Com.partialResetExploration();
        }

    }

    private RobotType decideNext() throws GameActionException {
        MapLocation an_enemy = Com.getTarget(0b001, 0b001, 8);

        int built_miner_count = Com.getHeadcount(RobotType.MINER);
        int built_soldier_count = Com.getHeadcount(RobotType.SOLDIER);
        int built_builder_count = Com.getHeadcount(RobotType.BUILDER);
        int built_watch_tower_count = Com.getHeadcount(RobotType.WATCHTOWER);

        ideal_soldier_count = ((built_miner_count>10)?4:2) * ideal_miner_count;

        RobotType ret = null;
        float progression = 10;

        if (built_miner_count < 10 * ideal_miner_count && (an_enemy == null || an_enemy.distanceSquaredTo(rc.getLocation()) > 80)) {
            float miner_progression = built_miner_count / (float) ideal_miner_count;
            if (miner_progression < progression) {
                ret = RobotType.MINER;
                progression = miner_progression;
            }
        }

        if (built_soldier_count < 10 * ideal_soldier_count && (rc.getRoundNum() > 10 || built_miner_count >= 2 * rc.getArchonCount())) {
            float soldier_progression = built_soldier_count / (float) ideal_soldier_count;
            if (soldier_progression < progression) {
                ret = RobotType.SOLDIER;
                progression = soldier_progression;
            }
        }

        if (built_builder_count < 10 * ideal_builder_count && built_builder_count <= built_watch_tower_count && (built_soldier_count + built_watch_tower_count > 10 || rc.getTeamLeadAmount(rc.getTeam()) > 500)) {
            float builder_progression = built_builder_count / (float) ideal_builder_count;
            if (builder_progression < progression) {
                ret = RobotType.BUILDER;
                progression = builder_progression;
            }
        }

        return ret;
    }

}
