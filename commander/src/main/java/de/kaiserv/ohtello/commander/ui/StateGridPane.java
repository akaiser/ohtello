package de.kaiserv.ohtello.commander.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class StateGridPane extends GridPane {

    public StateGridPane() {
        setPadding(new Insets(10));
        add(new Label("Connection:"), 0, 1);

        add(new Label("Battery:"), 0, 2);
        add(new Label("Wifi:"), 0, 3);
    }
}
