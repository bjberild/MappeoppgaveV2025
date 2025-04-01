package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Tile> tiles;
    
    //Creates a standard board with a given number of tiles
    public void initializeStandardBoard(int numTiles) {
        tiles = new ArrayList<>();
        for (int i = 1; i <= numTiles; i++) {
            Tile tile = new Tile(i);  
            tiles.add(tile);          
            if (i > 1) {
                tiles.get(i - 2).setNextTile(tile);
            }
        }
    }

    //This makes sure that the player starts at the first tile
    public Tile getStartTile() {
        if (tiles != null && !tiles.isEmpty()) {
            return tiles.get(0);
        }
        return null;
    }


    //This method returns the tile that is a given amount of steps away from the current tile
    public Tile getTileAfter(Tile current, int steps) {
        int currentIndex = tiles.indexOf(current);
        int destinationIndex = currentIndex + steps;
        if (destinationIndex >= tiles.size()) {
            destinationIndex = tiles.size() - 1;
        }
        return tiles.get(destinationIndex);
    }


}
