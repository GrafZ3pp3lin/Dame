package controller;

import Model.*;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Main control;
    private GamePaneController gamePaneController;
    private PlayerController playerController;

    private List<Field> possibleFields;
    private List<Field> visitedFields;
    private Move move;
    private Field currentField;

    public Game(Main control, GamePaneController gamePaneController, PlayerController playerController) {
        this.control = control;
        this.gamePaneController = gamePaneController;
        this.playerController = playerController;
        possibleFields = new ArrayList<>();
        visitedFields = new ArrayList<>();
        move = new Move();
    }

    /**
     *  Leert die Liste möglicher Zielfelder und setzt den Zug auf veraltet
     */
    public void reset() {
        possibleFields.clear();
        move.setOutdated(true);
    }

    private boolean emptyField(Field f) {
        return !playerController.getCurrentPlayer().hasStoneAt(f.getIndexX(), f.getIndexY()) &&
                !playerController.getOtherPlayer().hasStoneAt(f.getIndexX(), f.getIndexY());
    }

    /**
     * Überprüft für ein angegebenes Feld, ob darauf gesprungen werden kann
     * @param x x-Wert des Feldes auf dem der Stein liegt
     * @param y y-Wert des Feldes auf dem der Stein liegt
     * @param indexX Differenz zwischen x und dem x-Wert des zu überprüfenden Feldes
     * @param indexY Differenz zwischen y und dem y-Wert des zu überprüfenden Feldes
     * @param indexX2 Differenz zwischen x und dem x-Wert des Feldes hinter dem zu überprüfenden Feld
     * @param indexY2 Differenz zwischen y und dem y-Wert des Feldes hinter dem zu überprüfenden Feld
     * @param further gesetzt wenn nicht der erste Sprung
     * @return boolean Sprung möglich
     */
    private boolean testField(int x, int y, int indexX, int indexY, int indexX2, int indexY2, boolean further) {
        Field field = Main.playingField.getField(x + indexX, y + indexY);
        Field field2;
        if (field != null) {
            if (emptyField(field) && !further) {
                possibleFields.add(field);
                return true;
            }
            else if (playerController.getOtherPlayer().hasStoneAt(field.getIndexX(), field.getIndexY())) {
                field2 = Main.playingField.getField(x + indexX2, y + indexY2);
                if(!visitedFields.contains(field)) {
                    if (field2 != null && emptyField(field2) || field2 != null && move.getFirstField() == field2) {
                        possibleFields.add(field2);
                        currentField = field2;
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Überprüft abhängig vom ausgewählten Stein, auf welche Felder gesprungen werden kann
     * @param f ausgewähltes Feld
     * @param c Farbe des aktuellen Steins
     * @param further gesetzt wenn nicht der erste Sprung
     * @param superDame aktueller Stein ist Superdame
     */
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

    /**
     * Führt eine passende Option für den angeklickten Stein aus
     * @param s angeklickter Stein
     */
    public void selectStone(Stone s) {
        if (move != null && move.getStone() == s && !move.isOutdated() && Main.playingField.getField(s.getIndexX(), s.getIndexY()) != currentField) {
            gamePaneController.colorField();
            move.setOutdated(true);
            visitedFields.clear();
            currentField = null;
            return;
        }
        if(currentField == null || Main.playingField.getField(s.getIndexX(), s.getIndexY()) != currentField) {
            move.init(s);
            Field f = Main.playingField.getField(move.getStone().getIndexX(), move.getStone().getIndexY());
            move.addEnterField(f);
            possibleFields.clear();
            testFieldScope(f, s.getColor(), false, s.isSuperDame());
            gamePaneController.highlightFields(possibleFields, move);
        }
        else  if (move != null && !move.isOutdated()) {
            Field f = Main.playingField.getField(s.getIndexX(), s.getIndexY());
            selectField(f);
        }
    }

    /**
     * Führt eine passende Aktion für das angeklickte Feld aus
     * @param f angeklicktes Feld
     */
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
                            move.addSkipField(Main.playingField.getField(x, y));

                            visitedFields.add(Main.playingField.getField(x, y));
                            gamePaneController.colorField();
                            possibleFields.clear();

                            testFieldScope(move.getEndField(), move.getStone().getColor(), true, move.getStone().isSuperDame());
                            if (!possibleFields.isEmpty()) {
                                gamePaneController.highlightFields(possibleFields, move);
                                return;
                            }
                        }
                    }
                }
                currentField = null;
                visitedFields.clear();
                makeMove(move);
            }
            else if (move.getEndField().equals(f) && move.getEndField() != move.getFirstField()) {
                makeMove(move);
            }
            else if (playerController.getCurrentPlayer().hasStoneAt(f.getIndexX(), f.getIndexY()) && currentField == null) {
                selectStone(playerController.getCurrentPlayer().getStoneAt(f.getIndexX(), f.getIndexY()));
            }
            else if(currentField != null){
                currentField = null;
                visitedFields.clear();
                possibleFields.clear();
                move.setOutdated(true);
                selectStone(playerController.getCurrentPlayer().getStoneAt(f.getIndexX(), f.getIndexY()));
            }
        }
        else if (playerController.getCurrentPlayer().hasStoneAt(f.getIndexX(), f.getIndexY())) {
            selectStone(playerController.getCurrentPlayer().getStoneAt(f.getIndexX(), f.getIndexY()));
        }
    }

    /**
     * Bewegt den Stein anhand des ausgewählten Zuges und überprüft, ob der Stein anschließend eine Superdame wird
     * @param move Ausgewählter Zug, der mit dem Stein durchgeführt wird
     */
    private void makeMove(Move move) {
        gamePaneController.colorField();
        gamePaneController.moveToken(move);
        move.update();
        testForSuperDame(move.getStone());
        possibleFields.clear();
    }

    /**
     * Überprüft nach Beenden des Zuges, ob ein Spieler gewonnen hat und wechselt den aktuellen Spieler, wenn dies nicht der Fall ist
     */
    public void finishedMove() {
        if (!testForWinner()) {
            move.setOutdated(true);
            playerController.changePlayer();
            gamePaneController.updatePlayer();
            playKI();
        }
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

    private boolean testForWinner() {
        if (!isMovePossible(playerController.getOtherPlayer())) {
            control.winDialog(playerController.getCurrentPlayer().getName());
            return true;
        }
        return false;
    }

    /**
     * Überprüft für alle Steine eines Spielers, ob weitere Züge möglich sind
     * @param p Spiler dessen Steine geprüft werden
     * @return boolean, ob ein Zug möglich ist
     */
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

    /**
     *  Lässt im Falle eines Singleplayer-Spiels die KI ihren Zug machen, falls einer möglich ist
     */
    private void playKI() {
        if(playerController.isSinglePlayerGame() && !playerController.isCurrentPlayer1()) {
            try {
                Move m = ((KI) playerController.getPlayer2()).getBestMove();
                makeMove(m);
            } catch (NoPossibleMoveException e) {
                control.winDialog(playerController.getPlayer1().getName());
            }

        }
    }

}
