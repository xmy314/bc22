package Rushy_v5;

import battlecode.common.*;

import static Rushy_v5.Robot.*;

public class Com {
    /*
    0 - 1 are for head counting units. written when each unit is created and whe they are about to die.
    2 is for location of base and its nearby rubble count.
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
    static boolean death_registered = true;
    static int chunk_height;
    static int chunk_width;

    static boolean is_main_archon;
    static MapLocation main_archon_loc;
    static int non_depletion_radius;

    public static void init() throws GameActionException {
        if (rc.getType() == RobotType.ARCHON) {
            if (rc.readSharedArray(4) == 0) {
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
        }
        chunk_height = (int) Math.ceil(max_Y / 8f);
        chunk_width = (int) Math.ceil(max_X / 8f);
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
            if(potential_target!=null){
                return potential_target;
            }
        }

        return null;
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

        boolean is_moving=rc.getMode()==RobotMode.DROID || rc.getMode()==RobotMode.PORTABLE;

        int tbw = 0b000;
        // using enemy count as the scheme would turn away miners from component miner whom they want to compete against.
        // the result is generally lower economy then it could have had.
        if (enemy_dmg > 0) {
            tbw |= 0b1;
        }
        if ( is_moving && ally_miner_count < 3 * mine_over_thresh_count) {
            tbw |= 0b10;
        }
        // third digit is always 0 as belong to this chunk automatically makes it not empty.

        if (is_moving) {
            setTarget(0b111, tbw, rc.getLocation());
        } else {
            setTarget(0b101, tbw, rc.getLocation());
        }

        //TODO: following is only for debugging
        if (debugOn && rc.getType() == RobotType.ARCHON) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    int v = getFlags(x, y);
                    rc.setIndicatorDot(getChunkCenter(x, y), ((v & 0b001) != 0) ? 200 : 0, ((v & 0b010) != 0) ? 200 : 0, ((v & 0b100) != 0) ? 200 : 0);
                }
            }
        }
    }

    public static void analyzeTargets() throws GameActionException {
        // this function runs at the end of the exploration age.
        // turning all unexplored blocks that connects to enemy blocks through unexplored blocks enemy blocks.
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

        if( f0 ==0b0100&& ((f1 |f8 |f9 )&0b0001)!=0 ){ f0  |= 0b0001;}
        if( f1 ==0b0100&& ((f0 |f2 |f8 |f9 |f10)&0b0001)!=0 ){ f1  |= 0b0001;}
        if( f2 ==0b0100&& ((f1 |f3 |f9 |f10|f11)&0b0001)!=0 ){ f2  |= 0b0001;}
        if( f3 ==0b0100&& ((f2 |f4 |f10|f11|f12)&0b0001)!=0 ){ f3  |= 0b0001;}
        if( f4 ==0b0100&& ((f3 |f5 |f11|f12|f13)&0b0001)!=0 ){ f4  |= 0b0001;}
        if( f5 ==0b0100&& ((f4 |f6 |f12|f13|f14)&0b0001)!=0 ){ f5  |= 0b0001;}
        if( f6 ==0b0100&& ((f5 |f7 |f13|f14|f15)&0b0001)!=0 ){ f6  |= 0b0001;}
        if( f7 ==0b0100&& ((f6 |f14|f15)&0b0001)!=0 ){ f7  |= 0b0001;}
        if( f8 ==0b0100&& ((f0 |f1 |f9 |f16|f17)&0b0001)!=0 ){ f8  |= 0b0001;}
        if( f9 ==0b0100&& ((f0 |f1 |f2 |f8 |f10|f16|f17|f18)&0b0001)!=0 ){ f9  |= 0b0001;}
        if( f10==0b0100&& ((f1 |f2 |f3 |f9 |f11|f17|f18|f19)&0b0001)!=0 ){ f10 |= 0b0001;}
        if( f11==0b0100&& ((f2 |f3 |f4 |f10|f12|f18|f19|f20)&0b0001)!=0 ){ f11 |= 0b0001;}
        if( f12==0b0100&& ((f3 |f4 |f5 |f11|f13|f19|f20|f21)&0b0001)!=0 ){ f12 |= 0b0001;}
        if( f13==0b0100&& ((f4 |f5 |f6 |f12|f14|f20|f21|f22)&0b0001)!=0 ){ f13 |= 0b0001;}
        if( f14==0b0100&& ((f5 |f6 |f7 |f13|f15|f21|f22|f23)&0b0001)!=0 ){ f14 |= 0b0001;}
        if( f15==0b0100&& ((f6 |f7 |f14|f22|f23)&0b0001)!=0 ){ f15 |= 0b0001;}
        if( f16==0b0100&& ((f8 |f9 |f17|f24|f25)&0b0001)!=0 ){ f16 |= 0b0001;}
        if( f17==0b0100&& ((f8 |f9 |f10|f16|f18|f24|f25|f26)&0b0001)!=0 ){ f17 |= 0b0001;}
        if( f18==0b0100&& ((f9 |f10|f11|f17|f19|f25|f26|f27)&0b0001)!=0 ){ f18 |= 0b0001;}
        if( f19==0b0100&& ((f10|f11|f12|f18|f20|f26|f27|f28)&0b0001)!=0 ){ f19 |= 0b0001;}
        if( f20==0b0100&& ((f11|f12|f13|f19|f21|f27|f28|f29)&0b0001)!=0 ){ f20 |= 0b0001;}
        if( f21==0b0100&& ((f12|f13|f14|f20|f22|f28|f29|f30)&0b0001)!=0 ){ f21 |= 0b0001;}
        if( f22==0b0100&& ((f13|f14|f15|f21|f23|f29|f30|f31)&0b0001)!=0 ){ f22 |= 0b0001;}
        if( f23==0b0100&& ((f14|f15|f22|f30|f31)&0b0001)!=0 ){ f23 |= 0b0001;}
        if( f24==0b0100&& ((f16|f17|f25|f32|f33)&0b0001)!=0 ){ f24 |= 0b0001;}
        if( f25==0b0100&& ((f16|f17|f18|f24|f26|f32|f33|f34)&0b0001)!=0 ){ f25 |= 0b0001;}
        if( f26==0b0100&& ((f17|f18|f19|f25|f27|f33|f34|f35)&0b0001)!=0 ){ f26 |= 0b0001;}
        if( f27==0b0100&& ((f18|f19|f20|f26|f28|f34|f35|f36)&0b0001)!=0 ){ f27 |= 0b0001;}
        if( f28==0b0100&& ((f19|f20|f21|f27|f29|f35|f36|f37)&0b0001)!=0 ){ f28 |= 0b0001;}
        if( f29==0b0100&& ((f20|f21|f22|f28|f30|f36|f37|f38)&0b0001)!=0 ){ f29 |= 0b0001;}
        if( f30==0b0100&& ((f21|f22|f23|f29|f31|f37|f38|f39)&0b0001)!=0 ){ f30 |= 0b0001;}
        if( f31==0b0100&& ((f22|f23|f30|f38|f39)&0b0001)!=0 ){ f31 |= 0b0001;}
        if( f32==0b0100&& ((f24|f25|f33|f40|f41)&0b0001)!=0 ){ f32 |= 0b0001;}
        if( f33==0b0100&& ((f24|f25|f26|f32|f34|f40|f41|f42)&0b0001)!=0 ){ f33 |= 0b0001;}
        if( f34==0b0100&& ((f25|f26|f27|f33|f35|f41|f42|f43)&0b0001)!=0 ){ f34 |= 0b0001;}
        if( f35==0b0100&& ((f26|f27|f28|f34|f36|f42|f43|f44)&0b0001)!=0 ){ f35 |= 0b0001;}
        if( f36==0b0100&& ((f27|f28|f29|f35|f37|f43|f44|f45)&0b0001)!=0 ){ f36 |= 0b0001;}
        if( f37==0b0100&& ((f28|f29|f30|f36|f38|f44|f45|f46)&0b0001)!=0 ){ f37 |= 0b0001;}
        if( f38==0b0100&& ((f29|f30|f31|f37|f39|f45|f46|f47)&0b0001)!=0 ){ f38 |= 0b0001;}
        if( f39==0b0100&& ((f30|f31|f38|f46|f47)&0b0001)!=0 ){ f39 |= 0b0001;}
        if( f40==0b0100&& ((f32|f33|f41|f48|f49)&0b0001)!=0 ){ f40 |= 0b0001;}
        if( f41==0b0100&& ((f32|f33|f34|f40|f42|f48|f49|f50)&0b0001)!=0 ){ f41 |= 0b0001;}
        if( f42==0b0100&& ((f33|f34|f35|f41|f43|f49|f50|f51)&0b0001)!=0 ){ f42 |= 0b0001;}
        if( f43==0b0100&& ((f34|f35|f36|f42|f44|f50|f51|f52)&0b0001)!=0 ){ f43 |= 0b0001;}
        if( f44==0b0100&& ((f35|f36|f37|f43|f45|f51|f52|f53)&0b0001)!=0 ){ f44 |= 0b0001;}
        if( f45==0b0100&& ((f36|f37|f38|f44|f46|f52|f53|f54)&0b0001)!=0 ){ f45 |= 0b0001;}
        if( f46==0b0100&& ((f37|f38|f39|f45|f47|f53|f54|f55)&0b0001)!=0 ){ f46 |= 0b0001;}
        if( f47==0b0100&& ((f38|f39|f46|f54|f55)&0b0001)!=0 ){ f47 |= 0b0001;}
        if( f48==0b0100&& ((f40|f41|f49|f56|f57)&0b0001)!=0 ){ f48 |= 0b0001;}
        if( f49==0b0100&& ((f40|f41|f42|f48|f50|f56|f57|f58)&0b0001)!=0 ){ f49 |= 0b0001;}
        if( f50==0b0100&& ((f41|f42|f43|f49|f51|f57|f58|f59)&0b0001)!=0 ){ f50 |= 0b0001;}
        if( f51==0b0100&& ((f42|f43|f44|f50|f52|f58|f59|f60)&0b0001)!=0 ){ f51 |= 0b0001;}
        if( f52==0b0100&& ((f43|f44|f45|f51|f53|f59|f60|f61)&0b0001)!=0 ){ f52 |= 0b0001;}
        if( f53==0b0100&& ((f44|f45|f46|f52|f54|f60|f61|f62)&0b0001)!=0 ){ f53 |= 0b0001;}
        if( f54==0b0100&& ((f45|f46|f47|f53|f55|f61|f62|f63)&0b0001)!=0 ){ f54 |= 0b0001;}
        if( f55==0b0100&& ((f46|f47|f54|f62|f63)&0b0001)!=0 ){ f55 |= 0b0001;}
        if( f56==0b0100&& ((f48|f49|f57)&0b0001)!=0 ){ f56 |= 0b0001;}
        if( f57==0b0100&& ((f48|f49|f50|f56|f58)&0b0001)!=0 ){ f57 |= 0b0001;}
        if( f58==0b0100&& ((f49|f50|f51|f57|f59)&0b0001)!=0 ){ f58 |= 0b0001;}
        if( f59==0b0100&& ((f50|f51|f52|f58|f60)&0b0001)!=0 ){ f59 |= 0b0001;}
        if( f60==0b0100&& ((f51|f52|f53|f59|f61)&0b0001)!=0 ){ f60 |= 0b0001;}
        if( f61==0b0100&& ((f52|f53|f54|f60|f62)&0b0001)!=0 ){ f61 |= 0b0001;}
        if( f62==0b0100&& ((f53|f54|f55|f61|f63)&0b0001)!=0 ){ f62 |= 0b0001;}
        if( f63==0b0100&& ((f54|f55|f62)&0b0001)!=0 ){ f63 |= 0b0001;}
        int w0  = (f0 <<12)|(f1 <<8)|(f2 <<4)|(f3 );
        if( w0  != r0  )rc.writeSharedArray(4 ,w0 );
        int w1  = (f4 <<12)|(f5 <<8)|(f6 <<4)|(f7 );
        if( w1  != r1  )rc.writeSharedArray(5 ,w1 );
        int w2  = (f8 <<12)|(f9 <<8)|(f10<<4)|(f11);
        if( w2  != r2  )rc.writeSharedArray(6 ,w2 );
        int w3  = (f12<<12)|(f13<<8)|(f14<<4)|(f15);
        if( w3  != r3  )rc.writeSharedArray(7 ,w3 );
        int w4  = (f16<<12)|(f17<<8)|(f18<<4)|(f19);
        if( w4  != r4  )rc.writeSharedArray(8 ,w4 );
        int w5  = (f20<<12)|(f21<<8)|(f22<<4)|(f23);
        if( w5  != r5  )rc.writeSharedArray(9 ,w5 );
        int w6  = (f24<<12)|(f25<<8)|(f26<<4)|(f27);
        if( w6  != r6  )rc.writeSharedArray(10,w6 );
        int w7  = (f28<<12)|(f29<<8)|(f30<<4)|(f31);
        if( w7  != r7  )rc.writeSharedArray(11,w7 );
        int w8  = (f32<<12)|(f33<<8)|(f34<<4)|(f35);
        if( w8  != r8  )rc.writeSharedArray(12,w8 );
        int w9  = (f36<<12)|(f37<<8)|(f38<<4)|(f39);
        if( w9  != r9  )rc.writeSharedArray(13,w9 );
        int w10 = (f40<<12)|(f41<<8)|(f42<<4)|(f43);
        if( w10 != r10 )rc.writeSharedArray(14,w10);
        int w11 = (f44<<12)|(f45<<8)|(f46<<4)|(f47);
        if( w11 != r11 )rc.writeSharedArray(15,w11);
        int w12 = (f48<<12)|(f49<<8)|(f50<<4)|(f51);
        if( w12 != r12 )rc.writeSharedArray(16,w12);
        int w13 = (f52<<12)|(f53<<8)|(f54<<4)|(f55);
        if( w13 != r13 )rc.writeSharedArray(17,w13);
        int w14 = (f56<<12)|(f57<<8)|(f58<<4)|(f59);
        if( w14 != r14 )rc.writeSharedArray(18,w14);
        int w15 = (f60<<12)|(f61<<8)|(f62<<4)|(f63);
        if( w15 != r15 )rc.writeSharedArray(19,w15);
    }

    public static void archonInterchange() throws GameActionException{
        int v=rc.readSharedArray(2);
        int env = approximateSurrounding();

        if(env<(v>>12)){
            rc.writeSharedArray( 2, (env<<12) | (rc.getLocation().x) | (rc.getLocation().y)  );
        }
    }

    public static int approximateSurrounding() throws GameActionException{
        int sense_count = 0;
        float rubble_sum = 0;
        MapLocation me = rc.getLocation();

        int yn = Math.min(me.y,5); // how many steps can i go in the positive x direction without being outside.
        int yp = Math.min(max_Y - me.y -1,5);
        int xn = Math.min(me.x,5);
        int xp = Math.min(max_X - me.x -1,5);

        switch (yn){
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
        switch (yp){
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
        switch (xn){
            case 5:
                rubble_sum += rc.senseRubble(me.translate(-5, 0));
                sense_count++;
                switch (yn){
                    case 5:
                    case 4:
                    case 3:
                        rubble_sum += rc.senseRubble(me.translate(-5, -3));
                        sense_count++;
                }
                switch (yp){
                    case 5:
                    case 4:
                    case 3:
                        rubble_sum += rc.senseRubble(me.translate(-5, 3));
                        sense_count++;
                }
            case 4:
                switch (yn){
                    case 5:
                    case 4:
                    case 3:
                    case 2:
                        rubble_sum += rc.senseRubble(me.translate(-4, -2));
                        sense_count++;
                }
                switch (yp){
                    case 5:
                    case 4:
                    case 3:
                    case 2:
                        rubble_sum += rc.senseRubble(me.translate(-4, 2));
                        sense_count++;
                }
            case 3:
                switch (yn){
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
                switch (yp){
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
                switch (yn){
                    case 5:
                    case 4:
                        rubble_sum += rc.senseRubble(me.translate(-2, -4));
                        sense_count++;
                    case 3:
                    case 2:
                        rubble_sum += rc.senseRubble(me.translate(-2, -2));
                        sense_count++;
                }
                switch (yp){
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
                switch (yn){
                    case 5:
                    case 4:
                    case 3:
                        rubble_sum += rc.senseRubble(me.translate(-1, -3));
                        sense_count++;
                }
                switch (yp){
                    case 5:
                    case 4:
                    case 3:
                        rubble_sum += rc.senseRubble(me.translate(-1, 3));
                        sense_count++;
                }

        }
        switch (xp){
            case 5:
                rubble_sum += rc.senseRubble(me.translate(5, 0));
                sense_count++;
                switch (yn){
                    case 5:
                    case 4:
                    case 3:
                        rubble_sum += rc.senseRubble(me.translate(5, -3));
                        sense_count++;
                }
                switch (yp){
                    case 5:
                    case 4:
                    case 3:
                        rubble_sum += rc.senseRubble(me.translate(5, 3));
                        sense_count++;
                }
            case 4:
                switch (yn){
                    case 5:
                    case 4:
                    case 3:
                    case 2:
                        rubble_sum += rc.senseRubble(me.translate(4, -2));
                        sense_count++;
                }
                switch (yp){
                    case 5:
                    case 4:
                    case 3:
                    case 2:
                        rubble_sum += rc.senseRubble(me.translate(4, 2));
                        sense_count++;
                }
            case 3:
                switch (yn){
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
                switch (yp){
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
                switch (yn){
                    case 5:
                    case 4:
                        rubble_sum += rc.senseRubble(me.translate(2, -4));
                        sense_count++;
                    case 3:
                    case 2:
                        rubble_sum += rc.senseRubble(me.translate(2, -2));
                        sense_count++;
                }
                switch (yp){
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
                switch (yn){
                    case 5:
                    case 4:
                    case 3:
                        rubble_sum += rc.senseRubble(me.translate(1, -3));
                        sense_count++;
                }
                switch (yp){
                    case 5:
                    case 4:
                    case 3:
                        rubble_sum += rc.senseRubble(me.translate(1, 3));
                        sense_count++;
                }

        }

        return (int) (16 * Math.sqrt( rubble_sum / sense_count));
    }

    public static void incrementHeadCount() throws GameActionException {
        if (death_registered) {
            int v;
            switch (rc.getType()){
                case MINER:
                    v = rc.readSharedArray(0);
                    if ((v&0b1111_1111)==0b1111_1111)return; // avoid overflow
                    rc.writeSharedArray(0,  v+1);
                    break;
                case SOLDIER:
                    v = rc.readSharedArray(0);
                    if ((v&0b1111_1111_0000_0000)==0b1111_1111_0000_0000)return; // avoid overflow
                    rc.writeSharedArray(0,  v+0b1_0000_0000);
                    break;
                case BUILDER:
                    v = rc.readSharedArray(1);
                    if ((v&0b1111_1111)==0b1111_1111)return; // avoid overflow
                    rc.writeSharedArray(1,  v+1);
                    break;
                case WATCHTOWER:
                    v = rc.readSharedArray(1);
                    if ((v&0b1111_1111_0000_0000)==0b1111_1111_0000_0000)return; // avoid overflow
                    rc.writeSharedArray(1,  v+0b1_0000_0000);
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
            switch (rc.getType()){
                case MINER:
                    v = rc.readSharedArray(0);
                    if ((v&0b1111_1111)==0)return; // avoid overflow
                    rc.writeSharedArray(0,  v-1);
                    break;
                case SOLDIER:
                    v = rc.readSharedArray(0);
                    if ((v&0b1111_1111_0000_0000)==0)return; // avoid overflow
                    rc.writeSharedArray(0,  v-0b1_0000_0000);
                    break;
                case BUILDER:
                    v = rc.readSharedArray(1);
                    if ((v&0b1111_1111)==0)return; // avoid overflow
                    rc.writeSharedArray(1,  v-1);
                    break;
                case WATCHTOWER:
                    v = rc.readSharedArray(1);
                    if ((v&0b1111_1111_0000_0000)==0)return; // avoid overflow
                    rc.writeSharedArray(1,  v-0b1_0000_0000);
                    break;
                default:
                    return;
            }
            death_registered = true;
        }
    }

    public static int getHeadcount(RobotType desired_type) throws GameActionException {
        switch (desired_type){
            case MINER:
                return rc.readSharedArray(0)&0b1111_1111;
            case SOLDIER:
                return rc.readSharedArray(0)>>8;
            case BUILDER:
                return rc.readSharedArray(1)&0b1111_1111;
            case WATCHTOWER:
                return rc.readSharedArray(1)>>8;
            default:
                return 0;
        }
    }
}
