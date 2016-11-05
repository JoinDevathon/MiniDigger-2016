package org.devathon.contest2016.level;

import org.devathon.contest2016.stuff.Point2I;
import org.devathon.contest2016.stuff.TileType;

/**
 * Holds the start and stop locations for a tile type
 */
public class LineType {
    
    private TileType type;
    private Point2I start;
    private Point2I stop;
    
    public LineType(TileType type, Point2I start, Point2I stop) {
        this.type = type;
        this.start = start;
        this.stop = stop;
    }
    
    public TileType getType() {
        return type;
    }
    
    public void setType(TileType type) {
        this.type = type;
    }
    
    public Point2I getStart() {
        return start;
    }
    
    public void setStart(Point2I start) {
        this.start = start;
    }
    
    public Point2I getStop() {
        return stop;
    }
    
    public void setStop(Point2I stop) {
        this.stop = stop;
    }
}
