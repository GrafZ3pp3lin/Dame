package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
                field.add(temp);
                playingField.getChildren().add(temp);
            }
        }
        updatePlayingField();
    }

    public void clearField() {
        playingField.getChildren().clear();
    }

    //TODO Feld mit Spielsteinen übergeben und auf Feld platzieren
    public void updatePlayingField() {
        double a = size / amount;
        for (int i = 0; i < 30; i++) {
            Circle temp = new Circle();
            temp.setRadius(size / amount / 3);
            double x, y;
            if (i < 15) {
                temp.setFill(Color.WHITE);
                y = ((int)(i / 5));
                x = ((i % 5) * 2 + 0.5 + (y % 2)) * a;
            }
            else {
                temp.setFill(Color.BLACK);
                y = ((int)(i / 5)) + amount - 6;
                x = ((i % 5) * 2 + 0.5 + (y % 2)) * a;
            }
            temp.setLayoutX(x);
            temp.setLayoutY((y + 0.5) * a);
            playingField.getChildren().add(temp);
        }
    }

    //TODO Wenn sich nur ein Spielstein bewegt hat / von - zu übergeben
    public void updateToken() {

    }

    @FXML
    private void onFieldKlick(ActionEvent e) {
        if (e.getSource() instanceof Rectangle) {
            Rectangle temp = (Rectangle) e.getSource();
            int index = field.indexOf(temp);
            int x = (int)(index / amount), y = index % amount;
            //TODO Datensatz fehlt / richtiges Feld ermitteln und an Steuerung übergeben
        }
    }

}
