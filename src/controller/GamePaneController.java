package controller;

import Model.*;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GamePaneController {

    private Main controll;

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

    private ArrayList<Rectangle> field;
    private ArrayList<Circle> tokens;

    public void setInstances(Main controll) {
        this.controll = controll;
    }

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

    public void clearField() {
        playingField.getChildren().clear();
    }

    //Feld mit Spielsteinen übergeben und auf Feld platzieren
    public void createTokens(Player... p) {
//        for (int j = 0; j < 2; j++) {
//            for (int i = 0; i < 20; i++) {
//                int x, y;
//                if (j < 1) {
//                    y = ((int)(i / 5)) + 1;
//                    x = ((i % 5) * 2 + (y % 2));
//                    setToken(x, y, Color.WHITE);
//                }
//                else {
//                    y = amount - ((int)(i / 5));
//                    x = ((i % 5) * 2 + (y % 2));
//                    setToken(x, y, Color.BLACK);
//                }
//            }
//        }
        for (Player player : p) {
            for (Stone s : player.getArray()) {
//                System.out.println(s.getIndexX() + " " + s.getIndexY());
                setToken(s.getIndexX(), s.getIndexY(), s.getcCirc(), s.getColor() == Model.Color.BLACK ? Color.BLACK : Color.WHITE);
            }
        }
    }

    public void removeToken(Stone stone) {
        Player temp = controll.getPlayerController().getPlayerByColor(stone.getColor());
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

    public void moveToken(Move move) {
        graphicAction = true;
        double value = size / amount;
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
                    //TODO Test if other stone has to be removed
                }
                else {
                    move.getStone().getcCirc().setLayoutX((move.getNextField().getIndexX() + 0.5) * value);
                    move.getStone().getcCirc().setLayoutY(size - (move.getNextField().getIndexY() + 0.5) * value);
                    if (move.nextField()) {
                        t.cancel();
                        graphicAction = false;
                    }
                }
            }
        };
        t.schedule(task, 0, 40);
    }

    private boolean isStoneNearField(Stone s, Field f, double value) {
        return (f.getIndexX() + 0.4) * value <= s.getcCirc().getLayoutX() && (f.getIndexX() + 0.6) * value >= s.getcCirc().getLayoutX() &&
                size - (f.getIndexY() + 0.6) * value <= s.getcCirc().getLayoutY() && size - (f.getIndexY() + 0.4) * value >= s.getcCirc().getLayoutY();
    }

    public void highlightFields(List<Field> fields, Move move) {
        colorField();
        for (Field f : fields) {
            f.getcRec().setFill(Color.DARKGREEN);
        }
        for (Field f : move.getEnteredFields()) {
            f.getcRec().setFill(Color.BLUE);
        }
    }

    //TODO Taken auf platz setzen
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

    public void visualizeSuperDame(Stone s) {
        if (s.isSuperDame()) {
            s.getcCirc().setStrokeWidth(2);
            s.getcCirc().setStroke(Color.GREENYELLOW);
        }
        //visualize SuperDame
    }

    @FXML
    private void onFieldKlick(MouseEvent e) {
        if (!graphicAction) {
            if (e.getSource() instanceof Rectangle || e.getSource() instanceof Circle) {
                if (e.getSource() instanceof Rectangle) {
                    Rectangle temp = (Rectangle) e.getSource();
                    int index = field.indexOf(temp);
                    int x = (int)(index / amount);
                    int y = index % amount;
                    Field pressedField = controll.playingField.getField(x, y);
                    controll.getGame().selectField(pressedField);
                }
                else {
                    Circle temp = (Circle) e.getSource();
                    Stone s = controll.getPlayerController().getCurrentPlayer().getStoneOfClickedCircle(temp);
                    if (s == null) {
                        System.err.println("Dieser Stein gehört nicht dir");
                        return;
                    }
                    controll.getGame().selectStone(s);
                }
            }
        }
    }

}
