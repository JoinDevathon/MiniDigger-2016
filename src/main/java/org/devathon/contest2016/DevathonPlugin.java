package org.devathon.contest2016;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_10_R1.BlockPosition;

import org.devathon.contest2016.game.GameCommandExecutor;
import org.devathon.contest2016.game.GameHandler;
import org.devathon.contest2016.game.GameListener;
import org.devathon.contest2016.level.LevelHandler;
import org.devathon.contest2016.structure.StructureHandler;
import org.devathon.contest2016.structure.StructureUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import static net.md_5.bungee.api.ChatColor.*;

public class DevathonPlugin extends JavaPlugin {
    
    private GameHandler gameHandler;
    private StructureHandler structureHandler;
    private LevelHandler levelHandler;
    
    @Override
    public void onEnable() {
        structureHandler = new StructureHandler(this);
        structureHandler.load();
        
        levelHandler = new LevelHandler();
        
        gameHandler = new GameHandler(this);
        getCommand("game").setExecutor(new GameCommandExecutor(gameHandler, levelHandler));
        getServer().getPluginManager().registerEvents(new GameListener(gameHandler), this);
        
        getServer().getPluginManager().registerEvents(new GetRidOfStupidShit(), this);
    }
    
    @Override
    public void onDisable() {
        gameHandler.shutdown();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("test")) {
            Player player = (Player) sender;
            if (args[0].equalsIgnoreCase("save")) {
                StructureUtil.save(player.getLocation(), new BlockPosition(5, 5, 5), "TEST", "MiniDigger");
            } else {
                StructureUtil.load(player.getLocation(), "TEST");
            }
            return true;
        }
        return false;
    }
    
    /**
     * @return the prefix that should be used for messages
     */
    public ComponentBuilder getPrefix() {
        return new ComponentBuilder("[").color(ChatColor.BLUE).append("MachineGame").color(RED)
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("Made by MiniDigger for Devathon 2016!")}))
                .append("] ").color(BLUE);
    }
}

