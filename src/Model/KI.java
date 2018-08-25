package Model;

import controller.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by joel schmid on 10.08.2018.
 */
public class KI extends Player {

    private Player enemy;
    private List<Zugfolge> alleZuege;

    public KI(Color c, String name){
        super(c, name);
    }

    public KI(Color c, String name, int size, Player enemy) {
        this(c, name);
        createStones(size);
        this.enemy = enemy;
    }

    public Zugfolge KI(){
        alleZuege = new ArrayList<>();

        for (Stone s : getArray()) {
            KI(s, s.getIndexX(), s.getIndexY(), 0, true, new ArrayList<Field>(), new ArrayList<Field>());
        }

        for(Zugfolge z: alleZuege){
            System.out.println(z.toString());
        }
        System.out.println("Anzahl aller Züge: " + alleZuege.size());
        Zugfolge gewehlt = alleZuege.get(new Random().nextInt(alleZuege.size()));
        System.out.println("Ausgewählter Zug: " + gewehlt.toString());
        System.out.println("---------------------------------------------------------------------------");
        gewehlt.print();
        return gewehlt;
    }


    private int diagonalcheck(Direction d, int x, int y, boolean ersterDurchgang, List<Field>skipped, List<Field>entered){
        // Überprüft die möglichen Schläge von KI seite aus links (von Spielerseite aus rechts)
        if(d.equals(Direction.LEFT)) {
            if(Main.playingField.isPositionInsideField(x+2, y-2) && Main.playingField.isPositionInsideField(x+1, y-1) && enemy.hasStoneAt(x+1, y-1) && !(enemy.hasStoneAt(x+2, y-2) || hasStoneAt(x+2, y-2))){
                skipped.add(Main.playingField.getField(x+1,y-1));
                entered.add(Main.playingField.getField(x+2,y-2));
                return 2;
            }
            if(ersterDurchgang && Main.playingField.isPositionInsideField(x+1, y-1) && !(enemy.hasStoneAt(x+1, y-1) || hasStoneAt(x+1, y-1))){
                entered.add(Main.playingField.getField(x+1,y-1));
                return 1;
            }
            return 0;
        }

        else if(d.equals(Direction.RIGHT)) {
            if(Main.playingField.isPositionInsideField(x-2, y-2) && Main.playingField.isPositionInsideField(x-1, y-1) && enemy.hasStoneAt(x-1, y-1) && !(enemy.hasStoneAt(x-2, y-2) || hasStoneAt(x-2, y-2))){
                skipped.add(Main.playingField.getField(x-1,y-1));
                entered.add(Main.playingField.getField(x-2,y-2));
                return 2;
            }
            if(ersterDurchgang && Main.playingField.isPositionInsideField(x-1, y-1) && !(enemy.hasStoneAt(x-1, y-1) || hasStoneAt(x-1, y-1))){
                entered.add(Main.playingField.getField(x-1,y-1));
                return 1;
            }
            return 0;
        }
        return 0;
    }

    private void KI(Stone s, int x, int y, int zuglaenge, boolean ersterDurchgang, List<Field> skipped, List<Field> entered){

        if (ersterDurchgang){
            entered.add(Main.playingField.getField(x,y));
        }

        // kopien für schläge nach rechts und links werden erstellt, damit sich diese nicht durcheinandermischen
        List<Field> skippedLeft = new ArrayList<>(skipped);
        List<Field> enteredLeft = new ArrayList<>(entered);

        List<Field> skippedRight = new ArrayList<>(skipped);
        List<Field> enteredRight = new ArrayList<>(entered);

        int a;
        if((a = diagonalcheck(Direction.LEFT, x, y, ersterDurchgang, skippedLeft, enteredLeft)) > 1){
            //KI wird rekursiv aufgerufen, wenn ein gegnerischer Stein übersprungen werden kann
            KI(s,x+a, y-a, zuglaenge + a, false, new ArrayList<Field>(skippedLeft),new ArrayList<Field>(enteredLeft));
        }
        else{
            if(zuglaenge+a > 0){
                Zugfolge z = new Zugfolge(zuglaenge + a, s);
                z.addEnterField(enteredLeft);
                z.addSkipField(skippedLeft);
                alleZuege.add(z);
            }
        }

        if((a = diagonalcheck(Direction.RIGHT, x, y, ersterDurchgang, skippedRight, enteredRight)) > 1){
            KI(s,x-a, y-a, zuglaenge + a, false, new ArrayList<Field>(skippedRight),new ArrayList<Field>(enteredRight));
        }
        else{
            if(zuglaenge+a > 0){
                Zugfolge z = new Zugfolge(zuglaenge + a, s);
                z.addEnterField(enteredRight);
                z.addSkipField(skippedRight);
                alleZuege.add(z);
            }
        }
    }
}
