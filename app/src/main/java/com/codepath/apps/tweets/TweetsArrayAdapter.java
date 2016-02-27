package com.codepath.apps.tweets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.tweets.models.Tweet;
import com.codepath.apps.tweets.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nidhikulkarni on 2/16/16.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    private UserSelectedListener listener;

    public interface UserSelectedListener {
        public void handleUserSelected(User user);
    }

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    public void setListener(UserSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Tweet tweet = this.getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);

        }

        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvTime= (TextView) convertView.findViewById(R.id.tvTime);

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUserSelected(tweet.getUser());
            }
        });

        ivProfileImage.setImageResource(0);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageURL()).into(ivProfileImage);

        tvUsername.setText("@" + tweet.getUser().getScreenName());
        tvName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        tvTime.setText(tweet.getRelativeTimestamp());


        return convertView;
    }

    public void handleUserSelected(User user) {
        listener.handleUserSelected(user);
    }


}
