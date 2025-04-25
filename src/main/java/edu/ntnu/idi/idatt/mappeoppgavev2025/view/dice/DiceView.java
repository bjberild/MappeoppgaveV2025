package edu.ntnu.idi.idatt.mappeoppgavev2025.view.dice;


import java.util.stream.Stream;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Die;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;



public class DiceView extends VBox {
    private final ImageView die1 = new ImageView();
    private final ImageView die2 = new ImageView();
    private final Button rollButton = new Button("Roll Dice");
    private final Label resultLabel = new Label("Roll: -");
    

    public DiceView() {
        setPadding(new Insets(20));
        setSpacing(20);

        Stream.of(die1, die2).forEach(iv -> {
            iv.setFitWidth(150);
            iv.setFitHeight(150);
            iv.setImage(loadDieImage(1)); 
        });

        rollButton.setOnAction(e -> {
            int v1 = new Die().roll();
            int v2 = new Die().roll();
            die1.setImage(loadDieImage(v1));
            die2.setImage(loadDieImage(v2));
            int total = v1 + v2;
            resultLabel.setText(String.format("Total roll: %d", total));
        });

        getChildren().addAll(die1, die2, rollButton, resultLabel);
    }

    private Image loadDieImage(int face) {
        return new Image(getClass().getResourceAsStream(
            String.format("/edu/ntnu/idi/idatt/mappeoppgavev2025/images/die/dice-%d.png", face)
        ));
    } 
}
