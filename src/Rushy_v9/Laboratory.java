package Rushy_v9;

import battlecode.common.*;

public class Laboratory extends Robot {
    public Laboratory(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.
        if(rc.getTeamLeadAmount(rc.getTeam())>100 && rc.getTeamGoldAmount(rc.getTeam())<(rc.getTransmutationRate()<=8?100:25)+rc.getTeamGoldAmount(rc.getTeam().opponent())){
            if(rc.canTransmute()) {
                rc.transmute();
            }
        }
    }
}
