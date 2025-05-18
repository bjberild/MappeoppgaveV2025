package edu.ntnu.idi.idatt.mappeoppgavev2025.demos;

import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.GsonBoardPersistence;


//This demo forces the player to land on a portal and a trap.
public class PortalTrapDemo {

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
