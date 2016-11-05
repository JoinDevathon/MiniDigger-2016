package org.devathon.contest2016;

/**
 * Simple point, containing 2 ints
 */
public class Point2I {
    
    private int x;
    private int z;
    
    /**
     * Inits this point
     *
     * @param x the x coordinate
     * @param z the z coordinate
     */
    public Point2I(int x, int z) {
        this.x = x;
        this.z = z;
    }
    
    /**
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }
    
    /**
     * @return the z coordinate
     */
    public int getZ() {
        return z;
    }
}
