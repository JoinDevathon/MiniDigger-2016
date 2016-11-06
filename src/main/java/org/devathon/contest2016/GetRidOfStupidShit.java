package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * small class to get rid of annoying stuff
 */
public class GetRidOfStupidShit implements Listener {
    
    public GetRidOfStupidShit() {
        for (World world : Bukkit.getWorlds()) {
            world.setGameRuleValue("spawnRadius", "0");
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setTime(9000);
        }
    }
    
    @EventHandler
    public void food(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void weather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
}
