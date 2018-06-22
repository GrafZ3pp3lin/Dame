package Model;

public class Field {

    private Color cColor;
    private Boolean Stone[] = new Boolean [2];
    private int indexX, indexY;

    public Field(Color c, int x, int y){
        cColor = c;
        indexX = x;
        indexY = y;
    }

    public void setStone(Boolean player){
        Stone[0] = true;
        Stone[1] = player;
    }
    public Boolean[] getStone(){
        return Stone;
    }
}
