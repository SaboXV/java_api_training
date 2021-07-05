package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.Prototypes.*;
import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
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
        server.createContext("/ping", this::Ping);
        server.createContext("/api/game/start", s -> startGame(new RequestHandler(s)));
        server.createContext("/api/game/fire", s -> handleFire(new RequestHandler(s)));
        server.start();
    }
    private void Ping(HttpExchange exchange) throws IOException {
        String body = "OK";
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(body.getBytes());
        }
    }
    public void handleFire(RequestHandler handler) throws IOException {
        try {
            var response = new JSONObject();
            response.put("consequence", "Hello");
            response.put("shipLeft", true);
            handler.sendJSON(200, response);
        } catch (Exception e) {
            e.printStackTrace();handler.sendString(400, e.getMessage());
        }
    }

    public void startGame(RequestHandler handler) throws IOException {
        try {
            remoteServer.set(Info.fromJSON(handler.getJSONObject()));
            System.out.println("Run with " + remoteServer.get().getUrl());
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
            System.out.println("Run with " + remoteServer.get().getUrl());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed !");
        }
    }
}
