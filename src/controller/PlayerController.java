package controller;

import Model.Color;
import Model.Player;

public class PlayerController {

    private Player player1;
    private Player player2;

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

    public Player getCurrentPlayer() {
        return null;
    }

}
