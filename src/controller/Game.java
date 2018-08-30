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
    //SuperDame kann beliebig viele Schritte gehen
    private boolean testField(int x, int y, int indexX, int indexY, boolean further) {
        Field field = control.playingField.getField(x + indexX, y + indexY);
        if (field != null) {
            if (Math.abs(indexX) == 1 || further) {
                if (emptyField(field) && !further) {
                    possibleFields.add(field);
                    return true;
                } else {
                    if (playerController.getOtherPlayer().hasStoneAt(field.getIndexX(), field.getIndexY())) {
                        Field nextField = control.playingField.getField(x + indexX * 2, y + indexY * 2);
                        if (nextField != null && (emptyField(nextField) || (nextField == move.getFirstField() && further))) {
                            possibleFields.add(nextField);
                            return true;
                        }
                    }
                }
            } else {
                if(emptyField(field)) {
                    for (int prev = 1; prev < Math.abs(indexX); prev++) {
                        if (playerController.getCurrentPlayer().hasStoneAt(x + prev * (indexX / Math.abs(indexX)), y + prev * (indexY / Math.abs(indexY)))) {
                            return false;
                        }
                    }
                    possibleFields.add(field);
                    return true;
                } else if (playerController.getOtherPlayer().hasStoneAt(field.getIndexX(), field.getIndexY())) {
                    Field nextField = control.playingField.getField(x + indexX + (indexX / Math.abs(indexX)), y + indexY + (indexY / Math.abs(indexY)));
                    Field prevField = control.playingField.getField(x + indexX - (indexX / Math.abs(indexX)), y + indexY - (indexY / Math.abs(indexY)));
                    if (playerController.getOtherPlayer().hasStoneAt(prevField.getIndexX(), prevField.getIndexY())) {
                        return false;
                    } else if (nextField != null && emptyField(nextField)) {
                        possibleFields.add(nextField);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //TODO SuperDame
    //SuperDame kann in alle vier richtungen gehen
    private void testFieldScope(Stone s, Field f, boolean further) {
        if (s.isSuperDame() == false) {
            if (s.getColor() == Color.BLACK) {
                testField(f.getIndexX(), f.getIndexY(), 1, 1, further);
                testField(f.getIndexX(), f.getIndexY(), -1, 1, further);
            } else {
                testField(f.getIndexX(), f.getIndexY(), 1, -1, further);
                testField(f.getIndexX(), f.getIndexY(), -1, -1, further);
            }
        } else{
            for(int i = 1; i < control.playingField.getSize(); i++) {
                if (further == false) {
                    testField(f.getIndexX(), f.getIndexY(), i, i, further);
                    testField(f.getIndexX(), f.getIndexY(), i, -i, further);
                    testField(f.getIndexX(), f.getIndexY(), -i, i, further);
                    testField(f.getIndexX(), f.getIndexY(), -i, -i, further);
                } else {
                    if(f.getIndexX() - move.getLastField().getIndexX() > 0){
                        if(f.getIndexY() - move.getLastField().getIndexY() > 0){
                            testField(f.getIndexX(), f.getIndexY(), i, i, further);
                            testField(f.getIndexX(), f.getIndexY(), i, -i, further);
                            testField(f.getIndexX(), f.getIndexY(), -i, i, further);
                        } else {
                            testField(f.getIndexX(), f.getIndexY(), i, i, further);
                            testField(f.getIndexX(), f.getIndexY(), i, -i, further);
                            testField(f.getIndexX(), f.getIndexY(), -i, -i, further);
                        }
                    } else {
                        if(f.getIndexY() - move.getLastField().getIndexY() > 0){
                            testField(f.getIndexX(), f.getIndexY(), i, i, further);
                            testField(f.getIndexX(), f.getIndexY(), -i, i, further);
                            testField(f.getIndexX(), f.getIndexY(), -i, -i, further);
                        } else {
                            testField(f.getIndexX(), f.getIndexY(), i, -i, further);
                            testField(f.getIndexX(), f.getIndexY(), -i, i, further);
                            testField(f.getIndexX(), f.getIndexY(), -i, -i, further);
                        }
                    }
                }
            }
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
        testFieldScope(s, f, false);
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
                            testFieldScope(move.getStone(), move.getEndField(), true);
                            if (!possibleFields.isEmpty()) {
                                gamePaneController.highlightFields(possibleFields, move);
                                return;
                            }
                        }
                    }
                }
                makeMove(move);
            }
            else if (move.getEndField().equals(f) && move.getEndField() != move.getFirstField()) {
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
        //TODO alle übersprungenen Steine eliminieren (move.skippedFields) - wird bisher in GamePaneController gemacht - muss nicht zwingend geändert werden
        move.update();
        testForSuperDame(move.getStone());
        possibleFields.clear();
    }

    public void finishedMove() {
        testForWinner();
        move = null;
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
                testFieldScope(s, Main.playingField.getField(s.getIndexX(), s.getIndexY()), false);
                if (!possibleFields.isEmpty()) {
                    possibleFields.clear();
                    return true;
                }
            }
        }
        return false;
    }

    public void playKI(){
        if(control.getPlayerController().isSinglePlayerGame() && !control.getPlayerController().isCurrentPlayer1()) {
            Zugfolge z = ((KI) control.getPlayerController().getPlayer2()).KI();
            Platform.runLater(() -> makeMove(z));
        }
    }

}
