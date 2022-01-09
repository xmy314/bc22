package BasicPlayer;

import BasicPlayer.utils.Debug;
import battlecode.common.*;

import static BasicPlayer.Robot.*;

public class Com {
    /*
    first few postion are for head counting units. written when each unit is created and whe they are about to die.
    5 - 9 are for providing general overview of the map. each bit represent ~ a maximum of 50 squares of data.
    10 -19 are for special and specific location. such as where soldiers check and where watch tower attacks.
    */
    static boolean death_registered = true;

    public enum ComFlag {PROTECT, ATTACK, EXAMINE, SAFE}

    public static int typeToHeadcountIndex(RobotType t) {
        switch (t) {
            case MINER:
                return 0;
            case SOLDIER:
                return 1;
            case BUILDER:
                return 2;
        }
        return -1;
    }

    public static int getId(ComFlag flag) {
        switch (flag) {
            case PROTECT:
                return 1;
            case ATTACK:
                return 2;
            case EXAMINE:
                return 3;
            case SAFE:
                return 4;
        }
        return -1;
    }

    public static ComFlag getFlag(int v) {
        switch (v >> 12) {
            case 1:
                return ComFlag.PROTECT;
            case 2:
                return ComFlag.ATTACK;
            case 3:
                return ComFlag.EXAMINE;
            case 4:
                return ComFlag.SAFE;
        }
        return null;
    }

    public static int compressLocation(MapLocation loc) {
        return (loc.x << 6) | (loc.y);
    }

    public static MapLocation uncompressLocation(int v) {
        return new MapLocation((v >> 6) & 63, v & 63);
    }

    public static void verifyTargets() throws GameActionException {
        for (int i = 10; i < 20; i++) {
            int v = rc.readSharedArray(i);
            if (v == 0) continue;
            ComFlag v_flag = getFlag(v);
            if (v_flag == null) continue;
            MapLocation to_verify = uncompressLocation(v);
            if (to_verify == null) continue;

            switch (v_flag) {
                case PROTECT:
                    if (rc.canSenseLocation(to_verify)) {
                        if (protection_level > 10) {
                            rc.writeSharedArray(i, 0);
                        }
                    }
                    break;
                case ATTACK:
                    if (rc.canSenseLocation(to_verify)) {
                        if (nearby_enemy_units.length == 0) {
                            rc.writeSharedArray(i, (getId(ComFlag.SAFE) << 12) | (v & 4095));
                        }
                    }
                case EXAMINE:
                    if (rc.canSenseLocation(to_verify)) {
                        if (nearby_enemy_units.length != 0) {
                            rc.writeSharedArray(i, (getId(ComFlag.ATTACK) << 12) | (v & 4095));
                        } else {
                            rc.writeSharedArray(i, (getId(ComFlag.SAFE) << 12) | (v & 4095));
                        }
                    }
                    break;
            }
        }
    }

    public static MapLocation getTarget(ComFlag flag) throws GameActionException {
        int id = getId(flag);
        int[] possible_vs = new int[10];
        int v_count = 0;
        for (int i = 10; i < 20; i++) {
            int v = rc.readSharedArray(i);
            if ((v >> 12) == id) {
                possible_vs[v_count++] = v;
            }
        }
        return (v_count!=0)?uncompressLocation(possible_vs[rc.getID() % v_count]):null;
    }

    /* returns the place is registered with a different flag.*/
    public static boolean setTarget(ComFlag flag, MapLocation loc) throws GameActionException {
        int id = getId(flag);
        int new_v = (id << 12) | compressLocation(loc);
        int[] overridable_indices = new int[10];
        int overridable_indices_count = 0;
        for (int i = 10; i < 20; i++) {
            int v=rc.readSharedArray(i);
            if (v != 0) {
                MapLocation recorded = uncompressLocation(v);
                if (recorded.distanceSquaredTo(loc) < 25) {
                    return ((v >> 12) != id);
                }
            }
            if (v == 0 || (v >> 12) == id || v>>12==4) { // 4 is for safe house.
                overridable_indices[overridable_indices_count++] = i;
            }
        }
        if(overridable_indices_count>0) {
            rc.writeSharedArray(overridable_indices[rc.getID() % overridable_indices_count], new_v);
        }
        return true;
    }

    public static void incrementHeadCount() throws GameActionException {
        if (death_registered) {
            int headcount_id = typeToHeadcountIndex(rc.getType());
            if (headcount_id == -1) return;
            rc.writeSharedArray(headcount_id, rc.readSharedArray(headcount_id) + 1);
            death_registered = false;
        }
    }

    public static void decrementHeadcount() throws GameActionException {
        if (!death_registered) {
            int headcount_id = typeToHeadcountIndex(rc.getType());
            rc.writeSharedArray(headcount_id, rc.readSharedArray(headcount_id) - 1);
            death_registered = true;
        }
    }

    public static int getHeadcount(RobotType desired_type) throws GameActionException {
        int headcount_id = typeToHeadcountIndex(desired_type);
        return (headcount_id != -1) ? rc.readSharedArray(headcount_id) : 0;
    }
}
