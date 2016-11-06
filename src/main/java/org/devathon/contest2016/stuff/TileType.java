package org.devathon.contest2016.stuff;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Enumeration of all available tiles
 */
public enum TileType {
    
    AIR(Material.AIR, (byte) 0, 0, 0, 0),
    BLACK(Material.WOOL, (byte) 15, 25, 25, 25),
    RED(Material.WOOL, (byte) 14, 153, 51, 51),
    BROWN(Material.WOOL, (byte) 12, 102, 76, 51),
    BLUE(Material.WOOL, (byte) 11, 51, 76, 178),
    LILA(Material.WOOL, (byte) 10, 127, 63, 178),
    CYAN(Material.WOOL, (byte) 9, 76, 127, 153),
    GRAY(Material.WOOL, (byte) 8, 76, 76, 76),
    PINK(Material.WOOL, (byte) 6, 242, 127, 165),
    GREEN(Material.WOOL, (byte) 5, 127, 204, 25),
    YELLOW(Material.WOOL, (byte) 4, 229, 229, 51),
    ORANGE(Material.WOOL, (byte) 1, 216, 127, 51);
    
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
        return red / 256f;
    }
    
    public float getGreen() {
        return green / 256f;
    }
    
    public float getBlue() {
        return blue / 256f;
    }
}
