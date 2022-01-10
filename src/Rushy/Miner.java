package Rushy;

import battlecode.common.*;

public class Miner extends Robot {

    int mine_over_thresh_count;
    MapLocation[] mines;

    public Miner(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        MapLocation me = rc.getLocation();

        if(rc.isActionReady()) {
            mine_over_thresh_count=0;
            mines = rc.senseNearbyLocationsWithLead(20); // any larger than some other miner can probably get to it first
            for (MapLocation mine : mines) {
                if (rc.senseLead(mine) >= 15) {
                    mines[mine_over_thresh_count++]=mine;
                }
            }
        }

        if (rc.isActionReady()) {
            if (Com.getHeadcount(RobotType.MINER)>10 && rc.getRoundNum() < 1800) {
                if (rc.senseLead(me) == 0) {
                    int ally_miner_count = 0;
                    for (RobotInfo nearby_unit : nearby_ally_units) {
                        if (nearby_unit.getType() == RobotType.MINER) {
                            ally_miner_count++;
                            if (ally_miner_count >= 3*mine_over_thresh_count) {
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
            MapLocation ref=nearby_enemy_units[0].getLocation();
            MapLocation loc = rc.getLocation();
            nav.navigate(loc.translate(loc.x-ref.x,loc.y-ref.y));
            return;
        }

        // find a nearby mine if wasn't able to mine prior trying to move
        if(mine_over_thresh_count>0) {
            nav.navigate(mines[rc.getID() % mine_over_thresh_count]);
        }


        // avoid other miners.
        nav.disperseAround(nearby_ally_units);
    }
}
