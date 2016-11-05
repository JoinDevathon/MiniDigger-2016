package org.devathon.contest2016;

import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.Blocks;
import net.minecraft.server.v1_10_R1.DefinedStructure;
import net.minecraft.server.v1_10_R1.DefinedStructureManager;
import net.minecraft.server.v1_10_R1.MinecraftKey;
import net.minecraft.server.v1_10_R1.MinecraftServer;
import net.minecraft.server.v1_10_R1.WorldServer;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;

/**
 * Created by Martin on 05.11.2016.
 */
public class StructureUtil {
    
    /**
     * Saves a schematic
     *
     * @param start  the start location
     * @param size   the size
     * @param name   the name
     * @param author the author
     * @return idk, ask nms...
     */
    public static boolean save(Location start, BlockPosition size, String name, String author) {
        BlockPosition startPos = new BlockPosition(start.getBlockX(), start.getBlockY(), start.getBlockZ());
        WorldServer world = ((CraftWorld) start.getWorld()).getHandle();
        MinecraftServer server = world.getMinecraftServer();
        DefinedStructureManager structureManager = world.y();
        DefinedStructure structure = structureManager.a(server, new MinecraftKey(name));
        structure.a(world, startPos, size, false, Blocks.dj); // false -> do not includ entities, dj -> stucture void
        structure.a(author);
        return structureManager.d(server, new MinecraftKey(name));
    }
}
