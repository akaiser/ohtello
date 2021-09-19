package de.kaiserv.ohtello.commander;

import de.kaiserv.ohtello.commander.drone.TelloController;
import de.kaiserv.ohtello.commander.key.PressedHandler;
import de.kaiserv.ohtello.commander.key.ReleasedHandler;
import de.kaiserv.ohtello.commander.ui.StateGridPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TODO:
 * - implement "emergency" command
 * - add TelloState udp connection to read stats and repaint inside the frame
 * - embed video stream - check Tello4J
 */
public class Main extends Application {

    public static final Map<Command, Integer> COMMANDS = new ConcurrentHashMap<Command, Integer>() {{
        Arrays.stream(Command.values()).forEach(command -> put(command, 0));
    }};

    public static final AtomicBoolean
            IN_AIR_STATE = new AtomicBoolean(false),
            IN_HOVER_STATE = new AtomicBoolean(false); // TODO: drop or needed at some point?

    private static TelloController telloController;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("OhTello");
        primaryStage.setOnCloseRequest(__ -> {
            telloController.close(); // TODO: check if this really works
            Platform.exit();
            System.exit(0);
        });

        Scene scene = new Scene(new StateGridPane(), 640, 480);
        scene.setOnKeyPressed(new PressedHandler());
        scene.setOnKeyReleased(new ReleasedHandler());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String... args) {
        telloController = new TelloController();
        telloController.start();
        launch(args);
    }
}
