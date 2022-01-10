package Rushy.utils;

import static Rushy.Robot.debugOn;

public class Debug {
    public static void p(Object msg) {
        if (debugOn) System.out.println(msg);
    }
}