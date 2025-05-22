package edu.ntnu.idi.idatt.mappeoppgavev2025.view.dice;



import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import edu.ntnu.idi.idatt.mappeoppgavev2025.controller.PlayerController;
import edu.ntnu.idi.idatt.mappeoppgavev2025.event.GameEventListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class DiceView extends VBox implements GameEventListener {
    private static final Pattern ROLL_PATTERN = 
    Pattern.compile("(?i)player\\s+(\\w+)\\s+rolled\\s+(\\d+)\\s+and\\s+(\\d+)");
    
    private final ImageView die1 = new ImageView();
    private final ImageView die2 = new ImageView();
    private final Button rollButton = new Button("Roll Dice");
    private final Label resultLabel = new Label("Roll: -");
    private final PlayerController ctrl;
    

    public DiceView(PlayerController ctrl) {
        this.ctrl = ctrl;
        setPadding(new Insets(20));
        setSpacing(20);

        Stream.of(die1, die2).forEach(iv -> {
            iv.setFitWidth(150);
            iv.setFitHeight(150);
            iv.setImage(loadDieImage(1)); 
        });

        rollButton.setOnAction(e -> ctrl.rollDice());

        ctrl.getGame().addEventListener(this);

        getChildren().addAll(die1, die2, rollButton, resultLabel);

    }

    @Override
    public void onGameEvent(String message) {
        Matcher m = ROLL_PATTERN.matcher(message);
        if (!m.find()) return;

        int v1 = Integer.parseInt(m.group(2));
        int v2 = Integer.parseInt(m.group(3));

        die1.setImage(loadDieImage(v1));
        die2.setImage(loadDieImage(v2));
        resultLabel.setText("Total: " + (v1 + v2));

    }
  

    private Image loadDieImage(int face) {
        return new Image(getClass().getResourceAsStream(
            String.format("/edu/ntnu/idi/idatt/mappeoppgavev2025/images/die/dice-%d.png", face)
        ));
    } 
}
