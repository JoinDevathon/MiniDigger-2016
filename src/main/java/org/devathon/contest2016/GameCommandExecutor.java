package org.devathon.contest2016;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static net.md_5.bungee.api.ChatColor.*;

/**
 * Executor for the /game command
 */
public class GameCommandExecutor implements CommandExecutor {
    
    private GameHandler handler;
    
    public GameCommandExecutor(GameHandler handler) {
        this.handler = handler;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("game")) {
            return false;
        }
        
        Player player = (Player) sender;
        
        if (args.length == 0) {
            Optional<MachineGame> game = handler.getGame(player);
            if (game.isPresent()) {
                game.get().sendGameInfo();
            } else {
                player.spigot().sendMessage(handler.getPlugin().getPrefix().append("Hello there, welcome to my submission to the 2016 devathon!").color(YELLOW).create());
                player.spigot().sendMessage(handler.getPlugin().getPrefix().append("This is a game about machines, you will need a wrench ;)").color(YELLOW).create());
                if (player.getInventory().contains(handler.getWrenchItem())) {
                    player.spigot().sendMessage(handler.getPlugin().getPrefix().append("Oh, I see you already you one, have a cookie instead!").color(YELLOW).create());
                    player.getInventory().addItem(new ItemStack(Material.COOKIE));
                } else {
                    player.getInventory().addItem(handler.getWrenchItem());
                }
                player.spigot().sendMessage(handler.getPlugin().getPrefix().append("To start the game, enter (or just click) ").color(YELLOW)
                        .append("'/game start'").color(GOLD).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/game start")).create());
            }
            
            return true;
        }
        
        switch (args[0]) {
            case "start":
                if (args.length == 1) {
                    player.spigot().sendMessage(handler.getPlugin().getPrefix().append("You need to enter a difficulty!").color(RED).create());
                    ComponentBuilder builder = handler.getPlugin().getPrefix().append("You may choose between those difficulties: ").color(YELLOW);
                    for (Difficulty difficulty : Difficulty.values()) {
                        builder.append(difficulty.name() + "(" + difficulty.getXSize() + "x" + difficulty.getZSize() + ") ")
                                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/game start " + difficulty.name())).color(GOLD);
                    }
                    player.spigot().sendMessage(builder.create());
                    return true;
                }
                
                Difficulty difficulty;
                try {
                    difficulty = Difficulty.valueOf(args[1]);
                } catch (IllegalArgumentException ex) {
                    player.spigot().sendMessage(handler.getPlugin().getPrefix().append("Unknown difficulty ").color(RED).append(args[1]).color(DARK_RED).append("!").color(RED).create());
                    ComponentBuilder builder = handler.getPlugin().getPrefix().append("You may choose between those difficulties: ").color(YELLOW);
                    for (Difficulty diff : Difficulty.values()) {
                        builder.append(diff.name() + "(" + diff.getXSize() + "x" + diff.getZSize() + ") ")
                                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/game start " + diff.name()))
                                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("Click to start a " + diff.name() + " game")})).color(GOLD);
                    }
                    player.spigot().sendMessage(builder.create());
                    return true;
                }
                
                handler.startGame(difficulty, player);
                break;
            default:
                player.spigot().sendMessage(handler.getPlugin().getPrefix().append("Unknown sub command!").color(RED).create());
                break;
        }
        
        return true;
    }
}
