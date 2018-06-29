package controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GamePaneController {

    private int amount, size;

    @FXML
    private Pane playingField;

    @FXML
    private BorderPane parent;

    private ArrayList<Rectangle> field;
    private ArrayList<Circle> tokens;

    public void setInstances() {
        //TODO benötigte Instancen hier übergeben
    }

    public void buildPlayingField(int amount, int size) {
        this.amount = amount;
        this.size = size;
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
                if ((i + j) % 2 == 0) {
                    temp.setFill(Color.BROWN);
                } else {
                    temp.setFill(Color.WHEAT);
                }
                temp.setLayoutX(i * a);
                temp.setLayoutY(j * a);
                temp.setOnMouseClicked(e -> onFieldKlick(e));
                field.add(temp);
                playingField.getChildren().add(temp);
            }
        }
        createTokens();
    }

    public void clearField() {
        playingField.getChildren().clear();
    }

    //TODO Feld mit Spielsteinen übergeben und auf Feld platzieren
    public void createTokens() {
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 15; i++) {
                int x, y;
                if (j < 1) {
                    y = ((int)(i / 5));
                    x = ((i % 5) * 2 + (y % 2));
                    setToken(x, y, Color.WHITE);
                }
                else {
                    y = amount - 1 - ((int)(i / 5));
                    x = ((i % 5) * 2 + (y % 2));
                    setToken(x, y, Color.BLACK);
                }
            }
        }
    }

    public void removeToken() {

    }

    //TODO Wenn sich nur ein Spielstein bewegt hat / von - zu übergeben
    public void moveToken() {

    }

    public void highlightFields() {

    }

    //TODO Taken auf platz setzen
    private void setToken(int x, int y, Color c) {
        double a = size / amount;
        Circle temp = new Circle();
        temp.setRadius(size / amount / 3);
        temp.setFill(c);
        temp.setLayoutX((x + 0.5) * a);
        temp.setLayoutY((y + 0.5) * a);
        playingField.getChildren().add(temp);
    }

    @FXML
    private void onFieldKlick(MouseEvent e) {
        if (e.getSource() instanceof Rectangle || e.getSource() instanceof Circle) {
            int x, y;
            if (e.getSource() instanceof Rectangle) {
                Rectangle temp = (Rectangle) e.getSource();
                int index = field.indexOf(temp);
                x = (int)(index / amount);
                y = index % amount;
            }
            else {
                Circle temp = (Circle) e.getSource();

            }
//            System.out.println(x + " " + y);
            //TODO Datensatz fehlt / richtiges Feld ermitteln und an Steuerung übergeben
        }
    }

}
