package Model;

import javafx.scene.Node;

/**
 * speichert Daten eines Spielers.
 * Dazu gehören alle Steine des Spielers, die Farbe der Steine und den Namen.
 *
 * @author Mareike Giek
 */
public class Player {

    /**
     * alle Steine die dem Spieler gehören.
     */
    private Stone cStone[];

    /**
     * Name des Spielers.
     */
    private String cName;

    /**
     * Farbe der Steine des Spielers
     */
    private Color cColor;

    /**
     * Basic Constructor
     */
    public Player() {}

    /**
     * Constructor setzt Farbe und Namen des Spielers.
     *
     * @param c Farbe der Steine.
     * @param name Name des Spielers.
     */
    public Player(Color c, String name) {
        cName = name;
        cColor = c;
    }

    /**
     * Constructor setzt Farbe und Namen und generiert gleich alle Steine des Spielers.
     *
     * @param c Farbe der Steine.
     * @param name Name des Spielers.
     * @param size Größe des Spielfelds, um alle Steine zu generieren.
     */
    public Player(Color c, String name, int size) {
        this(c, name);
        createStones(size);
    }

    /**
     * initialisiert den Spieler mit Farbe, Namen und Steinen.
     *
     * @param name Name des Spielers.
     * @param size Größe des Spielfelds, um alle Steine zu generieren.
     * @param c Farbe der Steine.
     */
    private void init(String name, int size, Color c) {
        cName = name;
        cColor = c;
        createStones(size);
    }

    /**
     * erstellt ein Spielfeld aus weißen und schwarzen Steinen der angegebenen Größe.
     * Anzahl der Spielsteine berechnen, da von Feldgröße abhängig.
     *
     * @param size Größe des Spielfelds
     */
    private void createStones(int size) {
        int x, y;
        if (cColor == Color.BLACK){
            x = 0;
            y = 0;
        }
        else {
            y = size - (size / 2 - 1);
            if (y % 2 != 0) {
                x = 1;
            }
            else {
                x = 0;
            }
        }
        int f = (int)((((double)size/4) - 0.5) * size);            //Anzahl Spielsteine pro Spieler abhängig von Feldgröße
        cStone = new Stone[f];
        for (int i = 0; i < f; i++){
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

    /**
     * setzt Stein auf andere Stelle des Spielfeldes.
     *
     * @param indexStone Nummer des Steins
     * @param x x-Koordinate Stein
     * @param y y-Koordinate Stein
     */
    public void replaceStone (int indexStone, int x, int y){
        cStone[indexStone].setIndexX(x);
        cStone[indexStone].setIndexY(y);
    }

    public Stone[] getStones() {
        return cStone;
    }

    /**
     * Berechnet wie viele Steine noch nicht geschlagen wurden.
     *
     * @return Anzahl noch aktiver nicht geschlagener Steine
     */
    public int getActiveStones() {
        int value = 0;
        for (Stone s : getStones()) {
            if (!s.isEliminated()) {
                value++;
            }
        }
        return value;
    }

    public String getName(){
        return cName;
    }

    public Color getColor() {
        return cColor;
    }

    public Color getEnemyColor(){
        if (cColor.equals(Color.BLACK)){
            return Color.WHITE;
        }
        return Color.BLACK;
    }

    /**
     * Sucht den passenden Stein, zur gegklickten Node.
     *
     * @param n angeklickter Circle
     * @return Stein zu der geglickten Node
     */
    public Stone getStoneOfClickedCircle(Node n) {
        for (Stone s : cStone) {
            if (s.getcCirc().equals(n)) {
                return s;
            }
        }
        return null;
    }

    /**
     * ermittelt ob der Spieler einen Stein mit den passenden Koordinaten hat.
     *
     * @param x x-Koordinate.
     * @param y y-Koordinate.
     * @return {@code true}, wenn der Spieler einen Stein mit diesen Koordinaten hat.
     */
    public boolean hasStoneAt(int x, int y) {
        for (Stone s : cStone) {
            if (s.getIndexX() == x && s.getIndexY() == y && !s.isEliminated()) {
                return true;
            }
        }
        return false;
    }

    /**
     * gibt den passenden Stein mit den Koordinaten zurück.
     *
     * @param x x-Koordinate.
     * @param y y-Koordinate.
     * @return Stein mit den passenden Koordinaten.
     * @see #hasStoneAt(int, int)
     */
    public Stone getStoneAt(int x, int y) {
        for (Stone s : cStone) {
            if (s.getIndexX() == x && s.getIndexY() == y && !s.isEliminated()) {
                return s;
            }
        }
        return null;
    }



}
