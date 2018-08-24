package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

/**
 * Steuerung für das Start Fenster
 *
 * @author Johannes Gaiser
 */

public class StartPaneController {

    private final String acht = "8 * 8";
    private final String zehn = "10 * 10";

    private Main control;

    @FXML
    private ComboBox<String> comboBox_size;

    public void setInstances(Main control) {
        this.control = control;
    }

    @FXML
    private void initialize() {
        comboBox_size.getItems().add(acht);
        comboBox_size.getItems().add(zehn);
        comboBox_size.setValue(acht);
    }

    @FXML
    private void onSinglePlayerStart() {
        System.err.println("Oops. KI denied your request!");
    }

    @FXML
    private void onMultiPlayerStart() {
        control.startGame();
    }

    public int getSize() {
        return comboBox_size.getValue() == acht ? 8 : 10;
    }

}
