package fr.lernejo.navy_battle.Prototypes;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerInfoTest {
    @Test
    void testIt() {
        var one = new Info("id", "url", "message");
        assertEquals("url", one.getUrl());
    }

    @Test
    void testJSON() {
        JSONObject in = new JSONObject("{\"id\": \"my_id\", \"url\":\"my_url\",\"message\":\"my_message\"}");
        var v = Info.fromJSON(in);
        assertEquals("my_url", v.getUrl());

        assertEquals(v.toJSON().toString(), in.toString());
    }
}
