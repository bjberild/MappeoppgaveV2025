module edu.ntnu.idi.idatt.mappeoppgavev2025 {
  requires javafx.controls;
  //requires javafx.fxml;
  requires javafx.graphics;

  // Open and export the root package
  opens edu.ntnu.idi.idatt.mappeoppgavev2025 to javafx.base;
  exports edu.ntnu.idi.idatt.mappeoppgavev2025;

  // Event package
  exports edu.ntnu.idi.idatt.mappeoppgavev2025.event;
  opens edu.ntnu.idi.idatt.mappeoppgavev2025.event to javafx.base;

  // Model package
  exports edu.ntnu.idi.idatt.mappeoppgavev2025.model;
  opens edu.ntnu.idi.idatt.mappeoppgavev2025.model to javafx.base;

  // Controller package
  //exports edu.ntnu.idi.idatt.mappeoppgavev2025.controller;
  //opens edu.ntnu.idi.idatt.mappeoppgavev2025.controller to javafx.fxml;

  // View package
  exports edu.ntnu.idi.idatt.mappeoppgavev2025.view;
  opens edu.ntnu.idi.idatt.mappeoppgavev2025.view to javafx.graphics;}
