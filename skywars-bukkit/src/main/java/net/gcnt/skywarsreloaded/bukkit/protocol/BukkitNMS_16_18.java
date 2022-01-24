package net.gcnt.skywarsreloaded.bukkit.protocol;

import net.gcnt.skywarsreloaded.SkyWarsReloaded;
import net.gcnt.skywarsreloaded.bukkit.wrapper.player.BukkitSWPlayer;
import net.gcnt.skywarsreloaded.bukkit.wrapper.world.BukkitSWWorld;
import net.gcnt.skywarsreloaded.utils.SWCoord;
import net.gcnt.skywarsreloaded.wrapper.player.SWPlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

public class BukkitNMS_16_18 extends BukkitNMS_13_15 {

    public BukkitNMS_16_18(SkyWarsReloaded plugin, String serverPackage) {
        super(plugin, serverPackage);
    }

    @Override
    public void sendActionbar(SWPlayer player, String message) {
        if (!(player instanceof BukkitSWPlayer)) return;
        BukkitSWPlayer bukkitSWPlayer = (BukkitSWPlayer) player;

        bukkitSWPlayer.getPlayer().spigot().sendMessage(
                net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                player.getUuid(),
                new net.md_5.bungee.api.chat.TextComponent(message)
        );
    }

    @Override
    public void sendJSONMessage(SWPlayer player, String message) {
        if (!(player instanceof BukkitSWPlayer)) return;
        BukkitSWPlayer bukkitSWPlayer = (BukkitSWPlayer) player;

        bukkitSWPlayer.getPlayer().spigot().sendMessage(
                net.md_5.bungee.api.ChatMessageType.SYSTEM,
                new net.md_5.bungee.api.chat.TextComponent(message));

    }

    @Override
    public void setChestOpen(SWCoord loc, boolean open) {
        if (loc.world() == null || !(loc.world() instanceof BukkitSWWorld)) return;
        World world = ((BukkitSWWorld) loc.world()).getBukkitWorld();

        Block block = world.getBlockAt(loc.x(), loc.y(), loc.z());
        Chest chest = (Chest) block.getState();
        if (open) chest.open();
        else chest.close();
    }
}
