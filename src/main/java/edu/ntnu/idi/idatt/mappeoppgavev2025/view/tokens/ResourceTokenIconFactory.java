package edu.ntnu.idi.idatt.mappeoppgavev2025.view.tokens;

import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Fabrikk for å lage {@code ImageView}-ikoner for spillbrikker basert på ressursnavn.
 * Prøver å finne bilde med oppgitt navn, eller bruker standardikon om det mangler.
 *
 * @author StianDolerud
 */
public class ResourceTokenIconFactory implements TokenIconFactory {
    
    /**
     * Oppretter et ikon for en spillbrikke basert på navnet til brikken.
     * Faller tilbake på et standardikon dersom det spesifikke ikonet ikke finnes.
     *
     * @param tokenName navnet på spillbrikken/ikonet
     * @return et {@code ImageView} med riktig ikon
     * @throws IllegalArgumentException hvis ingen ikonressurs blir funnet
     */
    @Override
    public ImageView createIcon(String tokenName) {
        String path = "/resources/edu/ntnu/idi/idatt/mappeoppgavev2025/images/tokens/" + tokenName + ".png";
        InputStream in = getClass().getClassLoader().getResourceAsStream(path);
        if (in == null) {
            in = getClass().getResourceAsStream("/edu/ntnu/idi/idatt/mappeoppgavev2025/images/tokens/" + tokenName + ".png");
        }

        if (in == null) {
            in = getClass().getResourceAsStream("/edu/ntnu/idi/idatt/mappeoppgavev2025/images/tokens/DefaultToken.png");
        }

        if (in == null) {
            throw new IllegalArgumentException("Token not found: " + path);
        }

        ImageView iv = new ImageView(new Image(in));
        iv.setFitWidth(50);
        iv.setFitHeight(50);
        return iv;
    }
}