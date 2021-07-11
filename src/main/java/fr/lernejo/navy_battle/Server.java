package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.Prototypes.*;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.Executors;

public class Server extends Abstract {
    private final Option<Info> localServer = new Option<>();
    private final Option<Maps> localMap = new Option<>();
    private final Option<Info> remoteServer = new Option<>();
    private final Option<Maps> remoteMap = new Option<>();

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


    public void startGame(RequestHandler handler) throws IOException {
        try {
            remoteServer.set(Info.fromJSON(handler.getJSONObject()));
            localMap.set(new Maps(true));remoteMap.set(new Maps(false));
            System.out.println("Run with " + remoteServer.get().getUrl());
            handler.sendJSON(202, localServer.get().toJSON());
            fire();
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendString(400, e.getMessage());
        }
    }
    public void requestStart(String server) {
        try {
            localMap.set(new Maps(true));remoteMap.set(new Maps(false));
            var response = sendPOSTRequest(server + "/api/game/start", this.localServer.get().toJSON());
            this.remoteServer.set(Info.fromJSON(response).withURL(server));
            System.out.println("Run with " + remoteServer.get().getUrl());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed !");
        }
    }

    public void fire() throws IOException, InterruptedException {
        Coordinates coordinates = remoteMap.get().getNextPlaceToHit();
        var response =
            sendGETRequest(remoteServer.get().getUrl() + "/api/game/fire?cell=" + coordinates.toString());
        if (!response.getBoolean("shipLeft")) {
            return;
        }
        var result = Result.fromAPI(response.getString("consequence"));
        if (result == Result.MISS)
            remoteMap.get().setCell(coordinates, GameCell.MISSED_FIRE);
        else
            remoteMap.get().setCell(coordinates, GameCell.SUCCESSFUL_FIRE);
    }
    public void handleFire(RequestHandler handler) throws IOException {
        try {
            String cell = handler.getQueryParameter("cell");
            var position = new Coordinates(cell);var res = localMap.get().hit(position);
            var response = new JSONObject();
            response.put("consequence", res.toAPI());
            response.put("shipLeft", localMap.get().hasShipLeft());
            handler.sendJSON(200, response);
            if (localMap.get().hasShipLeft()) {
                fire();
            }
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendString(400, e.getMessage());
        }
    }
}
