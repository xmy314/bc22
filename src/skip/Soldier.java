package skip;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;

public class Soldier extends Robot {

    private MapLocation closestFight = null;

    public Soldier(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();


        // Try to attack someone
        if (nearby_enemy_units.length > 0) {
            // TODO: this might have a bug of units moving out of location and then units thinking they're dead
            for (int i = 20; i < 25; i+=2) {
                if (rc.readSharedArray(i) == rc.getID()) {
                    rc.writeSharedArray(i+1, encodeLocation(rc.getLocation()));
                }
                if (rc.canSenseRobotAtLocation(decodeLocation(rc.readSharedArray(i+1)))) {
                    if (rc.senseRobotAtLocation(decodeLocation(rc.readSharedArray(i+1))) == null) {
                        rc.writeSharedArray(i, rc.getID());
                        rc.writeSharedArray(i+1, encodeLocation(rc.getLocation()));
                    }
                }
            }
            closestFight = null;
            for (int i = 21; i < 26; i+=2) {
                MapLocation fight = decodeLocation(rc.readSharedArray(i));
                if (fight != null) {
                    if (closestFight == null || rc.getLocation().distanceSquaredTo(fight) < rc.getLocation().distanceSquaredTo(closestFight)) {
                        closestFight = fight;
                    }
                }
            }
            if (closestFight == null || rc.getLocation().distanceSquaredTo(closestFight) > 100) {
                if (decodeLocation(rc.readSharedArray(21)) == null) {
                    rc.writeSharedArray(20, rc.getID());
                    rc.writeSharedArray(21, encodeLocation(rc.getLocation()));
                }
                else if (decodeLocation(rc.readSharedArray(23)) == null) {
                    rc.writeSharedArray(22, rc.getID());
                    rc.writeSharedArray(23, encodeLocation(rc.getLocation()));
                }
                else if (decodeLocation(rc.readSharedArray(25)) == null) {
                    rc.writeSharedArray(24, rc.getID());
                    rc.writeSharedArray(25, encodeLocation(rc.getLocation()));
                } else {
                    int r = (int) Math.floor(Math.random()*3);
                    rc.writeSharedArray(20+2*r, rc.getID());
                    rc.writeSharedArray(21+2*4, encodeLocation(rc.getLocation()));
                }
            }
            combat();
        } else {
            for (int i = 20; i < 25; i+=2) {
                if (rc.readSharedArray(i) == rc.getID()) {
                    rc.writeSharedArray(i+1, 10000);
                }
                if (rc.canSenseRobotAtLocation(decodeLocation(rc.readSharedArray(i+1)))) {
                    if (rc.senseRobotAtLocation(decodeLocation(rc.readSharedArray(i+1))) == null) {
                        rc.writeSharedArray(i, 0);
                        rc.writeSharedArray(i+1, 10000);
                    }
                }
            }
            // TODO: finish this part
            safeMovement();
        }
    }

    public void combat() throws GameActionException {

        if (rc.isMovementReady()) {

            if (rc.getLocation().distanceSquaredTo(closestFight) <= 5) {
                // if there are more than 2 allied units than
                if (ally_soldier >= enemy_soldier +2) {
                    SoldierMicroInfo[] micro_infos = new SoldierMicroInfo[9];
                    for (int i = 0; i < 8; i++) {
                        micro_infos[i] = new SoldierMicroInfo(rc.adjacentLocation(directions[i]));
                    }
                    micro_infos[8] = new SoldierMicroInfo(rc.getLocation());

                    for (RobotInfo nearby_enemy_unit : nearby_enemy_units) {
                        for (SoldierMicroInfo micro_info : micro_infos) {
                            micro_info.update(nearby_enemy_unit);
                        }
                    }

                    int best_dex = 8;
                    for (int i = 0; i < 8; i++) {
                        if(!rc.canMove(directions[i]))continue;
                        if (micro_infos[i].isBetter(micro_infos[best_dex])) {
                            best_dex = i;
                        }
                    }

                    if (best_dex == 8) {
                        RobotInfo toAttack=chooseAttackTarget(nearby_enemy_units);
                        if(toAttack!=null) rc.attack(toAttack.location);
                    } else if (micro_infos[best_dex].rubble < micro_infos[8].rubble) {
                        nav.moveWrapper(directions[best_dex]);
                        RobotInfo toAttack=chooseAttackTarget(nearby_enemy_units);
                        if(toAttack!=null) rc.attack(toAttack.location);
                    } else {
                        RobotInfo toAttack=chooseAttackTarget(nearby_enemy_units);
                        if(toAttack!=null) rc.attack(toAttack.location);
                        nav.moveWrapper(directions[best_dex]);
                    }
                }
                // otherwise retreat back to base
                else {
                    RobotInfo toAttack=chooseAttackTarget(nearby_enemy_units);
                    if(toAttack!=null) rc.attack(toAttack.location);
                    nav.navigate(Com.getMainArchonLoc());
                }
            } else {
                // move closer to the fight
                // TODO: finish this part
            }
        } else {
            RobotInfo toAttack=chooseAttackTarget(nearby_enemy_units);
            if(toAttack!=null) rc.attack(toAttack.location);
        }

    }

