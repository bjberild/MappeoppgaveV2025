package edu.ntnu.idi.idatt.mappeoppgavev2025.demos;

import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.GsonBoardPersistence;


/**
 * Demo application showcasing the behavior of portal and trap actions on a board.
 * <p>
 * Loads a predefined board configuration from JSON, moves a player onto a portal tile,
 * then onto a trap tile, printing the player's position before and after each action.
 * </p>
 * 
 * @author StianDolerud
 */
public class PortalTrapDemo {

  /**
   * Entry point for the PortalTrapDemo.
   * <p>
   * - Reads the board definition from <code>src/test/resources/board90.json</code>.<br>
   * - Deserializes it into a {@link Board}.<br>
   * - Creates a {@link Player}, triggers a portal action at tile 5, and prints positions.<br>
   * - Triggers a trap action at tile 20, and prints positions.
   * </p>
   *
   * @param args command-line arguments (not used)
   * @throws Exception if reading the JSON file or parsing fails
   */

    public static void main(String[] args) throws Exception {
        String text = Files.readString(Path.of("src/test/resources/board90.json"));
        JsonObject json = JsonParser.parseString(text).getAsJsonObject();
        Board board = new GsonBoardPersistence().deserialize(json);

        Player p = new Player("Demo");

        p.setCurrentTile(board.getTileById(5).get());
        System.out.println("Before portal: at tile " + p.getCurrentTile().getId());
        board.getTileById(5).get().triggerAction(p);
        System.out.println("After portal: at tile" + p.getCurrentTile().getId());
        System.out.println();

        p.setCurrentTile(board.getTileById(20).get());
        System.out.println("Before trap: at tile " + p.getCurrentTile().getId());
        board.getTileById(20).get().triggerAction(p);
        System.out.println("After trap: at tile " + p.getCurrentTile().getId());
    }
}
