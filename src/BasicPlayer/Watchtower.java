package BasicPlayer;

import battlecode.common.*;

public class Watchtower extends Robot {
    public Watchtower(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.

        if (nearby_enemy_units.length > 0) {
            MapLocation toAttack = decideTarget(nearby_enemy_units).location;
            if (rc.canAttack(toAttack)) {
                rc.attack(toAttack);
            }
        }
    }
}
