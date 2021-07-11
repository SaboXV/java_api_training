
package fr.lernejo.navy_battle.Prototypes;

import org.json.JSONException;
import org.json.JSONObject;

public class Info {
    private final String id;
    private final String url;
    private final String message;

    public Info(String id, String url, String message) {
        this.id = id;
        this.url = url;
        this.message = message;
    }

    public String getId() {
        return id;
    }
    public String getUrl() {
        return url;
    }
    public String getMessage() {
        return message;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("url", url);
        obj.put("message", message);
        return obj;
    }

    public static Info fromJSON(JSONObject object) throws JSONException {
        return new Info(
            object.getString("id"),
            object.getString("url"),
            object.getString("message")
        );
    }

    public Info withURL(String url) {
        return new Info(
            this.id,
            url,
            this.message
        );
    }
}
