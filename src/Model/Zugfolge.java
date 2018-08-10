package Model;

/**
 * Created by joel schmid on 10.08.2018.
 */

public class Zugfolge {
    //Diese Attribute enthalten den Stein er bewegt wird und die Zielkoordinaten des von der KI ermittelten Spielzugs
    private Stone begin;
    private int endX;
    private int endY;

    public Zugfolge(Stone begin, int endX, int endY){
        this.begin = begin;
        this.endX = endX;
        this.endY = endY;
    }
}
