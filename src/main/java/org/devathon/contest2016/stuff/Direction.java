package org.devathon.contest2016.stuff;

/**
 * All directions flow and flow in
 */
public enum Direction {
    
    NORTH(0), EAST(1), SOUTH(2), WEST(3), UNDEFINED(-1);
    
    private int index;
    
    Direction(int index) {
        this.index = index;
    }
    
    /**
     * @return the next direction in the cycle
     */
    public Direction getNext() {
        return values()[(index + 1) % 3];
    }
}
