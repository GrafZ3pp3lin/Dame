package controller;

import Model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.*;

/**
 * Diese Klasse verwaltet das SpielFeld und steuert
 * Benutzereingaben innerhalb eines Spiels
 *
 * @author Johannes Gaiser
 */

public class GamePaneController {

    private Main control;

    public int size;
    private int amount, tokenRadius;
    private boolean graphicAction;

    @FXML
    private Pane playingField;

    @FXML
    private BorderPane parent;

    @FXML
    private VBox vbox_player1;

    @FXML
    private VBox vbox_player2;

    @FXML
    private Label label_player1;

    @FXML
    private Label label_player2;

    @FXML
    private Label label_status;

    private ArrayList<Rectangle> field;
    private ArrayList<Circle> tokens;

    /**
     * setzt alle notwendigen Instanzen
     *
     * @param control Instanz der Main Klasse
     */
    public void setInstances(Main control) {
        this.control = control;
    }

    /**
     *stellt das Spielfelds grafisch dar
     *
     * @param amount Größe des Spielfelds (8/10)
     * @param size Größe des Spielfelds in Pixel
     * @param pf Instanz von PlayingField (Daten für das Spielfeld)
     */
    public void buildPlayingField(int amount, int size, PlayingField pf) {
        this.amount = amount;
        this.size = size;
        this.tokenRadius = size / amount / 3;

        field = new ArrayList<>();
        tokens = new ArrayList<>();
        clearField();
        playingField.setPrefSize(size, size);
        double a = size / amount;
        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < amount; j++) {
                Rectangle temp = new Rectangle();
                temp.setArcHeight(0);
                temp.setArcWidth(0);
                temp.setHeight(a);
                temp.setWidth(a);
                temp.setLayoutX(i * a);
                temp.setLayoutY(size - j * a - a);
                temp.setOnMouseClicked(e -> onFieldKlick(e));
                field.add(temp);
                playingField.getChildren().add(temp);
                pf.getField(i, j).setcRec(temp);
            }
        }
        colorField();
    }

    /**
     * färbt das Spielfeld in seiner Standardfarbe ein
     */
    public void colorField() {
        int i = 0;
        for (Rectangle rec : field) {
            if (((int)(i / amount) + (i % amount)) % 2 == 0) {
                rec.setFill(Color.BROWN);
            } else {
                rec.setFill(Color.WHEAT);
            }
            i++;
        }
    }

    /**
     * entfernt alle grafischen Objekte von der Oberfläche
     */
    public void clearField() {
        vbox_player1.getChildren().clear();
        vbox_player2.getChildren().clear();
        playingField.getChildren().clear();
    }

    /**
     * setzt die Spielsteine der Spieler auf das Spielfeld
     * @param p alle Spieler
     */
    public void createTokens(Player... p) {
        for (Player player : p) {
            for (Stone s : player.getArray()) {
                setToken(s.getIndexX(), s.getIndexY(), s.getcCirc(), s.getColor() == Model.Color.BLACK ? Color.BLACK : Color.WHITE);
            }
        }
    }

    /**
     * setzt die Namen der beiden Spieler
     *
     * @param name1 Name Spieler1
     * @param name2 Name Spieler2
     */
    public void setNames(String name1, String name2) {
        if (name1 != null && !name1.isEmpty()) {
            label_player1.setText(name1);
        }
        if (name2 != null && !name2.isEmpty()) {
            label_player2.setText(name2);
        }
    }

    /**
     * update Player name visual
     */
    private void updatePlayer() {
        if (control.getPlayerController().isCurrentPlayer1()) {
            label_player1.setId("nameOnTurn");
            label_player2.setId("name");
        }
        else {
            label_player1.setId("name");
            label_player2.setId("nameOnTurn");
        }
    }

    /**
     * Message auf der Spieloberfläche
     *
     * @param message Message
     */
    public void setStatus(String message) {
        label_status.setText(message);
    }

    /**
     * entfernt einen einzigen Stein vom Spielfeld und setzt diesen an den Rand
     * @param stone Stein der entfernt wird
     */
    public void removeToken(Stone stone) {
        Player temp = control.getPlayerController().getPlayerByColor(stone.getColor());
        playingField.getChildren().remove(stone.getcCirc());
//        stone.getcCirc().setLayoutX((temp.getEliminatedStones() + 1) * tokenRadius);
//        stone.getcCirc().setLayoutY(tokenRadius);
        if (stone.getColor() == Model.Color.BLACK) {
            vbox_player1.getChildren().add(stone.getcCirc());
        }
        else {
            vbox_player2.getChildren().add(stone.getcCirc());
        }
    }

    /**
     * bewegt einen Stein entlang eines Moves
     * während der Stein bewegt wird, sind weitere Benutzereingaben gesperrt.
     *
     * @param move Move, den der Stein macht
     */
    public void moveToken(Move move) {
        graphicAction = true;
        double value = size / amount;
        updateToken(move.getStone().getcCirc());
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //Test ob Token in der Nähe vom richtigen Platz ist
                if (!isStoneNearField(move.getStone(), move.getNextField(), value)) {
                    move.getStone().getcCirc().setLayoutX(move.getStone().getcCirc().getLayoutX() + (value / 12)
                            * (move.getNextField().getIndexX() >= move.getCurrentField().getIndexX() ? 1 : -1));
                    move.getStone().getcCirc().setLayoutY(move.getStone().getcCirc().getLayoutY() + (value / 12)
                            * (move.getNextField().getIndexY() >= move.getCurrentField().getIndexY() ? -1 : 1));
                    if (move.getFirstSkipedField() != null && isStoneNearField(move.getStone(), move.getFirstSkipedField(), value)) {
                        Stone s = control.getPlayerController().getOtherPlayer().getStoneAt(move.getFirstSkipedField().getIndexX(), move.getFirstSkipedField().getIndexY());
                        if (s != null) {
                            s.setEliminated();
                            Platform.runLater(() -> removeToken(s));
                            move.nextSkipedField();
                        }
                    }
                }
                else {
                    move.getStone().getcCirc().setLayoutX((move.getNextField().getIndexX() + 0.5) * value);
                    move.getStone().getcCirc().setLayoutY(size - (move.getNextField().getIndexY() + 0.5) * value);
                    if (move.nextField()) {
                        t.cancel();
                        graphicAction = false;
                        control.getPlayerController().changePlayer();
                        updatePlayer();
                    }
                }
            }
        };
        t.schedule(task, 0, 40);
    }

    /**
     * testet ob ein Circle grafisch über einem Feld liegt
     *
     * @param s Stein
     * @param f Feld
     * @param value Abstand in dem der Stein bewegt wird
     * @return true, falls der Stein über dem Feld liegt
     */
    private boolean isStoneNearField(Stone s, Field f, double value) {
        return (f.getIndexX() + 0.4) * value <= s.getcCirc().getLayoutX() && (f.getIndexX() + 0.6) * value >= s.getcCirc().getLayoutX() &&
                size - (f.getIndexY() + 0.6) * value <= s.getcCirc().getLayoutY() && size - (f.getIndexY() + 0.4) * value >= s.getcCirc().getLayoutY();
    }

    /**
     * hebt alle besuchten Felder und mögliche weitere Felder hervor
     *
     * @param fields mögliche Felder
     * @param move Move (besuchte Felder)
     */
    public void highlightFields(List<Field> fields, Move move) {
        colorField();
        for (Field f : fields) {
            f.getcRec().setFill(Color.DARKGREEN);
        }
        for (Field f : move.getEnteredFields()) {
            f.getcRec().setFill(Color.BLUE);
        }
    }

    /**
     * setzt einen Token(Circle) richtig aufs Spielfeld
     *
     * @param x X-Position
     * @param y Y-Position
     * @param c Kreis
     * @param color Farbe
     */
    private void setToken(int x, int y, Circle c, Color color) {
        double a = size / amount;
        c.setRadius(tokenRadius);
        c.setFill(color);
        c.setLayoutX((x + 0.5) * a);
        c.setLayoutY(size - (y + 0.5) * a);
        c.setOnMouseClicked(event -> onFieldKlick(event));
        playingField.getChildren().add(c);
        tokens.add(c);
    }

    /**
     * updatet einen Token, sodass dieser ganz oben auf dem Feld liegt und sich
     * über anderen Tokens befindet.
     * Wichtig für moveToken
     * @param c Kreis
     */
    private void updateToken(Circle c) {
        playingField.getChildren().remove(c);
        playingField.getChildren().add(c);
    }

    /**
     * hebt einen Kreis grafisch hervor
     *
     * @param s Superdamen Stein
     */
    public void visualizeSuperDame(Stone s) {
        if (s.isSuperDame()) {
            s.getcCirc().setStrokeWidth(2);
            s.getcCirc().setStroke(Color.GREENYELLOW);
        }
        //visualize SuperDame
    }

    /**
     * verarbeitet die Maus Klicks auf das Spielfeld
     *
     * @param e MouseEvent
     */
    @FXML
    private void onFieldKlick(MouseEvent e) {
        if (!graphicAction && (!control.getPlayerController().isSinglePlayerGame() || control.getPlayerController().isCurrentPlayer1())) {
            if (e.getSource() instanceof Rectangle || e.getSource() instanceof Circle) {
                if (e.getSource() instanceof Rectangle) {
                    Rectangle temp = (Rectangle) e.getSource();
                    int index = field.indexOf(temp);
                    int x = (int)(index / amount);
                    int y = index % amount;
                    Field pressedField = control.playingField.getField(x, y);
                    control.getGame().selectField(pressedField);
                }
                else {
                    Circle temp = (Circle) e.getSource();
                    Stone s = control.getPlayerController().getCurrentPlayer().getStoneOfClickedCircle(temp);
                    if (s == null) {
                        System.err.println("Dieser Stein gehört nicht dir");
                        return;
                    }
                    else if (s.isEliminated()) {
                        System.err.println("Dieser Stein ist bereits eliminiert!");
                        return;
                    }
                    control.getGame().selectStone(s);
                }
            }
        }
    }

}
