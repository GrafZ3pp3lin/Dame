package Model;

import  java.util.List;
/**
 * Created by joels on 24.08.2018.
 */
public class Zugfolge {
    private List<Stone> rausgeschmissen;
    private int zuglaenge;
    private Stone s;
    private int endX;
    private int endY;

    public Zugfolge(List<Stone> rausgeschmissen, int zuglaenge, Stone s, int endX, int endY){
        this.rausgeschmissen = rausgeschmissen;
        this.zuglaenge = zuglaenge;
        this.s = s;
        this.endX = endX;
        this.endY = endY;
    }

    public int getZuglaenge(){return zuglaenge;}
}
