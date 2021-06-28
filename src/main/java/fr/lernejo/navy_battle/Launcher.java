package fr.lernejo.navy_battle;
import java.io.IOException;
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
}
