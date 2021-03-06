package Rushy_v2;

import battlecode.common.*;

public class Archon extends Robot {
    static int ideal_miner_count;
    static int ideal_builder_count;

    static int build_direction_index = 0;

    public Archon(RobotController rc) throws GameActionException {
        super(rc);
        ideal_miner_count = Math.max(40,(max_X * max_Y) / 16); // 16 for 13/turn/square.
        ideal_builder_count= 2 * Math.min((max_Y+max_X),10*rc.getArchonCount());
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.

        RobotType tb_built = decideNext();


        if (tb_built != null) {
            for (int i = 0; i < 8; i++) {
                build_direction_index = (build_direction_index + 1) % 8;
                Direction dir = directions[build_direction_index];
                if (rc.canBuildRobot(tb_built, dir)) {
                    rc.buildRobot(tb_built, dir);
                    break;
                }
            }
        }else{
            for(RobotInfo unit:nearby_ally_units){
                if(!rc.isActionReady()) break;
                if (unit.health<unit.type.actionRadiusSquared && unit.location.isWithinDistanceSquared(rc.getLocation(),20) ){
                    while (rc.canRepair(unit.location)){
                        rc.repair(unit.location);
                    }
                }
            }
        }

        if(rc.getRoundNum()==400){
            Com.analyzeTargets();
        }
    }

    private RobotType decideNext() throws GameActionException{
        MapLocation an_enemy=Com.getTarget(0b001,0b001,8);

        int built_miner_count  = Com.getHeadcount(RobotType.MINER);
        int built_soldier_count=Com.getHeadcount(RobotType.SOLDIER);
        int built_builder_count=Com.getHeadcount(RobotType.BUILDER);
        int built_watch_tower_count = Com.getHeadcount(RobotType.WATCHTOWER);

        int ideal_soldier_count = Math.max((max_X+max_Y),rc.getRoundNum()/10);

        RobotType ret = null;
        float progression = 10;

        if(built_miner_count<10*ideal_miner_count) {
            float miner_progression = built_miner_count/(float) ideal_miner_count;
            if(miner_progression < progression ){
                ret = RobotType.MINER;
                progression = miner_progression;
            }
        }

        if(built_soldier_count<10*ideal_soldier_count ){
            float soldier_progression =  built_soldier_count/(float) ideal_soldier_count;
            if(soldier_progression < progression ){
                ret = RobotType.SOLDIER;
                progression = soldier_progression;
            }
        }

        if(built_builder_count<10*ideal_builder_count && (built_soldier_count + built_watch_tower_count>20 || rc.getTeamLeadAmount(rc.getTeam())>1000)  ){
            float builder_progression = built_builder_count/(float) ideal_builder_count;
            if(builder_progression < progression ){
                ret = RobotType.BUILDER;
                progression = builder_progression;
            }
        }

        return ret;
    }

}
