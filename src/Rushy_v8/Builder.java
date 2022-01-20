package Rushy_v8;

import battlecode.common.*;

public class Builder extends Robot {

    private enum BUILDER_ACTION_STATE {DISTRIBUTE, SETTLE, STALL}

    ;

    BUILDER_ACTION_STATE current_state = BUILDER_ACTION_STATE.STALL;

    boolean onto_special_mission = false;
    MapLocation special_mission_target;

    public Builder(RobotController rc) throws GameActionException {
        super(rc);
        if (Com.getHeadcount(RobotType.BUILDER) == 1) onto_special_mission = true;
        MapLocation main = Com.getMainArchonLoc();
        int closest_x = (max_X - main.x - 1 < main.x) ? (max_X - 1) : 0;
        int closest_y = (max_Y - main.y - 1 < main.y) ? (max_Y - 1) : 0;
        special_mission_target = new MapLocation(closest_x, closest_y);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.

        if (!onto_special_mission) {

            movement();
            action();

        } else {
            specialMission();
        }
    }

    public void action() throws GameActionException {
        if (debugOn) rc.setIndicatorString("trying to build");

        RobotInfo[] units_in_action_range = rc.senseNearbyRobots(5, rc.getTeam());

        for (RobotInfo nearby_ally_unit : units_in_action_range) {
            if (nearby_ally_unit.type == RobotType.WATCHTOWER &&
                    nearby_ally_unit.getLevel() == 1 &&
                    nearby_ally_unit.getHealth() == 150 &&
                    rc.canMutate(nearby_ally_unit.location)) {
                rc.mutate(nearby_ally_unit.location);
                return;
            }
        }

        for (RobotInfo unit : units_in_action_range) {
            if (unit.health < unit.type.getMaxHealth(unit.level) && rc.canRepair(unit.location)) {
                rc.repair(unit.location);
                return;
            }
        }

        if (rc.getTeamLeadAmount(rc.getTeam()) > 180) {
            for (Direction direction : directions) {
                if (rc.canBuildRobot(RobotType.WATCHTOWER, direction)) {
                    rc.buildRobot(RobotType.WATCHTOWER, direction);
                    return;
                }
            }
        } else {
            if (rc.senseLead(rc.getLocation()) == 0) {
                Com.decrementHeadcount();
                rc.disintegrate();
            }
        }
    }

    public void movement() throws GameActionException {
        if (consistent_target != null) consistent_rounds++;
        if (debugOn && consistent_target != null) rc.setIndicatorLine(rc.getLocation(), consistent_target, 0, 0, 200);

        if (!rc.isMovementReady()) return;

        switch (current_state) {
            case DISTRIBUTE:
                if (rc.getLocation().x % 3 == 1 && rc.getLocation().y % 3 == 1 && nearby_ally_units.length <= 20) {
                    current_state = BUILDER_ACTION_STATE.SETTLE;
                } else if (rc.getLocation().isWithinDistanceSquared(consistent_target, 10)) {
                    consistent_target = Com.getTarget(0b1, 0b0, 12);
                }
                break;
        }

        if (current_state == BUILDER_ACTION_STATE.STALL) {
            if (rc.getLocation().x % 3 != 1 || rc.getLocation().y % 3 != 1 || nearby_ally_units.length > 20) {
                consistent_target = Com.getTarget(0b101, 0b100, 12);
                if (consistent_target != null) {
                    current_state = BUILDER_ACTION_STATE.DISTRIBUTE;
                }
            }
        }

        if (current_state == BUILDER_ACTION_STATE.DISTRIBUTE && consistent_target != null) {
            nav.navigate(consistent_target);
        }
    }

    public void specialMission() throws GameActionException {
        if (rc.getLocation().isWithinDistanceSquared(special_mission_target, 2)) {
            rc.setIndicatorString(rc.getLocation().toString() + " " + special_mission_target.toString());
            for (RobotInfo nearby_ally_unit : nearby_ally_units) {
                if (nearby_ally_unit.type == RobotType.LABORATORY && nearby_ally_unit.mode != RobotMode.PROTOTYPE) {
                    onto_special_mission = false;
                    return;
                }
                if (rc.canRepair(nearby_ally_unit.location) && nearby_ally_unit.health < nearby_ally_unit.type.getMaxHealth(nearby_ally_unit.level)) {
                    rc.repair(nearby_ally_unit.location);
                    return;
                }
            }
            for (Direction direction : directions) {
                if (rc.canBuildRobot(RobotType.LABORATORY, direction)) {
                    rc.buildRobot(RobotType.LABORATORY, direction);
                    return;
                }
            }
        } else {
            nav.navigate(special_mission_target);
        }
    }
}
