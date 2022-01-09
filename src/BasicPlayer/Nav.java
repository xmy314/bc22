package BasicPlayer;

import battlecode.common.*;

public class Nav {
    RobotController rc;
    boolean clockwise_rotation=false;
    int idle_counter=0;

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

    static final Direction[] cardinal_direction = {
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST,
    };

    public Nav(RobotController r) throws GameActionException {
        rc = r;
        rubble_count = new int[rc.getMapHeight() * rc.getMapWidth()];
    }

    public boolean navigate(MapLocation reference, boolean go_to) throws GameActionException {
        if (!rc.isMovementReady()) {
            return false;
        }

        MapLocation me = rc.getLocation();

        if (rc.getType() == RobotType.WATCHTOWER) {
            rc.setIndicatorLine(me, reference, 0, 0, 100);
        }

        int ref_distance = me.distanceSquaredTo(reference);
        int benefit_scalar = (go_to) ? 1 : -1;

        double record_benefit = -10;
        Direction record_direction = null;

        for (int i = 0; i < 8; i++) {
            if (rc.canMove(directions[i])) {
                MapLocation n_loc = me.add(directions[i]);
                double benefit = benefit_scalar * (ref_distance - n_loc.distanceSquaredTo(reference)) / (1 + 0.1 * rc.senseRubble(n_loc));
                if (n_loc.x == 0 || n_loc.y == 0 || n_loc.x == rc.getMapWidth() || n_loc.y == rc.getMapHeight()) {
                    if (benefit > 0) {
                        benefit *= 0.1;
                    } else {
                        benefit -= 5;
                    }
                }

                if (benefit > record_benefit) {
                    record_direction = directions[i];
                    record_benefit = benefit;

                }
            }
        }

        if (record_direction != null) {
            rc.move(record_direction);
            return true;
        }
        return false;
    }

    public boolean disperseAround(MapLocation reference, int inner_radius, int outer_radius, int settle_mask) throws GameActionException {
        if (!rc.isMovementReady()) return false;
        MapLocation me = rc.getLocation();
        int reference_distance = me.distanceSquaredTo(reference);

        if (reference_distance > outer_radius) {
            return navigate(reference, true);
        } else if (reference_distance < inner_radius) {
            return navigate(reference, false);
        } else if ((settle_mask & (1 << (2 * (me.x & 1) + (me.y & 1)))) != 0) {
            for (Direction direction : directions) {
                MapLocation n_loc = me.add(direction);
                if ((settle_mask & (1 << (2 * (n_loc.x & 1) + (n_loc.y & 1)))) != 0 && rc.canMove(direction)) {
                    rc.move(direction);
                    return true;
                }
            }
            boolean succeed = navigate(rotateAround(reference, me, 2f*(clockwise_rotation?-1:1)), true);
            if(!succeed){
                idle_counter++;
            }
            if (idle_counter>=5){
                idle_counter=0;
                clockwise_rotation=!clockwise_rotation;
                return navigate(rotateAround(reference, me, 2f*(clockwise_rotation?-1:1)), true);
            }else{
                return false;
            }
        } else {
            return true;
        }
    }

    private MapLocation rotateAround(MapLocation origin, MapLocation toRotate, float offset) {
        return new MapLocation(origin.x - Math.round((toRotate.y - origin.y) * offset), origin.y + Math.round((toRotate.x - origin.x) * offset));
    }


}
