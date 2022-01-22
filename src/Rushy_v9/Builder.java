package Rushy_v9;

import battlecode.common.*;

public class Builder extends Robot {

    private enum BUILDER_ACTION_STATE {DISTRIBUTE, SETTLE, STALL}

    ;

    BUILDER_ACTION_STATE current_state = BUILDER_ACTION_STATE.STALL;

    boolean onto_special_mission = false;

    public Builder(RobotController rc) throws GameActionException {
        super(rc);
        if (rc.getID()%3==0) {
            onto_special_mission = true;
        }
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // stuff that this type of bot does.

        movement();
        action();
    }

    public void action() throws GameActionException {
        RobotInfo[] units_in_action_range = rc.senseNearbyRobots(5, rc.getTeam());

        for (RobotInfo nearby_ally_unit : units_in_action_range) {
            if(nearby_ally_unit.getHealth() != RobotType.WATCHTOWER.getMaxHealth(nearby_ally_unit.level) ||
                !rc.canMutate(nearby_ally_unit.location)) continue;
            switch (nearby_ally_unit.type) {
                case WATCHTOWER:
                    switch (nearby_ally_unit.level) {
                        case 1:
                            rc.mutate(nearby_ally_unit.location);
                            return;
//                        case 2:
//                            if (rc.getTeamGoldAmount(rc.getTeam()) - 60 >= rc.getTeamGoldAmount(rc.getTeam().opponent())) {
//                                rc.mutate(nearby_ally_unit.location);
//                                return;
//                            }
                    }
                    break;
                case LABORATORY:
                    switch (nearby_ally_unit.level) {
                        case 1:
                            rc.mutate(nearby_ally_unit.location);
                            return;
                        case 2:
                            if (rc.getTeamGoldAmount(rc.getTeam()) - 25 >= rc.getTeamGoldAmount(rc.getTeam().opponent())) {
                                rc.mutate(nearby_ally_unit.location);
                                return;
                            }
                    }
                    break;
            }
        }

        for (RobotInfo unit : units_in_action_range) {
            if (unit.health < unit.type.getMaxHealth(unit.level) && rc.canRepair(unit.location)) {
                rc.repair(unit.location);
                return;
            }
        }

        RobotType tbb = (onto_special_mission&&rc.getTeamGoldAmount(rc.getTeam())==0)?RobotType.LABORATORY:RobotType.WATCHTOWER;
        boolean build_army = (Com.getHeadcount(RobotType.SOLDIER)+Com.getHeadcount(RobotType.WATCHTOWER)-5)/(float)ideal_army_count<Com.getHeadcount(RobotType.MINER)/(float)ideal_miner_count;
        if (tbb == RobotType.WATCHTOWER && rc.getTeamLeadAmount(rc.getTeam()) > 180 && build_army ) {
            for (Direction direction : directions) {
                if (rc.canBuildRobot(tbb, direction)) {
                    rc.buildRobot(tbb, direction);
                    return;
                }
            }
        }else if(tbb == RobotType.LABORATORY&& rc.getTeamLeadAmount(rc.getTeam())>180){
            for (Direction direction : directions) {
                if (rc.canBuildRobot(tbb, direction)) {
                    rc.buildRobot(tbb, direction);
                    onto_special_mission=false;
                    return;
                }
            }
        }else {
            if (rc.senseLead(rc.getLocation()) == 0) {
                Com.decrementHeadcount();
                rc.disintegrate();
            }
            if(Com.getHeadcount(RobotType.SOLDIER)+Com.getHeadcount(RobotType.WATCHTOWER)>0.25*ideal_army_count){
                for(Direction direction:directions){
                    MapLocation n_loc = rc.adjacentLocation(direction);
                    if(rc.onTheMap(n_loc) && rc.senseLead(n_loc)==0 && rc.canMove(direction)){
                        rc.move(direction);
                        Com.decrementHeadcount();
                        rc.disintegrate();
                    }
                }
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
                    consistent_target = null;
                    current_state=BUILDER_ACTION_STATE.STALL;
                }
                break;
        }



        if(enemy_dmg >0){
            if(consistent_target!=null && consistent_target.x>=0 && consistent_target.y>=0 && consistent_target.x<max_X && consistent_target.y<max_Y)Com.setTarget(0b1,0b1,consistent_target);
            consistent_target=null;
            current_state = BUILDER_ACTION_STATE.STALL;
        }



        if (current_state == BUILDER_ACTION_STATE.STALL) {
            if (rc.getLocation().x % 3 != 1 || rc.getLocation().y % 3 != 1 || nearby_ally_units.length > 20) {
                consistent_target = Com.getTarget(0b101, 0b100, 12,16);
                if (consistent_target ==null){
                    consistent_target = Com.getTarget(0b001, 0b000, 6,16);
                }
                if (consistent_target!=null){
                    current_state=BUILDER_ACTION_STATE.DISTRIBUTE;
                }
            }
        }

        if (current_state == BUILDER_ACTION_STATE.DISTRIBUTE && consistent_target != null) {
            nav.navigate(consistent_target);
        }
    }
}
