package com.codepath.apps.tweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nidhikulkarni on 2/16/16.
 */
public class User {

    private String name;
    private long uid;
    private String screenName;
    private String profileImageURL;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public static User fromJSON(JSONObject json) {
        User u = new User();

        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName =json.getString("screen_name");
            u.profileImageURL = json.getString("profile_image_url");

        } catch (JSONException e) {
            // FISME: do something
            e.printStackTrace();
        }

        return u;
    }
}
