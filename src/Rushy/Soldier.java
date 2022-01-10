package Rushy;

import battlecode.common.*;

public class Soldier extends Robot {

    MapLocation target_location;

    public Soldier(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        // Try to attack someone

        if (nearby_enemy_units.length > 0) {
            MapLocation toAttack = decideTarget(nearby_enemy_units).location;

            if (rc.canAttack(toAttack)) {
                rc.attack(toAttack);
            }
            nav.navigate(toAttack);
        } else{
            // check if there is any mission by communication
            if(target_location!=null){
                if (rc.canSenseLocation(target_location) && nearby_enemy_units.length==0 ){
                    target_location=null;
                }
            }

            if (target_location==null) {
                if (Com.getHeadcount(RobotType.SOLDIER) < 20) {
                    switch (rc.getID() % 3) {
                        case 0:
                            target_location = new MapLocation(max_X - spawn_point.x, max_Y - spawn_point.y);
                            break;
                        case 1:
                            target_location = new MapLocation(max_X - spawn_point.x, spawn_point.y);
                            break;
                        case 2:
                            target_location = new MapLocation(spawn_point.x, max_Y - spawn_point.y);
                            break;
                    }
                }
            }

            if(target_location==null){
                target_location = Com.getTarget(Com.ComFlag.ATTACK);
                if (target_location != null) {
                    rc.setIndicatorString("attacking!" + target_location);
                }
            }

            if(target_location!=null) {
                nav.navigate(target_location);
            }else{
                nav.disperseAround(nearby_ally_units);
            }
        }
    }
}
