package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

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
        control.sampleGame();
    }

    @FXML
    private void onMultiPlayerStart() {
        System.err.println("Oops. Somebody forgot to implement me!");
    }

    public int getSize() {
        return comboBox_size.getValue() == acht ? 8 : 10;
    }

}
