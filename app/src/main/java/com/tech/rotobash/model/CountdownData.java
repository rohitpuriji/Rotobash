package com.tech.rotobash.model;

import android.widget.TextView;

/**
 * Created by rohitpuri on 16/1/18.
 */

public class CountdownData {

    private TextView tvTimer;
    private MatchesData mMatchesData;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public TextView getTvTimer() {
        return tvTimer;
    }

    public void setTvTimer(TextView tvTimer) {
        this.tvTimer = tvTimer;
    }

    public MatchesData getmMatchesData() {
        return mMatchesData;
    }

    public void setmMatchesData(MatchesData mMatchesData) {
        this.mMatchesData = mMatchesData;
    }
}
