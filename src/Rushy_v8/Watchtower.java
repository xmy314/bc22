package Rushy_v8;

import battlecode.common.*;

public class Watchtower extends Robot {

    int moved_for_attack = 0;
    int peace_count_down=0;

    public Watchtower(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.

        // synchronized is way better.
        if(rc.getRoundNum() % 60 == 0){
            moved_for_attack=0;
        }

        int move_threshold_round=2;

        if(nearby_enemy_units.length>0){
            // if it should push, push.

            if(rc.getMode()==RobotMode.TURRET){
                if(ally_dmg - enemy_dmg >10 && (rc.getLocation().x&1)!=(rc.getLocation().y&1) &&moved_for_attack<move_threshold_round){
                    if(rc.isTransformReady()){
                        rc.transform();
                    }
                }else{
                    RobotInfo toAttack = chooseAttackTarget(nearby_enemy_units);
                    if (toAttack!=null) rc.attack(toAttack.location);
                }
            }else if(rc.getMode()==RobotMode.PORTABLE){
                if(moved_for_attack<2){
                    if(nav.navigate(nearby_enemy_units[0].location)) moved_for_attack++;
                }else if(rc.isTransformReady()){
                    rc.transform();
                }
            }
            peace_count_down=10;
        }else{
            peace_count_down--;
            if(peace_count_down<=0) {
                // in the safe zone, make sure minimum protect exists and continue walking.
                if (rc.getMode() == RobotMode.TURRET) {
                    if (rc.isTransformReady()) {
                        rc.transform();
                    }
                } else {
                    safeMovement();
                }
            }
        }
    }

    public void safeMovement() throws GameActionException{
        if(consistent_target!=null) {
            consistent_rounds++;
        }

        if(!rc.isMovementReady()) return;

        if(consistent_target!=null && debugOn){
            if(debugOn) rc.setIndicatorLine(rc.getLocation(), consistent_target, 200, 0, 0);
        }

        // check if there is any mission by communication
        if(consistent_target!=null){
            if (rc.getLocation().isWithinDistanceSquared(consistent_target,13) || consistent_rounds>=30 || is_target_from_com && (Com.getFlags(consistent_target)&0b101)==0){
                consistent_target=null;
                consistent_rounds=0;
                is_target_from_com=false;
            }
        }

        if(consistent_target == null) {
            consistent_target = Com.getTarget(0b001,0b001,6); // military support
            if (consistent_target!=null) {
                is_target_from_com = true;
            }
        }

        if(consistent_target == null) {
            consistent_target = Com.getTarget(0b100,0b100,12); // pioneer
            if (consistent_target!=null) {
                is_target_from_com = true;
            }
        }

        if (consistent_target != null) {
            nav.navigate(consistent_target);
        }
    }

}
