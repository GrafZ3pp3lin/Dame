package Model;

public class Player {
    private Stone cStone[] = {};
    private String cName;
    private Color cColor;

    public Player(Color c, String name){
        cName = name;
        cColor = c;
    }

    public Stone[] createStones(int size){
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
        return cStone;
    }
    public void replaceStone (int indexStone, Color c, int x, int y, boolean superD){
        cStone[indexStone] = new Stone (cColor, x, y, superD);
    }
    public void deleteStone (int indexStone, Color c){
        cStone[indexStone] = null;
    }
    public Stone[] getArray(){
        return cStone;
    }
    public String getName(){
        return cName;
    }
}
