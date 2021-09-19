package de.kaiserv.ohtello.gpsdr;

public class Main {

    public static void main(String... args) throws Exception {
        GpsdReader reader = new GpsdReader("localhost", 2947);
        Runtime.getRuntime().addShutdownHook(new Thread(reader::close));
        reader.start();
    }
}
