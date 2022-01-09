package BasicPlayer;

import BasicPlayer.utils.Debug;
import battlecode.common.*;

public class Soldier extends Robot {

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
            if (rc.isMovementReady()) {
                nav.navigate(toAttack, true);
            }
        } else {
            MapLocation target = Com.getTarget(Com.ComFlag.PROTECT);
            if (target != null) {
                rc.setIndicatorString("protecting!" + target);
                nav.disperseAround(target, 20, 225,0b1001);
                return;
            }

            target = Com.getTarget(Com.ComFlag.EXAMINE);
            if (target != null) {
                rc.setIndicatorString("examining!" + target);
                nav.disperseAround(target, 9, 20,0b1001);
                return;
            }
        }
    }
}
