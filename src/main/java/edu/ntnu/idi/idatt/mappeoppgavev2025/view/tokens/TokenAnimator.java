package edu.ntnu.idi.idatt.mappeoppgavev2025.view.tokens;

import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.BoardView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;



public class TokenAnimator {
    private final BoardView boardView;
    private final Duration stepDuration;
    
    public TokenAnimator(BoardView boardView, Duration stepDuration) {
        this.boardView = boardView;
        this.stepDuration = stepDuration;
    }

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

    public void animateTeleport (Player player, int fromTile, int toTile) {
        ImageView icon = boardView.getTokenIcon(player);
        if (icon == null) return;
        boardView.playerTokenOnTile(icon, toTile);
        boardView.refreshTile(fromTile);
        boardView.refreshTile(toTile);


    }
}
