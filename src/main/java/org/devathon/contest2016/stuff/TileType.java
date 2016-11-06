package org.devathon.contest2016.stuff;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Enumeration of all available tiles
 */
public enum TileType {
    
    AIR(Material.AIR, (byte) 0, 0, 0, 0),
    RED(Material.WOOL, (byte) 1, 1, 0, 0),
    GREEN(Material.WOOL, (byte) 10, 0, 1, 0),
    BLUE(Material.WOOL, (byte) 4, 0, 0, 1),
    LILA(Material.WOOL, (byte) 5, 0.37109375f, 0, 0.60546875f),
    PINK(Material.WOOL, (byte) 9, 0.953125f, 0, 1);
    
    private Material material;
    private byte data;
    private float red;
    private float green;
    private float blue;
    
    TileType(Material material, byte data, float red, float green, float blue) {
        this.material = material;
        this.data = data;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    /**
     * @return the block data for this tile type
     */
    public byte getData() {
        return data;
    }
    
    /**
     * @return the material of this tile type
     */
    public Material getMaterial() {
        return material;
    }
    
    /**
     * Searches for a tile type from the given itemstack
     *
     * @param itemInHand the itemstack that should be translated to a TileType
     * @return the found tile type, null if nothing could be found
     */
    public static TileType from(ItemStack itemInHand) {
        for (TileType type : values()) {
            if (type.getMaterial().equals(itemInHand.getType())) {
                if (type.getData() == (byte) itemInHand.getDurability()) {
                    return type;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Searches for a tile type from the given material and data
     *
     * @param mat  the material
     * @param data the data
     * @return the found tile type, null if nothing could be found
     */
    public static TileType from(Material mat, byte data) {
        for (TileType type : values()) {
            if (type.getMaterial().equals(mat)) {
                if (type.getData() == data) {
                    return type;
                }
            }
        }
        
        return null;
    }
    
    public float getRed() {
        return red;
    }
    
    public float getGreen() {
        return green;
    }
    
    public float getBlue() {
        return blue;
    }
}
