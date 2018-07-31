package Model;

import javafx.scene.shape.Circle;

public class Player {

    private Stone cStone[];
    private String cName;
    private Color cColor;
    private int eliminatedStones;

    public Player(Color c, String name){
        cName = name;
        cColor = c;
    }

    public void createStones(int size){
        cStone = new Stone[size * size];
        int x, y;
        int strtFirst = 3 + ((size - 8)/2);
        if (cColor == Color.BLACK){
            x = 0;
            y = 0;
        }
        else {
            x = 0;
            y = strtFirst;
        }
        int f = (int)(((size/4)-0.5)* size);            //Anzahl Spielsteine pro Spieler abhängig von Feldgröße
        for (int i=0; i< f; i++){
            cStone[i] = new Stone(cColor, x, y, false);
            if (( x + 2) > size){
                if((y % 2) != 0){
                    x = 1;
                }
                else {
                    x = 0;
                }
                y = y + 1;
            }
        }
    }

    public void replaceStone (int indexStone, int x, int y){
        cStone[indexStone].setIndexX(x);
        cStone[indexStone].setIndexY(y);
    }

    public void deleteStone (int indexStone){
        cStone[indexStone] = null;
    }

    public Stone[] getArray(){
        return cStone;
    }

    public String getName(){
        return cName;
    }

    public Color getColor() {
        return cColor;
    }

    public int getEliminatedStones() {
        return eliminatedStones;
    }

    public void increaseEliminatedStones() {
        eliminatedStones++;
    }

    public void setEliminatedStones(int value) {
        eliminatedStones = value;
    }

    public Stone getStoneOfClickedCircle(Circle c) {
        //return the Stone to this Circle
        return null;
    }

}
