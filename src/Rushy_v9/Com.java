package Rushy_v9;

import battlecode.common.*;

import static Rushy_v9.Robot.*;

public class Com {
    /*
    0 - 1 are for head counting units. written when each unit is created and whe they are about to die.
    2 is for location of base and its nearby rubble count.
    3 is for location of the army. should have been mode but mean also works.
        most protected and most dangerous point on the map.
    4 -19 are for map data for a total of 256 bit.
        the map is divided into 64 chunks, 8 along x and 8 along y.
        each chunk has 4 bits of properties.
            currently, they are whether military is needed here. 1 for yes
                                        miner is needed here. 1 for yes
                                        some sort of unit is needed here to alarm of potential enemy activity. 1 for yes
                                            it is also the flag needed for exploration at the start.
                                            it has the speciality of being the opposite of the rest.
                                        last bit's purpose is not determined. probably over population?
    */
    static final int map_memory_index_0 = 4;
    static final int allocated_map_memory_count = 64; // 64 represents 16 indexes
    static int chunk_side_length;
    static int chunk_count_y;
    static int chunk_count_x;
    static int total_chunk_count;

    static boolean death_registered = true;

    public static void init() throws GameActionException {

        double temp = Math.ceil(Math.sqrt(max_X * max_Y / (double) allocated_map_memory_count));
        chunk_side_length = (int) temp + ((Math.ceil(max_X / temp) * Math.ceil(max_Y / temp) > allocated_map_memory_count) ? 1 : 0);

        chunk_count_x = (int) Math.ceil(max_X / (double) chunk_side_length);
        chunk_count_y = (int) Math.ceil(max_Y / (double) chunk_side_length);
        total_chunk_count = chunk_count_x * chunk_count_y;

        if (rc.getType() == RobotType.ARCHON) {
            partialResetExploration();
        }
    }

    public static MapLocation getChunkCenter(int chunk_x, int chunk_y) {
        return new MapLocation(Math.min(chunk_x * chunk_side_length + chunk_side_length / 2, max_X - 1), Math.min(chunk_y * chunk_side_length + chunk_side_length / 2, max_Y - 1));
    }

    public static MapLocation getChunkCenter(int chunk_id) {
        return getChunkCenter(chunk_id / chunk_count_y, chunk_id % chunk_count_y);
    }

    public static int mapLocationToChunkID(MapLocation loc) {
        return (loc.x / chunk_side_length) * chunk_count_y + loc.y / chunk_side_length;
    }

