package com.walrusone.skywarsreloaded.commands.player;

import com.walrusone.skywarsreloaded.SkyWarsReloaded;
import com.walrusone.skywarsreloaded.commands.BaseCmd;
import com.walrusone.skywarsreloaded.enums.MatchState;
import com.walrusone.skywarsreloaded.game.GameMap;
import com.walrusone.skywarsreloaded.managers.MatchManager;
import com.walrusone.skywarsreloaded.utilities.Messaging;
import com.walrusone.skywarsreloaded.utilities.Party;
import com.walrusone.skywarsreloaded.utilities.SWRServer;
import com.walrusone.skywarsreloaded.utilities.Util;
import me.gaagjescraft.network.team.skywarsreloaded.extension.SWExtension;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Command to allow players (or parties) to join a game map.
 */
public class SWPlayCmd extends BaseCmd {

    public SWPlayCmd(String t) {
        type = t;
        forcePlayer = true;
        cmdName = "play";
        alias = new String[]{"jogar"};
        argLength = 2;
    }

    @Override
    public boolean run(CommandSender sender, Player player, String[] args) {
        // Check if the player is already in a game.
        GameMap currentMap = MatchManager.get().getPlayerMap(player);
        if (currentMap != null) {
            if (Bukkit.getPluginManager().isPluginEnabled("Skywars-Extension")) {
                String message = SWExtension.get().getConfig().getString("already_ingame");
                player.sendMessage(SWExtension.c(message));
            } else {
                player.sendMessage(format("error.already-in-game"));
            }
            return true;
        }

        // In bungee mode, if this is not the lobby server, use a simple join method.
        if (SkyWarsReloaded.getCfg().bungeeMode() && !SkyWarsReloaded.getCfg().isLobbyServer()) {
            SWRServer availableServer = SWRServer.getAvailableServer();
            if (availableServer != null) {
                availableServer.setPlayerCount(availableServer.getPlayerCount() + 1);
                availableServer.updateSigns();
                SkyWarsReloaded.get().sendBungeeMsg(player, "Connect", availableServer.getServerName());
            }
            return true;
        }

        // Join a specific map.
        String mapName = args[1];
        return joinSpecificMap(player, mapName);
    }

    /**
     * Attempts to join the player (or their party) to the specified game map.
     *
     * @param player  The player attempting to join.
     * @param mapName The name of the map to join.
     * @return Always returns true.
     */
    private boolean joinSpecificMap(Player player, String mapName) {
        GameMap gameMap = SkyWarsReloaded.getGameMapMgr().getMap(mapName);
        if (gameMap == null) {
            player.sendMessage(format("error.map-does-not-exist"));
            return true;
        }

        SWRServer server = null;
        MatchState matchState;
        boolean isBungeeLobby = SkyWarsReloaded.getCfg().bungeeMode() && SkyWarsReloaded.getCfg().isLobbyServer();

        if (isBungeeLobby) {
            server = SWRServer.getServer(mapName);
            if (server == null) {
                player.sendMessage(format("error.map-does-not-exist"));
                return true;
            }
            matchState = server.getMatchState();
        } else {
            matchState = gameMap.getMatchState();
        }

        // Only allow joining if the match state is appropriate.
        if (matchState != MatchState.WAITINGSTART && matchState != MatchState.WAITINGLOBBY) {
            Util.get().playSound(player, player.getLocation(), SkyWarsReloaded.getCfg().getErrorSound(), 1, 1);
            player.sendMessage(format("error.could-not-join"));
            return true;
        }

        // Check player permission.
        if (!player.hasPermission("sw.play")) {
            player.sendMessage(format("error.no-perm"));
            return true;
        }

        // Close the inventory to avoid visual conflicts.
        player.closeInventory();

        Party party = Party.getParty(player);
        boolean joined = false;

        if (party != null) {
            // Only the party leader may initiate the join.
            if (!party.getLeader().equals(player.getUniqueId())) {
                player.sendMessage(format("party.onlyleader"));
                return true;
            }
            if (gameMap.canAddParty(party)) {
                joined = gameMap.addPlayers(null, party);
            } else if (server != null && server.canAddParty(party)) {
                server.setPlayerCount(server.getPlayerCount() + party.getSize() - 1);
                server.updateSigns();
                // Send connection message to each party member.
                for (UUID memberId : party.getMembers()) {
                    Player member = Bukkit.getPlayer(memberId);
                    if (member != null) {
                        SkyWarsReloaded.get().sendBungeeMsg(member, "Connect", server.getServerName());
                    }
                }
                joined = true;
            }
        } else {
            // Single player join.
            if (gameMap.canAddPlayer(player)) {
                joined = gameMap.addPlayers(null, player);
            } else if (server != null && server.canAddPlayer()) {
                server.setPlayerCount(server.getPlayerCount() + 1);
                server.updateSigns();
                SkyWarsReloaded.get().sendBungeeMsg(player, "Connect", server.getServerName());
                joined = true;
            }
        }

        if (!joined) {
            player.sendMessage(format("error.could-not-join2"));
        }
        return true;
    }

    /**
     * Helper method to format messages.
     *
     * @param key The message key.
     * @return The formatted message.
     */
    private String format(String key) {
        return new Messaging.MessageFormatter().format(key);
    }
}
