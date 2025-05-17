package edu.ntnu.idi.idatt.mappeoppgavev2025.persistence;

import java.util.stream.StreamSupport;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.FallTrapAction;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.LadderAction;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.PortalAction;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Tile;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.TileAction;



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
            }
            TileAction act = tile.getAction();
            if (act instanceof LadderAction) {
              LadderAction la = (LadderAction) act;
              JsonObject action = new JsonObject();
              action.addProperty("type", "LadderAction");
              action.addProperty("destinationTileId", la.getDestinationTile().getId());
              t.add("action", action);
      
            } else if (act instanceof PortalAction) {
              PortalAction pa = (PortalAction) act;
              JsonObject action = new JsonObject();
              action.addProperty("type", "PortalAction");
              action.addProperty("destinationTileId", pa.getDestinationTile().getId());
              t.add("action", action);
      
            } else if (act instanceof FallTrapAction) {
              FallTrapAction fa = (FallTrapAction) act;
              JsonObject action = new JsonObject();
              action.addProperty("type", "FallTrapAction");
              action.addProperty("destinationTileId", fa.getDestinationTile().getId());
              t.add("action", action);
            }


            tilesArray.add(t);
        }

        root.add("tiles", tilesArray);
        return root;
    }

    @Override
    public Board deserialize(JsonObject json) {
        if (!json.has("tiles") || !json.get("tiles").isJsonArray()) {
            throw new IllegalArgumentException("Invalid JSON: missing or invalid 'tiles' array");
        }
        JsonArray tilesJson = json.getAsJsonArray("tiles"); 

        int maxId = StreamSupport.stream(tilesJson.spliterator(), false)
            .map(JsonElement::getAsJsonObject)
            .mapToInt(o -> o.get("id").getAsInt())
            .max()
            .orElseThrow(() -> new IllegalStateException("No tiles found in JSON")); 
        
        Board board = new Board();
        board.initializeStandardBoard(maxId);

        if (json.has("name")) {
            board.setName(json.get("name").getAsString());
        }
        
        for (JsonElement e : tilesJson) {
            if (!e.isJsonObject()) {
                throw new IllegalArgumentException("Invalid JSON: each tile must be a JSON object");
            }
            JsonObject t = e.getAsJsonObject();
            if (!t.has("id")) {
                throw new IllegalArgumentException("Invalid JSON: each tile must have an 'id'");
            }
            int id = t.get("id").getAsInt();
            Tile tile = board.getTileById(id)
                             .orElseThrow(() -> new IllegalStateException(
                                "Tile with id " + id + " not found"));


            if (t.has("nextTile")) {
                int nxt = t.get("nextTile").getAsInt();
                board.getTileById(nxt).ifPresent(tile::setNextTile);
            }

            if (t.has("action")) {
                JsonObject a = t.getAsJsonObject("action");
                String type = a.get("type").getAsString();
                int dest = a.get("destinationTileId").getAsInt();

                switch (type) {
                    case "LadderAction":
                      board.getTileById(dest)
                           .ifPresent(d -> tile.setAction(new LadderAction(d)));
                      break;
                    case "PortalAction":
                      board.getTileById(dest)
                           .ifPresent(d -> tile.setAction(new PortalAction(d)));
                        break;
                    case "FallTrapAction":
                        board.getTileById(dest)
                             .ifPresent(d -> tile.setAction(new FallTrapAction(d)));
                            break;
                    default:
                        throw new IllegalArgumentException("Invalid action type: " + type);
          }
        }
      }
        
    return board;
  }
}