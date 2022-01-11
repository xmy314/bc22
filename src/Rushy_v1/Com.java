package Rushy_v1;

import Rushy_v1.utils.Debug;
import battlecode.common.*;

import static Rushy_v1.Robot.*;

public class Com {
    /*
    first few position are for head counting units. written when each unit is created and whe they are about to die.
    4 -19 are for map data for a total of 256 bit.
        the map is divided into 64 chunks, 8 along x and 8 along y. (TODO: this can be more dynamic on setup if i got time)
        each chunk has 4 bits of properties.
            currently, they are whether military is needed here. 1 for yes
                                        miner is needed here. 1 for yes
                                        some sort of unit is needed here to alarm of potential enemy activity. 1 for yes
                                            it is also the flag needed for exploration at the start.
                                            it has the speciality of being the opposite of the rest.
                                        last bit's purpose is not determined. probably over population?
    */
    static boolean death_registered = true;
    static int chunk_height;
    static int chunk_width;

    public static void init() throws GameActionException {
        if (rc.getType() == RobotType.ARCHON && rc.readSharedArray(4) == 0) {
            for (int array_dex = 4; array_dex < 20; array_dex++) {
                if (array_dex >= 18 && !(22 <= max_X && max_X <= 24 || 29 <= max_X && max_X <= 32 || 36 <= max_X && max_X <= 40 || 43 <= max_X && max_X <= 48 || max_X > 50)) {
                    rc.writeSharedArray(array_dex, 0b0000_0000_0000_0000);
                } else if ((array_dex & 1) == 1 && !(22 <= max_Y && max_Y <= 24 || 29 <= max_Y && max_Y <= 32 || 36 <= max_Y && max_Y <= 40 || 43 <= max_Y && max_Y <= 48 || max_Y > 50)) {
                    rc.writeSharedArray(array_dex, 0b0100_0100_0100_0000);
                } else {
                    rc.writeSharedArray(array_dex, 0b0100_0100_0100_0100);
                }
            }
        }
        chunk_height = (int) Math.ceil(max_Y / 8f);
        chunk_width = (int) Math.ceil(max_X / 8f);
    }

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

    public static void verifyTargets() throws GameActionException {

        //TODO: this is fairly inefficient and hacky as it only checks the chunk it stands on.
        boolean is_moving=rc.getMode()==RobotMode.DROID || rc.getMode()==RobotMode.PORTABLE;

        int tbw = 0b100;
        if (2 * nearby_enemy_units.length > protection_level) {
            tbw |= 0b1;
        }
        if ( is_moving && ally_miner_count < 3 * mine_over_thresh_count) {
            tbw |= 0b10;
        }
        if (nearby_ally_units.length >= ((rc.getRoundNum()<200)?0:1) ) {
            tbw &= 0b011;
        }

        if (is_moving) {
            setTarget(0b111, tbw, rc.getLocation());
        } else {
            setTarget(0b101, tbw, rc.getLocation());
        }

        //TODO: following is only for debugging
        if (rc.getType() == RobotType.ARCHON) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    int v = getFlags(x, y);
                    if(debugOn) rc.setIndicatorDot(getChunkCenter(x, y), ((v & 0b001) != 0) ? 200 : 0, ((v & 0b010) != 0) ? 200 : 0, ((v & 0b100) != 0) ? 200 : 0);
                }
            }
        }
    }

    public static MapLocation getChunkCenter(int chunk_x, int chunk_y) {
        return new MapLocation(Math.min(chunk_x * chunk_width + chunk_width / 2, max_X - 1), Math.min(chunk_y * chunk_height + chunk_height / 2, max_Y - 1));
    }

    public static int getFlags(int chunk_x, int chunk_y) throws GameActionException {
        int array_dex = 4 + 2 * chunk_x + (chunk_y >> 2);
        int bit_dex = (chunk_y & 3) << 2;
        try {
            return (rc.readSharedArray(array_dex) >> bit_dex) & 0b1111;
        } catch (GameActionException e) {
            Debug.p(chunk_x + " " + chunk_y);
            e.printStackTrace();
            return 0;
        }
    }

    public static int getFlags(MapLocation loc) throws GameActionException {
        return getFlags(loc.x / chunk_width, loc.y / chunk_height);
    }

    public static MapLocation getTarget(int read_mask) throws GameActionException {
        // read mask is to only get location with that flag mask

        int current_chunk_x = rc.getLocation().x / chunk_width;
        int current_chunk_y = rc.getLocation().y / chunk_height;

        int avoidance_counter = rc.getID() % 4;

        MapLocation potential_target = null;

        int chunk_x;
        int chunk_y;

        for (int d = 1; d < 10; d++) {
            for (int off = 0; off < d; off++) {

                chunk_x = current_chunk_x - off;
                chunk_y = current_chunk_y + d - off;

                if (chunk_x >= 0 && chunk_y < 8 && chunk_y * chunk_height < max_Y) {
                    if ((getFlags(chunk_x, chunk_y) & read_mask) != 0) {
                        if (avoidance_counter == 0) {
                            return getChunkCenter(chunk_x, chunk_y);
                        } else {
                            potential_target = getChunkCenter(chunk_x, chunk_y);
                            avoidance_counter--;
                        }
                    }
                }

                chunk_x = current_chunk_x + d - off;
                chunk_y = current_chunk_y + off;
                if (chunk_x < 8 && chunk_y < 8 && chunk_x * chunk_width < max_X && chunk_y * chunk_height < max_Y) {
                    if ((getFlags(chunk_x, chunk_y) & read_mask) != 0) {
                        if (avoidance_counter == 0) {
                            return getChunkCenter(chunk_x, chunk_y);
                        } else {
                            potential_target = getChunkCenter(chunk_x, chunk_y);
                            avoidance_counter--;
                        }
                    }
                }

                chunk_x = current_chunk_x + off;
                chunk_y = current_chunk_y + off - d;
                if (chunk_x < 8 && chunk_y >= 0 && chunk_x * chunk_width < max_X) {
                    if ((getFlags(chunk_x, chunk_y) & read_mask) != 0) {
                        if (avoidance_counter == 0) {
                            return getChunkCenter(chunk_x, chunk_y);
                        } else {
                            potential_target = getChunkCenter(chunk_x, chunk_y);
                            avoidance_counter--;
                        }
                    }
                }

                chunk_x = current_chunk_x + off - d;
                chunk_y = current_chunk_y - off;
                if (chunk_x >= 0 && chunk_y >= 0) {
                    if ((getFlags(chunk_x, chunk_y) & read_mask) != 0) {
                        if (avoidance_counter == 0) {
                            return getChunkCenter(chunk_x, chunk_y);
                        } else {
                            potential_target = getChunkCenter(chunk_x, chunk_y);
                            avoidance_counter--;
                        }
                    }
                }
            }
        }

        return potential_target;
    }

    /* returns the place is registered with a different flag.*/
    public static void setTarget(int write_mask, int data_mask, MapLocation loc) throws GameActionException {

        int chunk_x = loc.x / chunk_width;
        int chunk_y = loc.y / chunk_height;

        int difference_mask = (getFlags(chunk_x, chunk_y) & write_mask) ^ data_mask;

        if (difference_mask != 0) {
            int array_dex = 4 + 2 * chunk_x + (chunk_y >> 2);
            int bit_dex = (chunk_y & 3) * 4;
            int v = rc.readSharedArray(array_dex);
            v ^= difference_mask << bit_dex;
            rc.writeSharedArray(array_dex, v);
        }
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
