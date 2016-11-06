package org.devathon.contest2016.score;

import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.stuff.Difficulty;

import org.bukkit.OfflinePlayer;

/**
 * Simple score handling
 */
public class ScoreHandler {
    
    private DevathonPlugin plugin;
    
    public ScoreHandler(DevathonPlugin plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Adds one to the score of the giving player
     *
     * @param difficulty the difficulty the player played
     * @param player     the player who played
     * @param random     if the player played a random game or a preset one
     * @param started    if the player just started the game or if he finished it
     */
    public void addScore(Difficulty difficulty, OfflinePlayer player, boolean random, boolean started) {
        plugin.getConfig().set(player.getUniqueId() + "." + difficulty.name() + "." + random + "." + started, getScore(difficulty, player, random, started) + 1);
        plugin.saveConfig();
    }
    
    /**
     * Returns the score for the given category for that player
     *
     * @param difficulty the difficulty to get
     * @param player     the player which score should returned
     * @param random     if random games or preset games should be showed
     * @param started    if started games or finished games should be showed
     * @return the score for the given category for that player
     */
    public int getScore(Difficulty difficulty, OfflinePlayer player, boolean random, boolean started) {
        return plugin.getConfig().getInt(player.getUniqueId() + "." + difficulty.name() + "." + random + "." + started, 0);
    }
}
