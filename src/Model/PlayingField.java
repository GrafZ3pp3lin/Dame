package Model;

public class PlayingField {
    private int FieldSize;
    private Field cField[];

    public PlayingField(int size){
        FieldSize = size;
    }
    public int getSize(){
        return FieldSize;
    }

    public void createField(){
        cField = new Field[FieldSize*FieldSize];
        int m = 0;
        boolean black = false;
        for (int i = 0; i < FieldSize; i++){
            for (int j = 0; j < FieldSize; j++){
                if (black) {
                    cField[m] = new Field(Color.BLACK, i, j);
                }
                else{
                    cField[m] = new Field(Color.WHITE, i, j);
                }
                m++;
                black = !black;
            }
        }
    }
    public Field[] getcField(){
        return cField;
    }

}
