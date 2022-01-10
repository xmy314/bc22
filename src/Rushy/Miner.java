package Rushy;

import Rushy.utils.Debug;
import battlecode.common.*;

public class Miner extends Robot {

    int mine_over_thresh_count;
    int ally_miner_count;
    MapLocation[] mines;

    public Miner(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        mines = rc.senseNearbyLocationsWithLead(20); // any larger than some other miner can probably get to it first
        mine_over_thresh_count=mines.length;

        ally_miner_count = 0;
        for (RobotInfo nearby_unit : nearby_ally_units) {
            if (nearby_unit.getType() == RobotType.MINER) {
                ally_miner_count++;
            }
        }

        if (rc.isActionReady()) {
            if (Com.getHeadcount(RobotType.MINER)>100 && rc.getRoundNum() < 1800) {
                if (rc.senseLead(rc.getLocation()) == 0) {
                    if (ally_miner_count >= Math.min(3,3*mine_over_thresh_count)) {
                        Com.decrementHeadcount();
                        rc.disintegrate();
                    }
                }
            }

            boolean mined=false;
            for (int dx = -1; dx <=1; dx++) {
                for (int dy = -1; dy <=1; dy++) {
                    MapLocation mineLocation = rc.getLocation().translate(dx,dy);
                    // Notice that the Miner's action cool down is very low.
                    // You can mine multiple times per turn!

                    while (rc.canMineGold(mineLocation)) {
                        rc.mineGold(mineLocation);
                        mined = true;
                    }
                    while (rc.canMineLead(mineLocation) && rc.senseLead(mineLocation) > 1) {
                        rc.mineLead(mineLocation);
                        rc.setIndicatorDot(rc.getLocation(), 0, 200, 0);
                        Debug.p("mined");
                        mined = true;
                    }
                }
            }
            if(!mined){
                rc.setIndicatorDot(rc.getLocation(),0,0,200);
                Debug.p("no mine");
            }

        }else{
            rc.setIndicatorDot(rc.getLocation(),200,0,0);
            Debug.p("can't mine");
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

        if(ally_miner_count>2){
            // avoid other miners.
            nav.disperseAround(nearby_ally_units);
        }

        // find a nearby mine if wasn't able to mine prior trying to move
        if(mine_over_thresh_count>ally_miner_count) {
            nav.navigate(mines[rc.getID() % mine_over_thresh_count]);
        }

        nav.disperseAround(nearby_ally_units);

    }
}
