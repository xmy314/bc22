package Rushy_v9;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

import static Rushy_v9.Robot.*;

public class BFS34 extends BFS {

    public BFS34(RobotController r) {
        super(r);
    }

    public Direction BFSTo(MapLocation target_location) {
        MapLocation me = rc.getLocation();

        //region setting up gdc values
        int c_2 = 110;
        int c_3 = 110;
        int c_4 = 110;
        int c_5 = 110;
        int c_6 = 110;
        int c_7 = 110;
        int c_8 = 110;
        int c_12 = 110;
        int c_13 = 110;
        int c_14 = 110;
        int c_15 = 110;
        int c_16 = 110;
        int c_17 = 110;
        int c_18 = 110;
        int c_19 = 110;
        int c_20 = 110;
        int c_22 = 110;
        int c_23 = 110;
        int c_24 = 110;
        int c_25 = 110;
        int c_26 = 110;
        int c_27 = 110;
        int c_28 = 110;
        int c_29 = 110;
        int c_30 = 110;
        int c_31 = 110;
        int c_32 = 110;
        int c_33 = 110;
        int c_34 = 110;
        int c_35 = 110;
        int c_36 = 110;
        int c_37 = 110;
        int c_38 = 110;
        int c_39 = 110;
        int c_40 = 110;
        int c_41 = 110;
        int c_42 = 110;
        int c_43 = 110;
        int c_44 = 110;
        int c_45 = 110;
        int c_46 = 110;
        int c_47 = 110;
        int c_48 = 110;
        int c_49 = 110;
        int c_50 = 110;
        int c_51 = 110;
        int c_52 = 110;
        int c_53 = 110;
        int c_54 = 110;
        int c_55 = 110;
        int c_56 = 110;
        int c_57 = 110;
        int c_58 = 110;
        int c_59 = 110;
        int c_61 = 110;
        int c_62 = 110;
        int c_63 = 110;
        int c_64 = 110;
        int c_65 = 110;
        int c_66 = 110;
        int c_67 = 110;
        int c_68 = 110;
        int c_69 = 110;
        int c_70 = 110;
        int c_71 = 110;
        int c_72 = 110;
        int c_73 = 110;
        int c_74 = 110;
        int c_75 = 110;
        int c_76 = 110;
        int c_77 = 110;
        int c_78 = 110;
        int c_79 = 110;
        int c_80 = 110;
        int c_81 = 110;
        int c_82 = 110;
        int c_83 = 110;
        int c_84 = 110;
        int c_85 = 110;
        int c_86 = 110;
        int c_87 = 110;
        int c_88 = 110;
        int c_89 = 110;
        int c_90 = 110;
        int c_91 = 110;
        int c_92 = 110;
        int c_93 = 110;
        int c_94 = 110;
        int c_95 = 110;
        int c_96 = 110;
        int c_97 = 110;
        int c_98 = 110;
        int c_100 = 110;
        int c_101 = 110;
        int c_102 = 110;
        int c_103 = 110;
        int c_104 = 110;
        int c_105 = 110;
        int c_106 = 110;
        int c_107 = 110;
        int c_108 = 110;
        int c_112 = 110;
        int c_113 = 110;
        int c_114 = 110;
        int c_115 = 110;
        int c_116 = 110;
        int c_117 = 110;
        int c_118 = 110;
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
        int g_7 = 1000000;
        Direction d_7 = null;
        int g_8 = 1000000;
        Direction d_8 = null;
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
        int g_17 = 1000000;
        Direction d_17 = null;
        int g_18 = 1000000;
        Direction d_18 = null;
        int g_19 = 1000000;
        Direction d_19 = null;
        int g_20 = 1000000;
        Direction d_20 = null;
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
        int g_40 = 1000000;
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
        int g_60;
        Direction d_60 = null;
        int g_61 = 1000000;
        Direction d_61 = null;
        int g_62 = 1000000;
        Direction d_62 = null;
        int g_63 = 1000000;
        Direction d_63 = null;
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
        int g_71 = 1000000;
        Direction d_71 = null;
        int g_72 = 1000000;
        Direction d_72 = null;
        int g_73 = 1000000;
        Direction d_73 = null;
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
        int g_79 = 1000000;
        Direction d_79 = null;
        int g_80 = 1000000;
        Direction d_80 = null;
        int g_81 = 1000000;
        Direction d_81 = null;
        int g_82 = 1000000;
        Direction d_82 = null;
        int g_83 = 1000000;
        Direction d_83 = null;
        int g_84 = 1000000;
        Direction d_84 = null;
        int g_85 = 1000000;
        Direction d_85 = null;
        int g_86 = 1000000;
        Direction d_86 = null;
        int g_87 = 1000000;
        Direction d_87 = null;
        int g_88 = 1000000;
        Direction d_88 = null;
        int g_89 = 1000000;
        Direction d_89 = null;
        int g_90 = 1000000;
        Direction d_90 = null;
        int g_91 = 1000000;
        Direction d_91 = null;
        int g_92 = 1000000;
        Direction d_92 = null;
        int g_93 = 1000000;
        Direction d_93 = null;
        int g_94 = 1000000;
        Direction d_94 = null;
        int g_95 = 1000000;
        Direction d_95 = null;
        int g_96 = 1000000;
        Direction d_96 = null;
        int g_97 = 1000000;
        Direction d_97 = null;
        int g_98 = 1000000;
        Direction d_98 = null;
        int g_100 = 1000000;
        Direction d_100 = null;
        int g_101 = 1000000;
        Direction d_101 = null;
        int g_102 = 1000000;
        Direction d_102 = null;
        int g_103 = 1000000;
        Direction d_103 = null;
        int g_104 = 1000000;
        Direction d_104 = null;
        int g_105 = 1000000;
        Direction d_105 = null;
        int g_106 = 1000000;
        Direction d_106 = null;
        int g_107 = 1000000;
        Direction d_107 = null;
        int g_108 = 1000000;
        Direction d_108 = null;
        int g_112 = 1000000;
        Direction d_112 = null;
        int g_113 = 1000000;
        Direction d_113 = null;
        int g_114 = 1000000;
        Direction d_114 = null;
        int g_115 = 1000000;
        Direction d_115 = null;
        int g_116 = 1000000;
        Direction d_116 = null;
        int g_117 = 1000000;
        Direction d_117 = null;
        int g_118 = 1000000;
        Direction d_118 = null;
        //endregion

        //region sense rubble values
        try {
            int yn = Math.min(me.y, 5);
            int yp = Math.min(max_Y - me.y - 1, 5);
            int xn = Math.min(me.x, 5);
            int xp = Math.min(max_X - me.x - 1, 5);

            switch (yn) {
                case 5:
                    c_55 = 10 + rc.senseRubble(me.translate(0, -5));
                case 4:
                    c_56 = 10 + rc.senseRubble(me.translate(0, -4));
                case 3:
                    c_57 = 10 + rc.senseRubble(me.translate(0, -3));
                case 2:
                    c_58 = 10 + rc.senseRubble(me.translate(0, -2));
                case 1:
                    c_59 = 10 + rc.senseRubble(me.translate(0, -1));
            }
            switch (yp) {
                case 5:
                    c_65 = 10 + rc.senseRubble(me.translate(0, 5));
                case 4:
                    c_64 = 10 + rc.senseRubble(me.translate(0, 4));
                case 3:
                    c_63 = 10 + rc.senseRubble(me.translate(0, 3));
                case 2:
                    c_62 = 10 + rc.senseRubble(me.translate(0, 2));
                case 1:
                    c_61 = 10 + rc.senseRubble(me.translate(0, 1));
            }
            switch (xn) {
                case 5:
                    c_5 = 10 + rc.senseRubble(me.translate(-5, 0));
                    switch (yn) {
                        case 5:
                        case 4:
                        case 3:
                            c_2 = 10 + rc.senseRubble(me.translate(-5, -3));
                        case 2:
                            c_3 = 10 + rc.senseRubble(me.translate(-5, -2));
                        case 1:
                            c_4 = 10 + rc.senseRubble(me.translate(-5, -1));
                    }
                    switch (yp) {
                        case 5:
                        case 4:
                        case 3:
                            c_8 = 10 + rc.senseRubble(me.translate(-5, 3));
                        case 2:
                            c_7 = 10 + rc.senseRubble(me.translate(-5, 2));
                        case 1:
                            c_6 = 10 + rc.senseRubble(me.translate(-5, 1));
                    }
                case 4:
                    c_16 = 10 + rc.senseRubble(me.translate(-4, 0));
                    switch (yn) {
                        case 5:
                        case 4:
                            c_12 = 10 + rc.senseRubble(me.translate(-4, -4));
                        case 3:
                            c_13 = 10 + rc.senseRubble(me.translate(-4, -3));
                        case 2:
                            c_14 = 10 + rc.senseRubble(me.translate(-4, -2));
                        case 1:
                            c_15 = 10 + rc.senseRubble(me.translate(-4, -1));
                    }
                    switch (yp) {
                        case 5:
                        case 4:
                            c_20 = 10 + rc.senseRubble(me.translate(-4, 4));
                        case 3:
                            c_19 = 10 + rc.senseRubble(me.translate(-4, 3));
                        case 2:
                            c_18 = 10 + rc.senseRubble(me.translate(-4, 2));
                        case 1:
                            c_17 = 10 + rc.senseRubble(me.translate(-4, 1));
                    }
                case 3:
                    c_27 = 10 + rc.senseRubble(me.translate(-3, 0));
                    switch (yn) {
                        case 5:
                            c_22 = 10 + rc.senseRubble(me.translate(-3, -5));
                        case 4:
                            c_23 = 10 + rc.senseRubble(me.translate(-3, -4));
                        case 3:
                            c_24 = 10 + rc.senseRubble(me.translate(-3, -3));
                        case 2:
                            c_25 = 10 + rc.senseRubble(me.translate(-3, -2));
                        case 1:
                            c_26 = 10 + rc.senseRubble(me.translate(-3, -1));
                    }
                    switch (yp) {
                        case 5:
                            c_32 = 10 + rc.senseRubble(me.translate(-3, 5));
                        case 4:
                            c_31 = 10 + rc.senseRubble(me.translate(-3, 4));
                        case 3:
                            c_30 = 10 + rc.senseRubble(me.translate(-3, 3));
                        case 2:
                            c_29 = 10 + rc.senseRubble(me.translate(-3, 2));
                        case 1:
                            c_28 = 10 + rc.senseRubble(me.translate(-3, 1));
                    }
                case 2:
                    c_38 = 10 + rc.senseRubble(me.translate(-2, 0));
                    switch (yn) {
                        case 5:
                            c_33 = 10 + rc.senseRubble(me.translate(-2, -5));
                        case 4:
                            c_34 = 10 + rc.senseRubble(me.translate(-2, -4));
                        case 3:
                            c_35 = 10 + rc.senseRubble(me.translate(-2, -3));
                        case 2:
                            c_36 = 10 + rc.senseRubble(me.translate(-2, -2));
                        case 1:
                            c_37 = 10 + rc.senseRubble(me.translate(-2, -1));
                    }
                    switch (yp) {
                        case 5:
                            c_43 = 10 + rc.senseRubble(me.translate(-2, 5));
                        case 4:
                            c_42 = 10 + rc.senseRubble(me.translate(-2, 4));
                        case 3:
                            c_41 = 10 + rc.senseRubble(me.translate(-2, 3));
                        case 2:
                            c_40 = 10 + rc.senseRubble(me.translate(-2, 2));
                        case 1:
                            c_39 = 10 + rc.senseRubble(me.translate(-2, 1));
                    }
                case 1:
                    c_49 = 10 + rc.senseRubble(me.translate(-1, 0));
                    switch (yn) {
                        case 5:
                            c_44 = 10 + rc.senseRubble(me.translate(-1, -5));
                        case 4:
                            c_45 = 10 + rc.senseRubble(me.translate(-1, -4));
                        case 3:
                            c_46 = 10 + rc.senseRubble(me.translate(-1, -3));
                        case 2:
                            c_47 = 10 + rc.senseRubble(me.translate(-1, -2));
                        case 1:
                            c_48 = 10 + rc.senseRubble(me.translate(-1, -1));
                    }
                    switch (yp) {
                        case 5:
                            c_54 = 10 + rc.senseRubble(me.translate(-1, 5));
                        case 4:
                            c_53 = 10 + rc.senseRubble(me.translate(-1, 4));
                        case 3:
                            c_52 = 10 + rc.senseRubble(me.translate(-1, 3));
                        case 2:
                            c_51 = 10 + rc.senseRubble(me.translate(-1, 2));
                        case 1:
                            c_50 = 10 + rc.senseRubble(me.translate(-1, 1));
                    }
            }
            switch (xp) {
                case 5:
                    c_115 = 10 + rc.senseRubble(me.translate(5, 0));
                    switch (yn) {
                        case 5:
                        case 4:
                        case 3:
                            c_112 = 10 + rc.senseRubble(me.translate(5, -3));
                        case 2:
                            c_113 = 10 + rc.senseRubble(me.translate(5, -2));
                        case 1:
                            c_114 = 10 + rc.senseRubble(me.translate(5, -1));
                    }
                    switch (yp) {
                        case 5:
                        case 4:
                        case 3:
                            c_118 = 10 + rc.senseRubble(me.translate(5, 3));
                        case 2:
                            c_117 = 10 + rc.senseRubble(me.translate(5, 2));
                        case 1:
                            c_116 = 10 + rc.senseRubble(me.translate(5, 1));
                    }
                case 4:
                    c_104 = 10 + rc.senseRubble(me.translate(4, 0));
                    switch (yn) {
                        case 5:
                        case 4:
                            c_100 = 10 + rc.senseRubble(me.translate(4, -4));
                        case 3:
                            c_101 = 10 + rc.senseRubble(me.translate(4, -3));
                        case 2:
                            c_102 = 10 + rc.senseRubble(me.translate(4, -2));
                        case 1:
                            c_103 = 10 + rc.senseRubble(me.translate(4, -1));
                    }
                    switch (yp) {
                        case 5:
                        case 4:
                            c_108 = 10 + rc.senseRubble(me.translate(4, 4));
                        case 3:
                            c_107 = 10 + rc.senseRubble(me.translate(4, 3));
                        case 2:
                            c_106 = 10 + rc.senseRubble(me.translate(4, 2));
                        case 1:
                            c_105 = 10 + rc.senseRubble(me.translate(4, 1));
                    }
                case 3:
                    c_93 = 10 + rc.senseRubble(me.translate(3, 0));
                    switch (yn) {
                        case 5:
                            c_88 = 10 + rc.senseRubble(me.translate(3, -5));
                        case 4:
                            c_89 = 10 + rc.senseRubble(me.translate(3, -4));
                        case 3:
                            c_90 = 10 + rc.senseRubble(me.translate(3, -3));
                        case 2:
                            c_91 = 10 + rc.senseRubble(me.translate(3, -2));
                        case 1:
                            c_92 = 10 + rc.senseRubble(me.translate(3, -1));
                    }
                    switch (yp) {
                        case 5:
                            c_98 = 10 + rc.senseRubble(me.translate(3, 5));
                        case 4:
                            c_97 = 10 + rc.senseRubble(me.translate(3, 4));
                        case 3:
                            c_96 = 10 + rc.senseRubble(me.translate(3, 3));
                        case 2:
                            c_95 = 10 + rc.senseRubble(me.translate(3, 2));
                        case 1:
                            c_94 = 10 + rc.senseRubble(me.translate(3, 1));
                    }
                case 2:
                    c_82 = 10 + rc.senseRubble(me.translate(2, 0));
                    switch (yn) {
                        case 5:
                            c_77 = 10 + rc.senseRubble(me.translate(2, -5));
                        case 4:
                            c_78 = 10 + rc.senseRubble(me.translate(2, -4));
                        case 3:
                            c_79 = 10 + rc.senseRubble(me.translate(2, -3));
                        case 2:
                            c_80 = 10 + rc.senseRubble(me.translate(2, -2));
                        case 1:
                            c_81 = 10 + rc.senseRubble(me.translate(2, -1));
                    }
                    switch (yp) {
                        case 5:
                            c_87 = 10 + rc.senseRubble(me.translate(2, 5));
                        case 4:
                            c_86 = 10 + rc.senseRubble(me.translate(2, 4));
                        case 3:
                            c_85 = 10 + rc.senseRubble(me.translate(2, 3));
                        case 2:
                            c_84 = 10 + rc.senseRubble(me.translate(2, 2));
                        case 1:
                            c_83 = 10 + rc.senseRubble(me.translate(2, 1));
                    }
                case 1:
                    c_71 = 10 + rc.senseRubble(me.translate(1, 0));
                    switch (yn) {
                        case 5:
                            c_66 = 10 + rc.senseRubble(me.translate(1, -5));
                        case 4:
                            c_67 = 10 + rc.senseRubble(me.translate(1, -4));
                        case 3:
                            c_68 = 10 + rc.senseRubble(me.translate(1, -3));
                        case 2:
                            c_69 = 10 + rc.senseRubble(me.translate(1, -2));
                        case 1:
                            c_70 = 10 + rc.senseRubble(me.translate(1, -1));
                    }
                    switch (yp) {
                        case 5:
                            c_76 = 10 + rc.senseRubble(me.translate(1, 5));
                        case 4:
                            c_75 = 10 + rc.senseRubble(me.translate(1, 4));
                        case 3:
                            c_74 = 10 + rc.senseRubble(me.translate(1, 3));
                        case 2:
                            c_73 = 10 + rc.senseRubble(me.translate(1, 2));
                        case 1:
                            c_72 = 10 + rc.senseRubble(me.translate(1, 1));
                    }
            }
        } catch (GameActionException e) {
            Debug.p("sense rubble attempt to sense outside of map");
            e.printStackTrace();
        }
        //endregion

        //region setting shorthands for boundary check
        boolean yn5 = me.y >= 5;
        boolean yn4 = me.y >= 4;
        boolean yn3 = me.y >= 3;
        boolean yn2 = me.y >= 2;
        boolean yn1 = me.y >= 1;
        boolean yp1 = max_Y - me.y > 1;
        boolean yp2 = max_Y - me.y > 2;
        boolean yp3 = max_Y - me.y > 3;
        boolean yp4 = max_Y - me.y > 4;
        boolean yp5 = max_Y - me.y > 5;

        boolean xn5 = me.x >= 5;
        boolean xn4 = me.x >= 4;
        boolean xn3 = me.x >= 3;
        boolean xn2 = me.x >= 2;
        boolean xn1 = me.x >= 1;
        boolean xp1 = max_X - me.x > 1;
        boolean xp2 = max_X - me.x > 2;
        boolean xp3 = max_X - me.x > 3;
        boolean xp4 = max_X - me.x > 4;
        boolean xp5 = max_X - me.x > 5;
        //endregion

        //region compare
        g_60 = 0;
        if(rc.canMove(Direction.NORTH)){
            g_61 = g_60;d_61 = Direction.NORTH;
            g_61 += c_61;
        }
        if(rc.canMove(Direction.EAST)){
            g_71 = g_60;d_71 = Direction.EAST;
            if (g_71 > g_61) {g_71 = g_61;d_71 = d_61;}
            g_71 += c_71;
        }
        if(rc.canMove(Direction.WEST)){
            g_49 = g_60;d_49 = Direction.WEST;
            if (g_49 > g_61) {g_49 = g_61;d_49 = d_61;}
            g_49 += c_49;
        }
        if(rc.canMove(Direction.SOUTH)){
            g_59 = g_60;d_59 = Direction.SOUTH;
            if (g_59 > g_71) {g_59 = g_71;d_59 = d_71;}
            if (g_59 > g_49) {g_59 = g_49;d_59 = d_49;}
            g_59 += c_59;
        }
        if(rc.canMove(Direction.NORTHEAST)){
            g_72 = g_60;d_72 = Direction.NORTHEAST;
            if (g_72 > g_61) {g_72 = g_61;d_72 = d_61;}
            if (g_72 > g_71) {g_72 = g_71;d_72 = d_71;}
            g_72 += c_72;
        }
        if(rc.canMove(Direction.NORTHWEST)){
            g_50 = g_60;d_50 = Direction.NORTHWEST;
            if (g_50 > g_61) {g_50 = g_61;d_50 = d_61;}
            if (g_50 > g_49) {g_50 = g_49;d_50 = d_49;}
            g_50 += c_50;
        }
        if(rc.canMove(Direction.SOUTHEAST)){
            g_70 = g_60;d_70 = Direction.SOUTHEAST;
            if (g_70 > g_71) {g_70 = g_71;d_70 = d_71;}
            if (g_70 > g_59) {g_70 = g_59;d_70 = d_59;}
            g_70 += c_70;
        }
        if(rc.canMove(Direction.SOUTHWEST)){
            g_48 = g_60;d_48 = Direction.SOUTHWEST;
            if (g_48 > g_49) {g_48 = g_49;d_48 = d_49;}
            if (g_48 > g_59) {g_48 = g_59;d_48 = d_59;}
            g_48 += c_48;
        }
        if(yp2){
            g_62 = g_61;d_62 = d_61;
            if (g_62 > g_72) {g_62 = g_72;d_62 = d_72;}
            if (g_62 > g_50) {g_62 = g_50;d_62 = d_50;}
            g_62 += c_62;
        }
        if(xp2){
            g_82 = g_71;d_82 = d_71;
            if (g_82 > g_72) {g_82 = g_72;d_82 = d_72;}
            if (g_82 > g_70) {g_82 = g_70;d_82 = d_70;}
            g_82 += c_82;
        }
        if(xn2){
            g_38 = g_49;d_38 = d_49;
            if (g_38 > g_50) {g_38 = g_50;d_38 = d_50;}
            if (g_38 > g_48) {g_38 = g_48;d_38 = d_48;}
            g_38 += c_38;
        }
        if(yn2){
            g_58 = g_59;d_58 = d_59;
            if (g_58 > g_70) {g_58 = g_70;d_58 = d_70;}
            if (g_58 > g_48) {g_58 = g_48;d_58 = d_48;}
            g_58 += c_58;
        }
        if(yp2&&xp1){
            g_73 = g_61;d_73 = d_61;
            if (g_73 > g_72) {g_73 = g_72;d_73 = d_72;}
            if (g_73 > g_62) {g_73 = g_62;d_73 = d_62;}
            g_73 += c_73;
        }
        if(yp2&&xn1){
            g_51 = g_61;d_51 = d_61;
            if (g_51 > g_50) {g_51 = g_50;d_51 = d_50;}
            if (g_51 > g_62) {g_51 = g_62;d_51 = d_62;}
            g_51 += c_51;
        }
        if(yp1&&xp2){
            g_83 = g_71;d_83 = d_71;
            if (g_83 > g_72) {g_83 = g_72;d_83 = d_72;}
            if (g_83 > g_82) {g_83 = g_82;d_83 = d_82;}
            if (g_83 > g_73) {g_83 = g_73;d_83 = d_73;}
            g_83 += c_83;
        }
        if(yp1&&xn2){
            g_39 = g_49;d_39 = d_49;
            if (g_39 > g_50) {g_39 = g_50;d_39 = d_50;}
            if (g_39 > g_38) {g_39 = g_38;d_39 = d_38;}
            if (g_39 > g_51) {g_39 = g_51;d_39 = d_51;}
            g_39 += c_39;
        }
        if(yn1&&xp2){
            g_81 = g_71;d_81 = d_71;
            if (g_81 > g_70) {g_81 = g_70;d_81 = d_70;}
            if (g_81 > g_82) {g_81 = g_82;d_81 = d_82;}
            g_81 += c_81;
        }
        if(yn1&&xn2){
            g_37 = g_49;d_37 = d_49;
            if (g_37 > g_48) {g_37 = g_48;d_37 = d_48;}
            if (g_37 > g_38) {g_37 = g_38;d_37 = d_38;}
            g_37 += c_37;
        }
        if(yn2&&xp1){
            g_69 = g_59;d_69 = d_59;
            if (g_69 > g_70) {g_69 = g_70;d_69 = d_70;}
            if (g_69 > g_58) {g_69 = g_58;d_69 = d_58;}
            if (g_69 > g_81) {g_69 = g_81;d_69 = d_81;}
            g_69 += c_69;
        }
        if(yn2&&xn1){
            g_47 = g_59;d_47 = d_59;
            if (g_47 > g_48) {g_47 = g_48;d_47 = d_48;}
            if (g_47 > g_58) {g_47 = g_58;d_47 = d_58;}
            if (g_47 > g_37) {g_47 = g_37;d_47 = d_37;}
            g_47 += c_47;
        }
        if(yp2&&xp2){
            g_84 = g_72;d_84 = d_72;
            if (g_84 > g_73) {g_84 = g_73;d_84 = d_73;}
            if (g_84 > g_83) {g_84 = g_83;d_84 = d_83;}
            g_84 += c_84;
        }
        if(yp2&&xn2){
            g_40 = g_50;d_40 = d_50;
            if (g_40 > g_51) {g_40 = g_51;d_40 = d_51;}
            if (g_40 > g_39) {g_40 = g_39;d_40 = d_39;}
            g_40 += c_40;
        }
        if(yn2&&xp2){
            g_80 = g_70;d_80 = d_70;
            if (g_80 > g_81) {g_80 = g_81;d_80 = d_81;}
            if (g_80 > g_69) {g_80 = g_69;d_80 = d_69;}
            g_80 += c_80;
        }
        if(yn2&&xn2){
            g_36 = g_48;d_36 = d_48;
            if (g_36 > g_37) {g_36 = g_37;d_36 = d_37;}
            if (g_36 > g_47) {g_36 = g_47;d_36 = d_47;}
            g_36 += c_36;
        }
        if(yp3){
            g_63 = g_62;d_63 = d_62;
            if (g_63 > g_73) {g_63 = g_73;d_63 = d_73;}
            if (g_63 > g_51) {g_63 = g_51;d_63 = d_51;}
            g_63 += c_63;
        }
        if(xp3){
            g_93 = g_82;d_93 = d_82;
            if (g_93 > g_83) {g_93 = g_83;d_93 = d_83;}
            if (g_93 > g_81) {g_93 = g_81;d_93 = d_81;}
            g_93 += c_93;
        }
        if(xn3){
            g_27 = g_38;d_27 = d_38;
            if (g_27 > g_39) {g_27 = g_39;d_27 = d_39;}
            if (g_27 > g_37) {g_27 = g_37;d_27 = d_37;}
            g_27 += c_27;
        }
        if(yn3){
            g_57 = g_58;d_57 = d_58;
            if (g_57 > g_69) {g_57 = g_69;d_57 = d_69;}
            if (g_57 > g_47) {g_57 = g_47;d_57 = d_47;}
            g_57 += c_57;
        }
        if(yp3&&xp1){
            g_74 = g_62;d_74 = d_62;
            if (g_74 > g_73) {g_74 = g_73;d_74 = d_73;}
            if (g_74 > g_84) {g_74 = g_84;d_74 = d_84;}
            if (g_74 > g_63) {g_74 = g_63;d_74 = d_63;}
            g_74 += c_74;
        }
        if(yp3&&xn1){
            g_52 = g_62;d_52 = d_62;
            if (g_52 > g_51) {g_52 = g_51;d_52 = d_51;}
            if (g_52 > g_40) {g_52 = g_40;d_52 = d_40;}
            if (g_52 > g_63) {g_52 = g_63;d_52 = d_63;}
            g_52 += c_52;
        }
        if(yp1&&xp3){
            g_94 = g_82;d_94 = d_82;
            if (g_94 > g_83) {g_94 = g_83;d_94 = d_83;}
            if (g_94 > g_84) {g_94 = g_84;d_94 = d_84;}
            if (g_94 > g_93) {g_94 = g_93;d_94 = d_93;}
            g_94 += c_94;
        }
        if(yp1&&xn3){
            g_28 = g_38;d_28 = d_38;
            if (g_28 > g_39) {g_28 = g_39;d_28 = d_39;}
            if (g_28 > g_40) {g_28 = g_40;d_28 = d_40;}
            if (g_28 > g_27) {g_28 = g_27;d_28 = d_27;}
            g_28 += c_28;
        }
        if(yn1&&xp3){
            g_92 = g_82;d_92 = d_82;
            if (g_92 > g_81) {g_92 = g_81;d_92 = d_81;}
            if (g_92 > g_80) {g_92 = g_80;d_92 = d_80;}
            if (g_92 > g_93) {g_92 = g_93;d_92 = d_93;}
            g_92 += c_92;
        }
        if(yn1&&xn3){
            g_26 = g_38;d_26 = d_38;
            if (g_26 > g_37) {g_26 = g_37;d_26 = d_37;}
            if (g_26 > g_36) {g_26 = g_36;d_26 = d_36;}
            if (g_26 > g_27) {g_26 = g_27;d_26 = d_27;}
            g_26 += c_26;
        }
        if(yn3&&xp1){
            g_68 = g_58;d_68 = d_58;
            if (g_68 > g_69) {g_68 = g_69;d_68 = d_69;}
            if (g_68 > g_80) {g_68 = g_80;d_68 = d_80;}
            if (g_68 > g_57) {g_68 = g_57;d_68 = d_57;}
            g_68 += c_68;
        }
        if(yn3&&xn1){
            g_46 = g_58;d_46 = d_58;
            if (g_46 > g_47) {g_46 = g_47;d_46 = d_47;}
            if (g_46 > g_36) {g_46 = g_36;d_46 = d_36;}
            if (g_46 > g_57) {g_46 = g_57;d_46 = d_57;}
            g_46 += c_46;
        }
        if(yp3&&xp2){
            g_85 = g_73;d_85 = d_73;
            if (g_85 > g_84) {g_85 = g_84;d_85 = d_84;}
            if (g_85 > g_74) {g_85 = g_74;d_85 = d_74;}
            g_85 += c_85;
        }
        if(yp3&&xn2){
            g_41 = g_51;d_41 = d_51;
            if (g_41 > g_40) {g_41 = g_40;d_41 = d_40;}
            if (g_41 > g_52) {g_41 = g_52;d_41 = d_52;}
            g_41 += c_41;
        }
        if(yp2&&xp3){
            g_95 = g_83;d_95 = d_83;
            if (g_95 > g_84) {g_95 = g_84;d_95 = d_84;}
            if (g_95 > g_94) {g_95 = g_94;d_95 = d_94;}
            if (g_95 > g_85) {g_95 = g_85;d_95 = d_85;}
            g_95 += c_95;
        }
        if(yp2&&xn3){
            g_29 = g_39;d_29 = d_39;
            if (g_29 > g_40) {g_29 = g_40;d_29 = d_40;}
            if (g_29 > g_28) {g_29 = g_28;d_29 = d_28;}
            if (g_29 > g_41) {g_29 = g_41;d_29 = d_41;}
            g_29 += c_29;
        }
        if(yn2&&xp3){
            g_91 = g_81;d_91 = d_81;
            if (g_91 > g_80) {g_91 = g_80;d_91 = d_80;}
            if (g_91 > g_92) {g_91 = g_92;d_91 = d_92;}
            g_91 += c_91;
        }
        if(yn2&&xn3){
            g_25 = g_37;d_25 = d_37;
            if (g_25 > g_36) {g_25 = g_36;d_25 = d_36;}
            if (g_25 > g_26) {g_25 = g_26;d_25 = d_26;}
            g_25 += c_25;
        }
        if(yn3&&xp2){
            g_79 = g_69;d_79 = d_69;
            if (g_79 > g_80) {g_79 = g_80;d_79 = d_80;}
            if (g_79 > g_68) {g_79 = g_68;d_79 = d_68;}
            if (g_79 > g_91) {g_79 = g_91;d_79 = d_91;}
            g_79 += c_79;
        }
        if(yn3&&xn2){
            g_35 = g_47;d_35 = d_47;
            if (g_35 > g_36) {g_35 = g_36;d_35 = d_36;}
            if (g_35 > g_46) {g_35 = g_46;d_35 = d_46;}
            if (g_35 > g_25) {g_35 = g_25;d_35 = d_25;}
            g_35 += c_35;
        }
        if(yp4){
            g_64 = g_63;d_64 = d_63;
            if (g_64 > g_74) {g_64 = g_74;d_64 = d_74;}
            if (g_64 > g_52) {g_64 = g_52;d_64 = d_52;}
            g_64 += c_64;
        }
        if(xp4){
            g_104 = g_93;d_104 = d_93;
            if (g_104 > g_94) {g_104 = g_94;d_104 = d_94;}
            if (g_104 > g_92) {g_104 = g_92;d_104 = d_92;}
            g_104 += c_104;
        }
        if(xn4){
            g_16 = g_27;d_16 = d_27;
            if (g_16 > g_28) {g_16 = g_28;d_16 = d_28;}
            if (g_16 > g_26) {g_16 = g_26;d_16 = d_26;}
            g_16 += c_16;
        }
        if(yn4){
            g_56 = g_57;d_56 = d_57;
            if (g_56 > g_68) {g_56 = g_68;d_56 = d_68;}
            if (g_56 > g_46) {g_56 = g_46;d_56 = d_46;}
            g_56 += c_56;
        }
        if(yp4&&xp1){
            g_75 = g_63;d_75 = d_63;
            if (g_75 > g_74) {g_75 = g_74;d_75 = d_74;}
            if (g_75 > g_85) {g_75 = g_85;d_75 = d_85;}
            if (g_75 > g_64) {g_75 = g_64;d_75 = d_64;}
            g_75 += c_75;
        }
        if(yp4&&xn1){
            g_53 = g_63;d_53 = d_63;
            if (g_53 > g_52) {g_53 = g_52;d_53 = d_52;}
            if (g_53 > g_41) {g_53 = g_41;d_53 = d_41;}
            if (g_53 > g_64) {g_53 = g_64;d_53 = d_64;}
            g_53 += c_53;
        }
        if(yp1&&xp4){
            g_105 = g_93;d_105 = d_93;
            if (g_105 > g_94) {g_105 = g_94;d_105 = d_94;}
            if (g_105 > g_95) {g_105 = g_95;d_105 = d_95;}
            if (g_105 > g_104) {g_105 = g_104;d_105 = d_104;}
            g_105 += c_105;
        }
        if(yp1&&xn4){
            g_17 = g_27;d_17 = d_27;
            if (g_17 > g_28) {g_17 = g_28;d_17 = d_28;}
            if (g_17 > g_29) {g_17 = g_29;d_17 = d_29;}
            if (g_17 > g_16) {g_17 = g_16;d_17 = d_16;}
            g_17 += c_17;
        }
        if(yn1&&xp4){
            g_103 = g_93;d_103 = d_93;
            if (g_103 > g_92) {g_103 = g_92;d_103 = d_92;}
            if (g_103 > g_91) {g_103 = g_91;d_103 = d_91;}
            if (g_103 > g_104) {g_103 = g_104;d_103 = d_104;}
            g_103 += c_103;
        }
        if(yn1&&xn4){
            g_15 = g_27;d_15 = d_27;
            if (g_15 > g_26) {g_15 = g_26;d_15 = d_26;}
            if (g_15 > g_25) {g_15 = g_25;d_15 = d_25;}
            if (g_15 > g_16) {g_15 = g_16;d_15 = d_16;}
            g_15 += c_15;
        }
        if(yn4&&xp1){
            g_67 = g_57;d_67 = d_57;
            if (g_67 > g_68) {g_67 = g_68;d_67 = d_68;}
            if (g_67 > g_79) {g_67 = g_79;d_67 = d_79;}
            if (g_67 > g_56) {g_67 = g_56;d_67 = d_56;}
            g_67 += c_67;
        }
        if(yn4&&xn1){
            g_45 = g_57;d_45 = d_57;
            if (g_45 > g_46) {g_45 = g_46;d_45 = d_46;}
            if (g_45 > g_35) {g_45 = g_35;d_45 = d_35;}
            if (g_45 > g_56) {g_45 = g_56;d_45 = d_56;}
            g_45 += c_45;
        }
        if(yp3&&xp3){
            g_96 = g_84;d_96 = d_84;
            if (g_96 > g_85) {g_96 = g_85;d_96 = d_85;}
            if (g_96 > g_95) {g_96 = g_95;d_96 = d_95;}
            g_96 += c_96;
        }
        if(yp3&&xn3){
            g_30 = g_40;d_30 = d_40;
            if (g_30 > g_41) {g_30 = g_41;d_30 = d_41;}
            if (g_30 > g_29) {g_30 = g_29;d_30 = d_29;}
            g_30 += c_30;
        }
        if(yn3&&xp3){
            g_90 = g_80;d_90 = d_80;
            if (g_90 > g_91) {g_90 = g_91;d_90 = d_91;}
            if (g_90 > g_79) {g_90 = g_79;d_90 = d_79;}
            g_90 += c_90;
        }
        if(yn3&&xn3){
            g_24 = g_36;d_24 = d_36;
            if (g_24 > g_25) {g_24 = g_25;d_24 = d_25;}
            if (g_24 > g_35) {g_24 = g_35;d_24 = d_35;}
            g_24 += c_24;
        }
        if(yp4&&xp2){
            g_86 = g_74;d_86 = d_74;
            if (g_86 > g_85) {g_86 = g_85;d_86 = d_85;}
            if (g_86 > g_75) {g_86 = g_75;d_86 = d_75;}
            if (g_86 > g_96) {g_86 = g_96;d_86 = d_96;}
            g_86 += c_86;
        }
        if(yp4&&xn2){
            g_42 = g_52;d_42 = d_52;
            if (g_42 > g_41) {g_42 = g_41;d_42 = d_41;}
            if (g_42 > g_53) {g_42 = g_53;d_42 = d_53;}
            if (g_42 > g_30) {g_42 = g_30;d_42 = d_30;}
            g_42 += c_42;
        }
        if(yp2&&xp4){
            g_106 = g_94;d_106 = d_94;
            if (g_106 > g_95) {g_106 = g_95;d_106 = d_95;}
            if (g_106 > g_105) {g_106 = g_105;d_106 = d_105;}
            if (g_106 > g_96) {g_106 = g_96;d_106 = d_96;}
            g_106 += c_106;
        }
        if(yp2&&xn4){
            g_18 = g_28;d_18 = d_28;
            if (g_18 > g_29) {g_18 = g_29;d_18 = d_29;}
            if (g_18 > g_17) {g_18 = g_17;d_18 = d_17;}
            if (g_18 > g_30) {g_18 = g_30;d_18 = d_30;}
            g_18 += c_18;
        }
        if(yn2&&xp4){
            g_102 = g_92;d_102 = d_92;
            if (g_102 > g_91) {g_102 = g_91;d_102 = d_91;}
            if (g_102 > g_103) {g_102 = g_103;d_102 = d_103;}
            if (g_102 > g_90) {g_102 = g_90;d_102 = d_90;}
            g_102 += c_102;
        }
        if(yn2&&xn4){
            g_14 = g_26;d_14 = d_26;
            if (g_14 > g_25) {g_14 = g_25;d_14 = d_25;}
            if (g_14 > g_15) {g_14 = g_15;d_14 = d_15;}
            if (g_14 > g_24) {g_14 = g_24;d_14 = d_24;}
            g_14 += c_14;
        }
        if(yn4&&xp2){
            g_78 = g_68;d_78 = d_68;
            if (g_78 > g_79) {g_78 = g_79;d_78 = d_79;}
            if (g_78 > g_67) {g_78 = g_67;d_78 = d_67;}
            if (g_78 > g_90) {g_78 = g_90;d_78 = d_90;}
            g_78 += c_78;
        }
        if(yn4&&xn2){
            g_34 = g_46;d_34 = d_46;
            if (g_34 > g_35) {g_34 = g_35;d_34 = d_35;}
            if (g_34 > g_45) {g_34 = g_45;d_34 = d_45;}
            if (g_34 > g_24) {g_34 = g_24;d_34 = d_24;}
            g_34 += c_34;
        }
        if(yp5){
            g_65 = g_64;d_65 = d_64;
            if (g_65 > g_75) {g_65 = g_75;d_65 = d_75;}
            if (g_65 > g_53) {g_65 = g_53;d_65 = d_53;}
            g_65 += c_65;
        }
        if(yp4&&xp3){
            g_97 = g_85;d_97 = d_85;
            if (g_97 > g_96) {g_97 = g_96;d_97 = d_96;}
            if (g_97 > g_86) {g_97 = g_86;d_97 = d_86;}
            g_97 += c_97;
        }
        if(yp4&&xn3){
            g_31 = g_41;d_31 = d_41;
            if (g_31 > g_30) {g_31 = g_30;d_31 = d_30;}
            if (g_31 > g_42) {g_31 = g_42;d_31 = d_42;}
            g_31 += c_31;
        }
        if(yp3&&xp4){
            g_107 = g_95;d_107 = d_95;
            if (g_107 > g_96) {g_107 = g_96;d_107 = d_96;}
            if (g_107 > g_106) {g_107 = g_106;d_107 = d_106;}
            if (g_107 > g_97) {g_107 = g_97;d_107 = d_97;}
            g_107 += c_107;
        }
        if(yp3&&xn4){
            g_19 = g_29;d_19 = d_29;
            if (g_19 > g_30) {g_19 = g_30;d_19 = d_30;}
            if (g_19 > g_18) {g_19 = g_18;d_19 = d_18;}
            if (g_19 > g_31) {g_19 = g_31;d_19 = d_31;}
            g_19 += c_19;
        }
        if(xp5){
            g_115 = g_104;d_115 = d_104;
            if (g_115 > g_105) {g_115 = g_105;d_115 = d_105;}
            if (g_115 > g_103) {g_115 = g_103;d_115 = d_103;}
            g_115 += c_115;
        }
        if(xn5){
            g_5 = g_16;d_5 = d_16;
            if (g_5 > g_17) {g_5 = g_17;d_5 = d_17;}
            if (g_5 > g_15) {g_5 = g_15;d_5 = d_15;}
            g_5 += c_5;
        }
        if(yn3&&xp4){
            g_101 = g_91;d_101 = d_91;
            if (g_101 > g_90) {g_101 = g_90;d_101 = d_90;}
            if (g_101 > g_102) {g_101 = g_102;d_101 = d_102;}
            g_101 += c_101;
        }
        if(yn3&&xn4){
            g_13 = g_25;d_13 = d_25;
            if (g_13 > g_24) {g_13 = g_24;d_13 = d_24;}
            if (g_13 > g_14) {g_13 = g_14;d_13 = d_14;}
            g_13 += c_13;
        }
        if(yn4&&xp3){
            g_89 = g_79;d_89 = d_79;
            if (g_89 > g_90) {g_89 = g_90;d_89 = d_90;}
            if (g_89 > g_78) {g_89 = g_78;d_89 = d_78;}
            if (g_89 > g_101) {g_89 = g_101;d_89 = d_101;}
            g_89 += c_89;
        }
        if(yn4&&xn3){
            g_23 = g_35;d_23 = d_35;
            if (g_23 > g_24) {g_23 = g_24;d_23 = d_24;}
            if (g_23 > g_34) {g_23 = g_34;d_23 = d_34;}
            if (g_23 > g_13) {g_23 = g_13;d_23 = d_13;}
            g_23 += c_23;
        }
        if(yn5){
            g_55 = g_56;d_55 = d_56;
            if (g_55 > g_67) {g_55 = g_67;d_55 = d_67;}
            if (g_55 > g_45) {g_55 = g_45;d_55 = d_45;}
            g_55 += c_55;
        }
        if(yp5&&xp1){
            g_76 = g_64;d_76 = d_64;
            if (g_76 > g_75) {g_76 = g_75;d_76 = d_75;}
            if (g_76 > g_86) {g_76 = g_86;d_76 = d_86;}
            if (g_76 > g_65) {g_76 = g_65;d_76 = d_65;}
            g_76 += c_76;
        }
        if(yp5&&xn1){
            g_54 = g_64;d_54 = d_64;
            if (g_54 > g_53) {g_54 = g_53;d_54 = d_53;}
            if (g_54 > g_42) {g_54 = g_42;d_54 = d_42;}
            if (g_54 > g_65) {g_54 = g_65;d_54 = d_65;}
            g_54 += c_54;
        }
        if(yp1&&xp5){
            g_116 = g_104;d_116 = d_104;
            if (g_116 > g_105) {g_116 = g_105;d_116 = d_105;}
            if (g_116 > g_106) {g_116 = g_106;d_116 = d_106;}
            if (g_116 > g_115) {g_116 = g_115;d_116 = d_115;}
            g_116 += c_116;
        }
        if(yp1&&xn5){
            g_6 = g_16;d_6 = d_16;
            if (g_6 > g_17) {g_6 = g_17;d_6 = d_17;}
            if (g_6 > g_18) {g_6 = g_18;d_6 = d_18;}
            if (g_6 > g_5) {g_6 = g_5;d_6 = d_5;}
            g_6 += c_6;
        }
        if(yn1&&xp5){
            g_114 = g_104;d_114 = d_104;
            if (g_114 > g_103) {g_114 = g_103;d_114 = d_103;}
            if (g_114 > g_102) {g_114 = g_102;d_114 = d_102;}
            if (g_114 > g_115) {g_114 = g_115;d_114 = d_115;}
            g_114 += c_114;
        }
        if(yn1&&xn5){
            g_4 = g_16;d_4 = d_16;
            if (g_4 > g_15) {g_4 = g_15;d_4 = d_15;}
            if (g_4 > g_14) {g_4 = g_14;d_4 = d_14;}
            if (g_4 > g_5) {g_4 = g_5;d_4 = d_5;}
            g_4 += c_4;
        }
        if(yn5&&xp1){
            g_66 = g_56;d_66 = d_56;
            if (g_66 > g_67) {g_66 = g_67;d_66 = d_67;}
            if (g_66 > g_78) {g_66 = g_78;d_66 = d_78;}
            if (g_66 > g_55) {g_66 = g_55;d_66 = d_55;}
            g_66 += c_66;
        }
        if(yn5&&xn1){
            g_44 = g_56;d_44 = d_56;
            if (g_44 > g_45) {g_44 = g_45;d_44 = d_45;}
            if (g_44 > g_34) {g_44 = g_34;d_44 = d_34;}
            if (g_44 > g_55) {g_44 = g_55;d_44 = d_55;}
            g_44 += c_44;
        }
        if(yp5&&xp2){
            g_87 = g_75;d_87 = d_75;
            if (g_87 > g_86) {g_87 = g_86;d_87 = d_86;}
            if (g_87 > g_97) {g_87 = g_97;d_87 = d_97;}
            if (g_87 > g_76) {g_87 = g_76;d_87 = d_76;}
            g_87 += c_87;
        }
        if(yp5&&xn2){
            g_43 = g_53;d_43 = d_53;
            if (g_43 > g_42) {g_43 = g_42;d_43 = d_42;}
            if (g_43 > g_31) {g_43 = g_31;d_43 = d_31;}
            if (g_43 > g_54) {g_43 = g_54;d_43 = d_54;}
            g_43 += c_43;
        }
        if(yp2&&xp5){
            g_117 = g_105;d_117 = d_105;
            if (g_117 > g_106) {g_117 = g_106;d_117 = d_106;}
            if (g_117 > g_107) {g_117 = g_107;d_117 = d_107;}
            if (g_117 > g_116) {g_117 = g_116;d_117 = d_116;}
            g_117 += c_117;
        }
        if(yp2&&xn5){
            g_7 = g_17;d_7 = d_17;
            if (g_7 > g_18) {g_7 = g_18;d_7 = d_18;}
            if (g_7 > g_19) {g_7 = g_19;d_7 = d_19;}
            if (g_7 > g_6) {g_7 = g_6;d_7 = d_6;}
            g_7 += c_7;
        }
        if(yn2&&xp5){
            g_113 = g_103;d_113 = d_103;
            if (g_113 > g_102) {g_113 = g_102;d_113 = d_102;}
            if (g_113 > g_101) {g_113 = g_101;d_113 = d_101;}
            if (g_113 > g_114) {g_113 = g_114;d_113 = d_114;}
            g_113 += c_113;
        }
        if(yn2&&xn5){
            g_3 = g_15;d_3 = d_15;
            if (g_3 > g_14) {g_3 = g_14;d_3 = d_14;}
            if (g_3 > g_13) {g_3 = g_13;d_3 = d_13;}
            if (g_3 > g_4) {g_3 = g_4;d_3 = d_4;}
            g_3 += c_3;
        }
        if(yn5&&xp2){
            g_77 = g_67;d_77 = d_67;
            if (g_77 > g_78) {g_77 = g_78;d_77 = d_78;}
            if (g_77 > g_89) {g_77 = g_89;d_77 = d_89;}
            if (g_77 > g_66) {g_77 = g_66;d_77 = d_66;}
            g_77 += c_77;
        }
        if(yn5&&xn2){
            g_33 = g_45;d_33 = d_45;
            if (g_33 > g_34) {g_33 = g_34;d_33 = d_34;}
            if (g_33 > g_23) {g_33 = g_23;d_33 = d_23;}
            if (g_33 > g_44) {g_33 = g_44;d_33 = d_44;}
            g_33 += c_33;
        }
        if(yp4&&xp4){
            g_108 = g_96;d_108 = d_96;
            if (g_108 > g_97) {g_108 = g_97;d_108 = d_97;}
            if (g_108 > g_107) {g_108 = g_107;d_108 = d_107;}
            g_108 += c_108;
        }
        if(yp4&&xn4){
            g_20 = g_30;d_20 = d_30;
            if (g_20 > g_31) {g_20 = g_31;d_20 = d_31;}
            if (g_20 > g_19) {g_20 = g_19;d_20 = d_19;}
            g_20 += c_20;
        }
        if(yn4&&xp4){
            g_100 = g_90;d_100 = d_90;
            if (g_100 > g_101) {g_100 = g_101;d_100 = d_101;}
            if (g_100 > g_89) {g_100 = g_89;d_100 = d_89;}
            g_100 += c_100;
        }
        if(yn4&&xn4){
            g_12 = g_24;d_12 = d_24;
            if (g_12 > g_13) {g_12 = g_13;d_12 = d_13;}
            if (g_12 > g_23) {g_12 = g_23;d_12 = d_23;}
            g_12 += c_12;
        }
        if(yp5&&xp3){
            g_98 = g_86;d_98 = d_86;
            if (g_98 > g_97) {g_98 = g_97;d_98 = d_97;}
            if (g_98 > g_87) {g_98 = g_87;d_98 = d_87;}
            if (g_98 > g_108) {g_98 = g_108;d_98 = d_108;}
            g_98 += c_98;
        }
        if(yp5&&xn3){
            g_32 = g_42;d_32 = d_42;
            if (g_32 > g_31) {g_32 = g_31;d_32 = d_31;}
            if (g_32 > g_43) {g_32 = g_43;d_32 = d_43;}
            if (g_32 > g_20) {g_32 = g_20;d_32 = d_20;}
            g_32 += c_32;
        }
        if(yp3&&xp5){
            g_118 = g_106;d_118 = d_106;
            if (g_118 > g_107) {g_118 = g_107;d_118 = d_107;}
            if (g_118 > g_117) {g_118 = g_117;d_118 = d_117;}
            if (g_118 > g_108) {g_118 = g_108;d_118 = d_108;}
            g_118 += c_118;
        }
        if(yp3&&xn5){
            g_8 = g_18;d_8 = d_18;
            if (g_8 > g_19) {g_8 = g_19;d_8 = d_19;}
            if (g_8 > g_7) {g_8 = g_7;d_8 = d_7;}
            if (g_8 > g_20) {g_8 = g_20;d_8 = d_20;}
            g_8 += c_8;
        }
        if(yn3&&xp5){
            g_112 = g_102;d_112 = d_102;
            if (g_112 > g_101) {g_112 = g_101;d_112 = d_101;}
            if (g_112 > g_113) {g_112 = g_113;d_112 = d_113;}
            if (g_112 > g_100) {g_112 = g_100;d_112 = d_100;}
            g_112 += c_112;
        }
        if(yn3&&xn5){
            g_2 = g_14;d_2 = d_14;
            if (g_2 > g_13) {g_2 = g_13;d_2 = d_13;}
            if (g_2 > g_3) {g_2 = g_3;d_2 = d_3;}
            if (g_2 > g_12) {g_2 = g_12;d_2 = d_12;}
            g_2 += c_2;
        }
        if(yn5&&xp3){
            g_88 = g_78;d_88 = d_78;
            if (g_88 > g_89) {g_88 = g_89;d_88 = d_89;}
            if (g_88 > g_77) {g_88 = g_77;d_88 = d_77;}
            if (g_88 > g_100) {g_88 = g_100;d_88 = d_100;}
            g_88 += c_88;
        }
        if(yn5&&xn3){
            g_22 = g_34;d_22 = d_34;
            if (g_22 > g_23) {g_22 = g_23;d_22 = d_23;}
            if (g_22 > g_33) {g_22 = g_33;d_22 = d_33;}
            if (g_22 > g_12) {g_22 = g_12;d_22 = d_12;}
            g_22 += c_22;
        }

        //endregion

        //region direct retrieval
        int dx = target_location.x - me.x;
        int dy = target_location.y - me.y;
        switch (dx) {
            case -5:
                switch (dy) {
                    case -3:
                        return d_2;
                    case -2:
                        return d_3;
                    case -1:
                        return d_4;
                    case 0:
                        return d_5;
                    case 1:
                        return d_6;
                    case 2:
                        return d_7;
                    case 3:
                        return d_8;
                }
                break;
            case -4:
                switch (dy) {
                    case -4:
                        return d_12;
                    case -3:
                        return d_13;
                    case -2:
                        return d_14;
                    case -1:
                        return d_15;
                    case 0:
                        return d_16;
                    case 1:
                        return d_17;
                    case 2:
                        return d_18;
                    case 3:
                        return d_19;
                    case 4:
                        return d_20;
                }
                break;
            case -3:
                switch (dy) {
                    case -5:
                        return d_22;
                    case -4:
                        return d_23;
                    case -3:
                        return d_24;
                    case -2:
                        return d_25;
                    case -1:
                        return d_26;
                    case 0:
                        return d_27;
                    case 1:
                        return d_28;
                    case 2:
                        return d_29;
                    case 3:
                        return d_30;
                    case 4:
                        return d_31;
                    case 5:
                        return d_32;
                }
                break;
            case -2:
                switch (dy) {
                    case -5:
                        return d_33;
                    case -4:
                        return d_34;
                    case -3:
                        return d_35;
                    case -2:
                        return d_36;
                    case -1:
                        return d_37;
                    case 0:
                        return d_38;
                    case 1:
                        return d_39;
                    case 2:
                        return d_40;
                    case 3:
                        return d_41;
                    case 4:
                        return d_42;
                    case 5:
                        return d_43;
                }
                break;
            case -1:
                switch (dy) {
                    case -5:
                        return d_44;
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
                    case 5:
                        return d_54;
                }
                break;
            case 0:
                switch (dy) {
                    case -5:
                        return d_55;
                    case -4:
                        return d_56;
                    case -3:
                        return d_57;
                    case -2:
                        return d_58;
                    case -1:
                        return d_59;
                    case 0:
                        return d_60;
                    case 1:
                        return d_61;
                    case 2:
                        return d_62;
                    case 3:
                        return d_63;
                    case 4:
                        return d_64;
                    case 5:
                        return d_65;
                }
                break;
            case 1:
                switch (dy) {
                    case -5:
                        return d_66;
                    case -4:
                        return d_67;
                    case -3:
                        return d_68;
                    case -2:
                        return d_69;
                    case -1:
                        return d_70;
                    case 0:
                        return d_71;
                    case 1:
                        return d_72;
                    case 2:
                        return d_73;
                    case 3:
                        return d_74;
                    case 4:
                        return d_75;
                    case 5:
                        return d_76;
                }
                break;
            case 2:
                switch (dy) {
                    case -5:
                        return d_77;
                    case -4:
                        return d_78;
                    case -3:
                        return d_79;
                    case -2:
                        return d_80;
                    case -1:
                        return d_81;
                    case 0:
                        return d_82;
                    case 1:
                        return d_83;
                    case 2:
                        return d_84;
                    case 3:
                        return d_85;
                    case 4:
                        return d_86;
                    case 5:
                        return d_87;
                }
                break;
            case 3:
                switch (dy) {
                    case -5:
                        return d_88;
                    case -4:
                        return d_89;
                    case -3:
                        return d_90;
                    case -2:
                        return d_91;
                    case -1:
                        return d_92;
                    case 0:
                        return d_93;
                    case 1:
                        return d_94;
                    case 2:
                        return d_95;
                    case 3:
                        return d_96;
                    case 4:
                        return d_97;
                    case 5:
                        return d_98;
                }
                break;
            case 4:
                switch (dy) {
                    case -4:
                        return d_100;
                    case -3:
                        return d_101;
                    case -2:
                        return d_102;
                    case -1:
                        return d_103;
                    case 0:
                        return d_104;
                    case 1:
                        return d_105;
                    case 2:
                        return d_106;
                    case 3:
                        return d_107;
                    case 4:
                        return d_108;
                }
                break;
            case 5:
                switch (dy) {
                    case -3:
                        return d_112;
                    case -2:
                        return d_113;
                    case -1:
                        return d_114;
                    case 0:
                        return d_115;
                    case 1:
                        return d_116;
                    case 2:
                        return d_117;
                    case 3:
                        return d_118;
                }
                break;
        }
        //endregion

        //region indirect retrieval
        //D_i stands for initial distance
        //HAR stands for highest average reward
        double D_i=Math.sqrt(me.distanceSquaredTo(target_location));
        double HAR = 0;
        Direction HAR_direction = null;
        double AR;
        AR = (D_i-Math.sqrt(Math.pow(dx + 5,2)+Math.pow(dy + 3,2)) )/g_2;
        if(AR>HAR){HAR=AR;HAR_direction=d_2;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 5,2)+Math.pow(dy + 2,2)) )/g_3;
        if(AR>HAR){HAR=AR;HAR_direction=d_3;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 5,2)+Math.pow(dy + 1,2)) )/g_4;
        if(AR>HAR){HAR=AR;HAR_direction=d_4;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 5,2)+Math.pow(dy    ,2)) )/g_5;
        if(AR>HAR){HAR=AR;HAR_direction=d_5;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 5,2)+Math.pow(dy - 1,2)) )/g_6;
        if(AR>HAR){HAR=AR;HAR_direction=d_6;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 5,2)+Math.pow(dy - 2,2)) )/g_7;
        if(AR>HAR){HAR=AR;HAR_direction=d_7;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 5,2)+Math.pow(dy - 3,2)) )/g_8;
        if(AR>HAR){HAR=AR;HAR_direction=d_8;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 4,2)+Math.pow(dy + 4,2)) )/g_12;
        if(AR>HAR){HAR=AR;HAR_direction=d_12;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 4,2)+Math.pow(dy + 3,2)) )/g_13;
        if(AR>HAR){HAR=AR;HAR_direction=d_13;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 4,2)+Math.pow(dy - 3,2)) )/g_19;
        if(AR>HAR){HAR=AR;HAR_direction=d_19;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 4,2)+Math.pow(dy - 4,2)) )/g_20;
        if(AR>HAR){HAR=AR;HAR_direction=d_20;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 3,2)+Math.pow(dy + 5,2)) )/g_22;
        if(AR>HAR){HAR=AR;HAR_direction=d_22;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 3,2)+Math.pow(dy + 4,2)) )/g_23;
        if(AR>HAR){HAR=AR;HAR_direction=d_23;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 3,2)+Math.pow(dy - 4,2)) )/g_31;
        if(AR>HAR){HAR=AR;HAR_direction=d_31;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 3,2)+Math.pow(dy - 5,2)) )/g_32;
        if(AR>HAR){HAR=AR;HAR_direction=d_32;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 2,2)+Math.pow(dy + 5,2)) )/g_33;
        if(AR>HAR){HAR=AR;HAR_direction=d_33;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 2,2)+Math.pow(dy - 5,2)) )/g_43;
        if(AR>HAR){HAR=AR;HAR_direction=d_43;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 1,2)+Math.pow(dy + 5,2)) )/g_44;
        if(AR>HAR){HAR=AR;HAR_direction=d_44;}
        AR = (D_i-Math.sqrt(Math.pow(dx + 1,2)+Math.pow(dy - 5,2)) )/g_54;
        if(AR>HAR){HAR=AR;HAR_direction=d_54;}
        AR = (D_i-Math.sqrt(Math.pow(dx    ,2)+Math.pow(dy + 5,2)) )/g_55;
        if(AR>HAR){HAR=AR;HAR_direction=d_55;}
        AR = (D_i-Math.sqrt(Math.pow(dx    ,2)+Math.pow(dy - 5,2)) )/g_65;
        if(AR>HAR){HAR=AR;HAR_direction=d_65;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 1,2)+Math.pow(dy + 5,2)) )/g_66;
        if(AR>HAR){HAR=AR;HAR_direction=d_66;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 1,2)+Math.pow(dy - 5,2)) )/g_76;
        if(AR>HAR){HAR=AR;HAR_direction=d_76;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 2,2)+Math.pow(dy + 5,2)) )/g_77;
        if(AR>HAR){HAR=AR;HAR_direction=d_77;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 2,2)+Math.pow(dy - 5,2)) )/g_87;
        if(AR>HAR){HAR=AR;HAR_direction=d_87;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 3,2)+Math.pow(dy + 5,2)) )/g_88;
        if(AR>HAR){HAR=AR;HAR_direction=d_88;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 3,2)+Math.pow(dy + 4,2)) )/g_89;
        if(AR>HAR){HAR=AR;HAR_direction=d_89;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 3,2)+Math.pow(dy - 4,2)) )/g_97;
        if(AR>HAR){HAR=AR;HAR_direction=d_97;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 3,2)+Math.pow(dy - 5,2)) )/g_98;
        if(AR>HAR){HAR=AR;HAR_direction=d_98;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 4,2)+Math.pow(dy + 4,2)) )/g_100;
        if(AR>HAR){HAR=AR;HAR_direction=d_100;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 4,2)+Math.pow(dy + 3,2)) )/g_101;
        if(AR>HAR){HAR=AR;HAR_direction=d_101;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 4,2)+Math.pow(dy - 3,2)) )/g_107;
        if(AR>HAR){HAR=AR;HAR_direction=d_107;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 4,2)+Math.pow(dy - 4,2)) )/g_108;
        if(AR>HAR){HAR=AR;HAR_direction=d_108;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 5,2)+Math.pow(dy + 3,2)) )/g_112;
        if(AR>HAR){HAR=AR;HAR_direction=d_112;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 5,2)+Math.pow(dy + 2,2)) )/g_113;
        if(AR>HAR){HAR=AR;HAR_direction=d_113;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 5,2)+Math.pow(dy + 1,2)) )/g_114;
        if(AR>HAR){HAR=AR;HAR_direction=d_114;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 5,2)+Math.pow(dy    ,2)) )/g_115;
        if(AR>HAR){HAR=AR;HAR_direction=d_115;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 5,2)+Math.pow(dy - 1,2)) )/g_116;
        if(AR>HAR){HAR=AR;HAR_direction=d_116;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 5,2)+Math.pow(dy - 2,2)) )/g_117;
        if(AR>HAR){HAR=AR;HAR_direction=d_117;}
        AR = (D_i-Math.sqrt(Math.pow(dx - 5,2)+Math.pow(dy - 3,2)) )/g_118;
        if(AR>HAR){HAR=AR;HAR_direction=d_118;}

        return HAR_direction;
        //endregion

    }
}
