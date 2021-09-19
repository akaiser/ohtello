package de.kaiserv.ohtello.commander.key;

import de.kaiserv.ohtello.commander.Command;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static de.kaiserv.ohtello.commander.Command.*;
import static javafx.scene.input.KeyCode.*;

abstract class Handler implements EventHandler<KeyEvent> {

    private static final Map<KeyCode, Command> KEY_COMMAND_MAPPINGS = new HashMap<KeyCode, Command>() {{
        put(LEFT, left);
        put(UP, forward);
        put(RIGHT, right);
        put(DOWN, backward);
        put(A, ccw);
        put(W, up);
        put(D, cw);
        put(S, down);
    }};

    static Optional<Command> resolveCommand(KeyCode keyCode) {
        return Optional.ofNullable(KEY_COMMAND_MAPPINGS.get(keyCode));
    }
}
