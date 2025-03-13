module edu.ntnu.idi.idatt.mappeoppgavev {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.controlsfx.controls;
  requires org.kordamp.bootstrapfx.core;
  requires com.almasb.fxgl.all;

  opens edu.ntnu.idi.idatt.mappeoppgavev2025 to javafx.fxml;
  exports edu.ntnu.idi.idatt.mappeoppgavev2025;
}