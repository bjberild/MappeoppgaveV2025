package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.*;

public class PlayerCreationDialog extends Stage {
  private Path savedFilePath = null;
  private static final List<String> COLORS = Arrays.asList("Red", "Blue", "Green", "Yellow", "Pink");
  private final VBox playerRows = new VBox(5);
  private final ComboBox<Integer> playerCountBox = new ComboBox<>();
  private final List<TextField> nameFields = new ArrayList<>();
  private final List<ComboBox<String>> colorBoxes = new ArrayList<>();

  public PlayerCreationDialog(Stage owner) {
    setTitle("Create New Players");
    initModality(Modality.APPLICATION_MODAL);
    initOwner(owner);

    playerCountBox.getItems().addAll(2, 3, 4, 5);
    playerCountBox.setValue(2);
    playerCountBox.setOnAction(e -> updatePlayerRows());

    updatePlayerRows();

    Button saveBtn = new Button("Save");
    saveBtn.setOnAction(e -> {
      if (validateInputs()) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save Players CSV");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        File file = fc.showSaveDialog(this);
        if (file != null) {
          saveToFile(file);
          savedFilePath = file.toPath();
          close();
        }
      }
    });

    VBox root = new VBox(10,
        new HBox(10, new Label("Number of players:"), playerCountBox),
        playerRows,
        saveBtn
    );
    root.setPadding(new Insets(15));
    root.setAlignment(Pos.CENTER);

    setScene(new Scene(root));
  }

  private void updatePlayerRows() {
    playerRows.getChildren().clear();
    nameFields.clear();
    colorBoxes.clear();
    int count = playerCountBox.getValue();
    Set<String> usedColors = new HashSet<>();
    for (int i = 0; i < count; i++) {
      TextField nameField = new TextField();
      nameField.setPromptText("Player " + (i + 1) + " name");
      ComboBox<String> colorBox = new ComboBox<>();
      colorBox.getItems().addAll(COLORS);
      colorBox.setPromptText("Color");
      colorBox.setEditable(false);
      colorBox.valueProperty().addListener((obs, oldVal, newVal) -> {
        // Prevent duplicate color selection
        if (newVal != null && Collections.frequency(getSelectedColors(), newVal) > 1) {
          colorBox.setValue(null);
        }
      });
      nameFields.add(nameField);
      colorBoxes.add(colorBox);
      HBox row = new HBox(10, nameField, colorBox);
      row.setAlignment(Pos.CENTER);
      playerRows.getChildren().add(row);
    }
  }

  private boolean validateInputs() {
    Set<String> names = new HashSet<>();
    Set<String> colors = new HashSet<>();
    for (int i = 0; i < nameFields.size(); i++) {
      String name = nameFields.get(i).getText().trim();
      String color = colorBoxes.get(i).getValue();
      if (name.isEmpty() || color == null) {
        showAlert("All fields must be filled and colors must be selected.");
        return false;
      }
      if (!names.add(name)) {
        showAlert("Player names must be unique.");
        return false;
      }
      if (!colors.add(color)) {
        showAlert("Each player must have a unique color.");
        return false;
      }
    }
    return true;
  }

  private List<String> getSelectedColors() {
    List<String> selected = new ArrayList<>();
    for (ComboBox<String> box : colorBoxes) {
      if (box.getValue() != null) selected.add(box.getValue());
    }
    return selected;
  }

  private void saveToFile(File file) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      for (int i = 0; i < nameFields.size(); i++) {
        writer.write(nameFields.get(i).getText().trim() + "," + colorBoxes.get(i).getValue());
        writer.newLine();
      }
    } catch (Exception ex) {
      showAlert("Failed to save file: " + ex.getMessage());
    }
  }

  private void showAlert(String msg) {
    Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
    alert.initOwner(this);
    alert.showAndWait();
  }

  public Path getSavedFilePath() {
    return savedFilePath;
  }
}