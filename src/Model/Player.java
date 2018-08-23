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

    public Player(Color c, String name, int size) {
        this(c, name);
        createStones(size);
    }

    public void createStones(int size){
        cStone = new Stone[(int)(size * 1.5)];
        int x, y;
        if (cColor == Color.BLACK){
            x = 0;
            y = 0;
        }
        else {
            y = size - 3;
            if (y % 2 != 0) {
                x = 1;
            }
            else {
                x = 0;
            }
        }
        int f = (int)(((size/4)-0.5)* size);            //Anzahl Spielsteine pro Spieler abhängig von Feldgröße
        for (int i=0; i< f; i++){
            cStone[i] = new Stone(cColor, x, y, false);
            if (( x += 2) >= size){
                y += 1;
                if((y % 2) != 0){
                    x = 1;
                }
                else {
                    x = 0;
                }
            }
        }
    }

    public void replaceStone (int indexStone, int x, int y){
        cStone[indexStone].setIndexX(x);
        cStone[indexStone].setIndexY(y);
    }

//    public void deleteStone (int indexStone){
//        cStone[indexStone] = null;
//    }

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

    //returns the Stone Object to the clicked Circle
    public Stone getStoneOfClickedCircle(Circle c) {
        for (Stone s : cStone) {
            if (s.getcCirc().equals(c)) {
                return s;
            }
        }
        return null;
    }

    public boolean hasStoneAt(int x, int y) {
        for (Stone s : cStone) {
            if (s.getIndexX() == x && s.getIndexY() == y) {
                return true;
            }
        }
        return false;
    }

    public Stone getStoneAt(int x, int y) {
        for (Stone s : cStone) {
            if (s.getIndexX() == x && s.getIndexY() == y) {
                return s;
            }
        }
        return null;
    }

}
