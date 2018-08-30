package controller;

import Model.*;

public class PlayerController {

    private Player player1;
    private Player player2;
    private boolean currentPlayer1;
    private boolean singlePlayerGame;

    public PlayerController() {}

    public PlayerController(boolean ki, int size, String name1, String name2) {
        init(ki, size, name1, name2);
    }

    public void init(boolean ki, int size, String name1, String name2) {
        singlePlayerGame = ki;
        player1 = new Player(Color.BLACK, name1.isEmpty() || name1.length() > 15 ? "Player 1" : name1, size);
        if (!ki) {
            player2 = new Player(Color.WHITE, name2.isEmpty() || name2.length() > 15 ? "Player 2" : name2, size);
        }
        else{
            player2 = new KI(Color.WHITE, name2.isEmpty() || name2.length() > 15 ? "KI" : name2, size, player1);
        }
        currentPlayer1 = true;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getPlayerByColor(Color c) {
        if (player1.getColor() == c) {
            return player1;
        }
        else {
            return player2;
        }
    }

    //return current Player
    public Player getCurrentPlayer() {
        if (currentPlayer1) {
            return player1;
        }
        else {
            return player2;
        }
    }

    public Player getOtherPlayer() {
        if (!currentPlayer1) {
            return player1;
        }
        else {
            return player2;
        }
    }

    public void changePlayer() {
        currentPlayer1 = !currentPlayer1;
    }

    public boolean isSinglePlayerGame() {
        return singlePlayerGame;
    }

    public boolean isCurrentPlayer1() {
        return currentPlayer1;
    }

}
