package Rushy_v1;

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
            case WATCHTOWER:
                return 3;
        }
        return -1;
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

    public static MapLocation getTarget(int read_mask, int data_mask,int depth) throws GameActionException {
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
                    if ((getFlags(chunk_x, chunk_y) & read_mask) == data_mask) {
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
                    if ((getFlags(chunk_x, chunk_y) & read_mask) == data_mask) {
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
                    if ((getFlags(chunk_x, chunk_y) & read_mask) == data_mask) {
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
                    if ((getFlags(chunk_x, chunk_y) & read_mask) == data_mask) {
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

    public static void analyzeTargets() throws GameActionException {
        int r0=rc.readSharedArray(4);
        int f0=r0>>12;
        int f1=(r0>>8)&0b1111;
        int f2=(r0>>4)&0b1111;
        int f3=r0&0b1111;
        int r1=rc.readSharedArray(5);
        int f4=r1>>12;
        int f5=(r1>>8)&0b1111;
        int f6=(r1>>4)&0b1111;
        int f7=r1&0b1111;
        int r2=rc.readSharedArray(6);
        int f8=r2>>12;
        int f9=(r2>>8)&0b1111;
        int f10=(r2>>4)&0b1111;
        int f11=r2&0b1111;
        int r3=rc.readSharedArray(7);
        int f12=r3>>12;
        int f13=(r3>>8)&0b1111;
        int f14=(r3>>4)&0b1111;
        int f15=r3&0b1111;
        int r4=rc.readSharedArray(8);
        int f16=r4>>12;
        int f17=(r4>>8)&0b1111;
        int f18=(r4>>4)&0b1111;
        int f19=r4&0b1111;
        int r5=rc.readSharedArray(9);
        int f20=r5>>12;
        int f21=(r5>>8)&0b1111;
        int f22=(r5>>4)&0b1111;
        int f23=r5&0b1111;
        int r6=rc.readSharedArray(10);
        int f24=r6>>12;
        int f25=(r6>>8)&0b1111;
        int f26=(r6>>4)&0b1111;
        int f27=r6&0b1111;
        int r7=rc.readSharedArray(11);
        int f28=r7>>12;
        int f29=(r7>>8)&0b1111;
        int f30=(r7>>4)&0b1111;
        int f31=r7&0b1111;
        int r8=rc.readSharedArray(12);
        int f32=r8>>12;
        int f33=(r8>>8)&0b1111;
        int f34=(r8>>4)&0b1111;
        int f35=r8&0b1111;
        int r9=rc.readSharedArray(13);
        int f36=r9>>12;
        int f37=(r9>>8)&0b1111;
        int f38=(r9>>4)&0b1111;
        int f39=r9&0b1111;
        int r10=rc.readSharedArray(14);
        int f40=r10>>12;
        int f41=(r10>>8)&0b1111;
        int f42=(r10>>4)&0b1111;
        int f43=r10&0b1111;
        int r11=rc.readSharedArray(15);
        int f44=r11>>12;
        int f45=(r11>>8)&0b1111;
        int f46=(r11>>4)&0b1111;
        int f47=r11&0b1111;
        int r12=rc.readSharedArray(16);
        int f48=r12>>12;
        int f49=(r12>>8)&0b1111;
        int f50=(r12>>4)&0b1111;
        int f51=r12&0b1111;
        int r13=rc.readSharedArray(17);
        int f52=r13>>12;
        int f53=(r13>>8)&0b1111;
        int f54=(r13>>4)&0b1111;
        int f55=r13&0b1111;
        int r14=rc.readSharedArray(18);
        int f56=r14>>12;
        int f57=(r14>>8)&0b1111;
        int f58=(r14>>4)&0b1111;
        int f59=r14&0b1111;
        int r15=rc.readSharedArray(19);
        int f60=r15>>12;
        int f61=(r15>>8)&0b1111;
        int f62=(r15>>4)&0b1111;
        int f63=r15&0b1111;
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
