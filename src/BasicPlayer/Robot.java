package BasicPlayer;

import battlecode.common.*;

import java.util.Random;

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
    static Com com;

    static int max_X;
    static int max_Y;

    static MapLocation spawn_point;

    static RobotInfo[] nearby_ally_units;
    static RobotInfo[] nearby_enemy_units;


    public Robot(RobotController r) throws GameActionException {
        rc = r;
        nav = new Nav(r);
        com = new Com(r);

        max_X = rc.getMapWidth();
        max_Y = rc.getMapHeight();

        if (r.getType() == RobotType.BUILDER || r.getType() == RobotType.SOLDIER || r.getType() == RobotType.MINER || r.getType() == RobotType.SAGE) {
            spawn_point = rc.getLocation();
        }

    }

    public void takeTurn() throws GameActionException {
        // stuff that every bot does.

        nearby_ally_units = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam());
        nearby_enemy_units = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam().opponent());
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
