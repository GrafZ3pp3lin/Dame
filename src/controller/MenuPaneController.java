package controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MenuPaneController {

    private Main control;

    public void setInstances(Main control) {
        this.control = control;
    }

    @FXML
    private MenuItem menuItem_return;

    @FXML
    private void onAbout() {

    }

    @FXML
    private void onReturn() {
        control.returnToStart();
    }

    public void disableReturnItem(boolean disable) {
        menuItem_return.setDisable(disable);
    }

}
