package org.devathon.contest2016.level;

import org.devathon.contest2016.game.Game;
import org.devathon.contest2016.structure.StructureUtil;
import org.devathon.contest2016.stuff.Difficulty;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

/**
 * Represents a level of the game
 */
public class Level {
    
    private String name;
    private Difficulty difficulty;
    private Location loc;
    private String schematic;
    private List<LineType> types;
    private boolean loaded;
    
    public Level(String name, Difficulty difficulty, Location loc, String schematic, List<LineType> types) {
        this.name = name;
        this.difficulty = difficulty;
        this.loc = loc;
        this.schematic = schematic;
        this.types = types;
    }
    
    public boolean isLoaded() {
        return loaded;
    }
    
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
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
    
    public void load(Location origin, Game game) {
        // load in the schematic
        boolean loaded = false;
        if (!isLoaded()) {
            StructureUtil.load(origin, schematic);
            setLoaded(true);
            loaded = true;
        }
        // rm structure block
        origin.getBlock().setType(Material.AIR);
        origin = origin.clone().add(1, 1, 1);
        // need to add one extra because of the offset
        if (!loaded) {
            origin = origin.add(0, 1, 0);
        }
        // set start and end
        for (LineType type : types) {
            Location start = new Location(origin.getWorld(), origin.getBlockX() + type.getStart().getX(), origin.getBlockY(), origin.getBlockZ() + type.getStart().getZ());
            game.setTile(start, type.getType(), start.getBlock().getState());
            start.getBlock().setType(type.getType().getMaterial());
            start.getBlock().setData(type.getType().getData());
            
            Location stop = new Location(origin.getWorld(), origin.getBlockX() + type.getStop().getX(), origin.getBlockY(), origin.getBlockZ() + type.getStop().getZ());
            game.setTile(stop, type.getType(), stop.getBlock().getState());
            stop.getBlock().setType(type.getType().getMaterial());
            stop.getBlock().setData(type.getType().getData());
        }
    }
}
