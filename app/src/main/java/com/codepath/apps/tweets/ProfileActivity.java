package com.codepath.apps.tweets;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.codepath.apps.tweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import fragments.UserTimelineFragment;

public class ProfileActivity extends AppCompatActivity {

    private TwitterClient client;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        client = TwitterApplication.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
                getSupportActionBar().setTitle("@" + user.getScreenName());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable); // FIXME: do something real
            }
        });

        if (savedInstanceState == null) {
            // get screen name
            String screenName = getIntent().getStringExtra("screen_name");
            UserTimelineFragment timelineFragment = UserTimelineFragment.newInstance(screenName);

            // display user fragment
            FragmentTransaction trns = getSupportFragmentManager().beginTransaction();
            trns.replace(R.id.flContainer, timelineFragment);
            trns.commit();
        }


    }

}
