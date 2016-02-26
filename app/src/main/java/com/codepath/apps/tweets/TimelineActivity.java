package com.codepath.apps.tweets;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.codepath.apps.tweets.models.Tweet;

import java.util.Arrays;

import fragments.ComposeTweetFragment;
import fragments.HomeTimelineFragment;
import fragments.TweetListFragment;

public class TimelineActivity extends AppCompatActivity implements ComposeTweetFragment.ComposeTweetListener {

    private TweetListFragment fragmentHomeTimeline;
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

        if (savedInstanceState == null) {
            fragmentHomeTimeline = (HomeTimelineFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_home_timeline);
        } else {
            //activity is already in memory, inflated already
        }

    }

    public void showComposeView() {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        composeFragment = new ComposeTweetFragment();
        composeFragment.show(ft, "COMPOSE");

    }

    public void handleTweetClicked(View view) {
        composeFragment.composeTweet();
        composeFragment.dismiss();
    }

    public void handleCancelClicked(View view) {
        composeFragment.dismiss();
    }

    @Override
    public void onTweetCreated(Tweet tweet) {
        fragmentHomeTimeline.addAll(Arrays.asList(tweet));
    }
}
