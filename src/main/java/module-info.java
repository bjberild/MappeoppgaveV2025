module edu.ntnu.idi.idatt.mappeoppgavev2025 {
  requires javafx.controls;
  requires javafx.fxml;
  
  // Open and export the root package
  opens edu.ntnu.idi.idatt.mappeoppgavev2025 to javafx.fxml;
  exports edu.ntnu.idi.idatt.mappeoppgavev2025;
  
  // Event package
  exports edu.ntnu.idi.idatt.mappeoppgavev2025.event;
  opens edu.ntnu.idi.idatt.mappeoppgavev2025.event to javafx.fxml;
  
  // Model package
  exports edu.ntnu.idi.idatt.mappeoppgavev2025.model;
  opens edu.ntnu.idi.idatt.mappeoppgavev2025.model to javafx.fxml;
  
  // Controller package
  //exports edu.ntnu.idi.idatt.mappeoppgavev2025.controller;
  //opens edu.ntnu.idi.idatt.mappeoppgavev2025.controller to javafx.fxml;
  
  // View package
  //exports edu.ntnu.idi.idatt.mappeoppgavev2025.view;
  //opens edu.ntnu.idi.idatt.mappeoppgavev2025.view to javafx.fxml;
  
  // Window packages (for GUI classes)
  exports edu.ntnu.idi.idatt.mappeoppgavev2025.window;
  opens edu.ntnu.idi.idatt.mappeoppgavev2025.window to javafx.fxml;
  
  exports edu.ntnu.idi.idatt.mappeoppgavev2025.window.view;
  opens edu.ntnu.idi.idatt.mappeoppgavev2025.window.view to javafx.fxml;
}