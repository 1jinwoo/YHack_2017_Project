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
    private int followersCount;
    private String tagline;
    private int followingCount;

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


    public int getFollowersCount() {
        return followersCount;
    }

    public String getTagline() {
        return tagline;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public static User fromJSON(JSONObject json) {
        User u = new User();

        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName =json.getString("screen_name");
            u.profileImageURL = json.getString("profile_image_url");
            u.followersCount = json.getInt("followers_count");
            u.followingCount = json.getInt("friends_count");
            u.tagline = json.getString("description");

        } catch (JSONException e) {
            // FISME: do something
            e.printStackTrace();
        }

        return u;
    }
}
