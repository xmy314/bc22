package BasicPlayer;

import battlecode.common.*;

public class Soldier extends Robot {

    private MapLocation target;

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
                com.setTarget(Com.ComFlag.SUPPORT,toAttack);
                nav.navigate(toAttack, true);
            }
        } else {
            MapLocation target = com.getTarget(Com.ComFlag.SUPPORT);
            if(target!=null) {
                nav.navigate(target, true);
            }
        }
    }
}
