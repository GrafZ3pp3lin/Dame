package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by joel schmid on 10.08.2018.
 */
public class KI extends Player{

    private Player enemy;
    private List<Zugfolge> alleZuege;
    private PlayingField playingField;

    public KI(Color c, String name){
        super(c, name);
    }

    public KI(Color c, String name, int size, Player enemy, PlayingField playingField) {
        this(c, name);
        createStones(size);
        this.enemy = enemy;
        this.playingField = playingField;
    }

    public Zugfolge KI(){
        alleZuege = new ArrayList<>();

        for (Stone s : getArray()) {
            KI(s, s.getIndexX(), s.getIndexY(), 0, true, new ArrayList<Field>(), new ArrayList<Field>());
        }
/*
        for(Zugfolge z: alleZuege){
            System.out.println(z.toString());
        }
        System.out.println(alleZuege.size());
*/
        return alleZuege.get(new Random().nextInt(alleZuege.size()));
    }


    private int diagonalcheck(Direction d, int x, int y, boolean ersterDurchgang, List<Field>skipped){
        if(d.equals(Direction.LEFT)) {
            if(enemy.hasStoneAt(x+1, y-1) && !(enemy.hasStoneAt(x+2, y-2) || hasStoneAt(x+2, y-2))){
                skipped.add(playingField.getField(x+1,y-1));
                return 2;
            }
            if(ersterDurchgang && playingField.isPositionInsideField(x+1, y-1) && !(enemy.hasStoneAt(x+1, y-1) || hasStoneAt(x+1, y-1))){
                return 1;
            }
            return 0;
        }

        else if(d.equals(Direction.RIGHT)) {
            if(enemy.hasStoneAt(x-1, y-1) && !(enemy.hasStoneAt(x-2, y-2) || hasStoneAt(x-2, y-2))){
                skipped.add(playingField.getField(x-1,y-1));
                return 2;
            }
            if(ersterDurchgang && playingField.isPositionInsideField(x-1, y-1) && !(enemy.hasStoneAt(x-1, y-1) || hasStoneAt(x-1, y-1))){
                return 1;
            }
            return 0;
        }
        return 0;
    }

    private void KI(Stone s, int x, int y, int zuglaenge, boolean ersterDurchgang, List<Field> skipped, List<Field>entered){
        int a;
        if((a = diagonalcheck(Direction.LEFT, x, y, ersterDurchgang, skipped)) > 1){
            entered.add(playingField.getField(x+a,y-a));
            KI(s,x+a, y-a, zuglaenge + a, false, new ArrayList<Field>(skipped),new ArrayList<Field>(entered));
        }
        else{
            if(zuglaenge+a > 0){
                entered.add(playingField.getField(x+a,y-a));
                Zugfolge z = new Zugfolge(zuglaenge + a, s);
                z.addEnterField(entered);
                z.addSkipField(skipped);
                alleZuege.add(z);
            }
        }

        if((a = diagonalcheck(Direction.RIGHT, x, y, ersterDurchgang, skipped)) > 1){
            entered.add(playingField.getField(x+a,y-a));
            KI(s,x-a, y-a, zuglaenge + a, false, new ArrayList<Field>(skipped),new ArrayList<Field>(entered));
        }
        else{
            if(zuglaenge+a > 0){
                entered.add(playingField.getField(x+a,y-a));
                Zugfolge z = new Zugfolge(zuglaenge + a, s);
                z.addEnterField(entered);
                z.addSkipField(skipped);
                alleZuege.add(z);
            }
        }
    }
}
