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
            KI(s, s.getIndexX(), s.getIndexY(), 0, true, new ArrayList<Stone>());
        }
/*
        for(Zugfolge z: alleZuege){
            System.out.println(z.toString());
        }
        System.out.println(alleZuege.size());
*/
        return alleZuege.get(new Random().nextInt(alleZuege.size()));
    }


    private int diagonalcheck(Direction d, int x, int y, boolean ersterDurchgang, List<Stone>rausgeschmissen){
        if(d.equals(Direction.LEFT)) {
            if(enemy.hasStoneAt(x+1, y-1) && !(enemy.hasStoneAt(x+2, y-2) || hasStoneAt(x+2, y-2))){
                rausgeschmissen.add(enemy.getStoneAt(x+1,y-1));
                return 2;
            }
            if(ersterDurchgang && playingField.isPositionInsideField(x+1, y-1) && !(enemy.hasStoneAt(x+1, y-1) || hasStoneAt(x+1, y-1))){
                return 1;
            }
            return 0;
        }

        else if(d.equals(Direction.RIGHT)) {
            if(enemy.hasStoneAt(x-1, y-1) && !(enemy.hasStoneAt(x-2, y-2) || hasStoneAt(x-2, y-2))){
                rausgeschmissen.add(enemy.getStoneAt(x-1,y-1));
                return 2;
            }
            if(ersterDurchgang && playingField.isPositionInsideField(x-1, y-1) && !(enemy.hasStoneAt(x-1, y-1) || hasStoneAt(x-1, y-1))){
                return 1;
            }
            return 0;
        }
        return 0;
    }

    private void KI(Stone s, int x, int y, int zuglaenge, boolean ersterDurchgang, List<Stone> rausgeschmissen){
        int a;
        if((a = diagonalcheck(Direction.LEFT, x, y, ersterDurchgang, rausgeschmissen)) > 1){
            KI(s,x+a, y-a, zuglaenge + a, false, new ArrayList<Stone>(rausgeschmissen));
        }
        else{
            if(zuglaenge+a > 0){
                alleZuege.add(new Zugfolge(rausgeschmissen, zuglaenge + a, s, x+a, y-a));
            }
        }

        if((a = diagonalcheck(Direction.RIGHT, x, y, ersterDurchgang, rausgeschmissen)) > 1){
            KI(s,x-a, y-a, zuglaenge + a, false, new ArrayList<Stone>(rausgeschmissen));
        }
        else{
            if(zuglaenge+a > 0){
                alleZuege.add(new Zugfolge(rausgeschmissen, zuglaenge + a, s, x-a, y-a));
            }
        }
    }
}
