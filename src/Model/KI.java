package Model;

import controller.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joel schmid on 10.08.2018.
 */
public class KI extends Player {

    private Player enemy;
    private List<Zugfolge> alleZuege;

    public KI(Color c, String name) {
        super(c, name);
    }

    public KI(Color c, String name, int size, Player enemy) {
        this(c, name);
        createStones(size);
        this.enemy = enemy;
    }

    public Zugfolge KI() {
        alleZuege = new ArrayList<>();

        for (Stone s : getStones()) {
            if (!s.isEliminated()) {

                KI(s, s.getIndexX(), s.getIndexY(), 0, true, new ArrayList<Field>(), new ArrayList<Field>());

            }
        }

        for (Zugfolge z : alleZuege) {
            System.out.println(z.toString());
        }
        System.out.println("Anzahl aller Züge: " + alleZuege.size());
        Zugfolge gewehlt = Zugfolge.getRandomZug(alleZuege);
        System.out.println("Ausgewählter Zug: " + gewehlt.toString());
        System.out.println("---------------------------------------------------------------------------");
        gewehlt.print();
        return gewehlt;
    }


    private int diagonalcheck(Direction d, int x, int y, boolean ersterDurchgang, List<Field> skipped, List<Field> entered) {
        // Überprüft die möglichen Schläge von KI seite aus links (von Spielerseite aus rechts)
        if (d.equals(Direction.LEFTDOWN)) {
            if (Main.playingField.isPositionInsideField(x + 2, y - 2) && Main.playingField.isPositionInsideField(x + 1, y - 1) && enemy.hasStoneAt(x + 1, y - 1) && !(enemy.hasStoneAt(x + 2, y - 2) || hasStoneAt(x + 2, y - 2))) {
                skipped.add(Main.playingField.getField(x + 1, y - 1));
                entered.add(Main.playingField.getField(x + 2, y - 2));
                return 2;
            }
            if (ersterDurchgang && Main.playingField.isPositionInsideField(x + 1, y - 1) && !(enemy.hasStoneAt(x + 1, y - 1) || hasStoneAt(x + 1, y - 1))) {
                entered.add(Main.playingField.getField(x + 1, y - 1));
                return 1;
            }
            return 0;
        } else if (d.equals(Direction.RIGHTDOWN)) {
            if (Main.playingField.isPositionInsideField(x - 2, y - 2) && Main.playingField.isPositionInsideField(x - 1, y - 1) && enemy.hasStoneAt(x - 1, y - 1) && !(enemy.hasStoneAt(x - 2, y - 2) || hasStoneAt(x - 2, y - 2))) {
                skipped.add(Main.playingField.getField(x - 1, y - 1));
                entered.add(Main.playingField.getField(x - 2, y - 2));
                return 2;
            }
            if (ersterDurchgang && Main.playingField.isPositionInsideField(x - 1, y - 1) && !(enemy.hasStoneAt(x - 1, y - 1) || hasStoneAt(x - 1, y - 1))) {
                entered.add(Main.playingField.getField(x - 1, y - 1));
                return 1;
            }
            return 0;
        } else if (d.equals(Direction.LEFTUP)) {
            if (Main.playingField.isPositionInsideField(x + 2, y + 2) && Main.playingField.isPositionInsideField(x + 1, y + 1) && enemy.hasStoneAt(x + 1, y + 1) && !(enemy.hasStoneAt(x + 2, y + 2) || hasStoneAt(x + 2, y + 2))) {
                skipped.add(Main.playingField.getField(x + 1, y + 1));
                entered.add(Main.playingField.getField(x + 2, y + 2));
                return 2;
            }
            if (ersterDurchgang && Main.playingField.isPositionInsideField(x + 1, y + 1) && !(enemy.hasStoneAt(x + 1, y + 1) || hasStoneAt(x + 1, y + 1))) {
                entered.add(Main.playingField.getField(x + 1, y + 1));
                return 1;
            }
            return 0;
        } else if (d.equals(Direction.RIGHTUP)) {
            if (Main.playingField.isPositionInsideField(x - 2, y + 2) && Main.playingField.isPositionInsideField(x - 1, y + 1) && enemy.hasStoneAt(x - 1, y + 1) && !(enemy.hasStoneAt(x - 2, y + 2) || hasStoneAt(x - 2, y + 2))) {
                skipped.add(Main.playingField.getField(x - 1, y + 1));
                entered.add(Main.playingField.getField(x - 2, y + 2));
                return 2;
            }
            if (ersterDurchgang && Main.playingField.isPositionInsideField(x - 1, y + 1) && !(enemy.hasStoneAt(x - 1, y + 1) || hasStoneAt(x - 1, y + 1))) {
                entered.add(Main.playingField.getField(x - 1, y + 1));
                return 1;
            }
            return 0;
        }
        return 0;
    }

