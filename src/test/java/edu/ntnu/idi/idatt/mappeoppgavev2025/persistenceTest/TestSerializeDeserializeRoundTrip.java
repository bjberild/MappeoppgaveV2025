package edu.ntnu.idi.idatt.mappeoppgavev2025.persistenceTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.JsonObject;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.GsonBoardPersistence;


public class TestSerializeDeserializeRoundTrip {

    @Test 
    public void testSerializeDeserializeRoundTrip() {
        Board original = new Board();
        original.initializeStandardBoard(10);      
        original.setName("TestBoard");  

        JsonObject json = new GsonBoardPersistence().serialize(original);
        Board copy      = new GsonBoardPersistence().deserialize(json);

        assertEquals(10, copy.getTileAfter(copy.getStartTile(), 9).getId());
        assertEquals("TestBoard", copy.getName());

    }
    
}
