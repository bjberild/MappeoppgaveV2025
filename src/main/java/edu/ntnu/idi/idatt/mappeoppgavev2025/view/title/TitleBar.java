package edu.ntnu.idi.idatt.mappeoppgavev2025.view.title;


import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;



public class TitleBar extends HBox {

    public TitleBar(String imagePath) {
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream(
            "/edu/ntnu/idi/idatt/mappeoppgavev2025/images/"+ imagePath)));
        logo.setFitHeight(150);
        logo.setFitWidth(500);
        setAlignment(Pos.CENTER);


        setSpacing(10);
        getChildren().addAll(logo); 
    }
}
