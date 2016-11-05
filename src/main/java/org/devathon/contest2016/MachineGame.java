package org.devathon.contest2016;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import static net.md_5.bungee.api.ChatColor.*;

/**
 * Created by Martin on 05.11.2016.
 */
public class MachineGame {
    
    private Difficulty difficulty;
    private Player player;
    private Board board;
    private GameHandler handler;
    
    public MachineGame(GameHandler handler, Difficulty difficulty, Player player) {
        this.handler = handler;
        this.difficulty = difficulty;
        this.player = player;
        this.board = new Board(difficulty.getXSize(), difficulty.getZSize());
    }
    
    public Board getBoard() {
        return board;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public Difficulty getDifficulty() {
        return difficulty;
    }
    
    public void start() {
        player.spigot().sendMessage(handler.getPlugin().getPrefix().append("The game starts now!").color(ChatColor.GOLD).create());
    }
    
    public void abort() {
        player.spigot().sendMessage(handler.getPlugin().getPrefix().append("Your game was aborted!").color(ChatColor.RED).create());
    }
    
    public void sendGameInfo() {
        //TODO send game info
        player.spigot().sendMessage(handler.getPlugin().getPrefix().append("GAME INFO").color(YELLOW).create());
    }
    
}
