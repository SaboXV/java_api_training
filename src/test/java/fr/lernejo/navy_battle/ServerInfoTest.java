package fr.lernejo.navy_battle.Prototypes;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerInfoTest {
    @Test
    void testIt() {
        var one = new Info("id", "url", "message");
        assertEquals("id", one.getId());
        assertEquals("url", one.getUrl());
        assertEquals("message", one.getMessage());
    }

    @Test
    void testJSON() {
        JSONObject in = new JSONObject("{\"id\": \"my_id\", \"url\":\"my_url\",\"message\":\"my_message\"}");
        var v = Info.fromJSON(in);
        assertEquals("my_id", v.getId());
        assertEquals("my_url", v.getUrl());
        assertEquals("my_message", v.getMessage());

        assertEquals(v.toJSON().toString(), in.toString());
    }
}
