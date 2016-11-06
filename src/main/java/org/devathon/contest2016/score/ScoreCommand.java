package org.devathon.contest2016.score;

import net.md_5.bungee.api.ChatColor;

import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.stuff.Difficulty;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Martin on 06.11.2016.
 */
public class ScoreCommand implements CommandExecutor {
    
    private DevathonPlugin plugin;
    
    public ScoreCommand(DevathonPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        if (args.length == 0) {
            printFor((Player) sender, (Player) sender);
            return true;
        } else {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            if (player == null) {
                ((Player) sender).spigot().sendMessage(plugin.getPrefix().append("Unknown player").color(ChatColor.RED).create());
                return true;
            }
            printFor(player, (Player) sender);
            return true;
        }
    }
    
    private void printFor(OfflinePlayer player, Player sender) {
        for (Difficulty difficulty : Difficulty.values()) {
            int randomStarted = plugin.getScoreHandler().getScore(difficulty, player, true, true);
            int randomWon = plugin.getScoreHandler().getScore(difficulty, player, true, false);
            int presetStarted = plugin.getScoreHandler().getScore(difficulty, player, false, true);
            int presetWon = plugin.getScoreHandler().getScore(difficulty, player, false, false);
            
            sender.spigot().sendMessage(plugin.getPrefix().append("Player " + player.getName() + " has started " + randomStarted + " random " + difficulty.name() + " games and finished " + randomWon + " of those").color(ChatColor.GOLD).create());
            sender.spigot().sendMessage(plugin.getPrefix().append("Player " + player.getName() + " has started " + presetStarted + " preset " + difficulty.name() + " games and finished " + presetWon + " of those").color(ChatColor.GOLD).create());
        }
    }
}
