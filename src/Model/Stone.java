package Model;

public class Stone {
    private int indexX , indexY;
    private Color cColor;
    private boolean superDame;

    public Stone(Color c, int x, int y, boolean superD){
        cColor = c;
        indexX = x;
        indexY = y;
        superDame =superD;
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
}