    private void KI(Stone s, int x, int y, int zuglaenge, boolean ersterDurchgang, List<Field> skipped, List<Field> entered) {

        if (ersterDurchgang) {
            entered.add(Main.playingField.getField(x, y));
        }

        // kopien für schläge nach rechts und links werden erstellt, damit sich diese nicht durcheinandermischen
        List<Field> skippedLeftDown = new ArrayList<>(skipped);
        List<Field> enteredLeftDown = new ArrayList<>(entered);

        List<Field> skippedRightDown = new ArrayList<>(skipped);
        List<Field> enteredRightDown = new ArrayList<>(entered);

        List<Field> skippedLeftUp = new ArrayList<>(skipped);
        List<Field> enteredLeftUp = new ArrayList<>(entered);

        List<Field> skippedRightUp = new ArrayList<>(skipped);
        List<Field> enteredRightUp = new ArrayList<>(entered);

        int a;
        if ((a = diagonalcheck(Direction.LEFTDOWN, x, y, ersterDurchgang, skippedLeftDown, enteredLeftDown)) > 1) {
            //KI wird rekursiv aufgerufen, wenn ein gegnerischer Stein übersprungen werden kann
            KI(s, x + a, y - a, zuglaenge + a, false, new ArrayList<Field>(skippedLeftDown), new ArrayList<Field>(enteredLeftDown));
        } else {
            if (zuglaenge + a > 0) {
                Zugfolge z = new Zugfolge(zuglaenge + a, s);
                z.addEnterField(enteredLeftDown);
                z.addSkipField(skippedLeftDown);
                alleZuege.add(z);
            }
        }

        if ((a = diagonalcheck(Direction.RIGHTDOWN, x, y, ersterDurchgang, skippedRightDown, enteredRightDown)) > 1) {
            KI(s, x - a, y - a, zuglaenge + a, false, new ArrayList<Field>(skippedRightDown), new ArrayList<Field>(enteredRightDown));
        } else {
            if (zuglaenge + a > 0) {
                Zugfolge z = new Zugfolge(zuglaenge + a, s);
                z.addEnterField(enteredRightDown);
                z.addSkipField(skippedRightDown);
                alleZuege.add(z);
            }
        }

        if (s.isSuperDame()) {
            if ((a = diagonalcheck(Direction.LEFTUP, x, y, ersterDurchgang, skippedLeftUp, enteredLeftUp)) > 1) {
                //KI wird rekursiv aufgerufen, wenn ein gegnerischer Stein übersprungen werden kann
                KI(s, x + a, y + a, zuglaenge + a, false, new ArrayList<Field>(skippedLeftUp), new ArrayList<Field>(enteredLeftUp));
            } else {
                if (zuglaenge + a > 0) {
                    Zugfolge z = new Zugfolge(zuglaenge + a, s);
                    z.addEnterField(enteredLeftUp);
                    z.addSkipField(skippedLeftUp);
                    alleZuege.add(z);
                }
            }

            if ((a = diagonalcheck(Direction.RIGHTUP, x, y, ersterDurchgang, skippedRightUp, enteredRightUp)) > 1) {
                KI(s, x - a, y + a, zuglaenge + a, false, new ArrayList<Field>(skippedRightUp), new ArrayList<Field>(enteredRightUp));
            } else {
                if (zuglaenge + a > 0) {
                    Zugfolge z = new Zugfolge(zuglaenge + a, s);
                    z.addEnterField(enteredRightUp);
                    z.addSkipField(skippedRightUp);
                    alleZuege.add(z);
                }
            }
        }
    }
}