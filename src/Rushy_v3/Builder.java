package Rushy_v3;

import battlecode.common.*;

public class Builder extends Robot {
    RobotInfo current_project = null;

    public Builder(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.

        if (  ally_archon_in_sight || (current_project == null && (nearby_ally_units.length > 30 && (rc.getLocation().x & rc.getLocation().y & 1) != 1) ) ) {
            if(debugOn) rc.setIndicatorString("trying to move");
            movement();
        } else {
            action();
        }

    }

    public void action() throws GameActionException {
        if(debugOn) rc.setIndicatorString("trying to build");
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

        if (current_project == null) {
            if (rc.getTeamLeadAmount(rc.getTeam()) > 180) {
                for (Direction direction : directions) {
                    if (rc.canBuildRobot(RobotType.WATCHTOWER, direction)) {
                        rc.buildRobot(RobotType.WATCHTOWER, direction);
                        current_project = rc.senseRobotAtLocation(rc.adjacentLocation(direction));
                        break;
                    }
                }
            }
        }

        if (current_project != null) {
            if (rc.canRepair(current_project.location)) {
                rc.repair(current_project.location);
            }
        }
    }

    public void movement() throws GameActionException {
        if (consistent_target != null) {
            consistent_rounds++;
        }

        if (!rc.isMovementReady()) return;

        if (consistent_target != null && debugOn) {
            if(debugOn) rc.setIndicatorLine(rc.getLocation(), consistent_target, 0, 0, 200);
        }

        if (consistent_target != null) {
            if (rc.getLocation().isWithinDistanceSquared(consistent_target, 2) || consistent_rounds >= 30 || is_target_from_com && (Com.getFlags(consistent_target) & 0b100) == 0) {
                consistent_target = null;
                consistent_rounds = 0;
                is_target_from_com = false;
            }
        }

        if (consistent_target == null) {
            if (nearby_ally_units.length > 10) {
                consistent_target = nav.disperseAround(nearby_ally_units);
            }
        }

        if(consistent_target == null) {
            consistent_target = Com.getTarget(0b101,0b100,4); // find a place with no friendly units and no enemy.
            if (consistent_target != null) {
                is_target_from_com = true;
            }
        }

        if (consistent_target == null) {
            consistent_target = Com.getTarget(0b001,0b000,4); // find a place with no enemy.
            if (consistent_target != null) {
                is_target_from_com = true;
            }
        }

        if (consistent_target == null) {
            consistent_target = nav.disperseAround(nearby_ally_units);
        }

        if (consistent_target != null) {
            nav.navigate(consistent_target);
        }
    }
}
