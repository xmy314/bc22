package BasicPlayer;

import battlecode.common.*;

public class Archon extends Robot {
    int build_direction_index=0;
    public Archon(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.

        MapLocation me=rc.getLocation();
        com.setTarget(Com.ComFlag.EXAMINE,new MapLocation(rc.getMapWidth()-me.x,rc.getMapHeight()-me.y));

        int protection_level = 0;
        for(RobotInfo nearby_unit:nearby_ally_units){
            if(nearby_unit.getType()==RobotType.WATCHTOWER){
                protection_level++;
            }
        }
        if(protection_level<5){
            com.setTarget(Com.ComFlag.PROTECT,rc.getLocation());
        }

        RobotType tb_built = null;
        int ally_miner_count=0;
        int enemy_count=nearby_enemy_units.length;
        for(RobotInfo nearby_unit:nearby_ally_units){
            if(nearby_unit.getType()==RobotType.MINER){
                ally_miner_count++;
            }
        }

        if(enemy_count>0){
            tb_built = RobotType.SOLDIER;
        }else if(ally_miner_count<25) { // this 25 is arbitrary (#^^#). not best, good enough
            tb_built = RobotType.MINER;
        }else if(rc.getRoundNum()>400 && rc.getTeamLeadAmount(rc.getTeam())>1000){
            tb_built = RobotType.SOLDIER;
        }

        if(tb_built!=null) {
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
