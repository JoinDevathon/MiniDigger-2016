package org.devathon.contest2016.stuff;

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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Point2I point2I = (Point2I) o;
        
        if (x != point2I.x) return false;
        return z == point2I.z;
    }
    
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + z;
        return result;
    }
}
