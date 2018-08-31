package controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

/**
 * Steuerung für das Menü.
 *
 * @author Johannes Gaiser
 */

public class MenuPaneController {

    /**
     * Objekt der Main Klasse.
     */
    private Main control;

    /**
     * Methode um Objekte zu übergeben.
     *
     * @param control Objekt der Main Klasse
     */
    public void setObjects(Main control) {
        this.control = control;
    }

    @FXML
    private MenuItem menuItem_return;

    /**
     * Verarbeitet einen Klick auf das {@link MenuItem} "About".
     */
    @FXML
    private void onAbout() {
        control.showAboutPane();
    }

    /**
     * Verarbeitet einen Klick auf das {@link MenuItem} "Rules".
     */
    @FXML
    private void onRules() {
        control.showRulesPane();
    }

    /**
     * Verarbeitet einen Klick auf das {@link MenuItem} "Return".
     */
    @FXML
    private void onReturn() {
        control.returnToStart();
    }

    /**
     * Setzt das {@link MenuItem} "Return" auf enabled/disabled.
     * Auf der Startoberfläche soll das {@link MenuItem} disabled sein.
     *
     * @param disable Flag, ob das {@link MenuItem} disabled sein soll oder nicht.
     */
    public void disableReturnItem(boolean disable) {
        menuItem_return.setDisable(disable);
    }

}
