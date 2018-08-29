package controller;

import Model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    private Pane pane_player1;

    @FXML
    private Pane pane_player2;

    @FXML
    private Label label_player1;

    @FXML
    private Label label_player2;

    @FXML
    private Label label_status;

    private ArrayList<Rectangle> field;

    /**
     * setzt alle notwendigen Instanzen
     *
     * @param control Instanz der Main Klasse
     */
    public void setObjects(Main control) {
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
        clearField();
        playingField.setPrefSize(size, size);
        double a = (double)size / amount;
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
        setStatus("");
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
        pane_player1.getChildren().clear();
        pane_player2.getChildren().clear();
        playingField.getChildren().clear();
    }

    /**
     * setzt die Spielsteine der Spieler auf das Spielfeld
     * @param p alle Spieler
     */
    public void createTokens(Player... p) {
        for (Player player : p) {
            for (Stone s : player.getStones()) {
                initToken(s.getIndexX(), s.getIndexY(), s.getcCirc(), s.getColor() == Model.Color.BLACK ? Color.BLACK : Color.WHITE);
            }
        }
        setNames(p[0].getName(), p[1].getName());
        updatePlayer();
    }

    /**
     * setzt die Namen der beiden Spieler
     *
     * @param name1 Name Spieler1
     * @param name2 Name Spieler2
     */
    private void setNames(String name1, String name2) {
        if (name1 != null && !name1.isEmpty()) {
            label_player1.setText(name1);
        }
        if (name2 != null && !name2.isEmpty()) {
            label_player2.setText(name2);
        }
    }

    /**
     * färbt die Spieler Namen richtig ein.
     * Der Spieler der am Zug ist, bekommt einen orangenen Namen
     */
    public void updatePlayer() {
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
     * ein leerer String resetet die Message
     *
     * @param message Message
     */
    public void setStatus(String message) {
        label_status.setText(message);
    }

    /**
     * entfernt einen einzigen Stein vom Spielfeld und setzt diesen an den Rand
     * die eliminierten Steine werden "gestapelt" dargestellt Jeder Stein überagt seinen Vorgänger zur Hälfte
     *
     * @param stone Stein der entfernt wird
     */
    public void removeToken(Stone stone) {
        Player temp = control.getPlayerController().getPlayerByColor(stone.getColor());
        playingField.getChildren().remove(stone.getcCirc());
        if (stone.getColor() == Model.Color.BLACK) {
            pane_player1.getChildren().add(stone.getcCirc());
            double off = 0;
            if (stone.getcCirc() instanceof StackPane) {
                off = tokenRadius;
            }
            stone.getcCirc().setLayoutX(pane_player1.getWidth() / 2 - off);
            stone.getcCirc().setLayoutY(pane_player1.getChildren().size() * tokenRadius - off);
        }
        else {
            pane_player2.getChildren().add(stone.getcCirc());
            double off = 0;
            if (stone.getcCirc() instanceof StackPane) {
                off = tokenRadius;
            }
            stone.getcCirc().setLayoutX(pane_player2.getWidth() / 2 - off);
            stone.getcCirc().setLayoutY(pane_player2.getChildren().size() * tokenRadius - off);
        }
    }

    /**
     * bewegt einen Stein entlang eines Moves
     * alle Felder (enterFields) von Move werden der Reihe nach angefahren
     * während des Zugs werden, sobald beide Steine übereinander sind, die übersprungenen Steine (skippedFields) entfernt
     * während der Stein bewegt wird, sind weitere Benutzereingaben gesperrt.
     *
     * @param move Move, den der Stein macht
     */
    public void moveToken(Move move) {
        graphicAction = true;
        double value = (double)size / amount;
        updateToken(move.getStone().getcCirc());
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    //TODO ArrayIndexOutOfBoundsException
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
                        if (!move.nextField()) {
                            Platform.runLater(() -> placeToken(move.getEndField().getIndexX(), move.getEndField().getIndexY(), move.getStone().getcCirc()));
                            t.cancel();
                            t.purge();
                            if (move.getStone().isSuperDame()) {
                                Platform.runLater(() -> visualizeSuperDame(move.getStone()));
                            }
                            graphicAction = false;
                            control.getGame().finishedMove();
                        }
                    }
                }
                catch (NullPointerException e) {
                    System.err.println("Something went wrong");
                    e.printStackTrace();
                    if (move != null) {
                        placeToken(move.getEndField().getIndexX(), move.getEndField().getIndexY(), move.getStone().getcCirc());
                    }
                    t.cancel();
                    t.purge();
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Something went wrong");
                    e.printStackTrace();
                    if (move != null) {
                        placeToken(move.getEndField().getIndexX(), move.getEndField().getIndexY(), move.getStone().getcCirc());
                    }
                    t.cancel();
                    t.purge();
                }
            }
        };
        t.schedule(task, 0, 40);
    }

    /**
     * testet ob eine Node grafisch über einem Feld liegt
     * ein leichter Versatz von +/- 2% wird beachtet
     *
     * @param s Stein
     * @param f Feld
     * @param value Abstand in dem der Stein bewegt wird
     * @return true, falls der Stein über dem Feld liegt
     */
    private boolean isStoneNearField(Stone s, Field f, double value) {
        double off = 0;
        if (s.getcCirc() instanceof StackPane) {
//            off = ((StackPane) s.getcCirc()).getWidth() / 2;
            off = tokenRadius;
        }
        return (f.getIndexX() + 0.48) * value <= s.getcCirc().getLayoutX() + off && (f.getIndexX() + 0.52) * value >= s.getcCirc().getLayoutX() + off &&
                size - (f.getIndexY() + 0.52) * value <= s.getcCirc().getLayoutY() + off && size - (f.getIndexY() + 0.48) * value >= s.getcCirc().getLayoutY() + off;
    }

    /**
     * hebt alle besuchten Felder und mögliche weitere Felder hervor
     * besuchte Felder werden blau hervorgehoben und mögliche grün
     *
     * @param fields mögliche Felder
     * @param move Move (besuchte Felder)
     */
    public void highlightFields(List<Field> fields, List<Field> visitedFields, Move move) {
        colorField();
        for (Field f : fields) {
            if(!visitedFields.contains(f)) {
                f.getcRec().setFill(Color.DARKGREEN);
            }
        }
        for (Field f : move.getEnteredFields()) {
            f.getcRec().setFill(Color.BLUE);
        }
    }

    /**
     * initialisiert einen Token(Circle) und setzt ihn richtig aufs Spielfeld
     *
     * @param x X-Position
     * @param y Y-Position
     * @param c Kreis
     * @param color Farbe
     */
    private void initToken(int x, int y, Node c, Color color) {
        if (c instanceof Circle) {
            ((Circle)c).setRadius(tokenRadius);
            ((Circle)c).setFill(color);
            ((Circle)c).setStroke(Color.GRAY);
            ((Circle)c).setStrokeWidth(1);
            placeToken(x, y, c);
            c.setOnMouseClicked(event -> onFieldKlick(event));
            playingField.getChildren().add(c);
        }
        else {
            System.err.println("Something went wrong");
        }
    }

    /**
     * setzt die x- und y-Koordinaten einer Node, unter Beachtung der Superdame
     * einfache Steine sind Kreise und haben ihren Nullpunkt in der Mitte.
     * eine Superdame ist ein Stackpane, mit einem Circle und einem Image. Diese hat ihren Nullpunkt links oben.
     *
     * @param x
     * @param y
     * @param node
     * @see Circle
     */
    private void placeToken(int x, int y, Node node) {
        double a = (double)size / amount;
        double off = 0;
        if (node instanceof StackPane) {
            off = tokenRadius;
        }
        node.setLayoutX((x + 0.5) * a - off);
        node.setLayoutY(size - (y + 0.5) * a - off);
    }

    /**
     * updatet einen Token, sodass dieser ganz oben auf dem Feld liegt und sich
     * über anderen Tokens befindet. Die Node wird kurz von der Oberfläche entfernt und wieder hinzugefügt
     * Wichtig für moveToken
     *
     * @param c Kreis
     */
    private void updateToken(Node c) {
        playingField.getChildren().remove(c);
        playingField.getChildren().add(c);
    }

    /**
     * verwandelt einen einfachen Stein in eine Superdame. Dazu wird ein Stackpane erzeugt,
     * in welches der Circle und ein Image platziert werden. Das Image hat 75% der Größe des Circles
     *
     * @param s Superdamen Stein
     */
    private void visualizeSuperDame(Stone s) {
        if (s.isSuperDame() && s.getcCirc() instanceof Circle) {
            Circle c = (Circle)s.getcCirc();
            c.setOnMouseClicked(null);
            StackPane sp = new StackPane();
            sp.setPrefSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
            sp.getChildren().add(c);

            ImageView iw = new ImageView(control.getSuperDameImage());
            iw.setFitHeight(c.getRadius() * 1.5);
            iw.setFitWidth(c.getRadius() * 1.5);

            sp.getChildren().add(iw);
            sp.setLayoutX(c.getLayoutX() - c.getRadius());
            sp.setLayoutY(c.getLayoutY() - c.getRadius());
            sp.setOnMouseClicked(event -> onFieldKlick(event));
            playingField.getChildren().remove(c);
            playingField.getChildren().add(sp);
            s.changeNode(sp);
        }
    }

    /**
     * verarbeitet die Maus Klicks auf das Spielfeld
     * Geglickt werden kann auf ein Feld (Rectangle) ein Stein (Circle) oder ein Stackpane(Superdame)
     * Zu der geglickten Node wird das entsprechende Feld, bzw. der Stein ermittelt und an das Game übergeben
     *
     * @param e MouseEvent
     * @see Game
     */
    @FXML
    private void onFieldKlick(MouseEvent e) {
        if (!graphicAction && (!control.getPlayerController().isSinglePlayerGame() || control.getPlayerController().isCurrentPlayer1())) {
            if (e.getSource() instanceof Rectangle || e.getSource() instanceof Circle || e.getSource() instanceof StackPane) {
                setStatus("");
                if (e.getSource() instanceof Rectangle) {
                    Rectangle temp = (Rectangle) e.getSource();
                    int index = field.indexOf(temp);
                    int x = (int)(index / amount);
                    int y = index % amount;
                    Field pressedField = control.playingField.getField(x, y);
                    control.getGame().selectField(pressedField);
                }
                else {
                    Stone s = control.getPlayerController().getCurrentPlayer().getStoneOfClickedCircle((Node)e.getSource());
                    if (s == null) {
                        setStatus("Dieser Stein gehört nicht dir");
                        return;
                    }
                    else if (s.isEliminated()) {
                        setStatus("Dieser Stein ist bereits eliminiert");
                        return;
                    }
                    control.getGame().selectStone(s);
                }
            }
        }
    }

}
