package BasicPlayer;

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


    public Robot(RobotController r) throws GameActionException {
        rc = r;
        nav = new Nav(r);

        max_X = rc.getMapWidth()-1;
        max_Y = rc.getMapHeight()-1;

        spawn_point = rc.getLocation();

        Com.incrementHeadCount();

    }

    public void takeTurn() throws GameActionException {
        // ~700 bytecode.

        nearby_ally_units = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam());
        nearby_enemy_units = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam().opponent());

        protection_level=0;
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
            }
        }

        threat_level=0;
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
                    Com.setTarget(Com.ComFlag.ATTACK,nearby_unit.location);
                    break;
            }
        }

        if (threat_level > 0 && protection_level<10) {
            Com.setTarget(Com.ComFlag.PROTECT, nearby_enemy_units[0].getLocation());
        }

        Com.verifyTargets();

        if(nearby_enemy_units.length!=0&&rc.getHealth()<=10){
            Com.decrementHeadcount();
        }
    }

    public static int getAttackPriority(RobotType rt) {
        switch (rt) {
            case MINER:
                return 0;
            case ARCHON:
            case LABORATORY:
            case SOLDIER:
                return 1;
            case BUILDER:
                return 2;
            case WATCHTOWER:
                return 3;
            case SAGE:
                return 4;
            default:
                return -1;
        }
    }

    public static RobotInfo decideTarget(RobotInfo[] enemies) {
        int record_priority = getAttackPriority(enemies[0].getType());
        int tie_breaker_health = enemies[0].getHealth();
        RobotInfo current_target = enemies[0];
        for (int i = 1; i < enemies.length; i++) {
            if(nearby_enemy_units[i].getLocation().distanceSquaredTo(rc.getLocation())>rc.getType().actionRadiusSquared)continue;
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
