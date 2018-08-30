package controller;

import Model.*;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Main control;
    private GamePaneController gamePaneController;
    private PlayerController playerController;

    private List<Field> possibleFields;
    private List<Field> visitedFields;
    private Move move;

    public Game(Main control, GamePaneController gamePaneController, PlayerController playerController) {
        this.control = control;
        this.gamePaneController = gamePaneController;
        this.playerController = playerController;
        possibleFields = new ArrayList<>();
        visitedFields = new ArrayList<>();
        move = new Move();
    }

    public void reset() {
        possibleFields.clear();
        move.setOutdated(true);
    }

    private boolean emptyField(Field f) {
        return !playerController.getCurrentPlayer().hasStoneAt(f.getIndexX(), f.getIndexY()) &&
                !playerController.getOtherPlayer().hasStoneAt(f.getIndexX(), f.getIndexY());
    }

    //SuperDame kann beliebig viele Schritte gehen
    private boolean testField(int x, int y, int indexX, int indexY, int indexX2, int indexY2, boolean further) {
        Field field = control.playingField.getField(x + indexX, y + indexY);
        Field field2;
        if (field != null) {
            if (emptyField(field) && !further) {
                possibleFields.add(field);
                return true;
            }
            else if (playerController.getOtherPlayer().hasStoneAt(field.getIndexX(), field.getIndexY())) {
                field2 = control.playingField.getField(x + indexX2, y + indexY2);
                if(!visitedFields.contains(field)) {
                    if (field2 != null && emptyField(field2)) {
                        possibleFields.add(field2);
                        return false;
                    }
                }
            }
        }
        return false;
    }

    //SuperDame kann in alle vier richtungen gehen
    private void testFieldScope(Field f, Color c, boolean further, boolean superDame) {
        if(superDame){
            for(int i = 1; i < Main.playingField.getSize(); i++) {
                if(!testField(f.getIndexX(), f.getIndexY(), i, i, i+1,i+1, further)){
                    break;
                }
            }
            for(int i = 1; i < Main.playingField.getSize(); i++) {
                if(!testField(f.getIndexX(), f.getIndexY(), i, -i,i+1,-i-1, further)){
                    break;
                }
            }
            for(int i = 1; i < Main.playingField.getSize(); i++) {
                if(!testField(f.getIndexX(), f.getIndexY(), -i, i,-i-1, i+1, further)){
                    break;
                }
            }
            for(int i = 1; i < Main.playingField.getSize(); i++) {
                if(!testField(f.getIndexX(), f.getIndexY(), -i, -i, -i-1, -i-1, further)){
                    break;
                }
            }
        }else {
            if (c == Color.BLACK) {
                testField(f.getIndexX(), f.getIndexY(), 1, 1,2,2, further);
                testField(f.getIndexX(), f.getIndexY(), -1, 1,-2,2, further);
            } else {
                testField(f.getIndexX(), f.getIndexY(), 1, -1,2,-2, further);
                testField(f.getIndexX(), f.getIndexY(), -1, -1,-2,-2, further);
            }
        }
    }

    public void selectStone(Stone s) {
        if (move != null && move.getStone() == s && !move.isOutdated()) {
            gamePaneController.colorField();
            move.setOutdated(true);
            return;
        }
        move.init(s);
        Field f = control.playingField.getField(move.getStone().getIndexX(), move.getStone().getIndexY());
        move.addEnterField(f);
        possibleFields.clear();
        testFieldScope(f, s.getColor(), false, s.isSuperDame());
        gamePaneController.highlightFields(possibleFields, visitedFields, move);
    }

    public void selectField(Field f) {
        if (move != null && !move.isOutdated()) {
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

                            visitedFields.add(control.playingField.getField(x, y));
                            gamePaneController.colorField();
                            possibleFields.clear();

                            testFieldScope(move.getEndField(), move.getStone().getColor(), true, move.getStone().isSuperDame());
                            if (!possibleFields.isEmpty()) {
                                gamePaneController.highlightFields(possibleFields, visitedFields, move);
                                return;
                            }
                        }
                    }
                }
                visitedFields.clear();
                makeMove(move);
            }
            else if (move.getEndField().equals(f) && move.getEndField() != move.getFirstField()) {
                makeMove(move);
            }
            else if (playerController.getCurrentPlayer().hasStoneAt(f.getIndexX(), f.getIndexY())) {
                selectStone(playerController.getCurrentPlayer().getStoneAt(f.getIndexX(), f.getIndexY()));
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
        testForSuperDame(move.getStone());
        possibleFields.clear();
    }

    public void finishedMove() {
        testForWinner();
        move.setOutdated(true);
        playerController.changePlayer();
        gamePaneController.updatePlayer();
        playKI();
    }

    private void testForSuperDame(Stone s) {
        if (s.getColor() == Color.BLACK) {
            if (s.getIndexY() == Main.playingField.getSize() - 1) {
                s.setSuperDame();
            }
        }
        else if (s.getIndexY() == 0) {
            s.setSuperDame();
        }
    }

    private void testForWinner() {
        if (playerController.getPlayer1().getActiveStones() == 0 || !isMovePossible(playerController.getPlayer1())) {
            System.out.println("Player2 Win");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    control.winDialog(playerController.getPlayer2().getName());
                }
            });
        }
        else if (playerController.getPlayer2().getActiveStones() == 0 || !isMovePossible(playerController.getPlayer2())) {
            System.out.println("Player1 Win");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    control.winDialog(playerController.getPlayer1().getName());

                }
            });
        }
    }

    private boolean isMovePossible(Player p) {
        for (Stone s : p.getStones()) {
            if (!s.isEliminated()) {
                testFieldScope(Main.playingField.getField(s.getIndexX(), s.getIndexY()), s.getColor(), false, s.isSuperDame());
                if (!possibleFields.isEmpty()) {
                    possibleFields.clear();
                    return true;
                }
            }
        }
        return false;
    }

    public void playKI() {
        if(playerController.isSinglePlayerGame() && !playerController.isCurrentPlayer1()) {

            try {
                Move m = ((KI) playerController.getPlayer2()).getBestMove();
                Platform.runLater(() -> makeMove(m));
            } catch (NoPossibleMoveException e) {
                //TODO KI hat verloren, da sie keine Züge mehr machen kann
            }

        }
    }

}
