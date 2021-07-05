package fr.lernejo.navy_battle;
import java.io.IOException;
<<<<<<< HEAD

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
=======
import java.util.UUID;

public class Launcher {
    public static void main(String[] args) throws IOException {
        if (args.length != 0) {
            int Port = Integer.parseInt(args[0]);
            Server server = new Server(UUID.randomUUID().toString(),"localhost:" + Port, "hey");
            server.StartServeur(Port);
    }
        else
        {
            System.out.print("ça marche pas");
        }
}
>>>>>>> 1ede561a4968bf0cb609e7b1a6c8e64e45d59609
}
