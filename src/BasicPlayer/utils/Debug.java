package BasicPlayer.utils;

import static BasicPlayer.Robot.debugOn;

public class Debug {
    public static void p(Object msg) {
        if (debugOn) System.out.println(msg);
    }
}