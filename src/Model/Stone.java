package Model;

import javafx.scene.shape.Circle;

public class Stone {
    private int indexX , indexY;
    private Color cColor;
    private boolean superDame;
    private Circle cCirc;

    public Stone(Color c, int x, int y, boolean superD){
        cColor = c;
        indexX = x;
        indexY = y;
        superDame =superD;
    }

    public Circle getcCirc(){
        return cCirc;
    }
    public Color getColor(){
        return cColor;
    }
    public boolean getSuperDame(){
        return superDame;
    }
    public void setSuperDame(){
        superDame = true;
    }
    public int getIndexX(){
        return indexX;
    }
    public int getIndexY(){
        return indexY;
    }
    public void setIndexX(int x){
        indexX = x;
    }
    public void setIndexY(int y){
        indexY = y;
    }
}
