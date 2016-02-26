package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.tweets.TwitterApplication;
import com.codepath.apps.tweets.TwitterClient;
import com.codepath.apps.tweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nidhikulkarni on 2/25/16.
 */
public class HomeTimelineFragment extends TweetListFragment {

    private TwitterClient client;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    @Override
    public void loadMoreData() {
        populateTimeline();
    }

    private void populateTimeline() {
        long lastTweetId = -1;
        Tweet oldestTweet = this.getOldestTweet();
        if (oldestTweet != null) {
            lastTweetId = oldestTweet.getUid();
        }

        client.getHomeTimeline(lastTweetId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.i("INFO", response.toString());
                List<Tweet> tweets = new ArrayList<Tweet>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject tweetJSON = response.getJSONObject(i);
                        tweets.add(Tweet.fromJSON(tweetJSON));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                addAll(tweets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                // FIXME: do something
                //Toast.makeText(TimelineActivity.this, "Sorry, unable to load the timeline.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
