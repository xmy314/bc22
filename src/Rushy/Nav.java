package Rushy;

import BasicPlayer.utils.Debug;
import battlecode.common.*;

import static Rushy.Robot.*;

public class Nav {
    RobotController rc;
    boolean clockwise_rotation = false;
    boolean smartness;
    long[] visited;

    //region setting up c values
    int c_2;
    int c_3;
    int c_4;
    int c_5;
    int c_6;
    int c_10;
    int c_11;
    int c_12;
    int c_13;
    int c_14;
    int c_15;
    int c_16;
    int c_18;
    int c_19;
    int c_20;
    int c_21;
    int c_22;
    int c_23;
    int c_24;
    int c_25;
    int c_26;
    int c_27;
    int c_28;
    int c_29;
    int c_30;
    int c_31;
    int c_32;
    int c_33;
    int c_34;
    int c_35;
    int c_36;
    int c_37;
    int c_38;
    int c_39;
    int c_40;
    int c_41;
    int c_42;
    int c_43;
    int c_44;
    int c_45;
    int c_46;
    int c_47;
    int c_48;
    int c_49;
    int c_50;
    int c_51;
    int c_52;
    int c_53;
    int c_54;
    int c_55;
    int c_56;
    int c_57;
    int c_58;
    int c_59;
    int c_60;
    int c_61;
    int c_62;
    int c_64;
    int c_65;
    int c_66;
    int c_67;
    int c_68;
    int c_69;
    int c_70;
    int c_74;
    int c_75;
    int c_76;
    int c_77;
    int c_78;
    //endregion

    // the destruction from vortex is neglected as it's not worth dealing with.

