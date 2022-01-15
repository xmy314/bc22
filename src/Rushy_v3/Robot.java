package Rushy_v3;

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

    static float ally_dmg;
    static float enemy_dmg;
    static float ally_health;
    static float enemy_health;
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
        nav = new Nav(r);

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

        ally_dmg =0;
        ally_health=0;
        ally_archon_in_sight=false;
        enemy_dmg =0;
        enemy_health=0;
        enemy_archon_in_sight = false;

        ally_miner_count = 0;

        for (RobotInfo nearby_unit : nearby_ally_units) {
            switch (nearby_unit.getType()){
                case SOLDIER:
                    ally_dmg += 3/(1+rc.senseRubble(nearby_unit.location)/10f);
                    ally_health += nearby_unit.health;
                    break;
                case WATCHTOWER:
                    if(nearby_unit.getMode()==RobotMode.TURRET){
                        ally_dmg +=nearby_unit.type.getDamage(nearby_unit.level)/(1+rc.senseRubble(nearby_unit.location)/10f);
                    }
                    ally_health+=nearby_unit.health;
                    break;
                case ARCHON:
                    ally_archon_in_sight = true;
                    enemy_dmg -=2;
                    break;
                case MINER:
                    ally_miner_count++;
                    break;
            }
        }


        for (RobotInfo nearby_unit : nearby_enemy_units) {
            switch (nearby_unit.getType()){
                case SOLDIER:
                    enemy_dmg +=3/(1+rc.senseRubble(nearby_unit.location)/10f);
                    enemy_health+=nearby_unit.health;
                    break;
                case WATCHTOWER:
                    enemy_dmg +=nearby_unit.type.getDamage(nearby_unit.level)/(1+rc.senseRubble(nearby_unit.location)/10f);
                    enemy_health+=nearby_unit.health;
                    break;
                case ARCHON:
                    enemy_archon_in_sight=true;
                    ally_dmg -=2;
                    break;
            }
        }

        mines = rc.senseNearbyLocationsWithLead(20,2); // any larger than some other miner can probably get to it first
        mine_over_thresh_count = mines.length;

        Com.verifyTargets();

        if(rc.getHealth()<=5+ enemy_dmg){
            Com.decrementHeadcount();
        }
    }

    public static RobotInfo chooseAttackTarget(RobotInfo[] enemies) {
        RobotInfo best_target = null;
        float highest_priority = -1;
        for (RobotInfo enemy : enemies) {
            float priority = Math.abs(enemy.type.getDamage(enemy.level)) / (float) enemy.health;
            if (priority > highest_priority) {
                highest_priority = priority;
                best_target = enemy;
            }
        }
        return best_target;
    }
}
