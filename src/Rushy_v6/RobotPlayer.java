package Rushy_v6;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public strictfp class RobotPlayer {

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


    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {
        RobotPlayer.rc = rc;
        Robot me = null;
        // separating the control of different type of robots.

        switch (rc.getType()) {
            case ARCHON:
                me = new Archon(rc);
                break;
            case LABORATORY:
                me = new Laboratory(rc);
                break;
            case WATCHTOWER:
                me = new Watchtower(rc);
                break;
            case MINER:
                me = new Miner(rc);
                break;
            case BUILDER:
                me = new Builder(rc);
                break;
            case SOLDIER:
                me = new Soldier(rc);
                break;
            case SAGE:
                me = new Sage(rc);
                break;
        }


        while (true) {
            try {
                me.takeTurn();
                Clock.yield();
            } catch (Exception e) {
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();
            }
        }
    }
}