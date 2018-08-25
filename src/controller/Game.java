package controller;

import Model.*;

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
    private boolean testField(int x, int y, int indexX, int indexY, boolean further) {
        Field field = control.playingField.getField(x + indexX, y + indexY);
        if (field != null) {
            if (emptyField(field) && !further) {
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
    private void testFieldScope(Field f, Color c, boolean further) {
        if (c == Color.BLACK) {
            testField(f.getIndexX(), f.getIndexY(), 1, 1, further);
            testField(f.getIndexX(), f.getIndexY(), -1, 1, further);
        }
        else {
            testField(f.getIndexX(), f.getIndexY(), 1, -1, further);
            testField(f.getIndexX(), f.getIndexY(), -1, -1, further);
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
        testFieldScope(f, s.getColor(), false);
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
                            testFieldScope(move.getEndField(), move.getStone().getColor(), true);
                            if (!possibleFields.isEmpty()) {
                                gamePaneController.highlightFields(possibleFields, move);
                                return;
                            }
                        }
                    }
                }
                makeMove(move);
            }
            else if (move.getEndField().equals(f)) {
                makeMove(move);
            }
        }
        else if (playerController.getCurrentPlayer().hasStoneAt(f.getIndexX(), f.getIndexY())) {
            selectStone(playerController.getCurrentPlayer().getStoneAt(f.getIndexX(), f.getIndexY()));
        }
    }

    private void makeMove(Move move) {
        gamePaneController.colorField();
        gamePaneController.moveToken(move);
        move.update();
        move = null;
        possibleFields.clear();
    }

    public void playKI(){
        if(control.getPlayerController().isSinglePlayerGame() && !control.getPlayerController().isCurrentPlayer1()){
            Zugfolge z = ((KI) control.getPlayerController().getPlayer2()).KI();
            System.out.println(z);
            control.getGamePaneController().moveToken(z);
            z.update();

            control.getPlayerController().changePlayer();
        }
    }

}
