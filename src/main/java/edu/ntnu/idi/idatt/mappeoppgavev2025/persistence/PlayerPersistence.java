package edu.ntnu.idi.idatt.mappeoppgavev2025.persistence;

import java.nio.file.Path;
import java.util.List;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;

/**
 * Grensesnitt for lagring og lasting av {@link Player}-objekter til og fra fil.
 * <p>
 * Implementasjoner av dette grensesnittet skal håndtere serialisering og
 * deserialisering av spillere, for eksempel til CSV- eller andre filformater.
 *
 * @author StianDolerud
 */
public interface PlayerPersistence {

    /**
     * Leser inn spillere fra en gitt fil.
     *
     * @param cvsFile filsti til filen hvor spillerdataene er lagret
     * @return en liste med {@link Player}-objekter hentet fra fil
     * @throws PlayerPersistenceException dersom det oppstår en feil under lesing av fil
     * @author StianDolerud
     */
    List<Player> load(Path cvsFile) throws PlayerPersistenceException;

    /**
     * Lagrer en liste med spillere til en gitt fil.
     *
     * @param cvsFile filsti til filen hvor spillerdataene skal lagres
     * @param players listen av {@link Player}-objekter som skal lagres
     * @throws PlayerPersistenceException dersom det oppstår en feil under skriving til fil
     * @author StianDolerud
     */
    void save(Path cvsFile, List<Player> players) throws PlayerPersistenceException;
    
}