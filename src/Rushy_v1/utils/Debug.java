package Rushy_v1.utils;

import static Rushy_v1.Robot.debugOn;

public class Debug {
    public static void p(Object msg) {
        if (debugOn) System.out.println(msg);
    }
}