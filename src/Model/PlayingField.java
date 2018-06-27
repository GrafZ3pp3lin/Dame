package Model;

public class PlayingField {
    private int FieldSize;

    public PlayingField(int size){
        FieldSize = size;
    }
    public int getSize(){
        return FieldSize;
    }

    public Field[] createField(){
        Field cNewPlayingField[] = {};
        int m = 0;
        boolean black = false;
        for (int i = 0; i < FieldSize; i++){
            for (int j = 0; j < FieldSize; j++){
                if (black) {
                    cNewPlayingField[m] = new Field(Color.BLACK, i, j);
                }
                else{
                    cNewPlayingField[m] = new Field(Color.WHITE, i, j);
                }
                m++;
                black = !black;
            }
        }
        return cNewPlayingField;
    }

}
