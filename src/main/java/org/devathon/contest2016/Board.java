package org.devathon.contest2016;

/**
 * The board for the game
 */
public class Board {
    
    private TileType[][] board;
    private Direction[][] directions;
    
    /**
     * Initialises this board
     *
     * @param xSize the number of tiles in x direction
     * @param zSize the number of tiles in z direction
     */
    public Board(int xSize, int zSize) {
        board = new TileType[xSize][zSize];
        directions = new Direction[xSize][zSize];
        
        for (int x = 0; x < xSize; x++) {
            for (int z = 0; z < zSize; z++) {
                board[x][z] = TileType.AIR;
                directions[x][z] = Direction.UNDEFINED;
            }
        }
    }
    
    /**
     * Sets a tile on this board
     *
     * @param x    the x coordinate
     * @param z    the z coordinate
     * @param tile the tile to set
     */
    public void setTile(int x, int z, TileType tile) {
        board[x][z] = tile;
    }
    
    /**
     * Returns the tile at a giving postion
     *
     * @param x the x coordinate
     * @param z the z coordinate
     * @return the tile at (x,z)
     */
    public TileType getTile(int x, int z) {
        return board[x][z];
    }
    
    /**
     * Sets the direction the flow is flowing at the given location
     *
     * @param x         the x coordinate
     * @param z         the z coordinate
     * @param direction the direction the flow should be flowing at (x,z)
     */
    public void setDirections(int x, int z, Direction direction) {
        this.directions[x][z] = direction;
    }
    
    /**
     * Returns the direction the flow is flowing at the given location
     *
     * @param x the x coordinate
     * @param z the z coordinate
     * @return the direction the flow should be flowing at (x,z)
     */
    public Direction getDirection(int x, int z) {
        return directions[x][z];
    }
}
