package com.walrusone.skywarsreloaded.nms.v1_18_R2;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.EnderChest;
import org.bukkit.entity.*;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NMSHandler extends com.walrusone.skywarsreloaded.nms.v1_17_R1.NMSHandler {

    public void spawnDragon(World world, Location loc) {
        EnderDragon dragon = (EnderDragon) world.spawnEntity(loc, EntityType.ENDER_DRAGON);
        dragon.setPhase(EnderDragon.Phase.CHARGE_PLAYER);
        Location locClone = loc.clone();
        locClone.setYaw(ThreadLocalRandom.current().nextFloat() * 360.0F);
        locClone.setPitch(0.0F);
        dragon.teleport(locClone);
    }

    public void playChestAction(Block block, boolean open) {
        Location location = block.getLocation();
        if (location.getWorld() == null) return;

        WorldServer world = ((org.bukkit.craftbukkit.v1_18_R2.CraftWorld) location.getWorld()).getHandle();
        BlockPosition position = new BlockPosition(location.getX(), location.getY(), location.getZ());

        if (!(block.getState() instanceof EnderChest enderChest)) return;

        world.a(position, ((org.bukkit.craftbukkit.v1_18_R2.block.CraftEnderChest) enderChest).getHandle(), 1, open ? 1 : 0);
    }

    public void setEntityTarget(org.bukkit.entity.Entity bukkitEntity, Player player) {
        if (bukkitEntity instanceof Creature) {
            ((Creature) bukkitEntity).setTarget(player);
        }
    }


    public ChunkGenerator getChunkGenerator() {
        return new ChunkGenerator() {
            @Override
            public ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int chunkX, int chunkZ, @NotNull BiomeGrid chunkGenerator) {
                ChunkData chunkData = Bukkit.getServer().createChunkData(world);
                int min = world.getMinHeight();
                int max = world.getMaxHeight();
                Biome biome = Biome.THE_VOID;
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        for (int y = min; y < max; y += 4) {
                            chunkGenerator.setBiome(x, y, z, biome);
                        }
                    }
                }
                return chunkData;
            }
        };
    }

    public boolean checkMaterial(FallingBlock fb, Material mat) {
        return fb.getBlockData().getMaterial().equals(mat);
    }

    public int getVersion() {
        return 18;
    }


}
