package controller;

import Model.*;

public class PlayerController {

    private Player player1;
    private Player player2;
    private boolean currentPlayer1;
    private boolean singlePlayerGame;
    private Main main;

    public PlayerController(boolean ki, int size, Main main) {
        singlePlayerGame = ki;
        this.main = main;
        player1 = new Player(Color.BLACK, "Spieler 1", size);
        if (!ki) {
            player2 = new Player(Color.WHITE, "Spieler 2", size);
        }
        else{
            player2 = new KI(Color.WHITE, "Spieler 2", size, player1, main.playingField);
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
