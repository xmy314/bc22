package Rushy_v9;

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
    static Random rng;

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

    // for all
    static MapLocation consistent_target;
    static int consistent_rounds;
    static boolean is_target_from_com=false;

    // just for miners
    static int mine_over_thresh_count;
    static int ally_miner_count;
    static MapLocation[] mines;

    // for archon and builders
    static int ideal_miner_count;
    static int ideal_builder_count;
    static int ideal_army_count;

    public Robot(RobotController r) throws GameActionException {
        rc = r;
        rng=new Random(rc.getID());
        max_X = rc.getMapWidth();
        max_Y = rc.getMapHeight();

        nav = new Nav(r);
        Com.init();
        Com.incrementHeadCount();

        spawn_point = rc.getLocation();

        ideal_miner_count = Math.max(10, (max_X * max_Y) / 10); // 16 for 13/turn/square.
        ideal_army_count = 2*ideal_miner_count;
        ideal_builder_count = ideal_miner_count;

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
                        ally_health+=nearby_unit.health;
                    }
                    break;
                case ARCHON:
                    ally_archon_in_sight = true;
                    ally_dmg+=1;
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
                    enemy_dmg+=1;
                    break;
            }
        }

        mines = rc.senseNearbyLocationsWithLead(20,(rc.getRoundNum()<200)?6:((rc.getRoundNum()<800)?11:16)); // any larger than some other miner can probably get to it first
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
            if(!rc.canAttack(enemy.location)) continue;
            float priority = Math.abs(enemy.type.getDamage(enemy.level)) / (float) enemy.health;
            if (priority > highest_priority) {
                highest_priority = priority;
                best_target = enemy;
            }
        }
        return best_target;
    }
}
