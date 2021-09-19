package de.kaiserv.ohtello.commander.key;

import javafx.scene.input.KeyEvent;

import static de.kaiserv.ohtello.commander.Main.COMMANDS;

public class ReleasedHandler extends Handler {

    @Override
    public void handle(KeyEvent event) {
        resolveCommand(event.getCode()).ifPresent(command -> COMMANDS.put(command, 0));
    }
}
