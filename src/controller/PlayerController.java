package controller;

import Model.Color;
import Model.Player;

public class PlayerController {

    private Player player1;
    private Player player2;

    public PlayerController(boolean ki, int size) {
        player1 = new Player(Color.BLACK, "Spieler 1");
        player1.createStones(size);
        if (!ki) {
            player2 = new Player(Color.WHITE, "Spieler 2");
            player2.createStones(size);
        }
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
        return null;
    }

}
