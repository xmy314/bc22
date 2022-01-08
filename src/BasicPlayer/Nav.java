package BasicPlayer;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class Nav {
    RobotController rc;

    int[] rubble_count;

    static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };

    public Nav(RobotController r) throws GameActionException {
        rc = r;
        rubble_count = new int[rc.getMapHeight()*rc.getMapWidth()];
    }

    public void navigate(MapLocation reference, boolean go_to) throws GameActionException {
        if (!rc.isActionReady()) {
            return;
        }

        MapLocation me = rc.getLocation();

        int ref_distance = me.distanceSquaredTo(reference);
        int benefit_scalar = (go_to)?1:-1;

        double record_benefit = -10;
        Direction record_direction = null;

        for (int i = 0; i < 8; i++) {
            if (rc.canMove(directions[i])) {
                MapLocation n_loc = me.add(directions[i]);
                double benefit = benefit_scalar*(ref_distance-n_loc.distanceSquaredTo(reference)) / (1 + 0.1 * rc.senseRubble(n_loc));
                if (benefit > record_benefit) {
                    record_direction = directions[i];
                    record_benefit = benefit;
                }
            }
        }

        if(record_direction!=null) {
            rc.move(record_direction);
        }
    }

    public void disperseAround(MapLocation reference, int inner_radius, int outer_radius) throws GameActionException {
        MapLocation me = rc.getLocation();
        int reference_distance = me.distanceSquaredTo(reference);

        if(reference_distance>outer_radius){
            navigate(reference,true);
        }else if(reference_distance<inner_radius){
            navigate(reference,false);
        }else if(((me.x^me.y)&1)==1 ){
            navigate(rotateAround(reference,me),true);
        }
    }

    private MapLocation rotateAround(MapLocation origin, MapLocation toRotate){
        return new MapLocation(origin.x-(toRotate.y-origin.y),origin.y+(toRotate.x-origin.x));
    }


}
