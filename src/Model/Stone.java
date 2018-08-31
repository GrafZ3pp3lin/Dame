package Model;

import javafx.scene.Node;
import javafx.scene.shape.Circle;

/**
 * speichert Daten für einen Stein.
 * Stein besteht aus Farbe, Koordinaten,
 * superDame (ja/nein), Aktivität (eleminiert ja/nein),
 * und Circle.
 *
 * @author Mareike Giek
 */
public class Stone {

    private int indexX , indexY;
    private boolean eliminated;
    private Color cColor;
    private boolean superDame;
    private Node cCirc;

    public Stone(Color c, int x, int y, boolean superD){
        cColor = c;
        indexX = x;
        indexY = y;
        superDame = superD;
        eliminated = false;
        cCirc = new Circle();
    }

    public Node getcCirc(){
        return cCirc;
    }

    /**
     * Attribut Circle wird zu übergebener NODE.
     * wird benötigt, um die Superdame grafisch darstellen zu können, da sich bei einer Superdame der Spielstein unterscheidet.
     *
     * @param n Neuer grafischer Spielstein
     */
    public void changeNode(Node n) {
        cCirc = n;
    }
    public Color getColor(){
        return cColor;
    }
    public boolean isSuperDame(){
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

    public boolean isEliminated() {
        return eliminated;
    }

    public void setEliminated() {
        eliminated = true;
    }

}
