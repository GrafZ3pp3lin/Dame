package controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GamePaneController {

    @FXML
    private BorderPane parent;

    @FXML
    private VBox deck;

    private ArrayList<Rectangle> field;
    private ArrayList<Circle> tokens;

    public void buildPlayingField(int amount, int size) {
        field = new ArrayList<>();
        tokens = new ArrayList<>();
        Pane pane = new Pane();
        pane.setMaxSize(size, size);
        double a = size / amount;
        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < amount; j++) {
                Rectangle temp = new Rectangle();
                temp.setArcHeight(0);
                temp.setArcWidth(0);
                temp.setHeight(a);
                temp.setWidth(a);
                if ((i + j) % 2 == 0) {
                    temp.setFill(Color.BLACK);
                } else {
                    temp.setFill(Color.WHITE);
                }
                temp.setLayoutX(i * a);
                temp.setLayoutY(j * a);
                field.add(temp);
                pane.getChildren().add(temp);
            }
        }
        deck.getChildren().add(pane);
    }

    public void deleteField() {
        deck.getChildren().clear();
    }

}
