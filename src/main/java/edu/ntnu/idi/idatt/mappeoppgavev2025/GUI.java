package edu.ntnu.idi.idatt.mappeoppgavev2025;


import javafx.application.Application;
import javafx.stage.Stage;
import edu.ntnu.idi.idatt.mappeoppgavev2025.window.MainWindow;

public class GUI extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    MainWindow mainWindow = new MainWindow(stage);
    mainWindow.init();
    mainWindow.show();
  }
}
