package edu.ntnu.idi.idatt.mappeoppgavev2025.event;

import edu.ntnu.idi.idatt.mappeoppgavev2025.view.BoardView;
import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class TileHighlighter implements GameEventListener {
    public final BoardView boardView;

    public TileHighlighter(BoardView boardView) {
      this.boardView = boardView;
    }

    @Override
    public void onGameEvent(String message) {
    if (message.contains("climbed") || message.contains("fell")) {

      String[] parts = message.split(" ");
      int from = Integer.parseInt(parts[4]);
      int to   = Integer.parseInt(parts[6]);
      
      Node source = boardView.lookup("#tile-" + from);
      Node dest   = boardView.lookup("#tile-" + to);
      if (source != null && dest != null) { 
      source.getStyleClass().add("highlight-source");
      dest.getStyleClass().add("highlight-dest");

      PauseTransition pause = new PauseTransition(Duration.seconds(1));
      pause.setOnFinished(e -> {
        source.getStyleClass().remove("highlight-source");
        dest  .getStyleClass().remove("highlight-dest");
      });
      pause.play();
   }
  }
 }
}