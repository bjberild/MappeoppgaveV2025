package edu.ntnu.idi.idatt.mappeoppgavev2025.view.tokens;

import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResourceTokenIconFactory implements TokenIconFactory {
    @Override
    public ImageView createIcon(String tokenName) {
        String path = "edu/ntnu/idi/idatt/mappeoppgavev2025/resources/tokens/" + tokenName + ".png";
        InputStream in = getClass().getClassLoader().getResourceAsStream(path);
        if (in == null) throw new IllegalArgumentException("Token not found: " + path);
        ImageView iv = new ImageView(new Image(in));
        iv.setFitWidth(30);
        iv.setFitHeight(30);
        return iv;
    }
    
}
