package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.tweets.EndlessScrollListener;
import com.codepath.apps.tweets.R;
import com.codepath.apps.tweets.TweetsArrayAdapter;
import com.codepath.apps.tweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nidhikulkarni on 2/25/16.
 */
public abstract class TweetListFragment extends Fragment {

    private List<Tweet> timeline;

    private TweetsArrayAdapter adapter;

    private ListView lvTweets;

    // Inflation logic

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);

        lvTweets = (ListView) view.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(adapter);

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                loadMoreData();
                return true;
            }
        });


        return view;
    }

    // Creation lifecycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeline = new ArrayList<>();
        adapter = new TweetsArrayAdapter(getActivity(), timeline);
    }

    // API

    public void addAll(List<Tweet> tweets) {
        timeline.addAll(tweets);
        adapter.notifyDataSetChanged();
    }

    public Tweet getOldestTweet() {
        Tweet oldestTweet = null;
        if (!timeline.isEmpty()) {
            oldestTweet = timeline.get(timeline.size() - 1);
        }
        return oldestTweet;
    }

    public abstract void loadMoreData();

}
