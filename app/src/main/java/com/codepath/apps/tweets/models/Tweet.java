package com.codepath.apps.tweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nidhikulkarni on 2/16/16.
 */
public class Tweet {

    private String body;
    private long uid;
    private User user;
    private String createdAt;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public static Tweet fromJSON(JSONObject json) {
        Tweet t = new Tweet();

        try {
            t.body = json.getString("text");
            t.uid = json.getLong("id");
            t.createdAt = json.getString("created_at");
            t.user = User.fromJSON(json.getJSONObject("user"));

        } catch (JSONException e) {
            //FIXME: do something
            e.printStackTrace();
        }

        return t;
    }

    public String toString() {
        return body;
    }
}
