package org.devathon.contest2016.level;

import org.devathon.contest2016.structure.StructureUtil;
import org.devathon.contest2016.stuff.Difficulty;
import org.devathon.contest2016.stuff.Point2I;
import org.devathon.contest2016.stuff.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

/**
 * Created by Martin on 05.11.2016.
 */
public class LevelHandler {
    
    private List<Level> levels = new ArrayList<>();
    
    /**
     * Searches for the level with that name
     *
     * @param name the name of the level
     * @return the found level, if present
     */
    public Optional<Level> getLevel(String name, Location origin) {
        Optional<Level> result = levels.stream().filter(level -> level.getName().equalsIgnoreCase(name)).findAny();
        if (!result.isPresent() && origin != null) {
            // load in level
            StructureUtil.load(origin, name);
            
            // search for origin
            for (int x = 0; x < 100; x++) {
                for (int y = 0; y < 100; y++) {
                    for (int z = 0; z < 100; z++) {
                        Block block = origin.clone().add(x, y, z).getBlock();
                        if (block.getType() == Material.STRUCTURE_BLOCK) {
                            block.setType(Material.AIR);
                            origin = block.getRelative(BlockFace.SOUTH).getLocation();
                            break;
                        }
                    }
                }
            }
            
            // load data
            loadLevel(origin);
            result = levels.stream().filter(level -> level.getName().equalsIgnoreCase(name)).findAny();
        }
        
        return result;
    }
    
    /**
     * Loads a level from the signs
     *
     * @param signLoc the start sign
     * @return the loaded level
     */
    public Level loadLevel(Location signLoc) {
        if (!(signLoc.getBlock().getState() instanceof Sign)) {
            return null;
        }
        Sign levelSign = (Sign) signLoc.getBlock().getState();
        String name = levelSign.getLine(1);
        Difficulty difficulty = Difficulty.valueOf(levelSign.getLine(2));
        BlockState blockstate = levelSign;
        List<LineType> types = new ArrayList<>();
        while ((blockstate = blockstate.getBlock().getRelative(BlockFace.SOUTH).getState()) instanceof Sign) {
            Sign sign = (Sign) blockstate;
            if (sign.getLine(0).startsWith("[") && sign.getLine(0).endsWith("]")) {
                TileType type = TileType.valueOf(sign.getLine(0).replace("[", "").replace("]", ""));
                Point2I start = stringToPoint(sign.getLine(1));
                Point2I end = stringToPoint(sign.getLine(2));
                types.add(new LineType(type, start, end));
            }
        }
        
        Level level = new Level(name, difficulty, levelSign.getLocation().add(1, 0, 0), name, types);
        level.setLoaded(true);
        levels.add(level);
        return level;
    }
    
    private Point2I stringToPoint(String string) {
        String[] cords = string.split(":");
        int x = Integer.parseInt(cords[0]);
        int z = Integer.parseInt(cords[1]);
        return new Point2I(x, z);
    }
}
