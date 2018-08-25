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

    @FXML
    private ComboBox<String> comboBox_size;

    @FXML
    private TextField textField_Player1;

    @FXML
    private TextField textField_Player2;

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
        control.startGame(true, textField_Player1.getText(), textField_Player2.getText());
    }

    @FXML
    private void onMultiPlayerStart() {
        control.startGame(false, textField_Player1.getText(), textField_Player2.getText());
    }

    public int getSize() {
        return comboBox_size.getValue() == acht ? 8 : 10;
    }

}
