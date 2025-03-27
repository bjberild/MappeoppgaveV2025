package edu.ntnu.idi.idatt.mappeoppgavev2025.window;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainWindow {
  private Stage primaryStage;
  private StackPane root = new StackPane();


  public MainWindow(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public void init() {
    primaryStage.setTitle("Hello World!");
    primaryStage.setScene(new Scene(root, 800, 600));
  }

  public void show() {
    primaryStage.show();
  }
}
