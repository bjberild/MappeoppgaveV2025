package edu.ntnu.idi.idatt.mappeoppgavev2025.view.tokens;

import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.BoardView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Ansvarlig for animasjon av spillbrikker på brettet.
 * Håndterer både gange over flere felter og teleportering direkte til et felt.
 *
 * @author StianDolerud
 */
public class TokenAnimator {
    private final BoardView boardView;
    private final Duration stepDuration;

    /**
     * Oppretter en ny {@code TokenAnimator} med tilhørende brettvisning og varighet per steg.
     *
     * @param boardView referanse til {@link BoardView} som skal oppdateres
     * @param stepDuration hvor lang tid ett steg i animasjonen skal ta
     */
    public TokenAnimator(BoardView boardView, Duration stepDuration) {
        this.boardView = boardView;
        this.stepDuration = stepDuration;
    }

    /**
     * Animerer at en spiller går stegvis fra én rute til en annen.
     * Oppdaterer plasseringen av spillerens ikon for hvert steg langs veien.
     *
     * @param player spilleren som flyttes
     * @param fromTile startfeltets ID
     * @param toTile sluttfeltets ID
     */
    public void animateWalk(Player player, int fromTile, int toTile) {
        ImageView icon = boardView.getTokenIcon(player);
        if (icon == null) return;

        int step = Integer.compare(toTile, fromTile);
        int totalSteps = Math.abs(toTile - fromTile);

        List<Integer> path = new ArrayList<>(totalSteps);
        for (int i = 1; i <= totalSteps; i++) {
            path.add(fromTile + (i * step));
        } 

        Timeline t1 = new Timeline();
        for (int i = 0; i < path.size(); i++) {
            int tileId = path.get(i);
            t1.getKeyFrames().add(new KeyFrame(
                stepDuration.multiply(i),
                e -> boardView.playerTokenOnTile(icon, tileId)
            ));
        }
        t1.setOnFinished(e -> {
            boardView.refreshTile(fromTile);
            int actualDest = player.getCurrentTile().getId();
            boardView.refreshTile(actualDest);
        });
        t1.play();
    }

    /**
     * Teleporterer en spillers brikke direkte til en angitt rute.
     * Ingen animasjon mellom start og slutt.
     *
     * @param player spilleren som teleporterer
     * @param fromTile startfeltets ID
     * @param toTile sluttfeltets ID
     */
    public void animateTeleport(Player player, int fromTile, int toTile) {
        ImageView icon = boardView.getTokenIcon(player);
        if (icon == null) return;
        boardView.playerTokenOnTile(icon, toTile);
        boardView.refreshTile(fromTile);
        boardView.refreshTile(toTile);
    }
}