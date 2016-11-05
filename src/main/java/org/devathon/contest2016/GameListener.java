package org.devathon.contest2016;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Handles all events for players that are currently playing the game
 */
public class GameListener implements Listener {
    
    private GameHandler gameHandler;
    
    public GameListener(GameHandler handler) {
        this.gameHandler = handler;
    }
    
    @EventHandler
    public void onTilePlace(BlockPlaceEvent event) {
        Optional<MachineGame> game = gameHandler.getGame(event.getPlayer());
        if (game.isPresent()) {
            if (isLocationOnBoard(event.getBlockPlaced().getLocation(), game.get())) {
                TileType type = TileType.from(event.getItemInHand());
                if (type != null) {
                    game.get().setTile(event.getBlockPlaced().getLocation(), type, event.getBlockReplacedState());
                } else {
                    // no building while ingame!
                    event.setBuild(false);
                }
            } else {
                // no building while ingame!
                event.setBuild(false);
            }
        }
    }
    
    @EventHandler
    public void onTileBreak(BlockBreakEvent event) {
        Optional<MachineGame> game = gameHandler.getGame(event.getPlayer());
        if (game.isPresent()) {
            if (isLocationOnBoard(event.getBlock().getLocation(), game.get())) {
                TileType type = TileType.from(event.getBlock().getType(), event.getBlock().getData());
                if (type != null) {
                    game.get().setTile(event.getBlock().getLocation(), TileType.AIR, null);
                } else {
                    // no building while ingame!
                    event.setCancelled(true);
                }
            } else {
                // no building while ingame!
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void wrench(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
    
        System.out.println("event");
        Optional<MachineGame> game = gameHandler.getGame(event.getPlayer());
        if (game.isPresent()) {
            if (isLocationOnBoard(event.getClickedBlock().getLocation(), game.get())) {
                if (event.getPlayer().getItemInHand().equals(gameHandler.getWrenchItem())) {
                    Point2I point2I = game.get().locationToXZ(event.getClickedBlock().getLocation());
                    game.get().wrench(point2I.getX(), point2I.getZ());
                }
            } else {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void noDrop(PlayerDropItemEvent event) {
        Optional<MachineGame> game = gameHandler.getGame(event.getPlayer());
        // no drops
        game.ifPresent(machineGame -> event.setCancelled(true));
    }
    
    private boolean isLocationOnBoard(Location loc, MachineGame game) {
        Point2I point2I = game.locationToXZ(loc);
        return point2I.getX() < game.getDifficulty().getXSize() && point2I.getZ() < game.getDifficulty().getZSize() && point2I.getX() >= 0 && point2I.getZ() >= 0;
    }
}
