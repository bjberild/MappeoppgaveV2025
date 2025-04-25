package edu.ntnu.idi.idatt.mappeoppgavev2025.view;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.controls.ControlPanelView;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.players.PlayerView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;



public class BoardGameGUI {

    private BoardGame game;

    public Scene getScene() {

        int sceneWidth = 800;
        int sceneHeight = 600;
        
        //Creates the main layout
        BorderPane root = new BorderPane();
        root.setPrefSize(sceneWidth, sceneHeight);

        Image bgImage = new Image(getClass().getResourceAsStream(
            "/edu/ntnu/idi/idatt/mappeoppgavev2025/images/Jungle-Background.png"));
        BackgroundSize bgSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, false);
        BackgroundImage bgImageCfg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgSize);
        
        root.setBackground(new Background(bgImageCfg));
      

        //Top of the layout: Title
     Image titleImage = new Image(getClass().getResourceAsStream(
            "/edu/ntnu/idi/idatt/mappeoppgavev2025/images/Snakes-and-Ladders.png"
        ));
        ImageView titleImageView = new ImageView(titleImage);
        titleImageView.setPreserveRatio(true);
        titleImageView.setFitHeight(40);
        BorderPane.setAlignment(titleImageView, Pos.CENTER);
        BorderPane.setMargin(titleImageView, new Insets(20));
        root.setTop(titleImageView);
        
        //Starts the game and creates the board, dice and players
        game = new BoardGame();
        game.createBoard();
        game.createDice(2);
        game.addPlayer(new Player("Alice"));
        game.addPlayer(new Player("Bob"));
        GridPane boardGrid = LadderBoardGameView.createBoardGrid(game.getBoard());
        boardGrid.setPadding(new Insets(20));
        boardGrid.setPrefWidth(500);
        root.setCenter(boardGrid);

        VBox playersBox = new PlayerView(game.getPlayers());
        
        DiceView diceView = new DiceView();

        HBox mainContent = new HBox(20, playersBox, boardGrid, diceView);
        mainContent.setAlignment(Pos.CENTER);
        HBox.setHgrow(playersBox, Priority.ALWAYS);
        HBox.setHgrow(boardGrid, Priority.ALWAYS);
        HBox.setHgrow(diceView, Priority.ALWAYS);
        root.setCenter(mainContent);



        ControlPanelView controlPanel = new ControlPanelView(game);
        root.setBottom(controlPanel);
        BorderPane.setMargin(controlPanel, new Insets(10));
    

        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        scene.getStylesheets().add(
            getClass().getResource("/edu/ntnu/idi/idatt/mappeoppgavev2025/styles/styles.css").toExternalForm()
            );

        return scene;
 
    }

}

    
