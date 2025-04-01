package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

public class Player {
    private final String name;
    private Tile currentTile;

    public Player(String name) {
        this.name = name; 
    }

    public String getName() {
        return name;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

}
