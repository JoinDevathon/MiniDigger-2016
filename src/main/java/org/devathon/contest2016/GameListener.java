package org.devathon.contest2016;

import java.util.Optional;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
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
            TileType type = TileType.from(event.getItemInHand());
            if (type != null) {
                game.get().setTile(event.getBlockPlaced().getLocation(), type, event.getBlockReplacedState());
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
            TileType type = TileType.from(event.getBlock().getType(), event.getBlock().getData());
            if (type != null) {
                game.get().setTile(event.getBlock().getLocation(), TileType.AIR, null);
            } else {
                // no building while ingame!
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void wrench(PlayerInteractEvent event) {
        
    }
}
