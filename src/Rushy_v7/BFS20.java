package Rushy_v7;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

import static Rushy_v7.Robot.max_X;
import static Rushy_v7.Robot.max_Y;

public class BFS20 extends BFS {

    public BFS20(RobotController r) {
        super(r);
    }

    public Direction BFSTo(MapLocation target_location) {
        MapLocation me=rc.getLocation();

        //region setting up gdc values
        int c_2 =100;
        int c_3 =100;
        int c_4 =100;
        int c_5 =100;
        int c_6 =100;
        int c_10=100;
        int c_11=100;
        int c_12=100;
        int c_13=100;
        int c_14=100;
        int c_15=100;
        int c_16=100;
        int c_18=100;
        int c_19=100;
        int c_20=100;
        int c_21=100;
        int c_22=100;
        int c_23=100;
        int c_24=100;
        int c_25=100;
        int c_26=100;
        int c_27=100;
        int c_28=100;
        int c_29=100;
        int c_30=100;
        int c_31=100;
        int c_32=100;
        int c_33=100;
        int c_34=100;
        int c_35=100;
        int c_36=100;
        int c_37=100;
        int c_38=100;
        int c_39=100;
//        int c_40=100;
        int c_41=100;
        int c_42=100;
        int c_43=100;
        int c_44=100;
        int c_45=100;
        int c_46=100;
        int c_47=100;
        int c_48=100;
        int c_49=100;
        int c_50=100;
        int c_51=100;
        int c_52=100;
        int c_53=100;
        int c_54=100;
        int c_55=100;
        int c_56=100;
        int c_57=100;
        int c_58=100;
        int c_59=100;
        int c_60=100;
        int c_61=100;
        int c_62=100;
        int c_64=100;
        int c_65=100;
        int c_66=100;
        int c_67=100;
        int c_68=100;
        int c_69=100;
        int c_70=100;
        int c_74=100;
        int c_75=100;
        int c_76=100;
        int c_77=100;
        int c_78=100;
        int g_40;
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
        //endregion

        //region sense rubble values
        try {
            int yn=Math.min(me.y,4);
            int yp=Math.min(max_Y-me.y-1,4);
            int xn=Math.min(me.x,4);
            int xp=Math.min(max_X-me.x-1,4);

//            c_40 = 10 + rc.senseRubble(me);
            switch(yn){
                case 4:c_36 = 10 + rc.senseRubble(me.translate(0,-4));
                case 3:c_37 = 10 + rc.senseRubble(me.translate(0,-3));
                case 2:c_38 = 10 + rc.senseRubble(me.translate(0,-2));
                case 1:c_39 = 10 + rc.senseRubble(me.translate(0,-1));
            }
            switch(yp){
                case 4:c_44 = 10 + rc.senseRubble(me.translate(0,4));
                case 3:c_43 = 10 + rc.senseRubble(me.translate(0,3));
                case 2:c_42 = 10 + rc.senseRubble(me.translate(0,2));
                case 1:c_41 = 10 + rc.senseRubble(me.translate(0,1));
            }
            switch(xn){
                case 4:
                    c_4 = 10 + rc.senseRubble(me.translate(-4,0));
                    switch(yn){
                        case 4:
                        case 3:
                        case 2:c_2 = 10 + rc.senseRubble(me.translate(-4,-2));
                        case 1:c_3 = 10 + rc.senseRubble(me.translate(-4,-1));
                    }
                    switch(yp){
                        case 4:
                        case 3:
                        case 2:c_6 = 10 + rc.senseRubble(me.translate(-4,2));
                        case 1:c_5 = 10 + rc.senseRubble(me.translate(-4,1));
                    }
                case 3:
                    c_13 = 10 + rc.senseRubble(me.translate(-3,0));
                    switch(yn){
                        case 4:
                        case 3:c_10 = 10 + rc.senseRubble(me.translate(-3,-3));
                        case 2:c_11 = 10 + rc.senseRubble(me.translate(-3,-2));
                        case 1:c_12 = 10 + rc.senseRubble(me.translate(-3,-1));
                    }
                    switch(yp){
                        case 4:
                        case 3:c_16 = 10 + rc.senseRubble(me.translate(-3,3));
                        case 2:c_15 = 10 + rc.senseRubble(me.translate(-3,2));
                        case 1:c_14 = 10 + rc.senseRubble(me.translate(-3,1));
                    }
                case 2:
                    c_22 = 10 + rc.senseRubble(me.translate(-2,0));
                    switch(yn){
                        case 4:c_18 = 10 + rc.senseRubble(me.translate(-2,-4));
                        case 3:c_19 = 10 + rc.senseRubble(me.translate(-2,-3));
                        case 2:c_20 = 10 + rc.senseRubble(me.translate(-2,-2));
                        case 1:c_21 = 10 + rc.senseRubble(me.translate(-2,-1));
                    }
                    switch(yp){
                        case 4:c_26 = 10 + rc.senseRubble(me.translate(-2,4));
                        case 3:c_25 = 10 + rc.senseRubble(me.translate(-2,3));
                        case 2:c_24 = 10 + rc.senseRubble(me.translate(-2,2));
                        case 1:c_23 = 10 + rc.senseRubble(me.translate(-2,1));
                    }
                case 1:
                    c_31 = 10 + rc.senseRubble(me.translate(-1,0));
                    switch(yn){
                        case 4:c_27 = 10 + rc.senseRubble(me.translate(-1,-4));
                        case 3:c_28 = 10 + rc.senseRubble(me.translate(-1,-3));
                        case 2:c_29 = 10 + rc.senseRubble(me.translate(-1,-2));
                        case 1:c_30 = 10 + rc.senseRubble(me.translate(-1,-1));
                    }
                    switch(yp){
                        case 4:c_35 = 10 + rc.senseRubble(me.translate(-1,4));
                        case 3:c_34 = 10 + rc.senseRubble(me.translate(-1,3));
                        case 2:c_33 = 10 + rc.senseRubble(me.translate(-1,2));
                        case 1:c_32 = 10 + rc.senseRubble(me.translate(-1,1));
                    }
            }
            switch(xp){
                case 4:
                    c_76 = 10 + rc.senseRubble(me.translate(4,0));
                    switch(yn){
                        case 4:
                        case 3:
                        case 2:c_74 = 10 + rc.senseRubble(me.translate(4,-2));
                        case 1:c_75 = 10 + rc.senseRubble(me.translate(4,-1));
                    }
                    switch(yp){
                        case 4:
                        case 3:
                        case 2:c_78 = 10 + rc.senseRubble(me.translate(4,2));
                        case 1:c_77 = 10 + rc.senseRubble(me.translate(4,1));
                    }
                case 3:
                    c_67 = 10 + rc.senseRubble(me.translate(3,0));
                    switch(yn){
                        case 4:
                        case 3:c_64 = 10 + rc.senseRubble(me.translate(3,-3));
                        case 2:c_65 = 10 + rc.senseRubble(me.translate(3,-2));
                        case 1:c_66 = 10 + rc.senseRubble(me.translate(3,-1));
                    }
                    switch(yp){
                        case 4:
                        case 3:c_70 = 10 + rc.senseRubble(me.translate(3,3));
                        case 2:c_69 = 10 + rc.senseRubble(me.translate(3,2));
                        case 1:c_68 = 10 + rc.senseRubble(me.translate(3,1));
                    }
                case 2:
                    c_58 = 10 + rc.senseRubble(me.translate(2,0));
                    switch(yn){
                        case 4:c_54 = 10 + rc.senseRubble(me.translate(2,-4));
                        case 3:c_55 = 10 + rc.senseRubble(me.translate(2,-3));
                        case 2:c_56 = 10 + rc.senseRubble(me.translate(2,-2));
                        case 1:c_57 = 10 + rc.senseRubble(me.translate(2,-1));
                    }
                    switch(yp){
                        case 4:c_62 = 10 + rc.senseRubble(me.translate(2,4));
                        case 3:c_61 = 10 + rc.senseRubble(me.translate(2,3));
                        case 2:c_60 = 10 + rc.senseRubble(me.translate(2,2));
                        case 1:c_59 = 10 + rc.senseRubble(me.translate(2,1));
                    }
                case 1:
                    c_49 = 10 + rc.senseRubble(me.translate(1,0));
                    switch(yn){
                        case 4:c_45 = 10 + rc.senseRubble(me.translate(1,-4));
                        case 3:c_46 = 10 + rc.senseRubble(me.translate(1,-3));
                        case 2:c_47 = 10 + rc.senseRubble(me.translate(1,-2));
                        case 1:c_48 = 10 + rc.senseRubble(me.translate(1,-1));
                    }
                    switch(yp){
                        case 4:c_53 = 10 + rc.senseRubble(me.translate(1,4));
                        case 3:c_52 = 10 + rc.senseRubble(me.translate(1,3));
                        case 2:c_51 = 10 + rc.senseRubble(me.translate(1,2));
                        case 1:c_50 = 10 + rc.senseRubble(me.translate(1,1));
                    }
            }
        } catch (GameActionException e) {
            Debug.p("sense rubble attempt to sense outside of map");
            e.printStackTrace();
        }
        //endregion

        //region setting shorthands for boundary check
        boolean yn4 = me.y >= 4;
        boolean yn3 = me.y >= 3;
        boolean yn2 = me.y >= 2;
        boolean yn1 = me.y >= 1;
        boolean yp1 = max_Y - me.y > 1;
        boolean yp2 = max_Y - me.y > 2;
        boolean yp3 = max_Y - me.y > 3;
        boolean yp4 = max_Y - me.y > 4;

        boolean xn4 = me.x >= 4;
        boolean xn3 = me.x >= 3;
        boolean xn2 = me.x >= 2;
        boolean xn1 = me.x >= 1;
        boolean xp1 = max_X - me.x > 1;
        boolean xp2 = max_X - me.x > 2;
        boolean xp3 = max_X - me.x > 3;
        boolean xp4 = max_X - me.x > 4;
        //endregion

        //region compare
        g_40 = 0;
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
        //endregion

        //region direct retrieval
        int dx = target_location.x - me.x;
        int dy = target_location.y - me.y;
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
        //endregion

        //region indirect retrieval
        double D_i = Math.sqrt(me.distanceSquaredTo(target_location)); // initial distance
        double HAR = -1; // highest average reward.
        Direction HAR_direction = null;
        double AR;

        AR = (D_i-Math.sqrt(Math.pow(dx + 4,2)+Math.pow(dy + 2,2)) )/g_2;
        if(AR>HAR){HAR=AR;HAR_direction=d_2;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 4,2)+Math.pow(dy + 1,2)) )/g_3;
        if(AR>HAR){HAR=AR;HAR_direction=d_3;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 4,2)+Math.pow(dy    ,2)) )/g_4;
        if(AR>HAR){HAR=AR;HAR_direction=d_4;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 4,2)+Math.pow(dy - 1,2)) )/g_5;
        if(AR>HAR){HAR=AR;HAR_direction=d_5;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 4,2)+Math.pow(dy - 2,2)) )/g_6;
        if(AR>HAR){HAR=AR;HAR_direction=d_6;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 3,2)+Math.pow(dy + 3,2)) )/g_10;
        if(AR>HAR){HAR=AR;HAR_direction=d_10;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 3,2)+Math.pow(dy + 2,2)) )/g_11;
        if(AR>HAR){HAR=AR;HAR_direction=d_11;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 3,2)+Math.pow(dy - 2,2)) )/g_15;
        if(AR>HAR){HAR=AR;HAR_direction=d_15;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 3,2)+Math.pow(dy - 3,2)) )/g_16;
        if(AR>HAR){HAR=AR;HAR_direction=d_16;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 2,2)+Math.pow(dy + 4,2)) )/g_18;
        if(AR>HAR){HAR=AR;HAR_direction=d_18;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 2,2)+Math.pow(dy + 3,2)) )/g_19;
        if(AR>HAR){HAR=AR;HAR_direction=d_19;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 2,2)+Math.pow(dy - 3,2)) )/g_25;
        if(AR>HAR){HAR=AR;HAR_direction=d_25;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 2,2)+Math.pow(dy - 4,2)) )/g_26;
        if(AR>HAR){HAR=AR;HAR_direction=d_26;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 1,2)+Math.pow(dy + 4,2)) )/g_27;
        if(AR>HAR){HAR=AR;HAR_direction=d_27;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 1,2)+Math.pow(dy - 4,2)) )/g_35;
        if(AR>HAR){HAR=AR;HAR_direction=d_35;}
        AR = (D_i-Math.sqrt(Math.pow(dx    ,2)+Math.pow(dy + 4,2)) )/g_36;
        if(AR>HAR){HAR=AR;HAR_direction=d_36;}
        AR = (D_i-Math.sqrt(Math.pow(dx    ,2)+Math.pow(dy - 4,2)) )/g_44;
        if(AR>HAR){HAR=AR;HAR_direction=d_44;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 1,2)+Math.pow(dy + 4,2)) )/g_45;
        if(AR>HAR){HAR=AR;HAR_direction=d_45;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 1,2)+Math.pow(dy - 4,2)) )/g_53;
        if(AR>HAR){HAR=AR;HAR_direction=d_53;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 2,2)+Math.pow(dy + 4,2)) )/g_54;
        if(AR>HAR){HAR=AR;HAR_direction=d_54;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 2,2)+Math.pow(dy + 3,2)) )/g_55;
        if(AR>HAR){HAR=AR;HAR_direction=d_55;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 2,2)+Math.pow(dy - 3,2)) )/g_61;
        if(AR>HAR){HAR=AR;HAR_direction=d_61;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 2,2)+Math.pow(dy - 4,2)) )/g_62;
        if(AR>HAR){HAR=AR;HAR_direction=d_62;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 3,2)+Math.pow(dy + 3,2)) )/g_64;
        if(AR>HAR){HAR=AR;HAR_direction=d_64;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 3,2)+Math.pow(dy + 2,2)) )/g_65;
        if(AR>HAR){HAR=AR;HAR_direction=d_65;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 3,2)+Math.pow(dy - 2,2)) )/g_69;
        if(AR>HAR){HAR=AR;HAR_direction=d_69;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 3,2)+Math.pow(dy - 3,2)) )/g_70;
        if(AR>HAR){HAR=AR;HAR_direction=d_70;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 4,2)+Math.pow(dy + 2,2)) )/g_74;
        if(AR>HAR){HAR=AR;HAR_direction=d_74;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 4,2)+Math.pow(dy + 1,2)) )/g_75;
        if(AR>HAR){HAR=AR;HAR_direction=d_75;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 4,2)+Math.pow(dy    ,2)) )/g_76;
        if(AR>HAR){HAR=AR;HAR_direction=d_76;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 4,2)+Math.pow(dy - 1,2)) )/g_77;
        if(AR>HAR){HAR=AR;HAR_direction=d_77;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 4,2)+Math.pow(dy - 2,2)) )/g_78;
        if(AR>HAR){HAR_direction=d_78;}

        return HAR_direction;
        //endregion

    }
}
