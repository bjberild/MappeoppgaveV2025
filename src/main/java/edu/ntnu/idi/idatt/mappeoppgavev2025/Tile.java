package edu.ntnu.idi.idatt.mappeoppgavev2025;

public class Tile {
    private final int id; 
    private Tile nextTile;

    public Tile(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Tile getNextTile() {
        return nextTile;
    }

    public void setNextTile(Tile nextTile) {
        this.nextTile = nextTile;
    }

    public boolean isFinalTile() {
        return nextTile == null;
    }
}