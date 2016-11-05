package org.devathon.contest2016;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static net.md_5.bungee.api.ChatColor.*;

/**
 * The game class, where everything comes together
 */
public class MachineGame {
    
    private Difficulty difficulty;
    private Player player;
    private Board board;
    private GameHandler handler;
    private List<BlockState> blocksToReset;
    // TODO load map stuff from somewhere
    private Location origin;
    private Map<TileType, Point2I> startPoints;
    private Map<TileType, Point2I> endPoints;
    
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
        this.board = new Board(difficulty.getXSize(), difficulty.getZSize(), this);
        this.blocksToReset = new ArrayList<>();
        this.origin = origin;
        this.startPoints = new HashMap<>();
        this.endPoints = new HashMap<>();
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
        checkWin();
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
        // run one tick later so that last block still can get cleared out
        new BukkitRunnable() {
            @Override
            public void run() {
                Collections.reverse(blocksToReset);
                blocksToReset.forEach(state -> state.update(true));
                blocksToReset.clear();
            }
        }.runTaskLater(handler.getPlugin(), 1);
    }
    
    /**
     * Sends the player some info about his game
     */
    public void sendGameInfo() {
        //TODO send game info
        player.spigot().sendMessage(handler.getPlugin().getPrefix().append("GAME INFO").color(YELLOW).create());
    }
    
    /**
     * Handles the wrench action to change the direction of the flow
     *
     * @param x the x coordinate
     * @param z the z coordinate
     */
    public void wrench(int x, int z) {
        Direction direction = board.getDirection(x, z);
        Direction next = direction.getNext();
        board.setDirections(x, z, next);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("(" + x + "," + z + ") set to ").color(YELLOW).append(next.name()).color(GOLD).create());
        checkWin();
    }
    
    /**
     * Checks if the player has won
     */
    public void checkWin() {
        startPoints.put(TileType.ELECTIRCITY, new Point2I(0, 0));
        endPoints.put(TileType.ELECTIRCITY, new Point2I(4, 4));
        
        if (board.checkWin(startPoints, endPoints)) {
            win();
        }
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
        //TODO fix this, this allows placement outside of the area
        int x = Math.max(location.getBlockX(), origin.getBlockX()) - Math.min(location.getBlockX(), origin.getBlockX());
        int z = Math.max(location.getBlockZ(), origin.getBlockZ()) - Math.min(location.getBlockZ(), origin.getBlockZ());
        
        return new Point2I(x, z);
    }
}
