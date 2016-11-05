package org.devathon.contest2016;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import static net.md_5.bungee.api.ChatColor.*;

/**
 * The game class, where everything comes together
 */
public class MachineGame {
    
    private Difficulty difficulty;
    private Player player;
    private Board board;
    private Location origin;
    private GameHandler handler;
    private List<BlockState> blocksToReset;
    
    /**
     * Inits this game
     *
     * @param handler    the gamehandler that handles this game
     * @param difficulty the difficulty of this game
     * @param player     the player that is playing this game
     */
    public MachineGame(GameHandler handler, Difficulty difficulty, Player player, Location origin) {
        this.handler = handler;
        this.difficulty = difficulty;
        this.player = player;
        this.board = new Board(difficulty.getXSize(), difficulty.getZSize());
        this.blocksToReset = new ArrayList<>();
        this.origin = origin;
    }
    
    /**
     * Sets the tile at (x,z) to the given tile
     *
     * @param x    the x coordinate
     * @param z    the z coordinate
     * @param type the tile type to set
     */
    public void setTile(int x, int z, TileType type) {
        System.out.printf("set " + x + ":" + z + " to " + type.name());
        board.setTile(x, z, type);
    }
    
    /**
     * Sets the tile at the giving location to the given type
     *
     * @param location the location
     * @param type     the tile type to set
     */
    public void setTile(Location location, TileType type, BlockState oldState) {
        Point2I point2I = locationToXZ(location);
        setTile(point2I.getX(), point2I.getZ(), type);
        if (oldState != null) {
            blocksToReset.add(oldState);
        }
    }
    
    /**
     * @return the board for this game
     */
    public Board getBoard() {
        return board;
    }
    
    /**
     * @return the player that plays this game
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * @return the difficulty of this game
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }
    
    /**
     * Sends the start message
     */
    public void start() {
        player.spigot().sendMessage(handler.getPlugin().getPrefix().append("The game starts now!").color(ChatColor.GOLD).create());
    }
    
    /**
     * Sends the player the abort message (and resets all blocks)
     */
    public void abort() {
        resetBlocks();
        player.spigot().sendMessage(handler.getPlugin().getPrefix().append("Your game was aborted!").color(ChatColor.RED).create());
    }
    
    /**
     * Sends the player the win message (and resets all blocks)
     */
    public void win() {
        resetBlocks();
        player.spigot().sendMessage(handler.getPlugin().getPrefix().append("You won").color(ChatColor.GREEN).create());
    }
    
    /**
     * Resets all blocks
     */
    public void resetBlocks() {
        Collections.reverse(blocksToReset);
        blocksToReset.forEach(state -> state.update(true));
        blocksToReset.clear();
    }
    
    /**
     * Sends the player some info about his game
     */
    public void sendGameInfo() {
        //TODO send game info
        player.spigot().sendMessage(handler.getPlugin().getPrefix().append("GAME INFO").color(YELLOW).create());
    }
    
    /**
     * Converts local space x z coordinates to a world space location
     *
     * @param x the local space x coordinate
     * @param z the local space z coordinate
     * @return the world space location
     */
    public Location xzToLocation(int x, int z) {
        return origin.clone().add(x, 0, z);
    }
    
    /**
     * Converts a word space location to a local space point2i
     *
     * @param location the world space location
     * @return the local space point2i
     */
    public Point2I locationToXZ(Location location) {
        int x = Math.max(location.getBlockX(), origin.getBlockX()) - Math.min(location.getBlockX(), origin.getBlockX());
        int z = Math.max(location.getBlockZ(), origin.getBlockZ()) - Math.min(location.getBlockZ(), origin.getBlockZ());
        
        return new Point2I(x, z);
    }
}
