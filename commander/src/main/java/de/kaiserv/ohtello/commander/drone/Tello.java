package de.kaiserv.ohtello.commander.drone;

import de.kaiserv.ohtello.commander.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class Tello {

    private static final Logger LOG = LoggerFactory.getLogger(Tello.class);

    private DatagramSocket socket;
    boolean isConnected;

    public void disconnect() {
        isConnected = false;
        socket.disconnect();
        socket.close();
    }

    public void connect() {
        try {
            socket = new DatagramSocket(Config.TELLO_PORT);
            socket.connect(InetAddress.getByName(Config.TELLO_IP), Config.TELLO_PORT);
            isConnected = true;
            LOG.info("CONNECTED");
        } catch (Exception ex) {
            LOG.error("ERROR", ex);
        }
    }

    public void send(String command) {
        try {
            LOG.info("SEND_SET: {}", command);
            send(command.getBytes());
        } catch (Exception ex) {
            LOG.error("ERROR", ex);
        }
    }

    public String sendAndRead(String command) {
        String response = null;
        try {
            LOG.info("SEND_CONTROL: {}", command);
            send(command.getBytes());
            LOG.info("RESPONSE: {}", response = receive());
        } catch (Exception ex) {
            LOG.error("ERROR", ex);
        }
        return response;
    }

    private void send(byte[] data) throws Exception {
        socket.send(new DatagramPacket(data, data.length, socket.getInetAddress(), socket.getPort()));
    }

    private String receive() throws Exception {
        byte[] data = new byte[256];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        socket.receive(packet);
        return trim(data, packet);
    }

    private String trim(byte[] data, DatagramPacket packet) {
        byte[] newData = Arrays.copyOf(data, packet.getLength());
        return new String(newData, StandardCharsets.UTF_8).trim();
    }
}
