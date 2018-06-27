package Model;

public class Field {

    private Color cColor;
    private Boolean Stone[] = new Boolean [3];          //1. Stein gesetzt, 2. welcher Spieler, 3. Superdame ja/nein
    private int indexX, indexY;

    public Field(Color c, int x, int y){
        cColor = c;
        indexX = x;
        indexY = y;
    }

    public void setStone(Boolean player, Boolean superDame){
        Stone[0] = true;
        Stone[1] = player;
        Stone[2] = superDame;
    }
    public Boolean[] getStone(){
        return Stone;
    }
}
