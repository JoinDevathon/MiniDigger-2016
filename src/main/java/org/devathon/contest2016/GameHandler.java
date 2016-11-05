package org.devathon.contest2016;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
public class GameHandler implements Listener, CommandExecutor {
    
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
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("game")) {
            return false;
        }
        
        Player player = (Player) sender;
        
        if (args.length == 0) {
            Optional<MachineGame> game = getGame(player);
            if (game.isPresent()) {
                game.get().sendGameInfo();
            } else {
                player.spigot().sendMessage(plugin.getPrefix().append("Hello there, welcome to my submission to the 2016 devathon!").color(YELLOW).create());
                player.spigot().sendMessage(plugin.getPrefix().append("This is a game about machines, you will need a wrench ;)").color(YELLOW).create());
                if (player.getInventory().contains(wrenchItem)) {
                    player.spigot().sendMessage(plugin.getPrefix().append("Oh, I see you already you one, have a cookie instead!").color(YELLOW).create());
                    player.getInventory().addItem(new ItemStack(Material.COOKIE));
                } else {
                    player.getInventory().addItem(wrenchItem);
                }
                player.spigot().sendMessage(plugin.getPrefix().append("To start the game, enter (or just click) ").color(YELLOW)
                        .append("'/game start'").color(GOLD).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/game start")).create());
            }
            
            return true;
        }
        
        switch (args[0]) {
            case "start":
                if (args.length == 1) {
                    player.spigot().sendMessage(plugin.getPrefix().append("You need to enter a difficulty!").color(RED).create());
                    ComponentBuilder builder = plugin.getPrefix().append("You may choose between those difficulties: ").color(YELLOW);
                    for (Difficulty difficulty : Difficulty.values()) {
                        builder.append(difficulty.name() + "(" + difficulty.getXSize() + "x" + difficulty.getZSize() + ") ")
                                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/game start " + difficulty.name())).color(GOLD);
                    }
                    return true;
                }
                
                Difficulty difficulty;
                try {
                    difficulty = Difficulty.valueOf(args[1]);
                } catch (IllegalArgumentException ex) {
                    player.spigot().sendMessage(plugin.getPrefix().append("Unknown difficulty ").color(RED).append(args[1]).color(DARK_RED).append("!").color(RED).create());
                    ComponentBuilder builder = plugin.getPrefix().append("You may choose between those difficulties: ").color(YELLOW);
                    for (Difficulty diff : Difficulty.values()) {
                        builder.append(diff.name() + "(" + diff.getXSize() + "x" + diff.getZSize() + ") ")
                                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/game start " + diff.name())).color(GOLD);
                    }
                    return true;
                }
                
                MachineGame game = startGame(difficulty, player);
                break;
            default:
                player.spigot().sendMessage(plugin.getPrefix().append("Unknown sub command!").color(RED).create());
                break;
        }
        
        return true;
    }
}
