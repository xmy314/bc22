package Rushy_v8;

import battlecode.common.*;

public class Laboratory extends Robot {
    public Laboratory(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.
        if(rc.getTeamLeadAmount(rc.getTeam())>100 && rc.getTeamGoldAmount(rc.getTeam())<40){
            if(rc.canTransmute()) {
                rc.transmute();
            }
        }
    }
}
