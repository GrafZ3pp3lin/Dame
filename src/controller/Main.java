package controller;

import Model.PlayingField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private Parent startLayout;
    private Parent gameLayout;
    private BorderPane menuLayout;

    //Model
    private PlayingField playingField;

    //Controller
    private GamePaneController gamePaneController;
    private StartPaneController startPaneController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Dame");
        primaryStage.setOnCloseRequest(e -> Platform.exit());


        initRootLayout();
        loadStartLayout();
        loadGameLayout();
        setStartLayout();
//        sampleGame();
        primaryStage.show();
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MenuPane.fxml"));
            menuLayout = loader.load();
            primaryStage.setScene(new Scene(menuLayout));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStartLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/StartPane.fxml"));
            startLayout = loader.load();
            startPaneController = loader.getController();
            startPaneController.setInstances(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setStartLayout() {
        menuLayout.setCenter(startLayout);
    }

    private void loadGameLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/GamePane.fxml"));
            gameLayout = loader.load();
            gamePaneController = loader.getController();
//            gamePaneController.setInstances();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setGameLayout() {
        menuLayout.setCenter(gameLayout);
    }

    public void sampleGame() {
        playingField = new PlayingField(startPaneController.getSize());
        gamePaneController.buildPlayingField(startPaneController.getSize(), 500, playingField);
        setGameLayout();
    }

}
