package io.github.ignorelicensescn.minimizeFactory.utils.mathutils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import static io.github.ignorelicensescn.minimizeFactory.utils.mathutils.BlockLocationSign.*;

public record BlockLocation(long x, long y, long z, int hash, BlockLocationSign sign, Vector vector) {
    public static final BlockLocationSign[][][] allSign = new BlockLocationSign[3][3][3];
    public BlockLocation(long x, long y, long z){
        this(x,y,z,(int)(y<<24+x*31+z),extractSign(x,y,z),new Vector(x,y,z));
    }

    public BlockLocation(Location from){
        this(from.getBlockX(),from.getBlockY(),from.getBlockZ());
    }

    public BlockLocation subtract(BlockLocation newZeroPoint){
        return new BlockLocation(x-newZeroPoint.x,y-newZeroPoint.y,z-newZeroPoint.z);
    }

    public BlockLocation add(BlockLocation location){
        return new BlockLocation(x+location.x,y+location.y,z+location.z);
    }

    //generated via python script

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BlockLocation that)) return false;

        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return hash;//idk whether hashCode will be calculated once record init so I did this.
    }
}
