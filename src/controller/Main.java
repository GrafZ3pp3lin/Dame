package controller;

import Model.PlayingField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

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
    private Stage rulesStage;

    //Model
    /**
     * Datensatz für das Spielfeld
     */
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

    /**
     * wird vor start aufgerufen (lädt alle benötigten Bilder)
     */
    @Override
    public void init() {
        logo = new Image(Main.class.getClassLoader().getResourceAsStream("resources/Dame-Logo.png"));
        superDame = new Image(Main.class.getClassLoader().getResourceAsStream("resources/SuperDame.png"));
    }

    /**
     * lädt alle Oberflächen und richtet das fenster ein
     * @param primaryStage Oberflächen Fenster
     */
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
        loadRulesPane();
        setStartLayout();

        playingField = new PlayingField();
        playerController = new PlayerController();
        game = new Game(this, gamePaneController, playerController);

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

    /**
     * setzt das Start Layout
     */
    private void setStartLayout() {
        menuLayout.setCenter(startLayout);
        menuPaneController.disableReturnItem(true);
    }

    /**
     * kehrt auf das Start Layout zurück und bricht das aktuelle Spiel ab
     * //TODO better clear (Game etc.)
     */
    public void returnToStart() {
        setStartLayout();
        gamePaneController.clearField();
        game.reset();
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

    /**
     * setzt die Spiel Oberfläche
     */
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
            ((AboutPaneController)loader.getController()).setInstances(aboutStage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * lädt die Rules Oberfläche
     */
    private void loadRulesPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RulesPane.fxml"));
            Parent aboutPane = loader.load();
            rulesStage = new Stage(StageStyle.UTILITY);
            rulesStage.setTitle("Rules Dame");
            rulesStage.setResizable(false);
            rulesStage.setScene(new Scene(aboutPane));
            rulesStage.sizeToScene();
            ((RulesPaneController)loader.getController()).setInstances(rulesStage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * öffnet das About Fenster
     */
    public void showAboutPane() {
        aboutStage.show();
    }

    /**
     * öffnet das regeln Fenster
     */
    public void showRulesPane() {
        rulesStage.show();
    }

    /**
     * startet ein Multiplayer Spiel
     */
    public void startGame(boolean ki, String name1, String name2) {
        playingField.rebuild(startPaneController.getSize());
        gamePaneController.buildPlayingField(startPaneController.getSize(), (int)primaryStage.getHeight() - 200, playingField);
        playerController.init(ki, startPaneController.getSize(), name1, name2);
        gamePaneController.createTokens(playerController.getPlayer1(), playerController.getPlayer2());
        setGameLayout();
    }

    /**
     * gibt eine Instanz des PlayerControllers zurück
     * @return PlayerController
     */
    public PlayerController getPlayerController() {
        return playerController;
    }

    /**
     * gibt eine Instanz des GamePaneControllers zurück
     * @return GamePaneController
     */
    public GamePaneController getGamePaneController(){
        return gamePaneController;
    }

    /**
     * gibt eine Instanz des Game zurück
     * @return Game
     */
    public Game getGame() {
        return game;
    }

    /**
     * gibt das Image für die SuperDame
     * @return Image SuperDame
     */
    public Image getSuperDameImage() {
        return superDame;
    }

}
