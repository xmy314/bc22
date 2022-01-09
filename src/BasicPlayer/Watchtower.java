package BasicPlayer;

import battlecode.common.*;

public class Watchtower extends Robot {

    int moved_for_attack = 0;

    public Watchtower(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.

        if(rc.getRoundNum() % 100 == 0){
            moved_for_attack=0;
        }

        int move_threshold_round=3;
        int move_threshold_protection = 2;

        if(nearby_enemy_units.length>0){
            // if it should push, push.

            if(rc.getMode()==RobotMode.TURRET){
                if(protection_level>move_threshold_protection && (rc.getLocation().x&1)!=(rc.getLocation().y&1) &&moved_for_attack<move_threshold_round){
                    if(rc.isTransformReady()){
                        rc.transform();
                    }
                }else{
                    MapLocation toAttack = decideTarget(nearby_enemy_units).location;
                    rc.setIndicatorLine(rc.getLocation(),toAttack,100,0,0);
                    if (rc.canAttack(toAttack)) {
                        rc.attack(toAttack);
                    }
                }
            }else if(rc.getMode()==RobotMode.PORTABLE){
                if(moved_for_attack<move_threshold_round) {
                    MapLocation target = decideTarget(nearby_enemy_units).location;
                    moved_for_attack+=nav.disperseAround(target, 20, 225,0b1001)?1:0;
                }else{
                    if(rc.isTransformReady()){
                        rc.transform();
                    }
                }
            }
        }else{
            // in the safe zone, make sure minimum protect exists and continue walking.
            if(rc.getMode()==RobotMode.PORTABLE && protection_level<=move_threshold_protection){
                if(rc.isTransformReady()){
                    rc.transform();
                }
            }else if(rc.getMode()==RobotMode.TURRET && protection_level>move_threshold_protection){
                if(rc.isTransformReady()){
                    rc.transform();
                }
            }else{
                MapLocation target = Com.getTarget(Com.ComFlag.ATTACK);
                if (target != null) {
                    nav.disperseAround(target, 0, 225,0b1001);
                    return;
                }

                target = Com.getTarget(Com.ComFlag.EXAMINE);
                if (target != null) {
                    nav.disperseAround(target, 0, 20,0b1001);
                    return;
                }

                if(rc.getID()%3==0) {
                    nav.disperseAround(new MapLocation(max_X-spawn_point.x,spawn_point.y), 9, 20,0b1001);
                }else if(rc.getID()%3==1){
                    nav.disperseAround(new MapLocation(spawn_point.x,max_Y-spawn_point.y), 9, 20,0b1001);
                }else{
                    nav.disperseAround(new MapLocation(max_X-spawn_point.x,max_Y-spawn_point.y), 9, 20,0b1001);
                }
            }
        }
    }
}
