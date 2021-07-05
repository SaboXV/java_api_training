package fr.lernejo.navy_battle;
import java.io.IOException;


public class Launcher {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.err.println("Need port and server url !");
                System.exit(-1);
            }

            int Port = Integer.parseInt(args[0]);
            System.out.println("Listen on port :" + Port);
            new Server().startServer(Port, args.length > 1 ? args[1] : null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
