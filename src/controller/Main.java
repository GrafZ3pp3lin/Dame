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
     * initialisiert alle Spielobjekte
     *
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
     * lädt das Menü (MenuPane.fxml)
     * das Menü ist auf einem BorderPane platziert. Im oberen Teil, ist eine Menü-Leiste, bestehend aus Optionen
     * und Hilfe.
     * Der mittlere Teil ist leer, sodass hier andere Oberflächen eingefügt werden können. Das Menü bleibt also immer
     * sichtbar.
     */
    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MenuPane.fxml"));
            menuLayout = loader.load();
            primaryStage.setScene(new Scene(menuLayout));
            menuPaneController = loader.getController();
            menuPaneController.setObjects(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * lädt die Start Oberfläche. Auf der Startoberfläche können die Spieleinstellungen verändert werden und
     * zwischen Single- und Multiplayer-Spiel gewählt werden.
     */
    private void loadStartLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/StartPane.fxml"));
            startLayout = loader.load();
            startPaneController = loader.getController();
            startPaneController.setObjects(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Das StartLayout wird in das "Center" der Menü Oberfläche gesetzt
     */
    private void setStartLayout() {
        menuLayout.setCenter(startLayout);
        this.primaryStage.setResizable(true);
        menuPaneController.disableReturnItem(true);
    }

    /**
     * kehrt auf das Start Layout zurück und bricht das aktuelle Spiel ab
     */
    public void returnToStart() {
        setStartLayout();
        gamePaneController.clearField();
        game.reset();
    }

    /**
     * lädt die Spiel Oberfläche. Auf der Spieloberfläche wird später das Spielfeld angezeigt
     */
    private void loadGameLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/GamePane.fxml"));
            gameLayout = loader.load();
            gamePaneController = loader.getController();
            gamePaneController.setObjects(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * setzt die Spiel Oberfläche. Dazu wird die das SpielLayout in das "Center" des Menü gesetzt
     */
    private void setGameLayout() {
        menuLayout.setCenter(gameLayout);
        this.primaryStage.setResizable(false);
        menuPaneController.disableReturnItem(false);
    }

    /**
     * lädt die About Oberfläche. Die About Oberfläche wird in einem seperaten Fenster (Stage) angezeigt.
     * Das seperate Fenster ist ein Utility-Fenster, nicht Größen-veränderbar und immer im Vordergrund.
     *
     * @see StageStyle
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
            ((AboutPaneController)loader.getController()).setObjects(aboutStage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * lädt die Rules Oberfläche. Die Regeln werden in einem seperaten Fenster angezeigt, sodass sowohl Regeln,
     * als auch Spiel sichtbar sein können. Das Regeln Fenster ist ein Utility-Fenster.
     *
     * @see StageStyle
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
            ((RulesPaneController)loader.getController()).setObjects(rulesStage);
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
     * öffnet das Regeln Fenster
     */
    public void showRulesPane() {
        rulesStage.show();
    }

    /**
     * startet ein Spiel. Dazu wird das Spielfeld neu generiert und an den GamePaneController übergeben, der
     * dieses Spielfeld grafisch darstellt. Der PlayerController wird neu initialisiert und generiert die neuen Spieler
     * Die neuen Spieler werden wieder an den gamePaneController übergeben, sodasss die Steine der Spieler garfisch dargestellt
     * werden können.
     *
     * @param ki gibt an, ob es sich um ein Single- (ki = true) oder Multiplayer-Spiel handelt
     * @param name1 Der Name von Spieler 1
     * @param name2 Der Name von Spieler 2
     * @see GamePaneController
     */
    public void startGame(boolean ki, String name1, String name2) {
        playingField.rebuild(startPaneController.getSize());
        gamePaneController.buildPlayingField(startPaneController.getSize(), (int)primaryStage.getHeight() - 200, playingField);
        playerController.init(ki, startPaneController.getSize(), name1, name2);
        gamePaneController.createTokens(playerController.getPlayer1(), playerController.getPlayer2());
        setGameLayout();
    }

    public void winDialog(String name){

        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.initStyle(StageStyle.UNDECORATED);
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
            Platform.exit();
        }

    }

    /**
     * gibt ein Objekt des PlayerControllers zurück
     * @return PlayerController
     */
    public PlayerController getPlayerController() {
        return playerController;
    }

    /**
     * gibt ein Objekt des GamePaneControllers zurück
     * @return GamePaneController
     */
    public GamePaneController getGamePaneController(){
        return gamePaneController;
    }

    /**
     * gibt ein Objekt von Game zurück
     * @return Game
     */
    public Game getGame() {
        return game;
    }

    /**
     * gibt das Image für die SuperDame zurück
     * @return Image SuperDame
     */
    public Image getSuperDameImage() {
        return superDame;
    }

}
