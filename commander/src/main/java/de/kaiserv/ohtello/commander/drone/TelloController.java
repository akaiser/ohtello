package de.kaiserv.ohtello.commander.drone;

import de.kaiserv.ohtello.commander.Config;
import de.kaiserv.ohtello.commander.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.kaiserv.ohtello.commander.Command.*;
import static de.kaiserv.ohtello.commander.Main.*;
import static java.lang.String.format;

public class TelloController extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(TelloController.class);

    private final Tello tello = new Tello();

    private long lastCommandMillis = System.currentTimeMillis();

    public void close() {
        tello.disconnect();
    }

    @Override
    public void run() {
        tello.connect();

        try {
            boolean isInAir = false;
            tello.sendAndRead(Config.CMD_COMMAND);

            while (tello.isConnected) {
                Thread.sleep(Config.CMD_RC_SLEEP_MS);

                boolean isInAirFromState = IN_AIR_STATE.get();
                if (isInAir != isInAirFromState) {
                    LOG.info("inAirState changed from '{}' to '{}'", isInAir, isInAirFromState);
                    if (isInAirFromState) {
                        tello.sendAndRead(Config.CMD_TAKEOFF);
                    } else {
                        tello.send(String.format(Config.CMD_RC_FORMAT, 0, 0, 0, 0));
                        tello.sendAndRead(Config.CMD_LAND);
                    }
                    isInAir = isInAirFromState;
                }

                if (isInAir) {

                    // rc a b c d
                    int roll = getCommandValue(left, right);
                    int pitch = getCommandValue(backward, forward);
                    int throttle = getCommandValue(down, up);
                    int yaw = getCommandValue(ccw, cw);

                    boolean isInHoverState = roll == 0 && pitch == 0 && throttle == 0 && yaw == 0;
                    boolean alreadyHovering = IN_HOVER_STATE.getAndSet(isInHoverState);

                    // TODO: that's basically the heartbeat...
                    // do something useful with it at some point...
                    // maybe even read and use height/battery etc.
                    long currentMillis = System.currentTimeMillis();
                    if (isInHoverState && alreadyHovering) {
                        if (currentMillis - lastCommandMillis < 10_000L) continue;
                    }

                    tello.send(String.format(Config.CMD_RC_FORMAT, roll, pitch, throttle, yaw));
                    lastCommandMillis = currentMillis;
                }
            }
        } catch (Exception ex) {
            LOG.error("Some explosion here", ex);
        }

        close();
    }

    private int getCommandValue(Command first, Command second) {
        int firstValue = COMMANDS.get(first);
        return firstValue == 0 ? COMMANDS.get(second) : firstValue;
    }
}
