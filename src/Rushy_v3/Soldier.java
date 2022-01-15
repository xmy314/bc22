package Rushy_v3;

import battlecode.common.*;

public class Soldier extends Robot {

    int combat_round = 0;

    public Soldier(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();


        // Try to attack someone

        if (nearby_enemy_units.length > 0) {
            combat();
        }else {
            safeMovement();
        }
    }

    public void combat() throws GameActionException {
        MapLocation toAttack = chooseAttackTarget(nearby_enemy_units).location;
        if(ally_dmg*ally_health>enemy_dmg*enemy_health) {
            Direction best_dir=null;
            int best_rubble=1000;
            for(Direction dir:directions){
                if (rc.adjacentLocation(dir).isWithinDistanceSquared(toAttack,13) && rc.onTheMap(rc.adjacentLocation(dir))) {
                    int n_rubble = rc.senseRubble(rc.adjacentLocation(dir));
                    if(n_rubble<best_rubble){
                        best_rubble=n_rubble;
                        best_dir=dir;
                    }
                }
            }
            nav.moveWrapper(best_dir);
            if (rc.canAttack(toAttack)) {
                rc.attack(toAttack);
            }
        }else{
            if (rc.canAttack(toAttack)) {
                rc.attack(toAttack);
            }
            nav.navigate(spawn_point);
        }
    }

    public void safeMovement() throws GameActionException {
        if (consistent_target != null) {
            consistent_rounds++;
        }

        if (!rc.isMovementReady()) return;

        if (consistent_target != null && debugOn) {
            if(debugOn) rc.setIndicatorLine(rc.getLocation(), consistent_target, 200, 0, 0);
        }

        // check if there is any mission by communication
        if (consistent_target != null) {
            if (    rc.getLocation().isWithinDistanceSquared(consistent_target, 13) ||
                    consistent_rounds >= 30 ||
                    is_target_from_com && (Com.getFlags(consistent_target) & 0b101) == 0) {
                consistent_target = null;
                consistent_rounds = 0;
                is_target_from_com = false;
            }
        }

        if (consistent_target == null) {
            consistent_target = Com.getTarget(0b001,0b001,6); // military support
            if (consistent_target != null) {
                is_target_from_com = true;
            }
        }

        if (consistent_target == null) {
            consistent_target = Com.getTarget(0b100,0b100,12); // pioneer
            if (consistent_target != null) {
                is_target_from_com = true;
            }
        }

        if (consistent_target != null) {
            nav.navigate(consistent_target);
        }
    }
}
