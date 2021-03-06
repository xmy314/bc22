package Rushy;

import Rushy.utils.Debug;
import battlecode.common.*;

public class Archon extends Robot {
    static int ideal_miner_count;
    static int ideal_builder_count;

    static int built_unit_count=0;

    static int build_direction_index = 0;

    public Archon(RobotController rc) throws GameActionException {
        super(rc);
        ideal_miner_count = Math.max(100,max_X * max_Y / (4)); // 4 for 5/turn/square. another 2 to share the area.
        ideal_builder_count= Math.min((max_Y+max_X),40*rc.getArchonCount());
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.

        MapLocation me = rc.getLocation();

        if (protection_level < 10) {
            Com.setTarget(Com.ComFlag.PROTECT, rc.getLocation());
        }

        RobotType tb_built = null;

        tb_built=decideNext();

        int total_built_unit = Com.getHeadcount(RobotType.MINER) + Com.getHeadcount(RobotType.SOLDIER) + Com.getHeadcount(RobotType.BUILDER);

        if (tb_built != null && (nearby_ally_units.length<20 || rc.getTeamLeadAmount(rc.getTeam()) - tb_built.buildCostLead > protection_level * 75) ) {
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

    private RobotType decideNext() throws GameActionException{
        MapLocation an_enemy=Com.getTarget(Com.ComFlag.ATTACK);

        int built_miner_count  = Com.getHeadcount(RobotType.MINER);
        int built_soldier_count=Com.getHeadcount(RobotType.SOLDIER);
        int built_builder_count=Com.getHeadcount(RobotType.BUILDER);

        int ideal_soldier_count = Math.max((max_X+max_Y)/2,built_miner_count/4)*((an_enemy!=null)?4:1);

        RobotType ret = null;
        float progression = 100f;

        if(built_miner_count<ideal_miner_count) {
            float miner_progression = built_miner_count/(float) ideal_miner_count;
            if(miner_progression < progression ){
                ret = RobotType.MINER;
                progression = miner_progression;
            }
        }

        if(built_soldier_count<ideal_soldier_count && built_miner_count>10){
            float soldier_progression =  built_soldier_count/(float) ideal_soldier_count;
            if(soldier_progression < progression ){
                ret = RobotType.SOLDIER;
                progression = soldier_progression;
            }
        }

        if(built_builder_count<ideal_builder_count && built_soldier_count>10){
            float builder_progression = built_builder_count/(float) ideal_builder_count;
            if(builder_progression < progression ){
                ret = RobotType.BUILDER;
                progression = builder_progression;
            }
        }

        return ret;
    }

}
