package edu.ntnu.idi.idatt.mappeoppgavev2025.view.controls;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;


public class ControlPanelView extends VBox {
    private final TextArea eventArea = new TextArea();
    private final Button nextRoundButton = new Button("Next Round");
    
    public ControlPanelView(BoardGame game) {
        setPadding(new Insets(20));
        setSpacing(5);
        eventArea.setEditable(false);
        eventArea.setPrefHeight(100);
        nextRoundButton.setOnAction(e -> {game.playOneRound();});
        getChildren().addAll(eventArea, nextRoundButton);
        
        game.addEventListener(message -> {eventArea.appendText(message + "\n");
        });

    }
    
}
