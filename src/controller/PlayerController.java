package controller;

import Model.Color;
import Model.Player;

public class PlayerController {

    private Player player1;
    private Player player2;
    private boolean currentPlayer1;

    public PlayerController(boolean ki, int size) {
        player1 = new Player(Color.BLACK, "Spieler 1", size);
        if (!ki) {
            player2 = new Player(Color.WHITE, "Spieler 2", size);
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

}
