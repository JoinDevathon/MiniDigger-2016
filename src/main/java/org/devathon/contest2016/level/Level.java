package org.devathon.contest2016.level;

import org.devathon.contest2016.stuff.Difficulty;

import java.util.List;

import org.bukkit.Location;

/**
 * Represents a level of the game
 */
public class Level {
    
    private String name;
    private Difficulty difficulty;
    private Location loc;
    private String schematic;
    private List<LineType> types;
    
    public Level(String name, Difficulty difficulty, Location loc, String schematic, List<LineType> types) {
        this.name = name;
        this.difficulty = difficulty;
        this.loc = loc;
        this.schematic = schematic;
        this.types = types;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Difficulty getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    
    public Location getLoc() {
        return loc;
    }
    
    public void setLoc(Location loc) {
        this.loc = loc;
    }
    
    public String getSchematic() {
        return schematic;
    }
    
    public void setSchematic(String schematic) {
        this.schematic = schematic;
    }
    
    public List<LineType> getTypes() {
        return types;
    }
    
    public void setTypes(List<LineType> types) {
        this.types = types;
    }
}
