package edu.ntnu.idi.idatt.mappeoppgavev2025.view.tokens;

import javafx.scene.image.ImageView;

/**
 * Grensesnitt for fabrikk som lager ikon (bilde) for en gitt spillbrikke.
 * Implementeres for å kunne levere ulike visuelle representasjoner av brikker.
 *
 * @author StianDolerud
 */
public interface TokenIconFactory {
    /**
     * Oppretter et ikon for en gitt brikke (token).
     *
     * @param tokenName navnet på brikken
     * @return {@link ImageView} med bildet av brikken
     */
    ImageView createIcon(String tokenName);
}