package Rushy_v2;

import battlecode.common.*;

public class Robot {

    final public static boolean debugOn = true;

    static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };

    static RobotController rc;
    static Nav nav;

    static int max_X;
    static int max_Y;

    static MapLocation spawn_point;

    static RobotInfo[] nearby_ally_units;
    static RobotInfo[] nearby_enemy_units;

    static int protection_level;
    static int threat_level;
    static boolean ally_archon_in_sight;
    static boolean enemy_archon_in_sight;

    // just for miners
    static int mine_over_thresh_count;
    static int ally_miner_count;
    static MapLocation[] mines;

    static MapLocation consistent_target;
    static int consistent_rounds;
    static boolean is_target_from_com=false;


    public Robot(RobotController r) throws GameActionException {
        rc = r;
        boolean smart_pathing=false;
        switch (r.getType()){
            case SOLDIER:
            case WATCHTOWER:
            case SAGE:
            case MINER:
//            case ARCHON:
                smart_pathing=true;
        }
        nav = new Nav(r,smart_pathing);

        max_X = rc.getMapWidth();
        max_Y = rc.getMapHeight();

        Com.init();

        spawn_point = rc.getLocation();

        Com.incrementHeadCount();

    }

    public void takeTurn() throws GameActionException {
        // ~700 bytecode.

        nearby_ally_units = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam());
        nearby_enemy_units = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam().opponent());

        protection_level=0;
        ally_archon_in_sight=false;
        for (RobotInfo nearby_unit : nearby_ally_units) {
            switch (nearby_unit.getType()){
                case SOLDIER:
                    protection_level++;
                    break;
                case WATCHTOWER:
                    if(nearby_unit.getMode()==RobotMode.TURRET){
                        protection_level++;
                    }
                    break;
                case ARCHON:
                    ally_archon_in_sight = true;
                    break;
            }
        }

        threat_level=0;
        enemy_archon_in_sight = false;
        for (RobotInfo nearby_unit : nearby_enemy_units) {
            switch (nearby_unit.getType()){
                case SOLDIER:
                    threat_level++;
                    break;
                case WATCHTOWER:
                    if(nearby_unit.getMode()==RobotMode.TURRET){
                        threat_level++;
                    }
                    break;
                case ARCHON:
                    Com.setTarget(0b001,0b001,nearby_unit.location);
                    enemy_archon_in_sight=true;
                    break;
            }
        }

        if(rc.getMode()==RobotMode.DROID || rc.getMode()==RobotMode.PORTABLE) {
            mines = rc.senseNearbyLocationsWithLead(20, 6); // any larger than some other miner can probably get to it first
            mine_over_thresh_count = mines.length;

            ally_miner_count = 0;
            for (RobotInfo nearby_unit : nearby_ally_units) {
                if (nearby_unit.getType() == RobotType.MINER) {
                    ally_miner_count++;
                }
            }
        }


        Com.verifyTargets();

        if(rc.getHealth()<=5*threat_level){
            Com.decrementHeadcount();
        }

    }

    public static int getAttackPriority(RobotType rt) {
        switch (rt) {
            case MINER:
                return 0;
            case LABORATORY:
                return 1;
            case ARCHON:
            case BUILDER:
                return 2;
            case WATCHTOWER:
            case SOLDIER:
                return 3;
            case SAGE:
                return 4;
            default:
                return -1;
        }
    }

    public static RobotInfo chooseAttackTarget(RobotInfo[] enemies) {
        int record_priority = getAttackPriority(enemies[0].getType());
        int tie_breaker_health = enemies[0].getHealth();
        RobotInfo current_target = enemies[0];
        for (int i = 1; i < enemies.length; i++) {
            int priority = getAttackPriority(enemies[i].getType());
            if (priority > record_priority || priority == record_priority && enemies[i].getHealth() < tie_breaker_health) {
                current_target = enemies[i];
                record_priority = priority;
                tie_breaker_health = enemies[i].getHealth();
            }
        }
        return current_target;
    }
}
