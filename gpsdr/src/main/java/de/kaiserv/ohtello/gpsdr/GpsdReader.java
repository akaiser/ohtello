package de.kaiserv.ohtello.gpsdr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class GpsdReader extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(GpsdReader.class);

    private final Socket socket;
    private final BufferedWriter writer;
    private final BufferedReader reader;

    public GpsdReader(String host, int port) throws Exception {
        socket = new Socket(InetAddress.getByName(host), port);
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        LOG.info("Starting...");

        try {
            writer.write("?WATCH={\"enable\":true,\"json\":true}");
            writer.flush();

            // TODO: implement is running
            while (true) {
                String line = reader.readLine();
                if (line.startsWith("{\"class\":\"TPV\"")) {
                    LOG.info(TPVObject.fromJson(line).toString());
                }
            }
        } catch (Exception ex) {
            LOG.error("Some explosion here", ex);
        }
    }

    public void close() {
        try {
            LOG.info("Closing...");

            writer.write("?WATCH={\"enable\":false}");
            writer.flush();

            reader.close();
            writer.close();
            socket.close();
        } catch (Exception ex) {
            LOG.error("Some explosion here", ex);
        }
    }
}
