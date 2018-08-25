package Model;

/**
 * Created by joels on 24.08.2018.
 */
public class Zugfolge extends Move {
    private int zuglaenge;

    public Zugfolge(int zuglaenge, Stone s){
        super(s);
        this.zuglaenge = zuglaenge;
    }

    public int getZuglaenge(){return zuglaenge;}

    public String toString(){
        return "laenge: " + zuglaenge + "; vonPos: " + super.getFirstField().getIndexX() + ", " + super.getFirstField().getIndexY() + " nachPos: " + super.getEndField().getIndexX() + ", " + super.getEndField().getIndexY();
    }

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


}
