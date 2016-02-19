package com.codepath.apps.tweets;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.tweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;

    private List<Tweet> timeline;

    private TweetsArrayAdapter adapter;

    private ListView lvTweets;

    private ComposeTweetFragment composeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showComposeView();
            }
        });

        lvTweets = (ListView) findViewById(R.id.lvTweets);
        timeline = new ArrayList<>();
        adapter = new TweetsArrayAdapter(this, timeline);
        lvTweets.setAdapter(adapter);

        client = TwitterApplication.getRestClient();
        populateTimeline();

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                populateTimeline();
                return true;
            }
        });
    }

    private void populateTimeline() {
        long lastTweetId = -1;
        if (!timeline.isEmpty()) {
            Tweet oldestTweet = timeline.get(timeline.size() - 1);
            lastTweetId = oldestTweet.getUid();
        }

        client.getHomeTimeline(lastTweetId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.i("INFO", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject tweetJSON = response.getJSONObject(i);
                        timeline.add(Tweet.fromJSON(tweetJSON));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                // FIXME: do something
                Toast.makeText(TimelineActivity.this, "Sorry, unable to load the timeline.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void composeTweet(String status) {
        client.composeTweet(status, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("INFO", response.toString());
                timeline.add(0, Tweet.fromJSON(response));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(TimelineActivity.this, "Sorry, unable to tweet.", Toast.LENGTH_SHORT).show();
                throwable.printStackTrace();
            }
        });
    }

    public void showComposeView() {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        composeFragment = new ComposeTweetFragment();
        composeFragment.show(ft, "COMPOSE");

    }

    public void handleTweetClicked(View view) {
        String status = composeFragment.getNewTweet();
        if (status.length() > 0) {
            composeTweet(status);
        } else {
            // FIXME: handle this properly
            Toast.makeText(TimelineActivity.this, "Unable to tweet.", Toast.LENGTH_SHORT).show();
        }
        composeFragment.dismiss();
    }

    public void handleCancelClicked(View view) {
        composeFragment.dismiss();
    }

}
