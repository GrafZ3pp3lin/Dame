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

        //Für alle noch nicht eliminierten Steine werden alle möglichen Züge ermittelt (KI ist eine rekursive Methode)
        for (Stone s : getStones()) {
            if (!s.isEliminated()) {
                System.out.println(s.isSuperDame());
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


    private int diagonalcheck(Stone s, Direction d, int x, int y, boolean ersterDurchgang, List<Field> skipped, List<Field> entered) {
        // Überprüft die möglichen Schläge von KI seite aus links (von Spielerseite aus rechts)

        //System.out.println(s.isSuperDame() ?  (Main.playingField.getSize()-2) : 1);

        if (d.equals(Direction.LEFTDOWN)) {
            //sind die nächsten zwei diagonalen Felder innerhalb des Spielfeldes
            //der zu überspringende Stein nicht bereits im gleichen zug schonmal besucht(Zyklus)
            //Gegnerischer Stein auf nächstem Feld und übernächstes Feld frei
            for(int a = 0; a < (s.isSuperDame() ?  (Main.playingField.getSize()-2) : 1); a++) {
                if(Main.playingField.isPositionInsideField(x + a + 1, y - a - 1) && hasStoneAt(x + a + 1, y - a - 1) ||
                        enemy.hasStoneAt(x + a + 1, y - a - 1) && enemy.hasStoneAt(x + a + 2, y - a - 2)) return 0;
                if (Main.playingField.isPositionInsideField(x + a + 2, y - a - 2) && Main.playingField.isPositionInsideField(x + a + 1, y - a - 1)
                        && !skipped.contains(Main.playingField.getField(x + a + 1, y - a - 1)) && !entered.contains(Main.playingField.getField(x + a + 2, y - a - 2))
                        && enemy.hasStoneAt(x + a + 1, y - a - 1)
                        && !(enemy.hasStoneAt(x + a + 2, y - a - 2) || hasStoneAt(x + a + 2, y - a - 2))) {
                    skipped.add(Main.playingField.getField(x + a + 1, y - a - 1));
                    entered.add(Main.playingField.getField(x + a + 2, y - a - 2));
                    return 2;
                }

            }
            //ist nächstes diagonales Feld innerhalb des Spielfeldes, und nicht von einem Spielstein besetzt, einfacher Zug, ist deshalb nur bei erstem
            // Aufruf möglich, nicht im rekursionsfall
            if (!s.isSuperDame() && ersterDurchgang && Main.playingField.isPositionInsideField(x + 1, y - 1) && !(enemy.hasStoneAt(x + 1, y - 1) || hasStoneAt(x + 1, y - 1))) {
                entered.add(Main.playingField.getField(x + 1, y - 1));
                return 1;
            }
        } else if (d.equals(Direction.RIGHTDOWN)) {
            for(int a = 0; a < (s.isSuperDame() ?  (Main.playingField.getSize()-2) : 1); a++) {
                if(Main.playingField.isPositionInsideField(x - a - 1, y - a - 1) && hasStoneAt(x - a + 1, y - a - 1)||
                        enemy.hasStoneAt(x - a - 1, y - a - 1) && enemy.hasStoneAt(x - a - 2, y - a - 2)) return 0;
                if (Main.playingField.isPositionInsideField(x - a - 2, y - a - 2) && Main.playingField.isPositionInsideField(x - a - 1, y - a - 1)
                        && !skipped.contains(Main.playingField.getField(x - a - 1, y - a - 1)) && !entered.contains(Main.playingField.getField(x - a - 2, y - a - 2))
                        && enemy.hasStoneAt(x - a - 1, y - a - 1)
                        && !(enemy.hasStoneAt(x - a - 2, y - a - 2) || hasStoneAt(x - a - 2, y - a - 2))) {
                    skipped.add(Main.playingField.getField(x - a - 1, y - a - 1));
                    entered.add(Main.playingField.getField(x - a - 2, y - a - 2));
                    return 2;
                }

            }
            if (!s.isSuperDame() &&ersterDurchgang && Main.playingField.isPositionInsideField(x - 1, y - 1) && !(enemy.hasStoneAt(x - 1, y - 1) || hasStoneAt(x - 1, y - 1))) {
                entered.add(Main.playingField.getField(x - 1, y - 1));
                return 1;
            }
        } else if (s.isSuperDame() && d.equals(Direction.LEFTUP)) {
            for(int a = 0; a < (s.isSuperDame() ?  (Main.playingField.getSize()-2) : 1); a++) {
                if(Main.playingField.isPositionInsideField(x + a + 1, y + a + 1) && hasStoneAt(x + a + 1, y + a + 1)||
                        enemy.hasStoneAt(x + a + 1, y + a + 1) && enemy.hasStoneAt(x + a + 2, y + a + 2)) return 0;
                if (Main.playingField.isPositionInsideField(x + a + 2, y + a + 2) && Main.playingField.isPositionInsideField(x + a + 1, y + a + 1)
                        && !skipped.contains(Main.playingField.getField(x + a + 1, y + a + 1)) && !entered.contains(Main.playingField.getField(x + a + 2, y + a + 2))
                        && enemy.hasStoneAt(x + a + 1, y + a + 1)
                        && !(enemy.hasStoneAt(x + a + 2, y + a + 2) || hasStoneAt(x + a + 2, y + a + 2))) {
                    skipped.add(Main.playingField.getField(x + a + 1, y + a + 1));
                    entered.add(Main.playingField.getField(x + a + 2, y + a + 2));
                    return 2;
                }

            }
            if (!s.isSuperDame() &&ersterDurchgang && Main.playingField.isPositionInsideField(x + 1, y + 1) && !(enemy.hasStoneAt(x + 1, y + 1) || hasStoneAt(x + 1, y + 1))) {
                entered.add(Main.playingField.getField(x + 1, y + 1));
                return 1;
            }
        } else if (s.isSuperDame() && d.equals(Direction.RIGHTUP)) {
            for(int a = 0; a < (s.isSuperDame() ?  (Main.playingField.getSize()-2) : 1); a++) {
                if(Main.playingField.isPositionInsideField(x - a - 1, y - a - 1) && hasStoneAt(x - a - 1, y - a - 1)||
                        enemy.hasStoneAt(x - a - 1, y - a - 1) && enemy.hasStoneAt(x - a - 2, y - a - 2)) return 0;
                if (Main.playingField.isPositionInsideField(x - a - 2, y + a + 2) && Main.playingField.isPositionInsideField(x - a - 1, y + a + 1)
                        && !skipped.contains(Main.playingField.getField(x - a - 1, y + a + 1)) && !entered.contains(Main.playingField.getField(x - a - 2, y + a + 2))
                        && enemy.hasStoneAt(x - a - 1, y + a + 1)
                        && !(enemy.hasStoneAt(x - a - 2, y + a + 2) || hasStoneAt(x - a - 2, y + a + 2))) {
                    skipped.add(Main.playingField.getField(x - a - 1, y + a + 1));
                    entered.add(Main.playingField.getField(x - a - 2, y + a + 2));
                    return 2;
                }

            }
            if (!s.isSuperDame() &&ersterDurchgang && Main.playingField.isPositionInsideField(x - 1, y + 1) && !(enemy.hasStoneAt(x - 1, y + 1) || hasStoneAt(x - 1, y + 1))) {
                entered.add(Main.playingField.getField(x - 1, y + 1));
                return 1;
            }
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
        if ((a = diagonalcheck(s, Direction.LEFTDOWN, x, y, ersterDurchgang, skippedLeftDown, enteredLeftDown)) > 1) {
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

        if ((a = diagonalcheck(s, Direction.RIGHTDOWN, x, y, ersterDurchgang, skippedRightDown, enteredRightDown)) > 1) {
            KI(s, x - a, y - a, zuglaenge + a, false, new ArrayList<Field>(skippedRightDown), new ArrayList<Field>(enteredRightDown));
        } else {
            if (zuglaenge + a > 0) {
                Zugfolge z = new Zugfolge(zuglaenge + a, s);
                z.addEnterField(enteredRightDown);
                z.addSkipField(skippedRightDown);
                alleZuege.add(z);
            }
        }

        if ((a = diagonalcheck(s, Direction.LEFTUP, x, y, ersterDurchgang, skippedLeftUp, enteredLeftUp)) > 1) {
            KI(s, x + a, y + a, zuglaenge + a, false, new ArrayList<Field>(skippedLeftUp), new ArrayList<Field>(enteredLeftUp));
        } else {
            if (zuglaenge + a > 0) {
                Zugfolge z = new Zugfolge(zuglaenge + a, s);
                z.addEnterField(enteredLeftUp);
                z.addSkipField(skippedLeftUp);
                alleZuege.add(z);
            }
        }

        if ((a = diagonalcheck(s, Direction.RIGHTUP, x, y, ersterDurchgang, skippedRightUp, enteredRightUp)) > 1) {
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