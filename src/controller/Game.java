package controller;

import Model.Color;
import Model.Field;
import Model.Stone;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Main control;
    private GamePaneController gamePaneController;
    private PlayerController playerController;

    public Game(Main control, GamePaneController gamePaneController, PlayerController playerController) {
        this.control = control;
        this.gamePaneController = gamePaneController;
        this.playerController = playerController;
    }

    public void selectStone(Stone s) {
        List<Field> f = new ArrayList();
        if (s.getColor() == Color.BLACK) {
            Field field = control.playingField.getField(s.getIndexX() + 1, s.getIndexY() + 1);
            if (field != null) {
                if (!playerController.getCurrentPlayer().hasStoneAt(field.getIndexX(), field.getIndexY()) &&
                        !playerController.getOtherPlayer().hasStoneAt(field.getIndexX(), field.getIndexY())) {
                    f.add(field);
                }
                else if (playerController.getOtherPlayer().hasStoneAt(field.getIndexX(), field.getIndexY())) {
                    field = control.playingField.getField(s.getIndexX() + 2, s.getIndexY() + 2);
                    if (!playerController.getCurrentPlayer().hasStoneAt(field.getIndexX(), field.getIndexY()) &&
                            !playerController.getOtherPlayer().hasStoneAt(field.getIndexX(), field.getIndexY())) {
                        f.add(field);
                    }
                }
            }

            field = control.playingField.getField(s.getIndexX() - 1, s.getIndexY() + 1);
            if (field != null) {
                if (!playerController.getCurrentPlayer().hasStoneAt(field.getIndexX(), field.getIndexY()) &&
                        !playerController.getOtherPlayer().hasStoneAt(field.getIndexX(), field.getIndexY())) {
                    f.add(field);
                }
                else if (playerController.getOtherPlayer().hasStoneAt(field.getIndexX(), field.getIndexY())) {
                    field = control.playingField.getField(s.getIndexX() - 2, s.getIndexY() + 2);
                    if (!playerController.getCurrentPlayer().hasStoneAt(field.getIndexX(), field.getIndexY()) &&
                            !playerController.getOtherPlayer().hasStoneAt(field.getIndexX(), field.getIndexY())) {
                        f.add(field);
                    }
                }
            }
        }
        else {
            //weiß
        }
        gamePaneController.highlightFields(f);
    }



}
