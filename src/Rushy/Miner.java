package Rushy;

import Rushy.utils.Debug;
import battlecode.common.*;

public class Miner extends Robot {

    public Miner(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        action();

        // Try to mine on squares around it.
        movement();
    }

    public void action() throws GameActionException {
        if (!rc.isActionReady()) return;

        if (Com.getHeadcount(RobotType.MINER) > 30 && rc.getRoundNum() < 1800) {
            if (rc.senseLead(rc.getLocation()) == 0) {
                if (ally_miner_count >= 3 * mine_over_thresh_count) {
                    Com.decrementHeadcount();
                    rc.disintegrate();
                }
            }
        }

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                MapLocation mineLocation = rc.getLocation().translate(dx, dy);
                // Notice that the Miner's action cool down is very low.
                // You can mine multiple times per turn!

                while (rc.canMineGold(mineLocation)) {
                    rc.mineGold(mineLocation);
                }
                while (rc.canMineLead(mineLocation) && rc.senseLead(mineLocation) > 1) {
                    rc.mineLead(mineLocation);
                }
                if (!rc.isActionReady()) {
                    return;
                }
            }
        }
    }


    public void movement() throws GameActionException {
        if (consistent_target != null) {
            consistent_rounds++;
        }

        if (!rc.isMovementReady()) return;

        if (consistent_target != null && debugOn) {
            rc.setIndicatorLine(rc.getLocation(), consistent_target, 0, 200, 0);
        }

        if (consistent_target != null) {
            if (rc.getLocation().isWithinDistanceSquared(consistent_target, 2) || consistent_rounds >= 30 || is_target_from_com && (Com.getFlags(consistent_target) & 0b110) == 0) {
                consistent_target = null;
                consistent_rounds = 0;
                is_target_from_com = false;
            }
        }

        // avoid enemy
        if (threat_level != 0) {
            MapLocation ref = nearby_enemy_units[0].getLocation();
            MapLocation loc = rc.getLocation();
            consistent_target = loc.translate(loc.x - ref.x, loc.y - ref.y);
        }

        if (consistent_target == null) {
            if (ally_miner_count > 5 || Com.getHeadcount(RobotType.MINER)<20) {
                consistent_target = Com.getTarget(0b100); // pioneer
                if (consistent_target != null) {
                    is_target_from_com = true;
                }
            }
        }

        if (consistent_target == null) {
            consistent_target = Com.getTarget(0b010); // mine
            if (consistent_target != null) {
                is_target_from_com = true;
            }
        }

        if (consistent_target == null) {
            if (ally_miner_count > 2) {
                // avoid other miners.
                consistent_target = nav.disperseAround(nearby_ally_units);
            }
        }

        // find a nearby mine if wasn't able to mine prior trying to move
        if (consistent_target == null) {
            if (mine_over_thresh_count > ally_miner_count) {
                consistent_target = mines[rc.getID() % mine_over_thresh_count];
            }
        }

        if (consistent_target != null) {
            nav.navigate(consistent_target);
        }

    }
}
