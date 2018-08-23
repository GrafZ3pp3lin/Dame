package controller;

import Model.Color;
import Model.Field;
import Model.Move;
import Model.Stone;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Main control;
    private GamePaneController gamePaneController;
    private PlayerController playerController;

    private List<Field> possibleFields;
    private Move move;

    public Game(Main control, GamePaneController gamePaneController, PlayerController playerController) {
        this.control = control;
        this.gamePaneController = gamePaneController;
        this.playerController = playerController;
        possibleFields = new ArrayList<>();
    }

    private boolean emptyField(Field f) {
        return !playerController.getCurrentPlayer().hasStoneAt(f.getIndexX(), f.getIndexY()) &&
                !playerController.getOtherPlayer().hasStoneAt(f.getIndexX(), f.getIndexY());
    }

    //TODO SuperDame
    private boolean testField(int x, int y, int indexX, int indexY) {
        Field field = control.playingField.getField(x + indexX, y + indexY);
        if (field != null) {
            if (emptyField(field)) {
                possibleFields.add(field);
                return true;
            }
            else if (playerController.getOtherPlayer().hasStoneAt(field.getIndexX(), field.getIndexY())) {
                field = control.playingField.getField(x + indexX * 2, y + indexY * 2);
                if (field != null && emptyField(field)) {
                    possibleFields.add(field);
                    return true;
                }
            }
        }
        return false;
    }

    //TODO SuperDame
    private void testFieldScope(Field f, Color c) {
        if (c == Color.BLACK) {
            testField(f.getIndexX(), f.getIndexY(), 1, 1);
            testField(f.getIndexX(), f.getIndexY(), -1, 1);
        }
        else {
            testField(f.getIndexX(), f.getIndexY(), 1, -1);
            testField(f.getIndexX(), f.getIndexY(), -1, -1);
        }
    }

    public void selectStone(Stone s) {
        if (move != null && move.getStone() == s) {
            gamePaneController.colorField();
            move = null;
            return;
        }
        move = new Move(s);
        Field f = control.playingField.getField(move.getStone().getIndexX(), move.getStone().getIndexY());
        move.addEnterField(f);
        possibleFields.clear();
        testFieldScope(f, s.getColor());
        gamePaneController.highlightFields(possibleFields, move);
    }

    public void selectField(Field f) {
        if (move != null) {
            if (!possibleFields.isEmpty() && possibleFields.contains(f)) {
                move.addEnterField(f);
                if (Math.abs(move.getLastField().getIndexX() - f.getIndexX()) >= 2) {
                    int x,y;
                    for (int i = 1; i < Math.abs(move.getLastField().getIndexX() - f.getIndexX()); i++) {
                        if (f.getIndexX() > move.getLastField().getIndexX()) {
                            x = move.getLastField().getIndexX() + i;
                        }
                        else {
                            x = move.getLastField().getIndexX() - i;
                        }
                        if (f.getIndexY() > move.getLastField().getIndexY()) {
                            y = move.getLastField().getIndexY() + i;
                        }
                        else {
                            y = move.getLastField().getIndexY() - i;
                        }
                        if (playerController.getOtherPlayer().hasStoneAt(x, y)) {
                            move.addSkipField(control.playingField.getField(x, y));

                            gamePaneController.colorField();
                            possibleFields.clear();
                            testFieldScope(move.getEndField(), move.getStone().getColor());
                            if (!possibleFields.isEmpty()) {
                                gamePaneController.highlightFields(possibleFields, move);
                                return;
                            }
                        }
                    }
                }
                gamePaneController.colorField();
                gamePaneController.moveToken(move);
                move.update();
                move = null;
                possibleFields.clear();
                playerController.changePlayer();
            }
        }
    }



}