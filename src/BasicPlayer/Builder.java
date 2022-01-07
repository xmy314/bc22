package BasicPlayer;

import battlecode.common.*;

public class Builder extends Robot {
    public Builder(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.
    }
}
