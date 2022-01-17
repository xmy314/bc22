package skip;

import battlecode.common.Clock;

import static Rushy_v6.Robot.debugOn;

public class Debug {
    public static void p(Object msg) {
        if (debugOn) System.out.println(msg);
    }

    // note: the following functions should only be used once place in the package.
    static int a;
    public static void ti(){a=Clock.getBytecodesLeft();}
    public static void tf(){p(a-Clock.getBytecodesLeft());}
}