package BasicPlayer;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class Com {
    RobotController rc;

    public enum ComFlag{PROTECT,ATTACK,EXAMINE,SUPPORT}

    public Com(RobotController r) throws GameActionException{
        rc=r;
    }

    private int getId(ComFlag flag){
        switch (flag){
            case PROTECT:
                return 10;
            case ATTACK:
                return 12;
            case EXAMINE:
                return 14;
            case SUPPORT:
                return 16;
            default:
                return 0;
        }
    }

    private int compressLocation(MapLocation loc){
        return  (loc.x<<6)|(loc.y);
    }

    private MapLocation uncompressLocation( int v){
        if(v!=0) {
            return new MapLocation(v >> 6, v & 63);
        }else{
            return null;
        }
    }

    public MapLocation getTarget(ComFlag flag) throws GameActionException{
        int id=getId(flag);
        MapLocation ret = null;
        if(rc.readSharedArray(id)!=0) {
            ret = uncompressLocation(rc.readSharedArray(id));
        }
        if(rc.readSharedArray(id+1)!=0 && ((rc.getID()&1)==1)){
            ret = uncompressLocation(rc.readSharedArray(id));
        }
        return ret;
    }

    public void setTarget(ComFlag flag, MapLocation loc) throws  GameActionException{
        int id=getId(flag);
        int v=compressLocation(loc);
        if(rc.readSharedArray(id)==0) {
            rc.writeSharedArray(id,v);
        }else if(rc.readSharedArray(id+1)==0){
            rc.writeSharedArray(id+1,v);
        }else{
            rc.writeSharedArray(id+rc.getID()&1,v);
        }
    }
}
