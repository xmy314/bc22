package BasicPlayer;

import battlecode.common.*;

public class Miner extends Robot {
    public Miner(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        if (!rc.isActionReady()) {
            return; // or use the computation power for other stuff.
        }

        // Try to mine on squares around it.
        MapLocation me = rc.getLocation();
        if (rc.getRoundNum() < 1800) {
            if (rc.senseLead(me) == 0) {
                int ally_miner_count = 0;
                for (RobotInfo nearby_unit : nearby_ally_units) {
                    if (nearby_unit.getType() == RobotType.MINER) {
                        ally_miner_count++;
                    }
                }
                if (ally_miner_count > 4) { // number of squares the robot can see over 4. 4 is number of squares a robot can mine with no rubble.
                    rc.disintegrate();
                }
            }
        }

        boolean acted = !rc.isActionReady();
        for (int dx = -1; dx <= 1 && !acted; dx++) {
            for (int dy = -1; dy <= 1 && !acted; dy++) {
                MapLocation mineLocation = new MapLocation(me.x + dx, me.y + dy);
                // Notice that the Miner's action cool down is very low.
                // You can mine multiple times per turn!

                if (rc.canMineGold(mineLocation)) {
                    rc.mineGold(mineLocation);
                    acted = true;
                }
                if (rc.canMineLead(mineLocation) && rc.senseLead(mineLocation) > 1) {
                    rc.mineLead(mineLocation);
                    acted = true;
                }
            }
        }

        boolean moved=!rc.isMovementReady();
        if (!moved) {
            if (nearby_enemy_units.length != 0) {
                rc.setIndicatorString("moving away from enemy!");
                nav.navigate(nearby_enemy_units[0].location, false);
                moved = true;
            }
        }

        if (!moved) {
            MapLocation[] mines = rc.senseNearbyLocationsWithLead(rc.getType().visionRadiusSquared);
            for(MapLocation mine:mines){
                if(!rc.canSenseLocation(mine)){
                    continue;
                }
                int lead_count=rc.senseLead(mine);
                if(lead_count>10&&lead_count>me.distanceSquaredTo(mine)){
                    rc.setIndicatorString("moving to ore!");
                    nav.navigate(mine,true);
                    moved=true;
                    break;
                }
            }
        }

        if(!moved && !acted){
            rc.setIndicatorString("moving around basically randomly!");
            nav.disperseAround(spawn_point,0,100);
            moved=true;
        }
    }
}
