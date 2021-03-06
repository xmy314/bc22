package Rushy_v3;

import battlecode.common.*;

public class Archon extends Robot {
    static int ideal_miner_count;
    static int ideal_builder_count;

    public Archon(RobotController rc) throws GameActionException {
        super(rc);
        ideal_miner_count = Math.max(40,(max_X * max_Y) / 16); // 16 for 13/turn/square.
        ideal_builder_count=  Math.min((max_Y+max_X),10*rc.getArchonCount());
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.
        if(rc.getMode()==RobotMode.PORTABLE){
            int v = rc.readSharedArray(2);
            nav.navigate(new MapLocation((v>>6)&0b111111,v&0b111111) );
        }else {

            RobotType tb_built = decideNext();
            if (tb_built != null && rc.getTeamLeadAmount(rc.getTeam())>tb_built.buildCostLead) {
                int lowest_rubble = 100;
                Direction lowest_rubble_direction = null;
                for (int i = 0; i < 8; i++) {
                    Direction dir = directions[i];
                    if (rc.canBuildRobot(tb_built, dir)) {
                        int n_rubble = rc.senseRubble(rc.adjacentLocation(dir));
                        if(n_rubble<lowest_rubble){
                            lowest_rubble=n_rubble;
                            lowest_rubble_direction=dir;
                        }
                    }
                }
                if(lowest_rubble_direction!=null){
                    rc.buildRobot(tb_built,lowest_rubble_direction);
                }
            }

            if (rc.isActionReady()) {
                float highest_benefit=-1;
                RobotInfo to_fix=null;
                for (RobotInfo unit : nearby_ally_units) {
                    float benefit = unit.type.getDamage(unit.level)/(float)unit.health;
                    if (benefit > highest_benefit && unit.location.isWithinDistanceSquared(rc.getLocation(), 20)&&rc.canRepair(unit.location)) {
                        highest_benefit=benefit;
                        to_fix=unit;
                    }
                }
                if(to_fix!=null){
                    rc.repair(to_fix.location);
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
