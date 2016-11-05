package org.devathon.contest2016;

/**
 * Created by Martin on 05.11.2016.
 */
public class Board {
    
    private TileType[][] board;
    
    public Board(int xSize, int zSize) {
        board = new TileType[xSize][zSize];
        
        for (int x = 0; x < xSize; x++) {
            for (int z = 0; z < zSize; z++) {
                board[x][z] = TileType.AIR;
            }
        }
    }
    
    public void setTile(int x, int z, TileType tile) {
        board[x][z] = tile;
    }
    
    public TileType getTile(int x, int z) {
        return board[x][z];
    }
}
