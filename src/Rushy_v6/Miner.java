package Rushy_v6;

import battlecode.common.*;

public class Miner extends Robot {

    public Miner(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        action();

        if(rc.isActionReady()) {
            movement();
        }
    }

    public void action() throws GameActionException {
        if (!rc.isActionReady()) return;
        if (mine_over_thresh_count==0) return;

        if (Com.getHeadcount(RobotType.MINER) > 30 && rc.getRoundNum() < 1300) {
            if (rc.senseLead(rc.getLocation()) == 0) {
                if (ally_miner_count >= 3 * mine_over_thresh_count) {
                    Com.decrementHeadcount();
                    rc.disintegrate();
                }
            }
        }

        int closest_dist = 100;
        MapLocation closest_mine=null;
        for(int i=0;i<mine_over_thresh_count;i++){
            int n_dist = rc.getLocation().distanceSquaredTo(mines[i]);
            if(n_dist<closest_dist){
                closest_dist=n_dist;
                closest_mine=mines[i];
            }
        }
        if(closest_mine==null) return;
        if(rc.getLocation().distanceSquaredTo(closest_mine)>10) return;

        int lowest_rubble = rc.senseRubble(rc.getLocation());
        MapLocation opt_loc = rc.getLocation();
        for (Direction direction : directions) {
            MapLocation n_loc = closest_mine.add(direction);
            if (n_loc.x < 0 || n_loc.y < 0 | n_loc.x >= max_X || n_loc.y >= max_Y) continue;
            int rubble = rc.senseRubble(n_loc);
            if (rubble < lowest_rubble) {
                opt_loc = n_loc;
                lowest_rubble = rubble;
            }
        }
        nav.navigate(opt_loc);

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                MapLocation mineLocation = rc.getLocation().translate(dx, dy);
                // You can mine multiple times per turn!

                while (rc.canMineGold(mineLocation)) {
                    rc.mineGold(mineLocation);
                }
                while (rc.canMineLead(mineLocation) && (rc.senseLead(mineLocation) > 1 || ((enemy_archon_in_sight||enemy_dmg>ally_dmg) && !ally_archon_in_sight)  )) {
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

        if(enemy_dmg >0 || (Com.getFlags(rc.getLocation())&1)!=0){
            if(consistent_target!=null)Com.setTarget(0b1,0b1,consistent_target);
            if(nearby_enemy_units.length>0) {
                consistent_target = rc.getLocation().translate(rc.getLocation().x- nearby_enemy_units[0].location.x,rc.getLocation().y- nearby_enemy_units[0].location.y);
            }else{
                consistent_target = spawn_point;
            }
            is_target_from_com = false;
        }

        // find a nearby mine if wasn't able to mine prior trying to move
        if (consistent_target == null) {
            if (ally_miner_count<3 && mine_over_thresh_count>0 || mine_over_thresh_count > ally_miner_count) {
                consistent_target = mines[rc.getID() % mine_over_thresh_count];
            }
        }

        if (consistent_target == null) {
            if (ally_miner_count > 5 || Com.getHeadcount(RobotType.MINER)<10) {
                consistent_target = Com.getTarget(0b101,0b100,12); // pioneer, but don't run to enemy
                if (consistent_target != null) {
                    is_target_from_com = true;
                }
            }
        }


        if (consistent_target == null) {
            consistent_target = Com.getTarget(0b011,0b010,12); // mine, don't run to enemy
            if (consistent_target != null) {
                is_target_from_com = true;
            }
        }

        if (consistent_target != null) {
            nav.navigate(consistent_target);
        }
    }
}
