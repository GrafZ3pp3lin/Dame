package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Steuerung für das Start Fenster
 *
 * @author Johannes Gaiser
 */

public class StartPaneController {

    private final String acht = "8 * 8";
    private final String zehn = "10 * 10";

    private Main control;

    /**
     * ComboBox, um zwischen der Spielfeldgröße zu wählen.
     * Zur Wahl stehen ein 8*8 Feld und ein 10*10 Feld.
     */
    @FXML
    private ComboBox<String> comboBox_size;

    /**
     * TextFeld um den Namen von Spieler 1 zu wählen.
     * Standard ist "Spieler 1".
     */
    @FXML
    private TextField textField_Player1;

    /**
     * TextFeld um den Namen von Spieler 2 zu wählen.
     * Standard ist "Spieler 2".
     */
    @FXML
    private TextField textField_Player2;

    public void setObjects(Main control) {
        this.control = control;
    }

    /**
     * initialisiert die ComboBox.
     * Wird automatisch bei der Initialisierung der Oberfläche aufgerufen.
     */
    @FXML
    private void initialize() {
        comboBox_size.getItems().add(acht);
        comboBox_size.getItems().add(zehn);
        comboBox_size.setValue(acht);
    }

    /**
     * startet ein Single Player Spiel.
     * Als zweiter Spieler wird eine KI initialisiert.
     */
    @FXML
    private void onSinglePlayerStart() {
        control.startGame(true, textField_Player1.getText(), textField_Player2.getText());
    }

    /**
     * startet ein Multi Player Spiel.
     * Als zweiter Spieler wird ein normaler Spieler initialisiert.
     */
    @FXML
    private void onMultiPlayerStart() {
        control.startGame(false, textField_Player1.getText(), textField_Player2.getText());
    }

    /**
     * Gibt die gewünschte Spielfeldgröße zurück.
     * ausgewählter Wert der ComboBox. Standard ist 8.
     * Auswahl steht zwischen 8 und 10.
     *
     * @return Wert, wie viele Felder das Spielfeld haben soll.
     */
    public int getSize() {
        return comboBox_size.getValue().equals(acht) ? 8 : 10;
    }

}
