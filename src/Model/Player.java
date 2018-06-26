package Model;

public class Player {

    public Stone[] createStones(int size, Color c){
        Stone cStone[] = {};
        int x, y;
        int strtFirst = 3 + ((size - 8)/2);
        if (c == Color.BLACK){
            x = 0;
            y = 0;
        }
        else {
            x = 0;
            y = strtFirst;
        }
        int f = (int)(((size/4)-0.5)* size);            //Anzahl Spielsteine pro Spieler abhängig von Feldgröße
        for (int i=0; i< f; i++){
            cStone[i] = new Stone(c, x, y);
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
        return cStone;
    }
    public void replaceStone (){

    }
}
