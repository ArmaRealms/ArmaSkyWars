package net.gcnt.skywarsreloaded.utils;

import com.sk89q.worldedit.world.World;
import net.gcnt.skywarsreloaded.wrapper.world.SWWorld;

import java.util.UUID;

public interface PlatformUtils {

    boolean isInt(String arg0);

    boolean isDouble(String arg0);

    boolean isFloat(String arg0);

    boolean isBoolean(String arg0);

    /**
     * @param arg0 UUID string to parse.
     * @return UUID if valid, null if not.
     */
    UUID getUUIDFromString(String arg0);

    String colorize(String arg0);

    int getServerVersion();

    int getBuildLimit();

    World getWorldEditWorld(String worldName);

    SWWorld getSWWorld(String worldName);

}
