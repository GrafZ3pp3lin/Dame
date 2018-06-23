package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.ArrayList;

public class GamePaneController {

    @FXML
    private BorderPane parent;

    private ArrayList<Rectangle> field;
    private ArrayList<Circle> tokens;

    public void buildPlayingField(int amount, int size) {
        field = new ArrayList<>();
        tokens = new ArrayList<>();
        Pane pane = new Pane();
        pane.setPrefSize(size, size);
        double a = size / amount;
        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < amount; j++) {
                Rectangle temp = new Rectangle();
                temp.setHeight(a);
                temp.setWidth(a);
                if (i + j % 2 == 0) {
                    temp.setFill(Color.BLACK);
                }
                else {
                    temp.setFill(Color.WHITE);
                }
                temp.setX(i * a);
                temp.setY(j * a);
                field.add(temp);
                pane.getChildren().add(temp);
            }
        }
        parent.setCenter(pane);
    }

    public void deleteField() {
        parent.setCenter(null);
    }

}
