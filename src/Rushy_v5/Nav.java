package Rushy_v5;

import battlecode.common.*;

public class Nav {
    RobotController rc;
    BFS bfs;


    // the destruction from vortex is neglected as it's not worth dealing with.

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
        switch (rc.getType().visionRadiusSquared){
            case  34:
            case  20:
                bfs=new BFS20(rc);
        }
    }


    public boolean moveWrapper(Direction best_dir) throws GameActionException {
        if (rc.canMove(best_dir)) {
            rc.move(best_dir);
            return true;
        }
        return false;
    }

    public boolean navigate(MapLocation reference) throws GameActionException {
        if (!rc.isMovementReady()) {return false;}

        Direction bestDir = bfs.BFSTo(reference);

        if (bestDir != null) { // TODO: i don't get why it might be null.
            return moveWrapper(bestDir);
        } else {
            return false;
        }
    }
}
