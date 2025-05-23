package edu.ntnu.idi.idatt.mappeoppgavev2025.view.board;

import javafx.geometry.Pos;

/**
 * Grensesnitt som definerer en strategi for plassering av brikker (pips) på et brett.
 * <p>
 * Implementasjoner av dette grensesnittet returnerer en liste med posisjoner for å vise et gitt antall brikker.
 *
 * @author StianDolerud
 */
public interface PipPlacementStrategy {

    /**
     * Returnerer en tabell med posisjoner for gitt antall brikker.
     *
     * @param numTokens antall brikker som skal plasseres
     * @return en tabell med {@link Pos}-objekter som representerer posisjonene
     * @author StianDolerud
     */
    Pos[] positionsFor(int numTokens);

}