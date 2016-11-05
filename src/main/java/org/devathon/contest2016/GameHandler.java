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
 * Created by Martin on 05.11.2016.
 */
public class GameHandler implements Listener {
    
    private List<MachineGame> games = new ArrayList<>();
    private DevathonPlugin plugin;
    private ItemStack wrenchItem;
    
    public GameHandler(DevathonPlugin plugin) {
        this.plugin = plugin;
        
        wrenchItem = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta meta = wrenchItem.getItemMeta();
        meta.setDisplayName(GRAY + "Wrench");
        wrenchItem.setItemMeta(meta);
    }
    
    public Optional<MachineGame> getGame(Player player) {
        return games.stream().filter(game -> game.getPlayer().getUniqueId().equals(player.getUniqueId())).findAny();
    }
    
    public MachineGame startGame(Difficulty difficulty, Player player) {
        if (getGame(player).isPresent()) {
            player.spigot().sendMessage(plugin.getPrefix().append("You can only be in one game at a time!").color(RED).create());
        }
        
        MachineGame game = new MachineGame(this, difficulty, player);
        games.add(game);
        game.start();
        return game;
    }
    
    public void shutdown() {
        games.forEach(MachineGame::abort);
        games.clear();
    }
    
    public DevathonPlugin getPlugin() {
        return plugin;
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        // remove game on quit, only looser quit!
        getGame(event.getPlayer()).ifPresent(machineGame -> games.remove(machineGame));
    }
    
    public ItemStack getWrenchItem() {
        return wrenchItem;
    }
}
