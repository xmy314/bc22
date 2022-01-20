package Rushy_v8;

import battlecode.common.*;

public class Builder extends Robot {
    RobotInfo current_project = null;
    boolean onto_special_mission=false;
    MapLocation special_mission_target;

    public Builder(RobotController rc) throws GameActionException {
        super(rc);
        if(Com.getHeadcount(RobotType.BUILDER)==1) onto_special_mission=true;
        MapLocation main = Com.getMainArchonLoc();
        int closest_x = (max_X-main.x-1<main.x)?(max_X-1):main.x;
        int closest_y = (max_Y-main.y-1<main.y)?(max_Y-1):main.y;
        special_mission_target=new MapLocation(closest_x,closest_y);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.

        if(!onto_special_mission) {

            if (current_project == null && (nearby_ally_units.length > 30 || (rc.getLocation().x & rc.getLocation().y & 1) != 1)) {
                if (debugOn) rc.setIndicatorString("trying to move");
                movement();
            }
            action();

        }else{
            if(rc.getLocation().isWithinDistanceSquared(special_mission_target,2)){
                for(RobotInfo nearby_ally_unit:nearby_ally_units){
                    if(rc.canRepair(nearby_ally_unit.location)){
                        rc.repair(nearby_ally_unit.location);
                        return;
                    }
                }
                for(Direction direction:directions){
                    if(rc.canBuildRobot(RobotType.LABORATORY,direction)){
                        rc.buildRobot(RobotType.LABORATORY,direction);
                        break;
                    }
                }
            }else{
                nav.navigate(special_mission_target);
            }
        }
    }

    public void action() throws GameActionException {
        if(debugOn) rc.setIndicatorString("trying to build");

        RobotInfo[] units_in_action_range = rc.senseNearbyRobots(5, rc.getTeam());

        for(RobotInfo nearby_ally_unit:units_in_action_range) {

            if( nearby_ally_unit.type==RobotType.WATCHTOWER &&
                nearby_ally_unit.getLevel() == 1 &&
                nearby_ally_unit.getHealth() == 150 &&
                rc.canMutate(nearby_ally_unit.location)){
                rc.mutate(nearby_ally_unit.location);
                return;
            }
        }

        if (current_project != null) {
            current_project = rc.senseRobotAtLocation(current_project.location);
            if (current_project != null && current_project.mode != RobotMode.PROTOTYPE) {
                current_project = null;
            }
        }

        if (current_project == null) {
            for (RobotInfo unit : units_in_action_range) {
                if (unit.health<unit.type.getMaxHealth(unit.level) && rc.canRepair(unit.location)) {
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
            }else{
                if(rc.senseLead(rc.getLocation())==0 && Com.getHeadcount(RobotType.SOLDIER)<20){
                    Com.decrementHeadcount();
                    rc.disintegrate();
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
        if (consistent_target != null) consistent_rounds++;
        if (debugOn && consistent_target != null) rc.setIndicatorLine(rc.getLocation(), consistent_target, 0, 0, 200);

        if (!rc.isMovementReady()) return;


        if (consistent_target != null) {
            if (rc.getLocation().isWithinDistanceSquared(consistent_target, 2) || consistent_rounds >= 30 || is_target_from_com && (Com.getFlags(consistent_target) & 0b100) == 0) {
                consistent_target = null;
                consistent_rounds = 0;
                is_target_from_com = false;
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

        if (consistent_target != null) {
            nav.navigate(consistent_target);
        }
    }

    public void specialMission() throws GameActionException{

    }
}
