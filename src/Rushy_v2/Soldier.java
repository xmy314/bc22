package Rushy_v2;

import battlecode.common.*;

public class Soldier extends Robot {

    public Soldier(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        // Try to attack someone

        if (nearby_enemy_units.length > 0) {
            MapLocation toAttack = chooseAttackTarget(nearby_enemy_units).location;
            if(protection_level>threat_level) {
                nav.optimalPlacementAround(toAttack, 13);
                if (rc.canAttack(toAttack)) {
                    rc.attack(toAttack);
                }
            }else{
                if (rc.canAttack(toAttack)) {
                    rc.attack(toAttack);
                }
                nav.navigate(spawn_point);
            }
        } else {
            safeMovement();
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
            if (rc.getLocation().isWithinDistanceSquared(consistent_target, 13) || consistent_rounds >= 30 || is_target_from_com && (Com.getFlags(consistent_target) & 0b101) == 0) {
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

        if (consistent_target == null) {
            consistent_target = nav.disperseAround(nearby_ally_units);
        }

        if (consistent_target != null) {
            nav.navigate(consistent_target);
        }
    }
}
