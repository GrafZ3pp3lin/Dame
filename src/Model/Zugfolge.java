package Model;

/**
 * Created by joels on 24.08.2018.
 */
public class Zugfolge extends Move{
    private int zuglaenge;

    public Zugfolge(int zuglaenge, Stone s){
        super(s);
        this.zuglaenge = zuglaenge;
    }

    public int getZuglaenge(){return zuglaenge;}

    public String toString(){
        return "laenge: " + zuglaenge + "; vonPos: " + super.getStone().getIndexX() + ", " + super.getStone().getIndexY() + " nachPos: " + super.getEnteredFields().get(super.getEnteredFields().size()-1).getIndexX() + ", " + super.getEnteredFields().get(super.getEnteredFields().size()-1).getIndexY();
    }


}
