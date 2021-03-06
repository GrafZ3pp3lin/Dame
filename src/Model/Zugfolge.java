package Model;

import java.util.*;

/**
 * @author Joel Schmid
 */
public class Zugfolge extends Move{
    private int zuglaenge;

    public Zugfolge(int zuglaenge, Stone s){
        super(s);
        this.zuglaenge = zuglaenge;
    }

    private int getZuglaenge(){return zuglaenge;}

    /**
     * gibt grundlegende Informationen zu einer Zugfolge.
     */
    public String toString(){
        return "laenge: " + zuglaenge + "; vonPos: " + super.getFirstField().getIndexX() + ", " + super.getFirstField().getIndexY() + " nachPos: " + super.getEndField().getIndexX() + ", " + super.getEndField().getIndexY();
    }

    /**
     * gibt detaillierte Informationen über eine Zugfolge aus.
     */
    public void print(){
        System.out.println("Entered Fields:");
        for(Field f : getEnteredFields()){
            System.out.println(f.getIndexX() + ", " + f.getIndexY());
        }
        System.out.println("Skipped Fields:");
        for(Field f : getSkipedFields()){
            System.out.println(f.getIndexX() + ", " + f.getIndexY());
        }
        System.out.println(toString());
    }


    /**
     * Aus einer Liste mit möglichen Zügen wird ein zufälliger Zug mit maximaler länge zurückgegeben.
     *
     * @param zuege Liste mit möglichen Zügen.
     * @return zufälliger Zug mit maximaler länge.
     */
    public static Zugfolge getBestZug(List<Zugfolge> zuege){
        if(zuege != null) {
            if (zuege.size() > 0) {
                //die längsten Zugmöglichkeiten werden herausgesucht und davon ein zufälliger Zug gewählt
                int max = zuege.get(0).getZuglaenge();
                List<Zugfolge> longZuege = new ArrayList<>();

                for (Zugfolge z : zuege) {
                    if (max < z.getZuglaenge()) {
                        max = z.getZuglaenge();
                    }
                }
                for (Zugfolge z : zuege) {
                    if (max == z.getZuglaenge()) {
                        longZuege.add(z);
                    }
                }
                return longZuege.get(new Random().nextInt(longZuege.size()));
            }
        }
        return null;
    }


}