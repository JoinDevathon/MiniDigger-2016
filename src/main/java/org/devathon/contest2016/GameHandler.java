package org.devathon.contest2016;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static net.md_5.bungee.api.ChatColor.*;

/**
 * Handles everything related to the games. manages the game instances and whatnot.
 */
public class GameHandler implements Listener {
    
    private List<MachineGame> games = new ArrayList<>();
    private DevathonPlugin plugin;
    private ItemStack wrenchItem;
    
    /**
     * initialises a new gamehandler
     *
     * @param plugin the calling plugin instance
     */
    public GameHandler(DevathonPlugin plugin) {
        this.plugin = plugin;
        
        wrenchItem = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta meta = wrenchItem.getItemMeta();
        meta.setDisplayName(GRAY + "Wrench");
        wrenchItem.setItemMeta(meta);
    }
    
    /**
     * Searches for the game player is playing in
     *
     * @param player the player which game should be searched for
     * @return the game, if present
     */
    public Optional<MachineGame> getGame(Player player) {
        return games.stream().filter(game -> game.getPlayer().getUniqueId().equals(player.getUniqueId())).findAny();
    }
    
    /**
     * Starts a new game
     *
     * @param difficulty the difficulty of the game
     * @param player     the player that wants to play
     * @return the new game
     */
    public MachineGame startGame(Difficulty difficulty, Player player) {
        if (getGame(player).isPresent()) {
            player.spigot().sendMessage(plugin.getPrefix().append("You can only be in one game at a time!").color(RED).create());
        }
        
        MachineGame game = new MachineGame(this, difficulty, player, player.getLocation()); //TODO change origin location
        games.add(game);
        game.start();
        return game;
    }
    
    /**
     * Shuts down this gamehandler, aborting all running games
     */
    public void shutdown() {
        games.forEach(MachineGame::abort);
        games.clear();
    }
    
    /**
     * @return the plugin instance
     */
    public DevathonPlugin getPlugin() {
        return plugin;
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        // remove game on quit, only looser quit!
        getGame(event.getPlayer()).ifPresent(machineGame -> games.remove(machineGame));
    }
    
    /**
     * @return the wrench item
     */
    public ItemStack getWrenchItem() {
        return wrenchItem;
    }
    
    /**
     * Aborts the game the player is playing in, sends a error message if he is not playing
     *
     * @param player the player who wants to abort his game
     */
    public void abortGame(Player player) {
        Optional<MachineGame> game = getGame(player);
        if (!game.isPresent()) {
            player.spigot().sendMessage(plugin.getPrefix().append("You are not in a game!").color(RED).create());
        } else {
            game.get().abort();
            games.remove(game.get());
        }
    }
}
