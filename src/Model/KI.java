package Model;

/**
 * Created by joel schmid on 10.08.2018.
 */
public class KI {

    private PlayingField playingField;
    private Player player;

    public void bestmoeglicherSpielzugErmitteln(PlayingField field, Player p){

        this.playingField = field;
        this.player = p;



    }

    private int diagonalcheck(Direction d, int x, int y, boolean ersterDurchgang){
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
            return 2;
        }
        // Feld ist leer und erster durchgang
        else if (f != null && f.getcColor().equals(Color.NONE) && ersterDurchgang){
            return 1;
        }
        // Alle anderen Fälle
        return 0;
    }

    private void KI(Stone s, int x, int y, int zuglaenge, boolean ersterDurchgang, Direction d){
        KI(s,x+1, y-1, zuglaenge + diagonalcheck(d, x, y, ersterDurchgang), false, Direction.LEFT);
        KI(s,x-1, y-1, zuglaenge + diagonalcheck(d, x, y, ersterDurchgang), false, Direction.RIGHT);
    }
}