    static class SoldierMicroInfo {
        float potential_dmg;
        int min_dist_to_enemy;
        MapLocation loc;
        boolean on_map;
        int rubble;

        public SoldierMicroInfo(MapLocation loc) {
            this.loc = loc;
            try {
                on_map = rc.onTheMap(loc);
                if (on_map) {
                    rubble = rc.senseRubble(loc);
                }
            } catch (GameActionException e) {
                e.printStackTrace(); // never happening as sense is always radius 1 away
            }
            potential_dmg = 0;
            min_dist_to_enemy = 10000;
        }

        public void update(RobotInfo ri) {
            if(!on_map) return;
            int d = ri.getLocation().distanceSquaredTo(loc);
            try {
                if (d <= ri.getType().actionRadiusSquared)
                    potential_dmg += Math.abs(ri.type.getDamage(ri.level) / (1 + rc.senseRubble(ri.getLocation()) / 10));
            } catch (GameActionException e) {
                e.printStackTrace(); // never happening since all input are within vision range.
            }
            if (d < min_dist_to_enemy) min_dist_to_enemy = d;
        }

        public boolean canAttack() {
            return rc.getType().actionRadiusSquared >= min_dist_to_enemy;
        }

        public boolean isBetter(SoldierMicroInfo mi) {
            // on map is better
            if (!mi.on_map) return true;
            if (!on_map) return false;

            // low potential_damage is better. however, there is a buffer.
            if (potential_dmg + 2 < mi.potential_dmg) return true;
            if (potential_dmg > mi.potential_dmg + 2) return false;

            // low rubble is better. here the cut-off is 2 times
            if (1.2f*(rubble + 10) < (mi.rubble+10)) return true;
            if ((rubble + 10) > 1.2f*(mi.rubble+10)) return false;

            // can attack is better
            if (rc.isActionReady()) {
                if (canAttack() && !mi.canAttack()) return true;
                if (!canAttack() && mi.canAttack()) return false;
            }

            // low rubble is better
            return rubble < mi.rubble;
        }
    }

    public void safeMovement() throws GameActionException {
        if (consistent_target != null) {
            consistent_rounds++;
        }

        if (!rc.isMovementReady()) return;

        if (consistent_target != null && debugOn) {
            if (debugOn) rc.setIndicatorLine(rc.getLocation(), consistent_target, 200, 0, 0);
        }

        // check if there is any mission by communication
        if (consistent_target != null) {
            if (rc.getLocation().isWithinDistanceSquared(consistent_target, 13) ||
                    consistent_rounds >= 30 ||
                    is_target_from_com && (Com.getFlags(consistent_target) & 0b101) == 0) {
                consistent_target = null;
                consistent_rounds = 0;
                is_target_from_com = false;
            }
        }

        if (consistent_target == null) {
            consistent_target = Com.getTarget(0b001, 0b001, 6); // military support
            if (consistent_target != null) {
                is_target_from_com = true;
            }
        }

        if (consistent_target == null) {
            consistent_target = Com.getTarget(0b100, 0b100, 12); // pioneer
            if (consistent_target != null) {
                is_target_from_com = true;
            }
        }

        if (consistent_target != null) {
            nav.navigate(consistent_target);
        }
    }

    public int encodeLocation(MapLocation loc) {
        return loc.x*64+loc.y;
    }

    public MapLocation decodeLocation(int loc) {
        if (loc == 10000) return null;
        else return new MapLocation(loc/64, loc%64);
    }
}
