package org.devathon.contest2016.structure;

import org.devathon.contest2016.DevathonPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.bukkit.Bukkit;

/**
 * Handles the structures
 */
public class StructureHandler {
    
    private static final String[] structures = {"DefaultLevel", "TestLevel", "Level1", "Level2"};
    
    private DevathonPlugin plugin;
    
    public StructureHandler(DevathonPlugin plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Save structures from the jar into the structures folder of the world
     */
    public void load() {
        File file = new File(Bukkit.getWorlds().get(0).getWorldFolder().getAbsoluteFile(), "structures");
        file.mkdirs();
        for (String structure : structures) {
            try {
                Files.copy(plugin.getResource(structure + ".nbt"), new File(file, structure + ".nbt").toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
