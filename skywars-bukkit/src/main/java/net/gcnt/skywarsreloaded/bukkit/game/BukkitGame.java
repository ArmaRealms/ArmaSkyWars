package net.gcnt.skywarsreloaded.bukkit.game;

import net.gcnt.skywarsreloaded.bukkit.wrapper.BukkitSWPlayer;
import net.gcnt.skywarsreloaded.wrapper.AbstractSWPlayer;
import net.gcnt.skywarsreloaded.game.Game;
import net.gcnt.skywarsreloaded.game.Game;
import net.gcnt.skywarsreloaded.utils.Coord;

import java.util.List;

public class BukkitGame implements Game {

    private final String name;
    private final int maxPlayers;
    private String creator;
    private List<BukkitSWPlayer> players;
    private GameStatus status;
    private int timer;
    private Coord spectateSpawn;
    private Coord lobbySpawn;
    private List<Coord> chests;

    public BukkitGame(String name, int maxPlayers) {
        this.name = name;
        this.maxPlayers = maxPlayers;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public String getCreator() {
        return creator;
    }

    @Override
    public void addPlayers(AbstractSWPlayer... players) {
        // todo this
    }

    @Override
    public void removePlayer(AbstractSWPlayer player) {
        // todo this
    }

    @Override
    public List<? extends AbstractSWPlayer> getPlayers() {
        return players;
    }

    @Override
    public List<AbstractSWPlayer> getAlivePlayers() {
        return null;
    }

    @Override
    public List<AbstractSWPlayer> getSpectators() {
        return null;
    }

    @Override
    public GameStatus getStatus() {
        return null;
    }

    @Override
    public void setStatus(GameStatus status) {

    }

    @Override
    public int getTeamSize() {
        return 0;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public int getTimer() {
        return 0;
    }

    @Override
    public int setTimer() {
        return 0;
    }

    @Override
    public Coord getSpectateSpawn() {
        return null;
    }

    @Override
    public void setSpectateSpawn(Coord loc) {

    }

    @Override
    public Coord getWaitingLobbySpawn() {
        return null;
    }

    @Override
    public void setWaitingLobbySpawn(Coord loc) {

    }

    @Override
    public void disable() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void saveData() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void addChest(Coord loc) {

    }

    @Override
    public void removeChest(Coord loc) {

    }

    @Override
    public List<Coord> getChests() {
        return null;
    }
}
