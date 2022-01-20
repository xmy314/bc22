package Rushy_v8;

import battlecode.common.*;

public class Miner extends Robot {

    private enum MINER_ACTION_STATES{MINE,EXPLORE,HEAL,STALL,EVADE}
    private MINER_ACTION_STATES current_state=MINER_ACTION_STATES.STALL;

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

        if (Com.getHeadcount(RobotType.MINER) >= 10 && rc.getRoundNum() < 1840) {
            if (rc.senseLead(rc.getLocation()) == 0) {
                if (ally_miner_count > 3 * mine_over_thresh_count) {
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

        int lowest_rubble = rc.senseRubble(closest_mine);
        MapLocation opt_loc = closest_mine;
        for (Direction direction : directions) {
            MapLocation n_loc = closest_mine.add(direction);
            if (n_loc.x < 0 || n_loc.y < 0 | n_loc.x >= max_X || n_loc.y >= max_Y || !rc.canSenseLocation(n_loc)) continue;
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
        if (debugOn && consistent_target != null ) rc.setIndicatorLine(rc.getLocation(), consistent_target, 0, 200, 0);
        if (!rc.isMovementReady()) return;

        if(rc.getHealth()<20) current_state=MINER_ACTION_STATES.HEAL;
        switch (current_state){
            case MINE:
                if((Com.getFlags(consistent_target) & 0b011) != 0b010){
                    current_state = MINER_ACTION_STATES.STALL;
                    consistent_target=null;
                }
                break;
            case EXPLORE:
                if((Com.getFlags(consistent_target) & 0b101) != 0b100){
                    current_state = MINER_ACTION_STATES.STALL;
                    consistent_target=null;
                }
                break;
            case HEAL:
                if(rc.getHealth()==40){
                    current_state = MINER_ACTION_STATES.STALL;
                    consistent_target=null;
                }
                break;
            case EVADE:
                if(enemy_dmg<=0){
                    current_state = MINER_ACTION_STATES.STALL;
                    consistent_target=null;
                }
        }

        if(enemy_dmg >0 && current_state != MINER_ACTION_STATES.EVADE){
            if(current_state != MINER_ACTION_STATES.STALL && consistent_target.x>=0 && consistent_target.y>=0 && consistent_target.x<max_X && consistent_target.y<max_Y)Com.setTarget(0b1,0b1,consistent_target);
            MapLocation current_location=rc.getLocation();
            MapLocation to_be_evade=nearby_enemy_units[0].location;
            consistent_target=current_location.translate(10*(current_location.x-to_be_evade.x),10*(current_location.y-to_be_evade.y));
            current_state = MINER_ACTION_STATES.EVADE;
        }

        // find a nearby mine if wasn't able to mine prior trying to move
        if (current_state==MINER_ACTION_STATES.STALL) {
            if (ally_miner_count<3 && mine_over_thresh_count>0 || mine_over_thresh_count > ally_miner_count) {
                consistent_target = mines[rc.getID() % mine_over_thresh_count];
                current_state = MINER_ACTION_STATES.MINE;
            }
        }

        if (current_state==MINER_ACTION_STATES.STALL) {
            if (ally_miner_count >= 5 || Com.getHeadcount(RobotType.MINER)<10) {
                consistent_target = Com.getTarget(0b101,0b100,12); // pioneer, but don't run to enemy
                if (consistent_target != null) {
                    current_state = MINER_ACTION_STATES.EXPLORE;
                }
            }
        }

        if (current_state==MINER_ACTION_STATES.STALL) {
            consistent_target = Com.getTarget(0b011,0b010,12); // mine, don't run to enemy
            if (consistent_target != null) {
                current_state = MINER_ACTION_STATES.MINE;
            }
        }

        if (current_state!=MINER_ACTION_STATES.STALL && consistent_target != null) {
            nav.navigate(consistent_target);
        }
    }
}
