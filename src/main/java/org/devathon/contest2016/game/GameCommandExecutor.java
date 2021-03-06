package org.devathon.contest2016.game;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.devathon.contest2016.level.Level;
import org.devathon.contest2016.level.LevelHandler;
import org.devathon.contest2016.level.LineType;
import org.devathon.contest2016.stuff.Difficulty;
import org.devathon.contest2016.stuff.Point2I;
import org.devathon.contest2016.stuff.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static net.md_5.bungee.api.ChatColor.*;

/**
 * Executor for the /game command
 */
public class GameCommandExecutor implements TabCompleter, CommandExecutor {
    
    private GameHandler handler;
    private LevelHandler levelHandler;
    
    public GameCommandExecutor(GameHandler handler, LevelHandler levelHandler) {
        this.handler = handler;
        this.levelHandler = levelHandler;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("game")) {
            return false;
        }
        
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Sadly, this game is not designed to be played form the command line...");
            return false;
        }
        
        Player player = (Player) sender;
      
        /*
         * /game
         */
        if (args.length == 0) {
            Optional<Game> game = handler.getGame(player);
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
            /*
             * /game start
             */
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
                
                Optional<Level> level = levelHandler.getLevel(args[1], player.getLocation().clone());
                if (!level.isPresent()) {
                    player.spigot().sendMessage(handler.getPlugin().getPrefix().append("Unknown level ").color(RED).append(args[1]).color(DARK_RED).append("!").color(RED).create());
                    return true;
                }
                level.get().setTypes(generateRandomType(difficulty));
                handler.startGame(player, level.get(), true);
                break;
            /*
             * /game abort
             */
            case "abort":
                handler.abortGame(player);
                break;
            /*
             * /game level
             */
            case "level":
                Optional<Level> lvl = levelHandler.getLevel(args[1], player.getLocation().clone());
                if (!lvl.isPresent()) {
                    player.spigot().sendMessage(handler.getPlugin().getPrefix().append("Unknown level ").color(RED).append(args[1]).color(DARK_RED).append("!").color(RED).create());
                    return true;
                }
                handler.startGame(player, lvl.get(), false);
                break;
            /*
             * /game <unknown>
             */
            default:
                player.spigot().sendMessage(handler.getPlugin().getPrefix().append("Unknown sub command!").color(RED).create());
                break;
        }
        
        return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("game")) {
            List<String> result = new ArrayList<>();
            if (args.length == 0) {
                result.add("start");
                result.add("abort");
                result.add("level");
                return result;
            } else if (args.length == 1) {
                result.add("start");
                result.add("abort");
                result.add("level");
                return complete(result, args[0]);
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("start")) {
                    for (Difficulty difficulty : Difficulty.values()) {
                        result.add(difficulty.name());
                    }
                    
                    return complete(result, args[1]);
                } else if (args[0].equalsIgnoreCase("level")) {
                    result.add("Level1");
                    result.add("Level2");
                    return complete(result, args[1]);
                }
            }
        }
        return null;
    }
    
    private List<String> complete(List<String> list, String prefix) {
        final List<String> result = new ArrayList<>();
        
        for (String s : list) {
            if (s.toLowerCase().startsWith(prefix.toLowerCase())) {
                result.add(s);
            }
        }
        
        return result;
    }
    
    private List<LineType> generateRandomType(Difficulty difficulty) {
        List<LineType> types = new ArrayList<>();
        List<Point2I> choosen = new ArrayList<>();
        
        switch (difficulty) {
            case HARD:
                types.add(generateType(TileType.ORANGE, difficulty, choosen));
                types.add(generateType(TileType.PINK, difficulty, choosen));
                types.add(generateType(TileType.GRAY, difficulty, choosen));
                types.add(generateType(TileType.YELLOW, difficulty, choosen));
            case NORMAL:
                types.add(generateType(TileType.BLUE, difficulty, choosen));
                types.add(generateType(TileType.GREEN, difficulty, choosen));
                types.add(generateType(TileType.CYAN, difficulty, choosen));
            case EASY:
                types.add(generateType(TileType.RED, difficulty, choosen));
                types.add(generateType(TileType.BROWN, difficulty, choosen));
        }
        
        return types;
    }
    
    private Point2I randomPointInRange(int maxX, int maxZ, List<Point2I> choosen) {
        int x = ThreadLocalRandom.current().nextInt(maxX);
        int z = ThreadLocalRandom.current().nextInt(maxZ);
        Point2I p = new Point2I(x, z);
        if (choosen.contains(p)) {
            return randomPointInRange(maxX, maxZ, choosen);
        } else {
            choosen.add(p);
            return p;
        }
    }
    
    private LineType generateType(TileType type, Difficulty difficulty, List<Point2I> choosen) {
        Point2I start = randomPointInRange(difficulty.getXSize(), difficulty.getZSize(), choosen);
        Point2I end = randomPointInRange(difficulty.getXSize(), difficulty.getZSize(), choosen);
        return new LineType(type, start, end);
    }
}
