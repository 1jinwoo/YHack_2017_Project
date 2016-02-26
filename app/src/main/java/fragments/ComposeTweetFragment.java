package fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.codepath.apps.tweets.R;
import com.codepath.apps.tweets.TwitterApplication;
import com.codepath.apps.tweets.TwitterClient;
import com.codepath.apps.tweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by nidhikulkarni on 2/18/16.
 */
public class ComposeTweetFragment  extends DialogFragment {

    private EditText etNewTweet;
    private TwitterClient client;
    private ComposeTweetListener listener;

    public interface ComposeTweetListener {
        public void onTweetCreated(Tweet tweet);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.compose_layout, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient();


        Activity parentActivity = getActivity();
        if (!(parentActivity instanceof ComposeTweetListener)) {
            throw new ClassCastException(parentActivity.getClass().getName() + " must implement ComposeTweetListener!");
        }

        listener = (ComposeTweetListener) parentActivity;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etNewTweet = (EditText) view.findViewById(R.id.etNewTweet);

        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setTitle("Compose Tweet");

    }

    private String getNewTweet() {
        String status = etNewTweet.getText().toString();

        if (status.length() > 140) {
            return status.substring(0, 140);
        }

        return status;
    }

    public void composeTweet() {
        String status = getNewTweet();

        if (status.length() > 0) {
            client.composeTweet(status, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.i("INFO", response.toString());
                    listener.onTweetCreated(Tweet.fromJSON(response));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }
        else {
            // FIXME: handle this properly
            Log.e("ERROR", "Length of tweet was 0. Unable to tweet.");
//            Toast.makeText(TimelineActivity.this, "Unable to tweet.", Toast.LENGTH_SHORT).show();
        }
    }
}
