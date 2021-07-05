package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
<<<<<<< HEAD
import fr.lernejo.navy_battle.Prototypes.Info;
import fr.lernejo.navy_battle.Prototypes.Option;
=======
>>>>>>> 1ede561a4968bf0cb609e7b1a6c8e64e45d59609

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
<<<<<<< HEAD
import java.util.UUID;
import java.util.concurrent.Executors;

public class Server extends Abstract {
    private final Option<Info> localServer = new Option<>();
    private final Option<Info> remoteServer = new Option<>();

    public void startServer(int port, String connectURL) throws IOException {
        localServer.set(new Info(
            UUID.randomUUID().toString(),
            "http://localhost:" + port,
            "OK"
        ));
        if (connectURL != null)
            new Thread(() -> this.requestStart(connectURL)).start();
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.createContext("/ping", this::handlePing);
        server.createContext("/api/game/start", s -> startGame(new RequestHandler(s)));
        server.start();
    }

    private void handlePing(HttpExchange exchange) throws IOException {
        String body = "OK";
=======
import java.util.concurrent.Executors;

public class Server {
    public final String id;
    public final String url;
    public final String msg;

    public Server(String id, String url, String msg){
        this.id = id;
        this.url = url;
        this.msg = msg;
    }


    public void StartServeur(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.createContext("/ping", this::ping);
        server.start();
    }

    private void ping(HttpExchange exchange) throws IOException {
        String body = "Hello";
>>>>>>> 1ede561a4968bf0cb609e7b1a6c8e64e45d59609
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(body.getBytes());
        }
    }
<<<<<<< HEAD

    public void startGame(RequestHandler handler) throws IOException {
        try {
            remoteServer.set(Info.fromJSON(handler.getJSONObject()));
            System.out.println("Will fight against " + remoteServer.get().getUrl());
            handler.sendJSON(202, localServer.get().toJSON());
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendString(400, e.getMessage());
        }
    }

    public void requestStart(String server) {
        try {
            var response = sendPOSTRequest(server + "/api/game/start", this.localServer.get().toJSON());
            this.remoteServer.set(Info.fromJSON(response).withURL(server));
            System.out.println("Will fight against " + remoteServer.get().getUrl());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed !");
        }
    }
=======
>>>>>>> 1ede561a4968bf0cb609e7b1a6c8e64e45d59609
}
