package controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

/**
 * Steuerung für das Menü
 *
 * @author Johannes Gaiser
 */

public class MenuPaneController {

    private Main control;

    public void setInstances(Main control) {
        this.control = control;
    }

    @FXML
    private MenuItem menuItem_return;

    @FXML
    private void onAbout() {
        control.showAboutPane();
    }

    @FXML
    private void onRules() {
        control.showRulesPane();
    }

    @FXML
    private void onReturn() {
        control.returnToStart();
    }

    public void disableReturnItem(boolean disable) {
        menuItem_return.setDisable(disable);
    }

}
