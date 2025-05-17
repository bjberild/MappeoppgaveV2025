package edu.ntnu.idi.idatt.mappeoppgavev2025.persistence;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.LadderAction;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Tile;


public class GsonBoardPersistence implements BoardPersistence {

    private final Gson gson = new Gson();

    @Override
    public JsonObject serialize(Board board) {
        JsonObject root = new JsonObject();
        root.addProperty("name", board.getName());
        JsonArray tilesArray = new JsonArray();
        for (Tile tile : board.getAllTiles()) {
            JsonObject t = new JsonObject();
            t.addProperty("id", tile.getId());
            if (tile.getNextTile() != null) {
                t.addProperty("nextTile", tile.getNextTile().getId());
            if (tile.getAction() instanceof LadderAction) {
                JsonObject action = new JsonObject();
                action.addProperty("type", "LadderAction");
                action.addProperty("destinationTileId", ((LadderAction) tile.getAction()).getDestinationTile().getId());
                t.add("action", action);
                }
            }
            tilesArray.add(t);
        }
        root.add("tiles", tilesArray);
        return root;
    }

    @Override
    public Board deserialize(JsonObject json) {
        JsonArray tilesJson = json.getAsJsonArray("tiles"); 
        Board board = new Board();
        if (json.has("name")) {
            board.setName(json.get("name").getAsString());
        }
        
        board.createEmpty(tilesJson.size());
        for (JsonElement e : tilesJson) {
            JsonObject t = e.getAsJsonObject();
            board.addTile(new Tile(t.get("id").getAsInt()));
        }

        for (JsonElement e : tilesJson) {
            JsonObject t = e.getAsJsonObject(); 
            int id = t.get("id").getAsInt();
            Tile tile = board.getTileById(id).orElseThrow(() -> new IllegalStateException("Tile with id " + id + " not found"));
            
            if (t.has("nextTile")) {
                int nxt = t.get("nextTile").getAsInt();
                board.getTileById(nxt).ifPresent(tile::setNextTile);
            }

            if (t.has("action")) {
                JsonObject a = t.getAsJsonObject("action");
                if ("LadderAction".equals(a.get("type").getAsString())) {
                    int dest = a.get("destinationTileId").getAsInt();
                    board.getTileById(dest).ifPresent(destination -> tile.setAction(new LadderAction(destination)));
                }
            }
        }
        return board;
    } 
}
