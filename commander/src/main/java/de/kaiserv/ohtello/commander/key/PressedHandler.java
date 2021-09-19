package de.kaiserv.ohtello.commander.key;

import de.kaiserv.ohtello.commander.Command;
import de.kaiserv.ohtello.commander.Config;
import de.kaiserv.ohtello.commander.Main;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PressedHandler extends Handler {

    @Override
    public void handle(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        boolean isInAir = Main.IN_AIR_STATE.get();

        if (keyCode == KeyCode.SPACE) Main.IN_AIR_STATE.set(!isInAir);
        else if (isInAir) resolveCommand(keyCode).ifPresent(command -> {
            int oldValue = Main.COMMANDS.get(command);
            int newValue = oldValue != 0 ? getUpdatedValue(oldValue) : getInitialValue(command);
            Main.COMMANDS.put(command, newValue);
        });
    }

    private static int getInitialValue(Command command) {
        switch (command) {
            case forward:
            case right:
            case up:
            case cw:
                return Config.CMD_RC_INITIAL_VALUE;
        }
        return Config.CMD_RC_INITIAL_VALUE * -1;
    }

    private static int getUpdatedValue(int oldValue) {
        int newValue = oldValue + (oldValue > 0 ? 1 : -1);
        return newValue > 100 ? 100 : Math.max(newValue, -100);
    }
}
