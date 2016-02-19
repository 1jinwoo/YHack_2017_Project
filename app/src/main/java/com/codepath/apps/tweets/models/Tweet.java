package com.codepath.apps.tweets.models;

import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by nidhikulkarni on 2/16/16.
 */
public class Tweet {

    private static String twitterTimestampFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";

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

    public String getRelativeTimestamp() {
        SimpleDateFormat sf = new SimpleDateFormat(twitterTimestampFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(createdAt).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
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
