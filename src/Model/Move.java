package Model;

import java.util.List;
import java.util.Vector;

public class Move {

    private Stone stone;
    private Vector<Field> enteredFields;
    private Vector<Field> skipedFields;
    private int index;
    private boolean outdated;

    public Move() {
        enteredFields = new Vector<>();
        skipedFields = new Vector<>();
    }

    public Move(Stone s) {
        this();
        stone = s;
        index = 1;
        outdated = false;
    }

    public void init(Stone s) {
        stone = s;
        enteredFields.clear();
        skipedFields.clear();
        index = 1;
        outdated = false;
    }

    public void setOutdated(boolean outdated) {
        this.outdated = outdated;
    }

    public boolean isOutdated() {
        return outdated;
    }

    public Stone getStone() {
        return stone;
    }

    public Field getFirstField() {
        if (enteredFields != null && !enteredFields.isEmpty()) {
            return enteredFields.get(0);
        }
        return null;
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
        if (index < enteredFields.size() - 1) {
            index++;
            return true;
        }
        return false;
    }

    public void addEnterField(Field f) {
        enteredFields.add(f);
    }

    public void addSkipField(List<Field> f) {
        skipedFields.addAll(f);
    }

    public void addEnterField(List<Field> f) {
        enteredFields.addAll(f);
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
