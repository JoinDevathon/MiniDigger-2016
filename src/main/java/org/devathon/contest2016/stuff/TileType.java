package org.devathon.contest2016.stuff;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Enumeration of all available tiles
 */
public enum TileType {
    
    AIR(Material.AIR, (byte) 0),
    RED(Material.WOOL, (byte) 1),
    GREEN(Material.WOOL, (byte) 10),
    BLUE(Material.WOOL, (byte) 4),
    LILA(Material.WOOL, (byte) 5),
    PINK(Material.WOOL, (byte) 9);
    
    private Material material;
    private byte data;
    
    TileType(Material material, byte data) {
        this.material = material;
        this.data = data;
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
}
