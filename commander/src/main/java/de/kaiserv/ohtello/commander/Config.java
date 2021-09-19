package de.kaiserv.ohtello.commander;

public class Config {

    public static final int
            TELLO_PORT = 8889,
            CMD_RC_SLEEP_MS = 200,
            CMD_RC_INITIAL_VALUE = 50;

    public static final String
            TELLO_IP = "192.168.10.1",
            CMD_RC_FORMAT = "rc %d %d %d %d",
            CMD_COMMAND = "command",
            CMD_LAND = "land",
            CMD_TAKEOFF = "takeoff",
            CMD_BATTERY = "battery?",
            CMD_HEIGHT = "height?";
}
