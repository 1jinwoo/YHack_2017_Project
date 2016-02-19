package com.codepath.apps.tweets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by nidhikulkarni on 2/18/16.
 */
public class ComposeTweetFragment  extends DialogFragment {

    private EditText etNewTweet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.compose_layout, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etNewTweet = (EditText) view.findViewById(R.id.etNewTweet);

        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setTitle("Compose Tweet");

    }

    public String getNewTweet() {
        String status = etNewTweet.getText().toString();

        if (status.length() > 140) {
            return status.substring(0, 140);
        }

        return status;
    }
}
