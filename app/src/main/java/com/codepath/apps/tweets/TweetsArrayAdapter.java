package com.codepath.apps.tweets;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.codepath.apps.tweets.models.Tweet;

import java.util.List;

/**
 * Created by nidhikulkarni on 2/16/16.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {


    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }
}
