package BasicPlayer;

import battlecode.common.*;

import BasicPlayer.utils.Debug;

public class Archon extends Robot {
    static int ideal_miner_count;

    static int build_direction_index = 0;
    static int built_miner_count;

    public Archon(RobotController rc) throws GameActionException {
        super(rc);
        ideal_miner_count = max_X * max_Y / (8 * rc.getArchonCount()); // 4 for 5/turn/square. another to make have large area.
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.
        MapLocation target = com.getTarget(Com.ComFlag.ATTACK);
        if (target != null) {
            Debug.p("atk" + target);
        }
        target = Com.uncompressLocation(16);
        if (target != null) {
            Debug.p("sup" + target);
        }
        target = Com.uncompressLocation(17);
        if (target != null) {
            Debug.p("sup" + target);
        }

        target = com.getTarget(Com.ComFlag.EXAMINE);
        if (target != null) {
            Debug.p("exa" + target);
        }

        MapLocation me = rc.getLocation();
        com.setTarget(Com.ComFlag.EXAMINE, new MapLocation(rc.getMapWidth() - me.x, rc.getMapHeight() - me.y));

        int protection_level = 0;
        for (RobotInfo nearby_unit : nearby_ally_units) {
            if (nearby_unit.getType() == RobotType.WATCHTOWER) {
                protection_level++;
            }
        }

        if (protection_level < 5) {
            com.setTarget(Com.ComFlag.PROTECT, rc.getLocation());
        }

        RobotType tb_built = null;
        int ally_miner_count = 0;
        int enemy_count = nearby_enemy_units.length;
        for (RobotInfo nearby_unit : nearby_ally_units) {
            if (nearby_unit.getType() == RobotType.MINER) {
                ally_miner_count++;
            }
        }

        if (enemy_count > 0) {
            tb_built = RobotType.SOLDIER;
        } else if (ally_miner_count < 25 && built_miner_count < ideal_miner_count) { // this 25 is arbitrary (#^^#). not best, good enough
            tb_built = RobotType.MINER;
        } else if (rc.getRoundNum() > 400 && rc.getTeamLeadAmount(rc.getTeam()) > 1000) {
            tb_built = RobotType.SOLDIER;
        }

        if (tb_built != null) {
            for (int i = 0; i < 8; i++) {
                build_direction_index = (build_direction_index + 1) % 8;
                Direction dir = directions[build_direction_index];
                if (rc.canBuildRobot(tb_built, dir)) {
                    rc.buildRobot(tb_built, dir);
                    if (tb_built == RobotType.MINER) {
                        built_miner_count++;
                    }
                    break;
                }
            }
        }

    }

}
