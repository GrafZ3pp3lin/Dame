package Model;

import java.util.ArrayList;
import java.util.List;

public class Move {

    private Stone stone;
    private List<Field> enteredFields;
    private List<Field> skipedFields;
    private int index = 1;

    public Move(Stone s) {
        stone = s;
        enteredFields = new ArrayList<>();
        skipedFields = new ArrayList<>();
    }

    public Stone getStone() {
        return stone;
    }

    public Field getEndField() {
        if (enteredFields != null && !enteredFields.isEmpty()) {
            return enteredFields.get(enteredFields.size() - 1);
        }
        return null;
    }

    public Field getLastField() {
        if (enteredFields != null && !enteredFields.isEmpty() && enteredFields.size() > 1) {
            return enteredFields.get(enteredFields.size() - 2);
        }
        return null;
    }

    public Field getNextField() {
        return enteredFields.get(index);
    }

    public Field getCurrentField() {
        return enteredFields.get(index - 1);
    }

    public boolean nextField() {
        return ++index >= enteredFields.size();
    }

    public void addEnterField(Field f) {
        enteredFields.add(f);
    }

    public void addSkipField(Field f) {
        skipedFields.add(f);
    }

    public List<Field> getEnteredFields() {
        return enteredFields;
    }

    public List<Field> getSkipedFields() {
        return skipedFields;
    }

    public Field getFirstSkipedField() {
        if (!skipedFields.isEmpty()) {
            return skipedFields.get(0);
        }
        return null;
    }

    public void nextSkipedField() {
        if (!skipedFields.isEmpty()) {
            skipedFields.remove(0);
        }
    }

    public void update() {
        stone.setIndexX(getEndField().getIndexX());
        stone.setIndexY(getEndField().getIndexY());
    }

}
