package controller;

import Model.PlayingField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

/**
 * Steuerung.
 * lädt alle Oberflächen und verwaltet diese
 *
 * @author Johannes Gaiser
 */
public class Main extends Application {

    private Stage primaryStage;
    private Parent startLayout;
    private Parent gameLayout;
    private BorderPane menuLayout;
    private Stage aboutStage;

    //Model
    public static PlayingField playingField;

    //Controller
    private GamePaneController gamePaneController;
    private StartPaneController startPaneController;
    private MenuPaneController menuPaneController;
    private PlayerController playerController;
    private Game game;

    private Image logo;
    private Image superDame;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        logo = new Image(Main.class.getClassLoader().getResourceAsStream("resources/Dame-Logo.png"));
        superDame = new Image(Main.class.getClassLoader().getResourceAsStream("resources/SuperDame.png"));
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Dame");
        primaryStage.getIcons().add(logo);
        primaryStage.setOnCloseRequest(e -> Platform.exit());

        initRootLayout();
        loadStartLayout();
        loadGameLayout();
        loadAboutPane();
        setStartLayout();

        primaryStage.sizeToScene();
        primaryStage.setMinHeight(menuLayout.getPrefHeight());
        primaryStage.setMinWidth(menuLayout.getPrefWidth());
        primaryStage.show();
    }

    /**
     * lädt das Menü
     */
    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MenuPane.fxml"));
            menuLayout = loader.load();
            primaryStage.setScene(new Scene(menuLayout));
            menuPaneController = loader.getController();
            menuPaneController.setInstances(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * lädt die Start Oberfläche
     */
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
        menuPaneController.disableReturnItem(true);
    }

    public void returnToStart() {
        setStartLayout();
        gamePaneController.clearField();
    }

    /**
     * lädt die Spiel Oberfläche
     */
    private void loadGameLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/GamePane.fxml"));
            gameLayout = loader.load();
            gamePaneController = loader.getController();
            gamePaneController.setInstances(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setGameLayout() {
        menuLayout.setCenter(gameLayout);
        menuPaneController.disableReturnItem(false);
    }

    /**
     * lädt die About Oberfläche
     */
    private void loadAboutPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/AboutPane.fxml"));
            Parent aboutPane = loader.load();
            aboutStage = new Stage(StageStyle.UTILITY);
            aboutStage.setAlwaysOnTop(true);
            aboutStage.setTitle("About Dame");
            aboutStage.setResizable(false);
            aboutStage.setScene(new Scene(aboutPane));
            aboutStage.sizeToScene();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAboutPane() {
        aboutStage.show();
    }

    /**
     * startet ein Multiplayer Spiel
     */
    public void startGame(boolean ki, String name1, String name2) {
        playingField = new PlayingField(startPaneController.getSize());
        gamePaneController.buildPlayingField(startPaneController.getSize(), (int)primaryStage.getHeight() - 200, playingField);
        playerController = new PlayerController(ki, startPaneController.getSize(), name1, name2);
        gamePaneController.createTokens(playerController.getPlayer1(), playerController.getPlayer2());
        game = new Game(this, gamePaneController, playerController);
        setGameLayout();
    }

    public void winDialog(String name){

        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle(name + " gewinnt");
        dialog.setHeaderText(name + " hat das Spiel gewonnen! Wähle nun, ob du eine neue Runde spielen, ins Hauptmenü zurückkehren oder das Spiel beenden möchtest.");

        ButtonType restartButton = new ButtonType("Neustart");
        ButtonType menuButton = new ButtonType("Hauptmenü");
        ButtonType closeButton = new ButtonType("Beenden");

        dialog.getButtonTypes().setAll(restartButton, menuButton, closeButton);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == restartButton){
            startGame(playerController.isSinglePlayerGame(), playerController.getPlayer1().getName(), playerController.getPlayer2().getName());
        } else if (result.get() == menuButton) {
            returnToStart();
        } else {
            System.exit(0);
        }

    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public GamePaneController getGamePaneController(){
        return gamePaneController;
    }

    public Game getGame() {
        return game;
    }

    public Image getSuperDameImage() {
        return superDame;
    }

}
