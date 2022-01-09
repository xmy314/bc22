package BasicPlayer;

import battlecode.common.*;

import BasicPlayer.utils.Debug;

public class Archon extends Robot {
    static int ideal_miner_count;
    static int ideal_builder_count;

    static int build_direction_index = 0;

    static boolean examined_180;
    static boolean examined_ud;
    static boolean examined_lr;

    public Archon(RobotController rc) throws GameActionException {
        super(rc);
        ideal_miner_count = Math.max(100,max_X * max_Y / (8)); // 4 for 5/turn/square. another 2 to share the area.
        ideal_builder_count= Math.min((max_Y+max_X)/2,20*rc.getArchonCount());
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.

        MapLocation me = rc.getLocation();

        if(!examined_180) {
            examined_180 = !Com.setTarget(Com.ComFlag.EXAMINE, new MapLocation(rc.getMapWidth() - me.x, rc.getMapHeight() - me.y));
        }
        if (!examined_ud) {
            examined_lr= !Com.setTarget(Com.ComFlag.EXAMINE, new MapLocation(me.x, rc.getMapHeight() - me.y));
        }
        if (!examined_lr) {
            examined_lr= !Com.setTarget(Com.ComFlag.EXAMINE, new MapLocation(rc.getMapWidth() - me.x, me.y));
        }

        if (protection_level < 10) {
            Com.setTarget(Com.ComFlag.PROTECT, rc.getLocation());
        }

        RobotType tb_built = null;
        int ally_miner_count = 0;
        for (RobotInfo nearby_unit : nearby_ally_units) {
            if (nearby_unit.getType() == RobotType.MINER) {
                ally_miner_count++;
            }
        }

        int built_miner_count=Com.getHeadcount(RobotType.MINER);
        int built_soldier_count=Com.getHeadcount(RobotType.SOLDIER);
        int built_builder_count=Com.getHeadcount(RobotType.BUILDER);

        int ideal_soldier_count = Math.max((max_X+max_Y)/2,built_miner_count/8);

        MapLocation an_enemy=Com.getTarget(Com.ComFlag.ATTACK);

        if(built_miner_count>5 && built_soldier_count<20&&(nearby_enemy_units.length > 0||an_enemy!=null && an_enemy.distanceSquaredTo(me)<100)){
            // no enough self defence? build a few soldiers for short term defence.
            tb_built=RobotType.SOLDIER;
        }else if(ally_miner_count<20 && built_miner_count<0.25*ideal_miner_count){
            // if safe for now, build more miner
            tb_built=RobotType.MINER;
        }else if(built_soldier_count<ideal_soldier_count){
            // build some soldiers before spamming calls.
            tb_built=RobotType.SOLDIER;
        }else if(built_miner_count/(float)ideal_miner_count <= built_builder_count/(float)ideal_builder_count) {
            // continue to construct the rest of the miners.
            tb_built=RobotType.MINER;
        }else if(built_builder_count<ideal_builder_count){
            // continue to build the rest of the builders.
            tb_built=RobotType.BUILDER;
        }

        if (tb_built != null) {
            for (int i = 0; i < 8; i++) {
                build_direction_index = (build_direction_index + 1) % 8;
                Direction dir = directions[build_direction_index];
                if (rc.canBuildRobot(tb_built, dir)) {
                    rc.buildRobot(tb_built, dir);
                    break;
                }
            }
        }
    }

}
