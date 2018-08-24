package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * Created by joel schmid on 10.08.2018.
 */
public class KI {

    private PlayingField playingField;
    private Player player;
    private HashMap<Stone, Zugfolge> alleZuege;

    public Zugfolge KI(PlayingField field, Player p){

        this.playingField = field;
        this.player = p;
        alleZuege = new HashMap<>();

        int maxZuglaenge = 0;
        // muss noch bisle geändert werden
        Stone s2 = p.getArray()[0];
        for (Stone s : p.getArray()) {
            KI(s, s.getIndexX(), s.getIndexY(), 0, true, Direction.LEFT, new ArrayList<Stone>());
            KI(s, s.getIndexX(), s.getIndexY(), 0, true, Direction.RIGHT, new ArrayList<Stone>());
            if(alleZuege.get(s).getZuglaenge() > maxZuglaenge){
                maxZuglaenge = alleZuege.get(s).getZuglaenge();
                s2 = s;
            }
        }


        return alleZuege.get(s2);
    }

    private int diagonalcheck(Direction d, int x, int y, boolean ersterDurchgang, List<Stone>rausgeschmissen){
        Field f;
        Field f2;
        if(d.equals(Direction.LEFT)) {
            f = playingField.getField(x + 1, y - 1);
            f2 = playingField.getField(x + 2, y - 2);
        }
        else{
            f = playingField.getField(x - 1, y - 1);
            f2 = playingField.getField(x - 2, y - 2);
        }

        // Feld ist vom Gegner besetzt, Feld dahinter aber leer
        if(f != null && f.getcColor().equals(player.getEnemyColor()) && f2 != null && f2.getcColor().equals(Color.NONE)){
            //Hier muss noch der gegnerische Stein in die Liste der rausgeschmissenen Steine hinzugefügt werden
            //rausgeschmissen.add();
            return 2;
        }
        // Feld ist leer und erster durchgang
        else if (f != null && f.getcColor().equals(Color.NONE) && ersterDurchgang){
            return 1;
        }
        // Alle anderen Fälle
        return 0;
    }

    private void KI(Stone s, int x, int y, int zuglaenge, boolean ersterDurchgang, Direction d, List<Stone> rausgeschmissen){
        int a;
        if((a = diagonalcheck(d, x, y, ersterDurchgang, rausgeschmissen)) > 1){
            KI(s,x+a, y-a, zuglaenge + a, false, Direction.LEFT, new ArrayList<Stone>(rausgeschmissen));
        }
        else{
            if(alleZuege.containsKey(s)){
                if(! (alleZuege.get(s).getZuglaenge() > zuglaenge + a)){
                    alleZuege.put(s, new Zugfolge(rausgeschmissen, zuglaenge + a, s, x+a, y-a));
                }
            }
        }
        if((a = diagonalcheck(d, x, y, ersterDurchgang, rausgeschmissen)) > 1){
            KI(s,x-a, y-a, zuglaenge + a, false, Direction.RIGHT, new ArrayList<Stone>(rausgeschmissen));
        }
        else{
            if(alleZuege.containsKey(s)){
                if(! (alleZuege.get(s).getZuglaenge() > zuglaenge + a)){
                    alleZuege.put(s, new Zugfolge(rausgeschmissen, zuglaenge + a, s, x-a, y-a));
                }
            }
        }
    }
}