    public static int getFlags(int chunk_id) {
        int array_dex = map_memory_index_0 + (chunk_id >> 2);
        int bit_dex = (chunk_id % 4) * 4;
        try {
            return (rc.readSharedArray(array_dex) >> bit_dex) & 0b1111;
        } catch (GameActionException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getFlags(int chunk_x, int chunk_y) {
        return getFlags(chunk_x * chunk_count_y + chunk_y);
    }

    public static int getFlags(MapLocation loc) {
        return getFlags(mapLocationToChunkID(loc));
    }

    public static MapLocation getTarget(int read_mask, int data_mask, int depth) throws GameActionException {
        // read mask is to only get location with that flag mask

        int current_chunk_x = rc.getLocation().x / chunk_side_length;
        int current_chunk_y = rc.getLocation().y / chunk_side_length;

        int avoidance_counter = rc.getID() % 8;

        MapLocation potential_target = null;

        int chunk_x;
        int chunk_y;

        for (int d = 1; d < depth; d++) {
            for (int off = 0; off < d; off++) {

                chunk_x = current_chunk_x - off;
                chunk_y = current_chunk_y + d - off;
                if (chunk_x >= 0 && chunk_y < chunk_count_y) {
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
                if (chunk_x < chunk_count_x && chunk_y < chunk_count_y) {
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
                if (chunk_x < chunk_count_x && chunk_y >= 0) {
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
//            if (potential_target != null) {
//                return potential_target;
//            }
        }

        return potential_target;
    }

    public static void setTarget(int write_mask, int data_mask, int chunk_id) throws GameActionException {
        int difference_mask = (getFlags(chunk_id) ^ data_mask) & write_mask;

        if (difference_mask != 0) {
            int array_dex = map_memory_index_0 + (chunk_id >> 2);
            int bit_dex = (chunk_id & 0b11) << 2;
            int v = rc.readSharedArray(array_dex);
            v ^= difference_mask << bit_dex;
            rc.writeSharedArray(array_dex, v);
        }
    }

    public static void setTarget(int write_mask, int data_mask, MapLocation loc) throws GameActionException {
        int chunk_x = loc.x / chunk_side_length;
        int chunk_y = loc.y / chunk_side_length;
        int chunk_id = chunk_x * chunk_count_y + chunk_y;
        setTarget(write_mask, data_mask, chunk_id);
    }

    public static void verifyTargets() throws GameActionException {
        int current_chunk_id = mapLocationToChunkID(rc.getLocation());
        boolean can_negate = rc.getLocation().isWithinDistanceSquared(getChunkCenter(current_chunk_id), 10);

        setTarget(0b100, 0b000, current_chunk_id);

        // using enemy count as the scheme would turn away miners from component miner whom they want to compete against.
        // the result is generally lower economy then it could have had.
        // TODO: this portion is fairly expensive but optimization can be done.
        boolean enemy_on_current_chunk = false;
        for (RobotInfo unit : nearby_enemy_units) {
            if(unit.type.getDamage(unit.level)>0) {
                int unit_chunk_id = mapLocationToChunkID(unit.location);
                setTarget(0b1, 0b1, unit_chunk_id);
                if (unit_chunk_id == current_chunk_id) {
                    enemy_on_current_chunk = true;
                }
            }
        }
        if (!enemy_on_current_chunk && can_negate) {
            setTarget(0b1, 0b0, current_chunk_id);
        }

        boolean mine_on_current_chunk = false;
        for (MapLocation mine : mines) {
            int unit_chunk_id = mapLocationToChunkID(mine);
            setTarget(0b10, 0b10, unit_chunk_id);
            if (unit_chunk_id == current_chunk_id) {
                mine_on_current_chunk = true;
            }
        }
        if (!mine_on_current_chunk && can_negate) {
            setTarget(0b10, 0b00, current_chunk_id);
        }

        //TODO: following is only for debugging
        if (debugOn && rc.getType() == RobotType.ARCHON) {
            for (int x = 0; x < chunk_count_x; x++) {
                for (int y = 0; y < chunk_count_y; y++) {
                    int v = getFlags(x, y);
                    rc.setIndicatorDot(getChunkCenter(x, y), ((v & 0b001) != 0) ? 200 : 0, ((v & 0b010) != 0) ? 200 : 0, ((v & 0b100) != 0) ? 200 : 0);
                }
            }
        }
    }

    public static void analyzeTargets() throws GameActionException {
        // the code is no longer applicable with dynamic chunk division
        for (int x_dex = 0; x_dex < chunk_count_x; x_dex++) {
            for (int y_dex = 0; y_dex < chunk_count_y; y_dex++) {
                int chunk_x = chunk_count_x / 2 + (((x_dex & 1) == 1) ? -1 : 1) * ((x_dex + 1) / 2);
                int chunk_y = chunk_count_y / 2 + (((y_dex & 1) == 1) ? -1 : 1) * ((y_dex + 1) / 2);

                int chunk_id = chunk_x * chunk_count_y + chunk_y;
                if (getFlags(chunk_x, chunk_y) == 0b100) { // ones that are labeled to explore.

                    if ((getFlags(chunk_x + (((x_dex & 1) == 1) ? 1 : -1), chunk_y) & 1) != 0) {
                        setTarget(0b1, 0b1, chunk_id);
                    }

                    if ((getFlags(chunk_x, chunk_y + (((y_dex & 1) == 1) ? 1 : -1)) & 1) != 0) {
                        setTarget(0b1, 0b1, chunk_id);
                    }
                }
            }
        }
    }

    public static void partialResetExploration() throws GameActionException {
        setTarget(0b100, 0b100, 0);
        setTarget(0b100, 0b100, chunk_count_y - 1);
        setTarget(0b100, 0b100, (chunk_count_x - 1) * chunk_count_y);
        setTarget(0b100, 0b100, total_chunk_count - 1);
        for (int i = 0; i < 10; i++) {
            setTarget(0b100, 0b100, rng.nextInt(chunk_count_x) * chunk_count_y + rng.nextInt(chunk_count_y));
        }
    }

    public static int approximateSurrounding() throws GameActionException {
        int sense_count = 10;
        float rubble_sum = 1000;
        MapLocation me = rc.getLocation();

        int yn = Math.min(me.y, 5); // how many steps can I go in the positive x direction without being outside.
        int yp = Math.min(max_Y - me.y - 1, 5);
        int xn = Math.min(me.x, 5);
        int xp = Math.min(max_X - me.x - 1, 5);

        switch (yn) {
            case 5:
                rubble_sum += rc.senseRubble(me.translate(0, -5));
                sense_count++;
            case 4:
            case 3:
            case 2:
            case 1:
                rubble_sum += rc.senseRubble(me.translate(0, -1));
                sense_count++;
        }
        switch (yp) {
            case 5:
                rubble_sum += rc.senseRubble(me.translate(0, 5));
                sense_count++;
            case 4:
            case 3:
            case 2:
            case 1:
                rubble_sum += rc.senseRubble(me.translate(0, 1));
                sense_count++;
        }
        switch (xn) {
            case 5:
                rubble_sum += rc.senseRubble(me.translate(-5, 0));
                sense_count++;
                switch (yn) {
                    case 5:
                    case 4:
                    case 3:
                        rubble_sum += rc.senseRubble(me.translate(-5, -3));
                        sense_count++;
                }
                switch (yp) {
                    case 5:
                    case 4:
                    case 3:
                        rubble_sum += rc.senseRubble(me.translate(-5, 3));
                        sense_count++;
                }
            case 4:
                switch (yn) {
                    case 5:
                    case 4:
                    case 3:
                    case 2:
                        rubble_sum += rc.senseRubble(me.translate(-4, -2));
                        sense_count++;
                }
                switch (yp) {
                    case 5:
                    case 4:
                    case 3:
                    case 2:
                        rubble_sum += rc.senseRubble(me.translate(-4, 2));
                        sense_count++;
                }
            case 3:
                switch (yn) {
                    case 5:
                        rubble_sum += rc.senseRubble(me.translate(-3, -5));
                        sense_count++;
                    case 4:
                    case 3:
                    case 2:
                    case 1:
                        rubble_sum += rc.senseRubble(me.translate(-3, -1));
                        sense_count++;
                }
                switch (yp) {
                    case 5:
                        rubble_sum += rc.senseRubble(me.translate(-3, 5));
                        sense_count++;
                    case 4:
                    case 3:
                    case 2:
                    case 1:
                        rubble_sum += rc.senseRubble(me.translate(-3, 1));
                        sense_count++;
                }
            case 2:
                switch (yn) {
                    case 5:
                    case 4:
                        rubble_sum += rc.senseRubble(me.translate(-2, -4));
                        sense_count++;
                    case 3:
                    case 2:
                        rubble_sum += rc.senseRubble(me.translate(-2, -2));
                        sense_count++;
                }
                switch (yp) {
                    case 5:
                    case 4:
                        rubble_sum += rc.senseRubble(me.translate(-2, 4));
                        sense_count++;
                    case 3:
                    case 2:
                        rubble_sum += rc.senseRubble(me.translate(-2, 2));
                        sense_count++;
                }
            case 1:
                rubble_sum += rc.senseRubble(me.translate(-1, 0));
                sense_count++;
                switch (yn) {
                    case 5:
                    case 4:
                    case 3:
                        rubble_sum += rc.senseRubble(me.translate(-1, -3));
                        sense_count++;
                }
                switch (yp) {
                    case 5:
                    case 4:
                    case 3:
                        rubble_sum += rc.senseRubble(me.translate(-1, 3));
                        sense_count++;
                }

        }
        switch (xp) {
            case 5:
                rubble_sum += rc.senseRubble(me.translate(5, 0));
                sense_count++;
                switch (yn) {
                    case 5:
                    case 4:
                    case 3:
                        rubble_sum += rc.senseRubble(me.translate(5, -3));
                        sense_count++;
                }
                switch (yp) {
                    case 5:
                    case 4:
                    case 3:
                        rubble_sum += rc.senseRubble(me.translate(5, 3));
                        sense_count++;
                }
            case 4:
                switch (yn) {
                    case 5:
                    case 4:
                    case 3:
                    case 2:
                        rubble_sum += rc.senseRubble(me.translate(4, -2));
                        sense_count++;
                }
                switch (yp) {
                    case 5:
                    case 4:
                    case 3:
                    case 2:
                        rubble_sum += rc.senseRubble(me.translate(4, 2));
                        sense_count++;
                }
            case 3:
                switch (yn) {
                    case 5:
                        rubble_sum += rc.senseRubble(me.translate(3, -5));
                        sense_count++;
                    case 4:
                    case 3:
                    case 2:
                    case 1:
                        rubble_sum += rc.senseRubble(me.translate(3, -1));
                        sense_count++;
                }
                switch (yp) {
                    case 5:
                        rubble_sum += rc.senseRubble(me.translate(3, 5));
                        sense_count++;
                    case 4:
                    case 3:
                    case 2:
                    case 1:
                        rubble_sum += rc.senseRubble(me.translate(3, 1));
                        sense_count++;
                }
            case 2:
                switch (yn) {
                    case 5:
                    case 4:
                        rubble_sum += rc.senseRubble(me.translate(2, -4));
                        sense_count++;
                    case 3:
                    case 2:
                        rubble_sum += rc.senseRubble(me.translate(2, -2));
                        sense_count++;
                }
                switch (yp) {
                    case 5:
                    case 4:
                        rubble_sum += rc.senseRubble(me.translate(2, 4));
                        sense_count++;
                    case 3:
                    case 2:
                        rubble_sum += rc.senseRubble(me.translate(2, 2));
                        sense_count++;
                }
            case 1:
                rubble_sum += rc.senseRubble(me.translate(1, 0));
                sense_count++;
                switch (yn) {
                    case 5:
                    case 4:
                    case 3:
                        rubble_sum += rc.senseRubble(me.translate(1, -3));
                        sense_count++;
                }
                switch (yp) {
                    case 5:
                    case 4:
                    case 3:
                        rubble_sum += rc.senseRubble(me.translate(1, 3));
                        sense_count++;
                }

        }

        return (int) (16 * Math.sqrt(rubble_sum / (100 * sense_count)));
    }

    public static void archonInterchange() throws GameActionException {
        int v = rc.readSharedArray(2);
        int env = approximateSurrounding();

        if (v == 0 || env < (v >> 12)) {
            Debug.p(env);
            rc.writeSharedArray(2, (env << 12) | (rc.getLocation().x << 6) | (rc.getLocation().y));
        }
    }

    public static MapLocation getMainArchonLoc() throws GameActionException {
        int v = rc.readSharedArray(2);
        return new MapLocation((v >> 6) & 0b111111, v & 0b111111);
    }

    public static void armyInterchange() throws GameActionException {
        int v = rc.readSharedArray(3);

        MapLocation loc = rc.getLocation();
        int soldier_count = getHeadcount(RobotType.SOLDIER);
        int n_x = (((v >> 8)) * soldier_count + 4 * loc.x) / (soldier_count + 1);
        int n_y = ((v & 0b11111111) * soldier_count + 4 * loc.y) / (soldier_count + 1);

        rc.writeSharedArray(3, (n_x << 8) | (n_y));
    }

    public static MapLocation getArmyLoc() throws GameActionException {
        int v = rc.readSharedArray(3);
        return new MapLocation((v >> 10), (v >> 2) & 0b111111);
    }

    public static void incrementHeadCount() throws GameActionException {
        if (death_registered) {
            int v;
            switch (rc.getType()) {
                case MINER:
                    v = rc.readSharedArray(0);
                    if ((v & 0b1111_1111) == 0b1111_1111) return; // avoid overflow
                    rc.writeSharedArray(0, v + 1);
                    break;
                case SOLDIER:
                    v = rc.readSharedArray(0);
                    if ((v & 0b1111_1111_0000_0000) == 0b1111_1111_0000_0000) return; // avoid overflow
                    rc.writeSharedArray(0, v + 0b1_0000_0000);
                    break;
                case BUILDER:
                    v = rc.readSharedArray(1);
                    if ((v & 0b1111_1111) == 0b1111_1111) return; // avoid overflow
                    rc.writeSharedArray(1, v + 1);
                    break;
                case WATCHTOWER:
                    v = rc.readSharedArray(1);
                    if ((v & 0b1111_1111_0000_0000) == 0b1111_1111_0000_0000) return; // avoid overflow
                    rc.writeSharedArray(1, v + 0b1_0000_0000);
                    break;
                default:
                    return;
            }
            death_registered = false;
        }
    }

    public static void decrementHeadcount() throws GameActionException {
        if (!death_registered) {
            int v;
            switch (rc.getType()) {
                case MINER:
                    v = rc.readSharedArray(0);
                    if ((v & 0b1111_1111) == 0) return; // avoid overflow
                    rc.writeSharedArray(0, v - 1);
                    break;
                case SOLDIER:
                    v = rc.readSharedArray(0);
                    if ((v & 0b1111_1111_0000_0000) == 0) return; // avoid overflow
                    rc.writeSharedArray(0, v - 0b1_0000_0000);
                    break;
                case BUILDER:
                    v = rc.readSharedArray(1);
                    if ((v & 0b1111_1111) == 0) return; // avoid overflow
                    rc.writeSharedArray(1, v - 1);
                    break;
                case WATCHTOWER:
                    v = rc.readSharedArray(1);
                    if ((v & 0b1111_1111_0000_0000) == 0) return; // avoid overflow
                    rc.writeSharedArray(1, v - 0b1_0000_0000);
                    break;
                default:
                    return;
            }
            death_registered = true;
        }
    }

    public static int getHeadcount(RobotType desired_type) throws GameActionException {
        switch (desired_type) {
            case MINER:
                return rc.readSharedArray(0) & 0b1111_1111;
            case SOLDIER:
                return rc.readSharedArray(0) >> 8;
            case BUILDER:
                return rc.readSharedArray(1) & 0b1111_1111;
            case WATCHTOWER:
                return rc.readSharedArray(1) >> 8;
            default:
                return 0;
        }
    }
}
