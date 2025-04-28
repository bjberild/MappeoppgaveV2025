package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;


public class BackgroundPane extends BorderPane {
    /** 
     * @param resourcePath to "/edu/ntnu/idi/idatt/mappeoppgavev2025/images/Jungle-Background.png"
    */
    public BackgroundPane(String resourcePath) {
        Image img = new Image(getClass().getResourceAsStream(resourcePath));
        BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, false);
        BackgroundImage bi = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        setBackground(new Background(bi));
    }
}
