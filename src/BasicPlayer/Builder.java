package BasicPlayer;

import battlecode.common.*;

public class Builder extends Robot {
    RobotInfo current_project=null;

    public Builder(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.

        if(rc.getLocation().distanceSquaredTo(spawn_point)<16 || (rc.getLocation().x&rc.getLocation().y&1)!=1) {
            rc.setIndicatorString("trying to move");
            nav.disperseAround(spawn_point, 49,7000,0b1000);// entire map;
        }else {
            rc.setIndicatorString("trying to build");
            if (current_project != null) {
                current_project = rc.senseRobotAtLocation(current_project.location);
                if (current_project != null && current_project.mode != RobotMode.PROTOTYPE) {
                    current_project = null;
                }
            }

            if (current_project == null) {
                RobotInfo[] units_in_action_range = rc.senseNearbyRobots(5, rc.getTeam());
                for (RobotInfo unit : units_in_action_range) {
                    if (unit.mode == RobotMode.PROTOTYPE) {
                        current_project = unit;
                        break;
                    }
                }
            }

            if (current_project != null) {
                if (rc.canRepair(current_project.location)) {
                    rc.repair(current_project.location);
                }
            } else {
                if (rc.getTeamLeadAmount(rc.getTeam()) > 1000) {
                    for (Direction direction : directions) {
                        if (rc.canBuildRobot(RobotType.WATCHTOWER, direction)) {
                            rc.buildRobot(RobotType.WATCHTOWER, direction);
                            current_project = rc.senseRobotAtLocation(rc.adjacentLocation(direction));
                            break;
                        }
                    }
                }
            }
        }
    }
}
