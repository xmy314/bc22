package BasicPlayer;

import BasicPlayer.utils.Debug;
import battlecode.common.*;

public class Miner extends Robot {
    public Miner(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        MapLocation me = rc.getLocation();

        if (rc.isActionReady()) {
            if (rc.getRoundNum() < 1800) {
                if (rc.senseLead(me) == 0) {
                    int ally_miner_count = 0;
                    for (RobotInfo nearby_unit : nearby_ally_units) {
                        if (nearby_unit.getType() == RobotType.MINER) {
                            ally_miner_count++;
                            if (ally_miner_count >= 5) {
                                Com.decrementHeadcount();
                                rc.disintegrate();
                            }
                        }
                    }
                }
            }

            for (int dx = 0; dx < 9; dx++) {
                MapLocation mineLocation = new MapLocation(me.x - 1 + dx / 3, me.y - 1 + dx % 3);
                // Notice that the Miner's action cool down is very low.
                // You can mine multiple times per turn!

                if (rc.canMineGold(mineLocation)) {
                    rc.mineGold(mineLocation);
                    break;
                }
                if (rc.canMineLead(mineLocation) && rc.senseLead(mineLocation) > 1) {
                    rc.mineLead(mineLocation);
                    break;
                }
            }
        }

        // Try to mine on squares around it.
        movement();
    }

    public void movement() throws GameActionException{
        if(!rc.isMovementReady()) return;

        // avoid enemy
        if (nearby_enemy_units.length != 0) {
            rc.setIndicatorString("moving away from enemy!");
            nav.navigate(nearby_enemy_units[0].location, false);
            return;
        }

        // avoid other miners.
        int ally_miner_count = 0;
        int t=0;
        int lr = 0;
        int ud = 0;
        for (RobotInfo nearby_unit : nearby_ally_units) {
            t++;
            if (nearby_unit.getType() == RobotType.MINER) {
                ally_miner_count++;
                lr += (rc.getLocation().x > nearby_unit.location.x ) ? 1 : -1;
                ud += (rc.getLocation().y > nearby_unit.location.y ) ? 1 : -1;
            }
            if(t>10){
                break;
            }
        }
        if (ally_miner_count >= 5 && (lr < -2 || lr > 2 || ud < -2 || ud > 2)) {
            nav.navigate(rc.getLocation().translate((10 * lr), (10 * ud)), true);
            return;
        }

        // find a nearby mine
        MapLocation[] mines = rc.senseNearbyLocationsWithLead(20); // its vision radius
        for (MapLocation mine : mines) {
            if (rc.senseLead(mine)>=15) {
                nav.navigate(mine,true);
                return;
            }
        }
    }
}
