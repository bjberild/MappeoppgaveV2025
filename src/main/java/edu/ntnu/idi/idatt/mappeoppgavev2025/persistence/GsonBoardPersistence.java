package edu.ntnu.idi.idatt.mappeoppgavev2025.persistence;

import java.util.stream.StreamSupport;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.FallTrapAction;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.PortalAction;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Tile;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.TileAction;

/**
 * Implementation of {@link BoardPersistence} using Gson.
 *
 * Provides methods to serialize a {@link edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board}
 * to JSON and to deserialize a JSON object back into a {@link edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board}.
 *
 * <p>The JSON format includes a <code>name</code> property and a <code>tiles</code> array,
 * where each tile object contains an <code>id</code>, an optional <code>nextTile</code>,
 * and an optional <code>action</code> block with <code>type</code> and <code>destinationTileId</code>.
 *
 * @author StianDolerud
 */

public class GsonBoardPersistence implements BoardPersistence {

    private final Gson gson = new Gson();

   /**
   * Serialize a {@link edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board} into a {@link com.google.gson.JsonObject}.
   *
   * @param board the board to serialize
   * @return a JsonObject representing the board, including <code>name</code> and <code>tiles</code>
   */

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
            if (act instanceof PortalAction) {
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

      /**
   * Deserialize a {@link com.google.gson.JsonObject} into a {@link edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board}.
   *
   * <p>The JSON must contain a <code>tiles</code> array of objects with
   * required <code>id</code> and optional <code>nextTile</code> and <code>action</code> entries.
   *
   * @param json the JsonObject to deserialize
   * @return the constructed {@link edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board}
   * @throws IllegalArgumentException if JSON is missing the <code>tiles</code> array or contains invalid entries
   */


    
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