module edu.ntnu.idi.idatt.mappeoppgavev {
  requires javafx.controls;
  requires javafx.fxml;

  opens edu.ntnu.idi.idatt.mappeoppgavev2025 to javafx.fxml;
  exports edu.ntnu.idi.idatt.mappeoppgavev2025;
  exports edu.ntnu.idi.idatt.mappeoppgavev2025.event;
  opens edu.ntnu.idi.idatt.mappeoppgavev2025.event to javafx.fxml;
  exports edu.ntnu.idi.idatt.mappeoppgavev2025.model;
  opens edu.ntnu.idi.idatt.mappeoppgavev2025.model to javafx.fxml;
}