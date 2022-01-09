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
                if (ally_miner_count >= 5) {
                    Com.decrementHeadcount();
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

        boolean moved = !rc.isMovementReady();
        if (!moved) {
            if (nearby_enemy_units.length != 0) {
                rc.setIndicatorString("moving away from enemy!");
                nav.navigate(nearby_enemy_units[0].location, false);
                moved = true;
            }
        }

        if (!moved) {
            int ally_miner_count = 0;
            int lr = 0;
            int ud = 0;
            for (RobotInfo nearby_unit : nearby_ally_units) {
                if (nearby_unit.getType() == RobotType.MINER) {
                    ally_miner_count++;
                    lr += (me.x - nearby_unit.location.x > 0) ? 1 : -1;
                    ud += (me.y - nearby_unit.location.y > 0) ? 1 : -1;
                }
            }
            if (ally_miner_count >= 5 && (lr < -2 || lr > 2 || ud < -2 || ud > 2)) {
                nav.navigate(me.translate((10 * lr), (10 * ud)), true);
                moved = true;
            }
        }

        if (!moved) {
            MapLocation[] mines = rc.senseNearbyLocationsWithLead(10);
            if (mines.length > 0) {
                nav.navigate(mines[rc.getID() % mines.length], true);
                rc.setIndicatorLine(me, mines[rc.getID() % mines.length], 10, 10, 10);
                moved = true;
            }
        }

        if (!moved && !acted) {
            nav.disperseAround(spawn_point, 0, 400,0);
        }
    }
}
