package Rushy_v8;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

abstract class BFS {
    RobotController rc;
    public BFS(RobotController r){rc=r;}

    abstract public Direction BFSTo(MapLocation target_location);
}