    static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };

    static final Direction[] cardinal_direction = {
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST,
    };

    public Nav(RobotController r, boolean smartness) throws GameActionException {
        rc = r;

        this.smartness = smartness;
        if (smartness) {
            initRubble();
        }

    }

    public void initRubble() throws GameActionException {
        //531 bytecode
        MapLocation loc = rc.getLocation();

        boolean yn4 = loc.y >= 4;
        boolean yn3 = loc.y >= 3;
        boolean yn2 = loc.y >= 2;
        boolean yn1 = loc.y >= 1;
        boolean yp1 = max_Y - loc.y > 1;
        boolean yp2 = max_Y - loc.y > 2;
        boolean yp3 = max_Y - loc.y > 3;
        boolean yp4 = max_Y - loc.y > 4;

        if (loc.x >= 4) {
            if (yn2) c_2 = 10 + rc.senseRubble(loc.translate(-4, -2));
            if (yn1) c_3 = 10 + rc.senseRubble(loc.translate(-4, -1));
            c_4 = 10 + rc.senseRubble(loc.translate(-4, 0));
            if (yp1) c_5 = 10 + rc.senseRubble(loc.translate(-4, 1));
            if (yp2) c_6 = 10 + rc.senseRubble(loc.translate(-4, 2));
        }
        if (loc.x >= 3) {
            if (yn3) c_10 = 10 + rc.senseRubble(loc.translate(-3, -3));
            if (yn2) c_11 = 10 + rc.senseRubble(loc.translate(-3, -2));
            if (yn1) c_12 = 10 + rc.senseRubble(loc.translate(-3, -1));
            c_13 = 10 + rc.senseRubble(loc.translate(-3, 0));
            if (yp1) c_14 = 10 + rc.senseRubble(loc.translate(-3, 1));
            if (yp2) c_15 = 10 + rc.senseRubble(loc.translate(-3, 2));
            if (yp3) c_16 = 10 + rc.senseRubble(loc.translate(-3, 3));
        }
        if (loc.x >= 2) {
            if (yn4) c_18 = 10 + rc.senseRubble(loc.translate(-2, -4));
            if (yn3) c_19 = 10 + rc.senseRubble(loc.translate(-2, -3));
            if (yn2) c_20 = 10 + rc.senseRubble(loc.translate(-2, -2));
            if (yn1) c_21 = 10 + rc.senseRubble(loc.translate(-2, -1));
            c_22 = 10 + rc.senseRubble(loc.translate(-2, 0));
            if (yp1) c_23 = 10 + rc.senseRubble(loc.translate(-2, 1));
            if (yp2) c_24 = 10 + rc.senseRubble(loc.translate(-2, 2));
            if (yp3) c_25 = 10 + rc.senseRubble(loc.translate(-2, 3));
            if (yp4) c_26 = 10 + rc.senseRubble(loc.translate(-2, 4));
        }
        if (loc.x >= 1) {
            if (yn4) c_27 = 10 + rc.senseRubble(loc.translate(-1, -4));
            if (yn3) c_28 = 10 + rc.senseRubble(loc.translate(-1, -3));
            if (yn2) c_29 = 10 + rc.senseRubble(loc.translate(-1, -2));
            if (yn1) c_30 = 10 + rc.senseRubble(loc.translate(-1, -1));
            c_31 = 10 + rc.senseRubble(loc.translate(-1, 0));
            if (yp1) c_32 = 10 + rc.senseRubble(loc.translate(-1, 1));
            if (yp2) c_33 = 10 + rc.senseRubble(loc.translate(-1, 2));
            if (yp3) c_34 = 10 + rc.senseRubble(loc.translate(-1, 3));
            if (yp4) c_35 = 10 + rc.senseRubble(loc.translate(-1, 4));
        }
        if (yn4) c_36 = 10 + rc.senseRubble(loc.translate(0, -4));
        if (yn3) c_37 = 10 + rc.senseRubble(loc.translate(0, -3));
        if (yn2) c_38 = 10 + rc.senseRubble(loc.translate(0, -2));
        if (yn1) c_39 = 10 + rc.senseRubble(loc.translate(0, -1));
        c_40 = 10 + rc.senseRubble(loc);
        if (yp1) c_41 = 10 + rc.senseRubble(loc.translate(0, 1));
        if (yp2) c_42 = 10 + rc.senseRubble(loc.translate(0, 2));
        if (yp3) c_43 = 10 + rc.senseRubble(loc.translate(0, 3));
        if (yp4) c_44 = 10 + rc.senseRubble(loc.translate(0, 4));
        if (max_X - loc.x > 1) {
            if (yn4) c_45 = 10 + rc.senseRubble(loc.translate(1, -4));
            if (yn3) c_46 = 10 + rc.senseRubble(loc.translate(1, -3));
            if (yn2) c_47 = 10 + rc.senseRubble(loc.translate(1, -2));
            if (yn1) c_48 = 10 + rc.senseRubble(loc.translate(1, -1));
            c_49 = 10 + rc.senseRubble(loc.translate(1, 0));
            if (yp1) c_50 = 10 + rc.senseRubble(loc.translate(1, 1));
            if (yp2) c_51 = 10 + rc.senseRubble(loc.translate(1, 2));
            if (yp3) c_52 = 10 + rc.senseRubble(loc.translate(1, 3));
            if (yp4) c_53 = 10 + rc.senseRubble(loc.translate(1, 4));
        }
        if (max_X - loc.x > 2) {
            if (yn4) c_54 = 10 + rc.senseRubble(loc.translate(2, -4));
            if (yn3) c_55 = 10 + rc.senseRubble(loc.translate(2, -3));
            if (yn2) c_56 = 10 + rc.senseRubble(loc.translate(2, -2));
            if (yn1) c_57 = 10 + rc.senseRubble(loc.translate(2, -1));
            c_58 = 10 + rc.senseRubble(loc.translate(2, 0));
            if (yp1) c_59 = 10 + rc.senseRubble(loc.translate(2, 1));
            if (yp2) c_60 = 10 + rc.senseRubble(loc.translate(2, 2));
            if (yp3) c_61 = 10 + rc.senseRubble(loc.translate(2, 3));
            if (yp4) c_62 = 10 + rc.senseRubble(loc.translate(2, 4));
        }
        if (max_X - loc.x > 3) {
            if (yn3) c_64 = 10 + rc.senseRubble(loc.translate(3, -3));
            if (yn2) c_65 = 10 + rc.senseRubble(loc.translate(3, -2));
            if (yn1) c_66 = 10 + rc.senseRubble(loc.translate(3, -1));
            c_67 = 10 + rc.senseRubble(loc.translate(3, 0));
            if (yp1) c_68 = 10 + rc.senseRubble(loc.translate(3, 1));
            if (yp2) c_69 = 10 + rc.senseRubble(loc.translate(3, 2));
            if (yp3) c_70 = 10 + rc.senseRubble(loc.translate(3, 3));
        }
        if (max_X - loc.x > 4) {
            if (yn2) c_74 = 10 + rc.senseRubble(loc.translate(4, -2));
            if (yn1) c_75 = 10 + rc.senseRubble(loc.translate(4, -1));
            c_76 = 10 + rc.senseRubble(loc.translate(4, 0));
            if (yp1) c_77 = 10 + rc.senseRubble(loc.translate(4, 1));
            if (yp2) c_78 = 10 + rc.senseRubble(loc.translate(4, 2));
        }
    }

    public void updateRubble(Direction last_movement) throws GameActionException {
        // 450 for cardinal, 446 for none cardinal
        MapLocation loc = rc.getLocation();

        boolean yn4 = loc.y >= 4;
        boolean yn3 = loc.y >= 3;
        boolean yn2 = loc.y >= 2;
        boolean yn1 = loc.y >= 1;
        boolean yp1 = max_Y - loc.y > 1;
        boolean yp2 = max_Y - loc.y > 2;
        boolean yp3 = max_Y - loc.y > 3;
        boolean yp4 = max_Y - loc.y > 4;

        boolean xn4 = loc.x >= 4;
        boolean xn3 = loc.x >= 3;
        boolean xn2 = loc.x >= 2;
        boolean xn1 = loc.x >= 1;
        boolean xp1 = max_X - loc.x > 1;
        boolean xp2 = max_X - loc.x > 2;
        boolean xp3 = max_X - loc.x > 3;
        boolean xp4 = max_X - loc.x > 4;

        switch (last_movement) {
            case NORTH:
                // group move right to left, each group is down to up
                c_74 = c_75;
                c_75 = c_76;
                c_76 = c_77;
                c_77 = c_78;
                if (yp2 && xp4) c_78 = 10 + rc.senseRubble(loc.translate(4, 2));
                c_64 = c_65;
                c_65 = c_66;
                c_66 = c_67;
                c_67 = c_68;
                c_68 = c_69;
                c_69 = c_70;
                if (yp3 && xp3) c_70 = 10 + rc.senseRubble(loc.translate(3, 3));
                c_54 = c_55;
                c_55 = c_56;
                c_56 = c_57;
                c_57 = c_58;
                c_58 = c_59;
                c_59 = c_60;
                c_60 = c_61;
                c_61 = c_62;
                if (yp4 && xp2) c_62 = 10 + rc.senseRubble(loc.translate(2, 4));
                c_45 = c_46;
                c_46 = c_47;
                c_47 = c_48;
                c_48 = c_49;
                c_49 = c_50;
                c_50 = c_51;
                c_51 = c_52;
                c_52 = c_53;
                if (yp4 && xp1) c_53 = 10 + rc.senseRubble(loc.translate(1, 4));
                c_36 = c_37;
                c_37 = c_38;
                c_38 = c_39;
                c_39 = c_40;
                c_40 = c_41;
                c_41 = c_42;
                c_42 = c_43;
                c_43 = c_44;
                if (yp4) c_44 = 10 + rc.senseRubble(loc.translate(0, 4));
                c_27 = c_28;
                c_28 = c_29;
                c_29 = c_30;
                c_30 = c_31;
                c_31 = c_32;
                c_32 = c_33;
                c_33 = c_34;
                c_34 = c_35;
                if (yp4 && xn1) c_35 = 10 + rc.senseRubble(loc.translate(-1, 4));
                c_18 = c_19;
                c_19 = c_20;
                c_20 = c_21;
                c_21 = c_22;
                c_22 = c_23;
                c_23 = c_24;
                c_24 = c_25;
                c_25 = c_26;
                if (yp4 && xn2) c_26 = 10 + rc.senseRubble(loc.translate(-2, 4));
                c_10 = c_11;
                c_11 = c_12;
                c_12 = c_13;
                c_13 = c_14;
                c_14 = c_15;
                c_15 = c_16;
                if (yp3 && xn3) c_16 = 10 + rc.senseRubble(loc.translate(-3, 3));
                c_2 = c_3;
                c_3 = c_4;
                c_4 = c_5;
                c_5 = c_6;
                if (yp2 && xn4) c_6 = 10 + rc.senseRubble(loc.translate(-4, 2));
                break;
            case EAST:
                // group move down to up, each group is left to right
                c_18 = c_27;
                c_27 = c_36;
                c_36 = c_45;
                c_45 = c_54;
                if (yn4 && xp2) c_54 = 10 + rc.senseRubble(loc.translate(2, -4));
                c_10 = c_19;
                c_19 = c_28;
                c_28 = c_37;
                c_37 = c_46;
                c_46 = c_55;
                c_55 = c_64;
                if (yn3 && xp3) c_64 = 10 + rc.senseRubble(loc.translate(3, -3));
                c_2 = c_11;
                c_11 = c_20;
                c_20 = c_29;
                c_29 = c_38;
                c_38 = c_47;
                c_47 = c_56;
                c_56 = c_65;
                c_65 = c_74;
                if (yn2 && xp4) c_74 = 10 + rc.senseRubble(loc.translate(4, -2));
                c_3 = c_12;
                c_12 = c_21;
                c_21 = c_30;
                c_30 = c_39;
                c_39 = c_48;
                c_48 = c_57;
                c_57 = c_66;
                c_66 = c_75;
                if (yn1 && xp4) c_75 = 10 + rc.senseRubble(loc.translate(4, -1));
                c_4 = c_13;
                c_13 = c_22;
                c_22 = c_31;
                c_31 = c_40;
                c_40 = c_49;
                c_49 = c_58;
                c_58 = c_67;
                c_67 = c_76;
                if (xp4) c_76 = 10 + rc.senseRubble(loc.translate(4, 0));
                c_5 = c_14;
                c_14 = c_23;
                c_23 = c_32;
                c_32 = c_41;
                c_41 = c_50;
                c_50 = c_59;
                c_59 = c_68;
                c_68 = c_77;
                if (yp1 && xp4) c_77 = 10 + rc.senseRubble(loc.translate(4, 1));
                c_6 = c_15;
                c_15 = c_24;
                c_24 = c_33;
                c_33 = c_42;
                c_42 = c_51;
                c_51 = c_60;
                c_60 = c_69;
                c_69 = c_78;
                if (yp2 && xp4) c_78 = 10 + rc.senseRubble(loc.translate(4, 2));
                c_16 = c_25;
                c_25 = c_34;
                c_34 = c_43;
                c_43 = c_52;
                c_52 = c_61;
                c_61 = c_70;
                if (yp3 && xp3) c_70 = 10 + rc.senseRubble(loc.translate(3, 3));
                c_26 = c_35;
                c_35 = c_44;
                c_44 = c_53;
                c_53 = c_62;
                if (yp4 && xp2) c_62 = 10 + rc.senseRubble(loc.translate(2, 4));
                break;
            case SOUTH:
                // group move left to right, each group is up to down
                c_6 = c_5;
                c_5 = c_4;
                c_4 = c_3;
                c_3 = c_2;
                if (yn2 && xn4) c_2 = 10 + rc.senseRubble(loc.translate(-4, -2));
                c_16 = c_15;
                c_15 = c_14;
                c_14 = c_13;
                c_13 = c_12;
                c_12 = c_11;
                c_11 = c_10;
                if (yn3 && xn3) c_10 = 10 + rc.senseRubble(loc.translate(-3, -3));
                c_26 = c_25;
                c_25 = c_24;
                c_24 = c_23;
                c_23 = c_22;
                c_22 = c_21;
                c_21 = c_20;
                c_20 = c_19;
                c_19 = c_18;
                if (yn4 && xn2) c_18 = 10 + rc.senseRubble(loc.translate(-2, -4));
                c_35 = c_34;
                c_34 = c_33;
                c_33 = c_32;
                c_32 = c_31;
                c_31 = c_30;
                c_30 = c_29;
                c_29 = c_28;
                c_28 = c_27;
                if (yn4 && xn1) c_27 = 10 + rc.senseRubble(loc.translate(-1, -4));
                c_44 = c_43;
                c_43 = c_42;
                c_42 = c_41;
                c_41 = c_40;
                c_40 = c_39;
                c_39 = c_38;
                c_38 = c_37;
                c_37 = c_36;
                if (yn4) c_36 = 10 + rc.senseRubble(loc.translate(0, -4));
                c_53 = c_52;
                c_52 = c_51;
                c_51 = c_50;
                c_50 = c_49;
                c_49 = c_48;
                c_48 = c_47;
                c_47 = c_46;
                c_46 = c_45;
                if (yn4 && xp1) c_45 = 10 + rc.senseRubble(loc.translate(1, -4));
                c_62 = c_61;
                c_61 = c_60;
                c_60 = c_59;
                c_59 = c_58;
                c_58 = c_57;
                c_57 = c_56;
                c_56 = c_55;
                c_55 = c_54;
                if (yn4 && xp2) c_54 = 10 + rc.senseRubble(loc.translate(2, -4));
                c_70 = c_69;
                c_69 = c_68;
                c_68 = c_67;
                c_67 = c_66;
                c_66 = c_65;
                c_65 = c_64;
                if (yn3 && xp3) c_64 = 10 + rc.senseRubble(loc.translate(3, -3));
                c_78 = c_77;
                c_77 = c_76;
                c_76 = c_75;
                c_75 = c_74;
                if (yn2 && xp4) c_74 = 10 + rc.senseRubble(loc.translate(4, -2));
                break;
            case WEST:
                // up to down, r to l
                c_62 = c_53;
                c_53 = c_44;
                c_44 = c_35;
                c_35 = c_26;
                if (yp4 && xn2) c_26 = 10 + rc.senseRubble(loc.translate(-2, 4));
                c_70 = c_61;
                c_61 = c_52;
                c_52 = c_43;
                c_43 = c_34;
                c_34 = c_25;
                c_25 = c_16;
                if (yp3 && xn3) c_16 = 10 + rc.senseRubble(loc.translate(-3, 3));
                c_78 = c_69;
                c_69 = c_60;
                c_60 = c_51;
                c_51 = c_42;
                c_42 = c_33;
                c_33 = c_24;
                c_24 = c_15;
                c_15 = c_6;
                if (yp2 && xn4) c_6 = 10 + rc.senseRubble(loc.translate(-4, 2));
                c_77 = c_68;
                c_68 = c_59;
                c_59 = c_50;
                c_50 = c_41;
                c_41 = c_32;
                c_32 = c_23;
                c_23 = c_14;
                c_14 = c_5;
                if (yp1 && xn4) c_5 = 10 + rc.senseRubble(loc.translate(-4, 1));
                c_76 = c_67;
                c_67 = c_58;
                c_58 = c_49;
                c_49 = c_40;
                c_40 = c_31;
                c_31 = c_22;
                c_22 = c_13;
                c_13 = c_4;
                if (xn4) c_4 = 10 + rc.senseRubble(loc.translate(-4, 0));
                c_75 = c_66;
                c_66 = c_57;
                c_57 = c_48;
                c_48 = c_39;
                c_39 = c_30;
                c_30 = c_21;
                c_21 = c_12;
                c_12 = c_3;
                if (yn1 && xn4) c_3 = 10 + rc.senseRubble(loc.translate(-4, -1));
                c_74 = c_65;
                c_65 = c_56;
                c_56 = c_47;
                c_47 = c_38;
                c_38 = c_29;
                c_29 = c_20;
                c_20 = c_11;
                c_11 = c_2;
                if (yn2 && xn4) c_2 = 10 + rc.senseRubble(loc.translate(-4, -2));
                c_64 = c_55;
                c_55 = c_46;
                c_46 = c_37;
                c_37 = c_28;
                c_28 = c_19;
                c_19 = c_10;
                if (yn3 && xn3) c_10 = 10 + rc.senseRubble(loc.translate(-3, -3));
                c_54 = c_45;
                c_45 = c_36;
                c_36 = c_27;
                c_27 = c_18;
                if (yn4 && xn2) c_18 = 10 + rc.senseRubble(loc.translate(-2, -4));
                break;
            case NORTHEAST:
                c_54 = c_64;
                c_64 = c_74;
                if (yn2 && xp4) c_74 = 10 + rc.senseRubble(loc.translate(4, -2));
                c_45 = c_55;
                c_55 = c_65;
                c_65 = c_75;
                if (yn1 && xp4) c_75 = 10 + rc.senseRubble(loc.translate(4, -1));
                c_36 = c_46;
                c_46 = c_56;
                c_56 = c_66;
                c_66 = c_76;
                if (xp4) c_76 = 10 + rc.senseRubble(loc.translate(4, 0));
                c_27 = c_37;
                c_37 = c_47;
                c_47 = c_57;
                c_57 = c_67;
                c_67 = c_77;
                if (yp1 && xp4) c_77 = 10 + rc.senseRubble(loc.translate(4, 1));
                c_18 = c_28;
                c_28 = c_38;
                c_38 = c_48;
                c_48 = c_58;
                c_58 = c_68;
                c_68 = c_78;
                if (yp2 && xp4) c_78 = 10 + rc.senseRubble(loc.translate(4, 2));
                c_19 = c_29;
                c_29 = c_39;
                c_39 = c_49;
                c_49 = c_59;
                c_59 = c_69;
                if (yp2 && xp3) c_69 = 10 + rc.senseRubble(loc.translate(3, 2));
                c_10 = c_20;
                c_20 = c_30;
                c_30 = c_40;
                c_40 = c_50;
                c_50 = c_60;
                c_60 = c_70;
                if (yp3 && xp3) c_70 = 10 + rc.senseRubble(loc.translate(3, 3));
                c_11 = c_21;
                c_21 = c_31;
                c_31 = c_41;
                c_41 = c_51;
                c_51 = c_61;
                if (yp3 && xp2) c_61 = 10 + rc.senseRubble(loc.translate(2, 3));
                c_2 = c_12;
                c_12 = c_22;
                c_22 = c_32;
                c_32 = c_42;
                c_42 = c_52;
                c_52 = c_62;
                if (yp4 && xp2) c_62 = 10 + rc.senseRubble(loc.translate(2, 4));
                c_3 = c_13;
                c_13 = c_23;
                c_23 = c_33;
                c_33 = c_43;
                c_43 = c_53;
                if (yp4 && xp1) c_53 = 10 + rc.senseRubble(loc.translate(1, 4));
                c_4 = c_14;
                c_14 = c_24;
                c_24 = c_34;
                c_34 = c_44;
                if (yp4) c_44 = 10 + rc.senseRubble(loc.translate(0, 4));
                c_5 = c_15;
                c_15 = c_25;
                c_25 = c_35;
                if (yp4 && xn1) c_35 = 10 + rc.senseRubble(loc.translate(-1, 4));
                c_6 = c_16;
                c_16 = c_26;
                if (yp4 && xn2) c_26 = 10 + rc.senseRubble(loc.translate(-2, 4));
                break;
            case SOUTHEAST:
                c_2 = c_10;
                c_10 = c_18;
                if (yn4 && xn2) c_18 = 10 + rc.senseRubble(loc.translate(-2, -4));
                c_3 = c_11;
                c_11 = c_19;
                c_19 = c_27;
                if (yn4 && xn1) c_27 = 10 + rc.senseRubble(loc.translate(-1, -4));
                c_4 = c_12;
                c_12 = c_20;
                c_20 = c_28;
                c_28 = c_36;
                if (yn4) c_36 = 10 + rc.senseRubble(loc.translate(0, -4));
                c_5 = c_13;
                c_13 = c_21;
                c_21 = c_29;
                c_29 = c_37;
                c_37 = c_45;
                if (yn4 && xp1) c_45 = 10 + rc.senseRubble(loc.translate(1, -4));
                c_6 = c_14;
                c_14 = c_22;
                c_22 = c_30;
                c_30 = c_38;
                c_38 = c_46;
                c_46 = c_54;
                if (yn4 && xp2) c_54 = 10 + rc.senseRubble(loc.translate(2, -4));
                c_15 = c_23;
                c_23 = c_31;
                c_31 = c_39;
                c_39 = c_47;
                c_47 = c_55;
                if (yn3 && xp2) c_55 = 10 + rc.senseRubble(loc.translate(2, -3));
                c_16 = c_24;
                c_24 = c_32;
                c_32 = c_40;
                c_40 = c_48;
                c_48 = c_56;
                c_56 = c_64;
                if (yn3 && xp3) c_64 = 10 + rc.senseRubble(loc.translate(3, -3));
                c_25 = c_33;
                c_33 = c_41;
                c_41 = c_49;
                c_49 = c_57;
                c_57 = c_65;
                if (yn2 && xp3) c_65 = 10 + rc.senseRubble(loc.translate(3, -2));
                c_26 = c_34;
                c_34 = c_42;
                c_42 = c_50;
                c_50 = c_58;
                c_58 = c_66;
                c_66 = c_74;
                if (yn2 && xp4) c_74 = 10 + rc.senseRubble(loc.translate(4, -2));
                c_35 = c_43;
                c_43 = c_51;
                c_51 = c_59;
                c_59 = c_67;
                c_67 = c_75;
                if (yn1 && xp4) c_75 = 10 + rc.senseRubble(loc.translate(4, -1));
                c_44 = c_52;
                c_52 = c_60;
                c_60 = c_68;
                c_68 = c_76;
                if (xp4) c_76 = 10 + rc.senseRubble(loc.translate(4, 0));
                c_53 = c_61;
                c_61 = c_69;
                c_69 = c_77;
                if (yp1 && xp4) c_77 = 10 + rc.senseRubble(loc.translate(4, 1));
                c_62 = c_70;
                c_70 = c_78;
                if (yp2 && xp4) c_78 = 10 + rc.senseRubble(loc.translate(4, 2));
                break;
            case SOUTHWEST:
                c_26 = c_16;
                c_16 = c_6;
                if (yp2 && xn4) c_6 = 10 + rc.senseRubble(loc.translate(-4, 2));
                c_35 = c_25;
                c_25 = c_15;
                c_15 = c_5;
                if (yp1 && xn4) c_5 = 10 + rc.senseRubble(loc.translate(-4, 1));
                c_44 = c_34;
                c_34 = c_24;
                c_24 = c_14;
                c_14 = c_4;
                if (xn4) c_4 = 10 + rc.senseRubble(loc.translate(-4, 0));
                c_53 = c_43;
                c_43 = c_33;
                c_33 = c_23;
                c_23 = c_13;
                c_13 = c_3;
                if (yn1 && xn4) c_3 = 10 + rc.senseRubble(loc.translate(-4, -1));
                c_62 = c_52;
                c_52 = c_42;
                c_42 = c_32;
                c_32 = c_22;
                c_22 = c_12;
                c_12 = c_2;
                if (yn2 && xn4) c_2 = 10 + rc.senseRubble(loc.translate(-4, -2));
                c_61 = c_51;
                c_51 = c_41;
                c_41 = c_31;
                c_31 = c_21;
                c_21 = c_11;
                if (yn2 && xn3) c_11 = 10 + rc.senseRubble(loc.translate(-3, -2));
                c_70 = c_60;
                c_60 = c_50;
                c_50 = c_40;
                c_40 = c_30;
                c_30 = c_20;
                c_20 = c_10;
                if (yn3 && xn3) c_10 = 10 + rc.senseRubble(loc.translate(-3, -3));
                c_69 = c_59;
                c_59 = c_49;
                c_49 = c_39;
                c_39 = c_29;
                c_29 = c_19;
                if (yn3 && xn2) c_19 = 10 + rc.senseRubble(loc.translate(-2, -3));
                c_78 = c_68;
                c_68 = c_58;
                c_58 = c_48;
                c_48 = c_38;
                c_38 = c_28;
                c_28 = c_18;
                if (yn4 && xn2) c_18 = 10 + rc.senseRubble(loc.translate(-2, -4));
                c_77 = c_67;
                c_67 = c_57;
                c_57 = c_47;
                c_47 = c_37;
                c_37 = c_27;
                if (yn4 && xn1) c_27 = 10 + rc.senseRubble(loc.translate(-1, -4));
                c_76 = c_66;
                c_66 = c_56;
                c_56 = c_46;
                c_46 = c_36;
                if (yn4) c_36 = 10 + rc.senseRubble(loc.translate(0, -4));
                c_75 = c_65;
                c_65 = c_55;
                c_55 = c_45;
                if (yn4 && xp1) c_45 = 10 + rc.senseRubble(loc.translate(1, -4));
                c_74 = c_64;
                c_64 = c_54;
                if (yn4 && xp2) c_54 = 10 + rc.senseRubble(loc.translate(2, -4));
                break;
            case NORTHWEST:
                c_78 = c_70;
                c_70 = c_62;
                if (yp4 && xp2) c_62 = 10 + rc.senseRubble(loc.translate(2, 4));
                c_77 = c_69;
                c_69 = c_61;
                c_61 = c_53;
                if (yp4 && xp1) c_53 = 10 + rc.senseRubble(loc.translate(1, 4));
                c_76 = c_68;
                c_68 = c_60;
                c_60 = c_52;
                c_52 = c_44;
                if (yp4) c_44 = 10 + rc.senseRubble(loc.translate(0, 4));
                c_75 = c_67;
                c_67 = c_59;
                c_59 = c_51;
                c_51 = c_43;
                c_43 = c_35;
                if (yp4 && xn1) c_35 = 10 + rc.senseRubble(loc.translate(-1, 4));
                c_74 = c_66;
                c_66 = c_58;
                c_58 = c_50;
                c_50 = c_42;
                c_42 = c_34;
                c_34 = c_26;
                if (yp4 && xn2) c_26 = 10 + rc.senseRubble(loc.translate(-2, 4));
                c_65 = c_57;
                c_57 = c_49;
                c_49 = c_41;
                c_41 = c_33;
                c_33 = c_25;
                if (yp3 && xn2) c_25 = 10 + rc.senseRubble(loc.translate(-2, 3));
                c_64 = c_56;
                c_56 = c_48;
                c_48 = c_40;
                c_40 = c_32;
                c_32 = c_24;
                c_24 = c_16;
                if (yp3 && xn3) c_16 = 10 + rc.senseRubble(loc.translate(-3, 3));
                c_55 = c_47;
                c_47 = c_39;
                c_39 = c_31;
                c_31 = c_23;
                c_23 = c_15;
                if (yp2 && xn3) c_15 = 10 + rc.senseRubble(loc.translate(-3, 2));
                c_54 = c_46;
                c_46 = c_38;
                c_38 = c_30;
                c_30 = c_22;
                c_22 = c_14;
                c_14 = c_6;
                if (yp2 && xn4) c_6 = 10 + rc.senseRubble(loc.translate(-4, 2));
                c_45 = c_37;
                c_37 = c_29;
                c_29 = c_21;
                c_21 = c_13;
                c_13 = c_5;
                if (yp1 && xn4) c_5 = 10 + rc.senseRubble(loc.translate(-4, 1));
                c_36 = c_28;
                c_28 = c_20;
                c_20 = c_12;
                c_12 = c_4;
                if (xn4) c_4 = 10 + rc.senseRubble(loc.translate(-4, 0));
                c_27 = c_19;
                c_19 = c_11;
                c_11 = c_3;
                if (yn1 && xn4) c_3 = 10 + rc.senseRubble(loc.translate(-4, -1));
                c_18 = c_10;
                c_10 = c_2;
                if (yn2 && xn4) c_2 = 10 + rc.senseRubble(loc.translate(-4, -2));
                break;
        }
    }

    public Direction BFSTo(MapLocation reference) {
        // ~1800 bytecode.

        MapLocation loc = rc.getLocation();

        boolean yn4 = loc.y >= 4;
        boolean yn3 = loc.y >= 3;
        boolean yn2 = loc.y >= 2;
        boolean yn1 = loc.y >= 1;
        boolean yp1 = max_Y - loc.y > 1;
        boolean yp2 = max_Y - loc.y > 2;
        boolean yp3 = max_Y - loc.y > 3;
        boolean yp4 = max_Y - loc.y > 4;

        boolean xn4 = loc.x >= 4;
        boolean xn3 = loc.x >= 3;
        boolean xn2 = loc.x >= 2;
        boolean xn1 = loc.x >= 1;
        boolean xp1 = max_X - loc.x > 1;
        boolean xp2 = max_X - loc.x > 2;
        boolean xp3 = max_X - loc.x > 3;
        boolean xp4 = max_X - loc.x > 4;

        //this is made because referencing a array when unnecessary is too expensive. way too expensive.
        int g_2 = 1000000;
        Direction d_2 = null;
        int g_3 = 1000000;
        Direction d_3 = null;
        int g_4 = 1000000;
        Direction d_4 = null;
        int g_5 = 1000000;
        Direction d_5 = null;
        int g_6 = 1000000;
        Direction d_6 = null;
        int g_10 = 1000000;
        Direction d_10 = null;
        int g_11 = 1000000;
        Direction d_11 = null;
        int g_12 = 1000000;
        Direction d_12 = null;
        int g_13 = 1000000;
        Direction d_13 = null;
        int g_14 = 1000000;
        Direction d_14 = null;
        int g_15 = 1000000;
        Direction d_15 = null;
        int g_16 = 1000000;
        Direction d_16 = null;
        int g_18 = 1000000;
        Direction d_18 = null;
        int g_19 = 1000000;
        Direction d_19 = null;
        int g_20 = 1000000;
        Direction d_20 = null;
        int g_21 = 1000000;
        Direction d_21 = null;
        int g_22 = 1000000;
        Direction d_22 = null;
        int g_23 = 1000000;
        Direction d_23 = null;
        int g_24 = 1000000;
        Direction d_24 = null;
        int g_25 = 1000000;
        Direction d_25 = null;
        int g_26 = 1000000;
        Direction d_26 = null;
        int g_27 = 1000000;
        Direction d_27 = null;
        int g_28 = 1000000;
        Direction d_28 = null;
        int g_29 = 1000000;
        Direction d_29 = null;
        int g_30 = 1000000;
        Direction d_30 = null;
        int g_31 = 1000000;
        Direction d_31 = null;
        int g_32 = 1000000;
        Direction d_32 = null;
        int g_33 = 1000000;
        Direction d_33 = null;
        int g_34 = 1000000;
        Direction d_34 = null;
        int g_35 = 1000000;
        Direction d_35 = null;
        int g_36 = 1000000;
        Direction d_36 = null;
        int g_37 = 1000000;
        Direction d_37 = null;
        int g_38 = 1000000;
        Direction d_38 = null;
        int g_39 = 1000000;
        Direction d_39 = null;
        int g_40 = 0;
        Direction d_40 = null;
        int g_41 = 1000000;
        Direction d_41 = null;
        int g_42 = 1000000;
        Direction d_42 = null;
        int g_43 = 1000000;
        Direction d_43 = null;
        int g_44 = 1000000;
        Direction d_44 = null;
        int g_45 = 1000000;
        Direction d_45 = null;
        int g_46 = 1000000;
        Direction d_46 = null;
        int g_47 = 1000000;
        Direction d_47 = null;
        int g_48 = 1000000;
        Direction d_48 = null;
        int g_49 = 1000000;
        Direction d_49 = null;
        int g_50 = 1000000;
        Direction d_50 = null;
        int g_51 = 1000000;
        Direction d_51 = null;
        int g_52 = 1000000;
        Direction d_52 = null;
        int g_53 = 1000000;
        Direction d_53 = null;
        int g_54 = 1000000;
        Direction d_54 = null;
        int g_55 = 1000000;
        Direction d_55 = null;
        int g_56 = 1000000;
        Direction d_56 = null;
        int g_57 = 1000000;
        Direction d_57 = null;
        int g_58 = 1000000;
        Direction d_58 = null;
        int g_59 = 1000000;
        Direction d_59 = null;
        int g_60 = 1000000;
        Direction d_60 = null;
        int g_61 = 1000000;
        Direction d_61 = null;
        int g_62 = 1000000;
        Direction d_62 = null;
        int g_64 = 1000000;
        Direction d_64 = null;
        int g_65 = 1000000;
        Direction d_65 = null;
        int g_66 = 1000000;
        Direction d_66 = null;
        int g_67 = 1000000;
        Direction d_67 = null;
        int g_68 = 1000000;
        Direction d_68 = null;
        int g_69 = 1000000;
        Direction d_69 = null;
        int g_70 = 1000000;
        Direction d_70 = null;
        int g_74 = 1000000;
        Direction d_74 = null;
        int g_75 = 1000000;
        Direction d_75 = null;
        int g_76 = 1000000;
        Direction d_76 = null;
        int g_77 = 1000000;
        Direction d_77 = null;
        int g_78 = 1000000;
        Direction d_78 = null;

        if(rc.canMove(Direction.NORTH)){
            g_41 = g_40;d_41 = Direction.NORTH;
            g_41 += c_41;
        }
        if(rc.canMove(Direction.EAST)){
            g_49 = g_40;d_49 = Direction.EAST;
            if (g_49 > g_41) {g_49 = g_41;d_49 = d_41;}
            g_49 += c_49;
        }
        if(rc.canMove(Direction.WEST)){
            g_31 = g_40;d_31 = Direction.WEST;
            if (g_31 > g_41) {g_31 = g_41;d_31 = d_41;}
            g_31 += c_31;
        }
        if(rc.canMove(Direction.SOUTH)){
            g_39 = g_40;d_39 = Direction.SOUTH;
            if (g_39 > g_49) {g_39 = g_49;d_39 = d_49;}
            if (g_39 > g_31) {g_39 = g_31;d_39 = d_31;}
            g_39 += c_39;
        }
        if(rc.canMove(Direction.NORTHEAST)){
            g_50 = g_40;d_50 = Direction.NORTHEAST;
            if (g_50 > g_41) {g_50 = g_41;d_50 = d_41;}
            if (g_50 > g_49) {g_50 = g_49;d_50 = d_49;}
            g_50 += c_50;
        }
        if(rc.canMove(Direction.NORTHWEST)){
            g_32 = g_40;d_32 = Direction.NORTHWEST;
            if (g_32 > g_41) {g_32 = g_41;d_32 = d_41;}
            if (g_32 > g_31) {g_32 = g_31;d_32 = d_31;}
            g_32 += c_32;
        }
        if(rc.canMove(Direction.SOUTHEAST)){
            g_48 = g_40;d_48 = Direction.SOUTHEAST;
            if (g_48 > g_49) {g_48 = g_49;d_48 = d_49;}
            if (g_48 > g_39) {g_48 = g_39;d_48 = d_39;}
            g_48 += c_48;
        }
        if(rc.canMove(Direction.SOUTHWEST)){
            g_30 = g_40;d_30 = Direction.SOUTHWEST;
            if (g_30 > g_31) {g_30 = g_31;d_30 = d_31;}
            if (g_30 > g_39) {g_30 = g_39;d_30 = d_39;}
            g_30 += c_30;
        }
        if(yp2){
            g_42 = g_41;d_42 = d_41;
            if (g_42 > g_50) {g_42 = g_50;d_42 = d_50;}
            if (g_42 > g_32) {g_42 = g_32;d_42 = d_32;}
            g_42 += c_42;
        }
        if(xp2){
            g_58 = g_49;d_58 = d_49;
            if (g_58 > g_50) {g_58 = g_50;d_58 = d_50;}
            if (g_58 > g_48) {g_58 = g_48;d_58 = d_48;}
            g_58 += c_58;
        }
        if(xn2){
            g_22 = g_31;d_22 = d_31;
            if (g_22 > g_32) {g_22 = g_32;d_22 = d_32;}
            if (g_22 > g_30) {g_22 = g_30;d_22 = d_30;}
            g_22 += c_22;
        }
        if(yn2){
            g_38 = g_39;d_38 = d_39;
            if (g_38 > g_48) {g_38 = g_48;d_38 = d_48;}
            if (g_38 > g_30) {g_38 = g_30;d_38 = d_30;}
            g_38 += c_38;
        }
        if(yp2&&xp1){
            g_51 = g_41;d_51 = d_41;
            if (g_51 > g_50) {g_51 = g_50;d_51 = d_50;}
            if (g_51 > g_42) {g_51 = g_42;d_51 = d_42;}
            g_51 += c_51;
        }
        if(yp2&&xn1){
            g_33 = g_41;d_33 = d_41;
            if (g_33 > g_32) {g_33 = g_32;d_33 = d_32;}
            if (g_33 > g_42) {g_33 = g_42;d_33 = d_42;}
            g_33 += c_33;
        }
        if(yp1&&xp2){
            g_59 = g_49;d_59 = d_49;
            if (g_59 > g_50) {g_59 = g_50;d_59 = d_50;}
            if (g_59 > g_58) {g_59 = g_58;d_59 = d_58;}
            if (g_59 > g_51) {g_59 = g_51;d_59 = d_51;}
            g_59 += c_59;
        }
        if(yp1&&xn2){
            g_23 = g_31;d_23 = d_31;
            if (g_23 > g_32) {g_23 = g_32;d_23 = d_32;}
            if (g_23 > g_22) {g_23 = g_22;d_23 = d_22;}
            if (g_23 > g_33) {g_23 = g_33;d_23 = d_33;}
            g_23 += c_23;
        }
        if(yn1&&xp2){
            g_57 = g_49;d_57 = d_49;
            if (g_57 > g_48) {g_57 = g_48;d_57 = d_48;}
            if (g_57 > g_58) {g_57 = g_58;d_57 = d_58;}
            g_57 += c_57;
        }
        if(yn1&&xn2){
            g_21 = g_31;d_21 = d_31;
            if (g_21 > g_30) {g_21 = g_30;d_21 = d_30;}
            if (g_21 > g_22) {g_21 = g_22;d_21 = d_22;}
            g_21 += c_21;
        }
        if(yn2&&xp1){
            g_47 = g_39;d_47 = d_39;
            if (g_47 > g_48) {g_47 = g_48;d_47 = d_48;}
            if (g_47 > g_38) {g_47 = g_38;d_47 = d_38;}
            if (g_47 > g_57) {g_47 = g_57;d_47 = d_57;}
            g_47 += c_47;
        }
        if(yn2&&xn1){
            g_29 = g_39;d_29 = d_39;
            if (g_29 > g_30) {g_29 = g_30;d_29 = d_30;}
            if (g_29 > g_38) {g_29 = g_38;d_29 = d_38;}
            if (g_29 > g_21) {g_29 = g_21;d_29 = d_21;}
            g_29 += c_29;
        }
        if(yp2&&xp2){
            g_60 = g_50;d_60 = d_50;
            if (g_60 > g_51) {g_60 = g_51;d_60 = d_51;}
            if (g_60 > g_59) {g_60 = g_59;d_60 = d_59;}
            g_60 += c_60;
        }
        if(yp2&&xn2){
            g_24 = g_32;d_24 = d_32;
            if (g_24 > g_33) {g_24 = g_33;d_24 = d_33;}
            if (g_24 > g_23) {g_24 = g_23;d_24 = d_23;}
            g_24 += c_24;
        }
        if(yn2&&xp2){
            g_56 = g_48;d_56 = d_48;
            if (g_56 > g_57) {g_56 = g_57;d_56 = d_57;}
            if (g_56 > g_47) {g_56 = g_47;d_56 = d_47;}
            g_56 += c_56;
        }
        if(yn2&&xn2){
            g_20 = g_30;d_20 = d_30;
            if (g_20 > g_21) {g_20 = g_21;d_20 = d_21;}
            if (g_20 > g_29) {g_20 = g_29;d_20 = d_29;}
            g_20 += c_20;
        }
        if(yp3){
            g_43 = g_42;d_43 = d_42;
            if (g_43 > g_51) {g_43 = g_51;d_43 = d_51;}
            if (g_43 > g_33) {g_43 = g_33;d_43 = d_33;}
            g_43 += c_43;
        }
        if(xp3){
            g_67 = g_58;d_67 = d_58;
            if (g_67 > g_59) {g_67 = g_59;d_67 = d_59;}
            if (g_67 > g_57) {g_67 = g_57;d_67 = d_57;}
            g_67 += c_67;
        }
        if(xn3){
            g_13 = g_22;d_13 = d_22;
            if (g_13 > g_23) {g_13 = g_23;d_13 = d_23;}
            if (g_13 > g_21) {g_13 = g_21;d_13 = d_21;}
            g_13 += c_13;
        }
        if(yn3){
            g_37 = g_38;d_37 = d_38;
            if (g_37 > g_47) {g_37 = g_47;d_37 = d_47;}
            if (g_37 > g_29) {g_37 = g_29;d_37 = d_29;}
            g_37 += c_37;
        }
        if(yp3&&xp1){
            g_52 = g_42;d_52 = d_42;
            if (g_52 > g_51) {g_52 = g_51;d_52 = d_51;}
            if (g_52 > g_60) {g_52 = g_60;d_52 = d_60;}
            if (g_52 > g_43) {g_52 = g_43;d_52 = d_43;}
            g_52 += c_52;
        }
        if(yp3&&xn1){
            g_34 = g_42;d_34 = d_42;
            if (g_34 > g_33) {g_34 = g_33;d_34 = d_33;}
            if (g_34 > g_24) {g_34 = g_24;d_34 = d_24;}
            if (g_34 > g_43) {g_34 = g_43;d_34 = d_43;}
            g_34 += c_34;
        }
        if(yp1&&xp3){
            g_68 = g_58;d_68 = d_58;
            if (g_68 > g_59) {g_68 = g_59;d_68 = d_59;}
            if (g_68 > g_60) {g_68 = g_60;d_68 = d_60;}
            if (g_68 > g_67) {g_68 = g_67;d_68 = d_67;}
            g_68 += c_68;
        }
        if(yp1&&xn3){
            g_14 = g_22;d_14 = d_22;
            if (g_14 > g_23) {g_14 = g_23;d_14 = d_23;}
            if (g_14 > g_24) {g_14 = g_24;d_14 = d_24;}
            if (g_14 > g_13) {g_14 = g_13;d_14 = d_13;}
            g_14 += c_14;
        }
        if(yn1&&xp3){
            g_66 = g_58;d_66 = d_58;
            if (g_66 > g_57) {g_66 = g_57;d_66 = d_57;}
            if (g_66 > g_56) {g_66 = g_56;d_66 = d_56;}
            if (g_66 > g_67) {g_66 = g_67;d_66 = d_67;}
            g_66 += c_66;
        }
        if(yn1&&xn3){
            g_12 = g_22;d_12 = d_22;
            if (g_12 > g_21) {g_12 = g_21;d_12 = d_21;}
            if (g_12 > g_20) {g_12 = g_20;d_12 = d_20;}
            if (g_12 > g_13) {g_12 = g_13;d_12 = d_13;}
            g_12 += c_12;
        }
        if(yn3&&xp1){
            g_46 = g_38;d_46 = d_38;
            if (g_46 > g_47) {g_46 = g_47;d_46 = d_47;}
            if (g_46 > g_56) {g_46 = g_56;d_46 = d_56;}
            if (g_46 > g_37) {g_46 = g_37;d_46 = d_37;}
            g_46 += c_46;
        }
        if(yn3&&xn1){
            g_28 = g_38;d_28 = d_38;
            if (g_28 > g_29) {g_28 = g_29;d_28 = d_29;}
            if (g_28 > g_20) {g_28 = g_20;d_28 = d_20;}
            if (g_28 > g_37) {g_28 = g_37;d_28 = d_37;}
            g_28 += c_28;
        }
        if(yp3&&xp2){
            g_61 = g_51;d_61 = d_51;
            if (g_61 > g_60) {g_61 = g_60;d_61 = d_60;}
            if (g_61 > g_52) {g_61 = g_52;d_61 = d_52;}
            g_61 += c_61;
        }
        if(yp3&&xn2){
            g_25 = g_33;d_25 = d_33;
            if (g_25 > g_24) {g_25 = g_24;d_25 = d_24;}
            if (g_25 > g_34) {g_25 = g_34;d_25 = d_34;}
            g_25 += c_25;
        }
        if(yp2&&xp3){
            g_69 = g_59;d_69 = d_59;
            if (g_69 > g_60) {g_69 = g_60;d_69 = d_60;}
            if (g_69 > g_68) {g_69 = g_68;d_69 = d_68;}
            if (g_69 > g_61) {g_69 = g_61;d_69 = d_61;}
            g_69 += c_69;
        }
        if(yp2&&xn3){
            g_15 = g_23;d_15 = d_23;
            if (g_15 > g_24) {g_15 = g_24;d_15 = d_24;}
            if (g_15 > g_14) {g_15 = g_14;d_15 = d_14;}
            if (g_15 > g_25) {g_15 = g_25;d_15 = d_25;}
            g_15 += c_15;
        }
        if(yn2&&xp3){
            g_65 = g_57;d_65 = d_57;
            if (g_65 > g_56) {g_65 = g_56;d_65 = d_56;}
            if (g_65 > g_66) {g_65 = g_66;d_65 = d_66;}
            g_65 += c_65;
        }
        if(yn2&&xn3){
            g_11 = g_21;d_11 = d_21;
            if (g_11 > g_20) {g_11 = g_20;d_11 = d_20;}
            if (g_11 > g_12) {g_11 = g_12;d_11 = d_12;}
            g_11 += c_11;
        }
        if(yn3&&xp2){
            g_55 = g_47;d_55 = d_47;
            if (g_55 > g_56) {g_55 = g_56;d_55 = d_56;}
            if (g_55 > g_46) {g_55 = g_46;d_55 = d_46;}
            if (g_55 > g_65) {g_55 = g_65;d_55 = d_65;}
            g_55 += c_55;
        }
        if(yn3&&xn2){
            g_19 = g_29;d_19 = d_29;
            if (g_19 > g_20) {g_19 = g_20;d_19 = d_20;}
            if (g_19 > g_28) {g_19 = g_28;d_19 = d_28;}
            if (g_19 > g_11) {g_19 = g_11;d_19 = d_11;}
            g_19 += c_19;
        }
        if(yp4){
            g_44 = g_43;d_44 = d_43;
            if (g_44 > g_52) {g_44 = g_52;d_44 = d_52;}
            if (g_44 > g_34) {g_44 = g_34;d_44 = d_34;}
            g_44 += c_44;
        }
        if(xp4){
            g_76 = g_67;d_76 = d_67;
            if (g_76 > g_68) {g_76 = g_68;d_76 = d_68;}
            if (g_76 > g_66) {g_76 = g_66;d_76 = d_66;}
            g_76 += c_76;
        }
        if(xn4){
            g_4 = g_13;d_4 = d_13;
            if (g_4 > g_14) {g_4 = g_14;d_4 = d_14;}
            if (g_4 > g_12) {g_4 = g_12;d_4 = d_12;}
            g_4 += c_4;
        }
        if(yn4){
            g_36 = g_37;d_36 = d_37;
            if (g_36 > g_46) {g_36 = g_46;d_36 = d_46;}
            if (g_36 > g_28) {g_36 = g_28;d_36 = d_28;}
            g_36 += c_36;
        }
        if(yp4&&xp1){
            g_53 = g_43;d_53 = d_43;
            if (g_53 > g_52) {g_53 = g_52;d_53 = d_52;}
            if (g_53 > g_61) {g_53 = g_61;d_53 = d_61;}
            if (g_53 > g_44) {g_53 = g_44;d_53 = d_44;}
            g_53 += c_53;
        }
        if(yp4&&xn1){
            g_35 = g_43;d_35 = d_43;
            if (g_35 > g_34) {g_35 = g_34;d_35 = d_34;}
            if (g_35 > g_25) {g_35 = g_25;d_35 = d_25;}
            if (g_35 > g_44) {g_35 = g_44;d_35 = d_44;}
            g_35 += c_35;
        }
        if(yp1&&xp4){
            g_77 = g_67;d_77 = d_67;
            if (g_77 > g_68) {g_77 = g_68;d_77 = d_68;}
            if (g_77 > g_69) {g_77 = g_69;d_77 = d_69;}
            if (g_77 > g_76) {g_77 = g_76;d_77 = d_76;}
            g_77 += c_77;
        }
        if(yp1&&xn4){
            g_5 = g_13;d_5 = d_13;
            if (g_5 > g_14) {g_5 = g_14;d_5 = d_14;}
            if (g_5 > g_15) {g_5 = g_15;d_5 = d_15;}
            if (g_5 > g_4) {g_5 = g_4;d_5 = d_4;}
            g_5 += c_5;
        }
        if(yn1&&xp4){
            g_75 = g_67;d_75 = d_67;
            if (g_75 > g_66) {g_75 = g_66;d_75 = d_66;}
            if (g_75 > g_65) {g_75 = g_65;d_75 = d_65;}
            if (g_75 > g_76) {g_75 = g_76;d_75 = d_76;}
            g_75 += c_75;
        }
        if(yn1&&xn4){
            g_3 = g_13;d_3 = d_13;
            if (g_3 > g_12) {g_3 = g_12;d_3 = d_12;}
            if (g_3 > g_11) {g_3 = g_11;d_3 = d_11;}
            if (g_3 > g_4) {g_3 = g_4;d_3 = d_4;}
            g_3 += c_3;
        }
        if(yn4&&xp1){
            g_45 = g_37;d_45 = d_37;
            if (g_45 > g_46) {g_45 = g_46;d_45 = d_46;}
            if (g_45 > g_55) {g_45 = g_55;d_45 = d_55;}
            if (g_45 > g_36) {g_45 = g_36;d_45 = d_36;}
            g_45 += c_45;
        }
        if(yn4&&xn1){
            g_27 = g_37;d_27 = d_37;
            if (g_27 > g_28) {g_27 = g_28;d_27 = d_28;}
            if (g_27 > g_19) {g_27 = g_19;d_27 = d_19;}
            if (g_27 > g_36) {g_27 = g_36;d_27 = d_36;}
            g_27 += c_27;
        }
        if(yp3&&xp3){
            g_70 = g_60;d_70 = d_60;
            if (g_70 > g_61) {g_70 = g_61;d_70 = d_61;}
            if (g_70 > g_69) {g_70 = g_69;d_70 = d_69;}
            g_70 += c_70;
        }
        if(yp3&&xn3){
            g_16 = g_24;d_16 = d_24;
            if (g_16 > g_25) {g_16 = g_25;d_16 = d_25;}
            if (g_16 > g_15) {g_16 = g_15;d_16 = d_15;}
            g_16 += c_16;
        }
        if(yn3&&xp3){
            g_64 = g_56;d_64 = d_56;
            if (g_64 > g_65) {g_64 = g_65;d_64 = d_65;}
            if (g_64 > g_55) {g_64 = g_55;d_64 = d_55;}
            g_64 += c_64;
        }
        if(yn3&&xn3){
            g_10 = g_20;d_10 = d_20;
            if (g_10 > g_11) {g_10 = g_11;d_10 = d_11;}
            if (g_10 > g_19) {g_10 = g_19;d_10 = d_19;}
            g_10 += c_10;
        }
        if(yp4&&xp2){
            g_62 = g_52;d_62 = d_52;
            if (g_62 > g_61) {g_62 = g_61;d_62 = d_61;}
            if (g_62 > g_53) {g_62 = g_53;d_62 = d_53;}
            if (g_62 > g_70) {g_62 = g_70;d_62 = d_70;}
            g_62 += c_62;
        }
        if(yp4&&xn2){
            g_26 = g_34;d_26 = d_34;
            if (g_26 > g_25) {g_26 = g_25;d_26 = d_25;}
            if (g_26 > g_35) {g_26 = g_35;d_26 = d_35;}
            if (g_26 > g_16) {g_26 = g_16;d_26 = d_16;}
            g_26 += c_26;
        }
        if(yp2&&xp4){
            g_78 = g_68;d_78 = d_68;
            if (g_78 > g_69) {g_78 = g_69;d_78 = d_69;}
            if (g_78 > g_77) {g_78 = g_77;d_78 = d_77;}
            if (g_78 > g_70) {g_78 = g_70;d_78 = d_70;}
            g_78 += c_78;
        }
        if(yp2&&xn4){
            g_6 = g_14;d_6 = d_14;
            if (g_6 > g_15) {g_6 = g_15;d_6 = d_15;}
            if (g_6 > g_5) {g_6 = g_5;d_6 = d_5;}
            if (g_6 > g_16) {g_6 = g_16;d_6 = d_16;}
            g_6 += c_6;
        }
        if(yn2&&xp4){
            g_74 = g_66;d_74 = d_66;
            if (g_74 > g_65) {g_74 = g_65;d_74 = d_65;}
            if (g_74 > g_75) {g_74 = g_75;d_74 = d_75;}
            if (g_74 > g_64) {g_74 = g_64;d_74 = d_64;}
            g_74 += c_74;
        }
        if(yn2&&xn4){
            g_2 = g_12;d_2 = d_12;
            if (g_2 > g_11) {g_2 = g_11;d_2 = d_11;}
            if (g_2 > g_3) {g_2 = g_3;d_2 = d_3;}
            if (g_2 > g_10) {g_2 = g_10;d_2 = d_10;}
            g_2 += c_2;
        }
        if(yn4&&xp2){
            g_54 = g_46;d_54 = d_46;
            if (g_54 > g_55) {g_54 = g_55;d_54 = d_55;}
            if (g_54 > g_45) {g_54 = g_45;d_54 = d_45;}
            if (g_54 > g_64) {g_54 = g_64;d_54 = d_64;}
            g_54 += c_54;
        }
        if(yn4&&xn2){
            g_18 = g_28;d_18 = d_28;
            if (g_18 > g_19) {g_18 = g_19;d_18 = d_19;}
            if (g_18 > g_27) {g_18 = g_27;d_18 = d_27;}
            if (g_18 > g_10) {g_18 = g_10;d_18 = d_10;}
            g_18 += c_18;
        }

        if(d_2!=null)rc.setIndicatorLine(loc.translate(-4,-2),loc.translate(-4,-2).add(d_2),10,10,10);
        if(d_3!=null)rc.setIndicatorLine(loc.translate(-4,-1),loc.translate(-4,-1).add(d_3),10,10,10);
        if(d_4!=null)rc.setIndicatorLine(loc.translate(-4, 0),loc.translate(-4, 0).add(d_4),10,10,10);
        if(d_5!=null)rc.setIndicatorLine(loc.translate(-4, 1),loc.translate(-4, 1).add(d_5),10,10,10);
        if(d_6!=null)rc.setIndicatorLine(loc.translate(-4, 2),loc.translate(-4, 2).add(d_6),10,10,10);
        if(d_10!=null)rc.setIndicatorLine(loc.translate(-3,-3),loc.translate(-3,-3).add(d_10),10,10,10);
        if(d_11!=null)rc.setIndicatorLine(loc.translate(-3,-2),loc.translate(-3,-2).add(d_11),10,10,10);
        if(d_12!=null)rc.setIndicatorLine(loc.translate(-3,-1),loc.translate(-3,-1).add(d_12),10,10,10);
        if(d_13!=null)rc.setIndicatorLine(loc.translate(-3, 0),loc.translate(-3, 0).add(d_13),10,10,10);
        if(d_14!=null)rc.setIndicatorLine(loc.translate(-3, 1),loc.translate(-3, 1).add(d_14),10,10,10);
        if(d_15!=null)rc.setIndicatorLine(loc.translate(-3, 2),loc.translate(-3, 2).add(d_15),10,10,10);
        if(d_16!=null)rc.setIndicatorLine(loc.translate(-3, 3),loc.translate(-3, 3).add(d_16),10,10,10);
        if(d_18!=null)rc.setIndicatorLine(loc.translate(-2,-4),loc.translate(-2,-4).add(d_18),10,10,10);
        if(d_19!=null)rc.setIndicatorLine(loc.translate(-2,-3),loc.translate(-2,-3).add(d_19),10,10,10);
        if(d_20!=null)rc.setIndicatorLine(loc.translate(-2,-2),loc.translate(-2,-2).add(d_20),10,10,10);
        if(d_21!=null)rc.setIndicatorLine(loc.translate(-2,-1),loc.translate(-2,-1).add(d_21),10,10,10);
        if(d_22!=null)rc.setIndicatorLine(loc.translate(-2, 0),loc.translate(-2, 0).add(d_22),10,10,10);
        if(d_23!=null)rc.setIndicatorLine(loc.translate(-2, 1),loc.translate(-2, 1).add(d_23),10,10,10);
        if(d_24!=null)rc.setIndicatorLine(loc.translate(-2, 2),loc.translate(-2, 2).add(d_24),10,10,10);
        if(d_25!=null)rc.setIndicatorLine(loc.translate(-2, 3),loc.translate(-2, 3).add(d_25),10,10,10);
        if(d_26!=null)rc.setIndicatorLine(loc.translate(-2, 4),loc.translate(-2, 4).add(d_26),10,10,10);
        if(d_27!=null)rc.setIndicatorLine(loc.translate(-1,-4),loc.translate(-1,-4).add(d_27),10,10,10);
        if(d_28!=null)rc.setIndicatorLine(loc.translate(-1,-3),loc.translate(-1,-3).add(d_28),10,10,10);
        if(d_29!=null)rc.setIndicatorLine(loc.translate(-1,-2),loc.translate(-1,-2).add(d_29),10,10,10);
        if(d_30!=null)rc.setIndicatorLine(loc.translate(-1,-1),loc.translate(-1,-1).add(d_30),10,10,10);
        if(d_31!=null)rc.setIndicatorLine(loc.translate(-1, 0),loc.translate(-1, 0).add(d_31),10,10,10);
        if(d_32!=null)rc.setIndicatorLine(loc.translate(-1, 1),loc.translate(-1, 1).add(d_32),10,10,10);
        if(d_33!=null)rc.setIndicatorLine(loc.translate(-1, 2),loc.translate(-1, 2).add(d_33),10,10,10);
        if(d_34!=null)rc.setIndicatorLine(loc.translate(-1, 3),loc.translate(-1, 3).add(d_34),10,10,10);
        if(d_35!=null)rc.setIndicatorLine(loc.translate(-1, 4),loc.translate(-1, 4).add(d_35),10,10,10);
        if(d_36!=null)rc.setIndicatorLine(loc.translate( 0,-4),loc.translate( 0,-4).add(d_36),10,10,10);
        if(d_37!=null)rc.setIndicatorLine(loc.translate( 0,-3),loc.translate( 0,-3).add(d_37),10,10,10);
        if(d_38!=null)rc.setIndicatorLine(loc.translate( 0,-2),loc.translate( 0,-2).add(d_38),10,10,10);
        if(d_39!=null)rc.setIndicatorLine(loc.translate( 0,-1),loc.translate( 0,-1).add(d_39),10,10,10);
        if(d_40!=null)rc.setIndicatorLine(loc.translate( 0, 0),loc.translate( 0, 0).add(d_40),10,10,10);
        if(d_41!=null)rc.setIndicatorLine(loc.translate( 0, 1),loc.translate( 0, 1).add(d_41),10,10,10);
        if(d_42!=null)rc.setIndicatorLine(loc.translate( 0, 2),loc.translate( 0, 2).add(d_42),10,10,10);
        if(d_43!=null)rc.setIndicatorLine(loc.translate( 0, 3),loc.translate( 0, 3).add(d_43),10,10,10);
        if(d_44!=null)rc.setIndicatorLine(loc.translate( 0, 4),loc.translate( 0, 4).add(d_44),10,10,10);
        if(d_45!=null)rc.setIndicatorLine(loc.translate( 1,-4),loc.translate( 1,-4).add(d_45),10,10,10);
        if(d_46!=null)rc.setIndicatorLine(loc.translate( 1,-3),loc.translate( 1,-3).add(d_46),10,10,10);
        if(d_47!=null)rc.setIndicatorLine(loc.translate( 1,-2),loc.translate( 1,-2).add(d_47),10,10,10);
        if(d_48!=null)rc.setIndicatorLine(loc.translate( 1,-1),loc.translate( 1,-1).add(d_48),10,10,10);
        if(d_49!=null)rc.setIndicatorLine(loc.translate( 1, 0),loc.translate( 1, 0).add(d_49),10,10,10);
        if(d_50!=null)rc.setIndicatorLine(loc.translate( 1, 1),loc.translate( 1, 1).add(d_50),10,10,10);
        if(d_51!=null)rc.setIndicatorLine(loc.translate( 1, 2),loc.translate( 1, 2).add(d_51),10,10,10);
        if(d_52!=null)rc.setIndicatorLine(loc.translate( 1, 3),loc.translate( 1, 3).add(d_52),10,10,10);
        if(d_53!=null)rc.setIndicatorLine(loc.translate( 1, 4),loc.translate( 1, 4).add(d_53),10,10,10);
        if(d_54!=null)rc.setIndicatorLine(loc.translate( 2,-4),loc.translate( 2,-4).add(d_54),10,10,10);
        if(d_55!=null)rc.setIndicatorLine(loc.translate( 2,-3),loc.translate( 2,-3).add(d_55),10,10,10);
        if(d_56!=null)rc.setIndicatorLine(loc.translate( 2,-2),loc.translate( 2,-2).add(d_56),10,10,10);
        if(d_57!=null)rc.setIndicatorLine(loc.translate( 2,-1),loc.translate( 2,-1).add(d_57),10,10,10);
        if(d_58!=null)rc.setIndicatorLine(loc.translate( 2, 0),loc.translate( 2, 0).add(d_58),10,10,10);
        if(d_59!=null)rc.setIndicatorLine(loc.translate( 2, 1),loc.translate( 2, 1).add(d_59),10,10,10);
        if(d_60!=null)rc.setIndicatorLine(loc.translate( 2, 2),loc.translate( 2, 2).add(d_60),10,10,10);
        if(d_61!=null)rc.setIndicatorLine(loc.translate( 2, 3),loc.translate( 2, 3).add(d_61),10,10,10);
        if(d_62!=null)rc.setIndicatorLine(loc.translate( 2, 4),loc.translate( 2, 4).add(d_62),10,10,10);
        if(d_64!=null)rc.setIndicatorLine(loc.translate( 3,-3),loc.translate( 3,-3).add(d_64),10,10,10);
        if(d_65!=null)rc.setIndicatorLine(loc.translate( 3,-2),loc.translate( 3,-2).add(d_65),10,10,10);
        if(d_66!=null)rc.setIndicatorLine(loc.translate( 3,-1),loc.translate( 3,-1).add(d_66),10,10,10);
        if(d_67!=null)rc.setIndicatorLine(loc.translate( 3, 0),loc.translate( 3, 0).add(d_67),10,10,10);
        if(d_68!=null)rc.setIndicatorLine(loc.translate( 3, 1),loc.translate( 3, 1).add(d_68),10,10,10);
        if(d_69!=null)rc.setIndicatorLine(loc.translate( 3, 2),loc.translate( 3, 2).add(d_69),10,10,10);
        if(d_70!=null)rc.setIndicatorLine(loc.translate( 3, 3),loc.translate( 3, 3).add(d_70),10,10,10);
        if(d_74!=null)rc.setIndicatorLine(loc.translate( 4,-2),loc.translate( 4,-2).add(d_74),10,10,10);
        if(d_75!=null)rc.setIndicatorLine(loc.translate( 4,-1),loc.translate( 4,-1).add(d_75),10,10,10);
        if(d_76!=null)rc.setIndicatorLine(loc.translate( 4, 0),loc.translate( 4, 0).add(d_76),10,10,10);
        if(d_77!=null)rc.setIndicatorLine(loc.translate( 4, 1),loc.translate( 4, 1).add(d_77),10,10,10);
        if(d_78!=null)rc.setIndicatorLine(loc.translate( 4, 2),loc.translate( 4, 2).add(d_78),10,10,10);

        int dx = reference.x - loc.x;
        int dy = reference.y - loc.y;
        switch (dx) {
            case -4:
                switch (dy) {
                    case -2:
                        return d_2;
                    case -1:
                        return d_3;
                    case 0:
                        return d_4;
                    case 1:
                        return d_5;
                    case 2:
                        return d_6;
                }
                break;
            case -3:
                switch (dy) {
                    case -3:
                        return d_10;
                    case -2:
                        return d_11;
                    case -1:
                        return d_12;
                    case 0:
                        return d_13;
                    case 1:
                        return d_14;
                    case 2:
                        return d_15;
                    case 3:
                        return d_16;
                }
                break;
            case -2:
                switch (dy) {
                    case -4:
                        return d_18;
                    case -3:
                        return d_19;
                    case -2:
                        return d_20;
                    case -1:
                        return d_21;
                    case 0:
                        return d_22;
                    case 1:
                        return d_23;
                    case 2:
                        return d_24;
                    case 3:
                        return d_25;
                    case 4:
                        return d_26;
                }
                break;
            case -1:
                switch (dy) {
                    case -4:
                        return d_27;
                    case -3:
                        return d_28;
                    case -2:
                        return d_29;
                    case -1:
                        return d_30;
                    case 0:
                        return d_31;
                    case 1:
                        return d_32;
                    case 2:
                        return d_33;
                    case 3:
                        return d_34;
                    case 4:
                        return d_35;
                }
                break;
            case 0:
                switch (dy) {
                    case -4:
                        return d_36;
                    case -3:
                        return d_37;
                    case -2:
                        return d_38;
                    case -1:
                        return d_39;
                    case 0:
                        return Direction.CENTER;
                    case 1:
                        return d_41;
                    case 2:
                        return d_42;
                    case 3:
                        return d_43;
                    case 4:
                        return d_44;
                }
                break;
            case 1:
                switch (dy) {
                    case -4:
                        return d_45;
                    case -3:
                        return d_46;
                    case -2:
                        return d_47;
                    case -1:
                        return d_48;
                    case 0:
                        return d_49;
                    case 1:
                        return d_50;
                    case 2:
                        return d_51;
                    case 3:
                        return d_52;
                    case 4:
                        return d_53;
                }
                break;
            case 2:
                switch (dy) {
                    case -4:
                        return d_54;
                    case -3:
                        return d_55;
                    case -2:
                        return d_56;
                    case -1:
                        return d_57;
                    case 0:
                        return d_58;
                    case 1:
                        return d_59;
                    case 2:
                        return d_60;
                    case 3:
                        return d_61;
                    case 4:
                        return d_62;
                }
                break;
            case 3:
                switch (dy) {
                    case -3:
                        return d_64;
                    case -2:
                        return d_65;
                    case -1:
                        return d_66;
                    case 0:
                        return d_67;
                    case 1:
                        return d_68;
                    case 2:
                        return d_69;
                    case 3:
                        return d_70;
                }
                break;
            case 4:
                switch (dy) {
                    case -2:
                        return d_74;
                    case -1:
                        return d_75;
                    case 0:
                        return d_76;
                    case 1:
                        return d_77;
                    case 2:
                        return d_78;
                }
                break;
        }

        //D_i stands for initial distance
        //HAR stands for highest average reward
        double D_i=Math.sqrt(loc.distanceSquaredTo(reference));
        double HAR = 0;
        Direction HAR_direction = null;
        int basic_x = reference.x-loc.x;
        int basic_y = reference.y-loc.y;
        double AR;

        AR = (D_i-Math.sqrt(Math.pow(basic_x + 4,2)+Math.pow(basic_y + 2,2)) )/g_2;
        if(AR>HAR){HAR=AR;HAR_direction=d_2;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x + 4,2)+Math.pow(basic_y + 1,2)) )/g_3;
        if(AR>HAR){HAR=AR;HAR_direction=d_3;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x + 4,2)+Math.pow(basic_y    ,2)) )/g_4;
        if(AR>HAR){HAR=AR;HAR_direction=d_4;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x + 4,2)+Math.pow(basic_y - 1,2)) )/g_5;
        if(AR>HAR){HAR=AR;HAR_direction=d_5;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x + 4,2)+Math.pow(basic_y - 2,2)) )/g_6;
        if(AR>HAR){HAR=AR;HAR_direction=d_6;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x + 3,2)+Math.pow(basic_y + 3,2)) )/g_10;
        if(AR>HAR){HAR=AR;HAR_direction=d_10;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x + 3,2)+Math.pow(basic_y + 2,2)) )/g_11;
        if(AR>HAR){HAR=AR;HAR_direction=d_11;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x + 3,2)+Math.pow(basic_y - 2,2)) )/g_15;
        if(AR>HAR){HAR=AR;HAR_direction=d_15;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x + 3,2)+Math.pow(basic_y - 3,2)) )/g_16;
        if(AR>HAR){HAR=AR;HAR_direction=d_16;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x + 2,2)+Math.pow(basic_y + 4,2)) )/g_18;
        if(AR>HAR){HAR=AR;HAR_direction=d_18;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x + 2,2)+Math.pow(basic_y + 3,2)) )/g_19;
        if(AR>HAR){HAR=AR;HAR_direction=d_19;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x + 2,2)+Math.pow(basic_y - 3,2)) )/g_25;
        if(AR>HAR){HAR=AR;HAR_direction=d_25;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x + 2,2)+Math.pow(basic_y - 4,2)) )/g_26;
        if(AR>HAR){HAR=AR;HAR_direction=d_26;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x + 1,2)+Math.pow(basic_y + 4,2)) )/g_27;
        if(AR>HAR){HAR=AR;HAR_direction=d_27;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x + 1,2)+Math.pow(basic_y - 4,2)) )/g_35;
        if(AR>HAR){HAR=AR;HAR_direction=d_35;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x    ,2)+Math.pow(basic_y + 4,2)) )/g_36;
        if(AR>HAR){HAR=AR;HAR_direction=d_36;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x    ,2)+Math.pow(basic_y - 4,2)) )/g_44;
        if(AR>HAR){HAR=AR;HAR_direction=d_44;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x - 1,2)+Math.pow(basic_y + 4,2)) )/g_45;
        if(AR>HAR){HAR=AR;HAR_direction=d_45;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x - 1,2)+Math.pow(basic_y - 4,2)) )/g_53;
        if(AR>HAR){HAR=AR;HAR_direction=d_53;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x - 2,2)+Math.pow(basic_y + 4,2)) )/g_54;
        if(AR>HAR){HAR=AR;HAR_direction=d_54;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x - 2,2)+Math.pow(basic_y + 3,2)) )/g_55;
        if(AR>HAR){HAR=AR;HAR_direction=d_55;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x - 2,2)+Math.pow(basic_y - 3,2)) )/g_61;
        if(AR>HAR){HAR=AR;HAR_direction=d_61;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x - 2,2)+Math.pow(basic_y - 4,2)) )/g_62;
        if(AR>HAR){HAR=AR;HAR_direction=d_62;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x - 3,2)+Math.pow(basic_y + 3,2)) )/g_64;
        if(AR>HAR){HAR=AR;HAR_direction=d_64;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x - 3,2)+Math.pow(basic_y + 2,2)) )/g_65;
        if(AR>HAR){HAR=AR;HAR_direction=d_65;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x - 3,2)+Math.pow(basic_y - 2,2)) )/g_69;
        if(AR>HAR){HAR=AR;HAR_direction=d_69;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x - 3,2)+Math.pow(basic_y - 3,2)) )/g_70;
        if(AR>HAR){HAR=AR;HAR_direction=d_70;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x - 4,2)+Math.pow(basic_y + 2,2)) )/g_74;
        if(AR>HAR){HAR=AR;HAR_direction=d_74;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x - 4,2)+Math.pow(basic_y + 1,2)) )/g_75;
        if(AR>HAR){HAR=AR;HAR_direction=d_75;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x - 4,2)+Math.pow(basic_y    ,2)) )/g_76;
        if(AR>HAR){HAR=AR;HAR_direction=d_76;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x - 4,2)+Math.pow(basic_y - 1,2)) )/g_77;
        if(AR>HAR){HAR=AR;HAR_direction=d_77;}
        AR = (D_i-Math.sqrt(Math.pow(basic_x - 4,2)+Math.pow(basic_y - 2,2)) )/g_78;
        if(AR>HAR){HAR=AR;HAR_direction=d_78;}

        return HAR_direction;
    }

    public boolean moveWrapper(Direction bestDir) throws GameActionException {
        if (rc.canMove(bestDir)) {
            rc.move(bestDir);
            if (smartness) updateRubble(bestDir);
            return true;
        } else if (rc.canMove(bestDir.rotateRight())) {
            rc.move(bestDir.rotateRight());
            if (smartness) updateRubble(bestDir);
            return true;
        } else if (rc.canMove(bestDir.rotateLeft())) {
            rc.move(bestDir.rotateLeft());
            if (smartness) updateRubble(bestDir);
            return true;
        } else {
            return false;
        }
    }

    public boolean navigate(MapLocation reference) throws GameActionException {
        if (!rc.isMovementReady()) {return false;}
        rc.setIndicatorLine(rc.getLocation(),reference,100,120,110);

        Direction bestDir = (smartness)?BFSTo(reference):rc.getLocation().directionTo(reference);
        if(bestDir!=null) { // TODO: i don't get why it might be null.
            return moveWrapper(bestDir);
        }else{
            return false;
        }
    }

    public boolean disperseAround(RobotInfo[] nearby_ally_units) throws GameActionException {
        rc.setIndicatorDot(rc.getLocation(),100,100,100);
        // un predicable;
        if (!rc.isMovementReady()) return false;

        int ally_unit_count = 0;
        int lr = 0;
        int ud = 0;
        RobotType tb_avoid = rc.getType();
        for (int i = 0; i < nearby_ally_units.length && Clock.getBytecodesLeft() > 750; i++) {
            RobotInfo nearby_unit = nearby_ally_units[i];
            if (nearby_unit.getType() == tb_avoid) {
                ally_unit_count++;
                lr += (rc.getLocation().x > nearby_unit.location.x) ? 1 : -1;
                ud += (rc.getLocation().y > nearby_unit.location.y) ? 1 : -1;
            }
        }
        if (ally_unit_count >= 5 && (lr < -2 || lr > 2 || ud < -2 || ud > 2)) {
            Direction best_dir = rc.getLocation().directionTo(rc.getLocation().translate((10 * lr), (10 * ud)));
            return moveWrapper(best_dir);
        }
        return false;
    }

    public static MapLocation rotateAround(MapLocation origin, MapLocation toRotate, float offset) {
        return new MapLocation(origin.x - Math.round((toRotate.y - origin.y) * offset), origin.y + Math.round((toRotate.x - origin.x) * offset));
    }
}
