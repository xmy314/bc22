package examplefuncsplayer;

import battlecode.common.*;

public class Robot {

    static RobotController rc;

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

    public Robot(RobotController r) {
        rc = r;
    }

    public void takeTurn() throws GameActionException {
        // stuff that every bot does.
    }
}
