package edu.ntnu.idi.idatt.mappeoppgavev2025.persistence;

/**
 * Unntaksklasse som brukes ved feil relatert til lagring eller lasting av spillere.
 * <p>
 * Kastes dersom det oppstår en feil under operasjoner definert i {@link PlayerPersistence}.
 *
 * @author StianDolerud
 */
public class PlayerPersistenceException extends Exception {

    /**
     * Oppretter et nytt PlayerPersistenceException med en forklarende melding og en årsak.
     *
     * @param message forklarende melding om feilen
     * @param cause   årsaken til feilen
     * @author StianDolerud
     */
    public PlayerPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Oppretter et nytt PlayerPersistenceException med en forklarende melding.
     *
     * @param message forklarende melding om feilen
     * @author StianDolerud
     */
    public PlayerPersistenceException(String message) {
        super(message);
    }
}