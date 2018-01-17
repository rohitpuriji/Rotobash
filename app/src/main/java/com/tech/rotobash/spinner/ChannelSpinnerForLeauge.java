package com.tech.rotobash.spinner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tech.rotobash.R;
import com.tech.rotobash.model.LeagueContestData;

import java.util.ArrayList;

/**
 * Created by sombirbisht on 17/1/18.
 */

public class ChannelSpinnerForLeauge extends BaseAdapter {
    LayoutInflater inflater = null;
    ArrayList<LeagueContestData> list;
    Context context;

    public ChannelSpinnerForLeauge(Context context, ArrayList<LeagueContestData> list) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.e("name:", list.get(position).getName());
        Log.e("league size:", list.size()+"");

        View myView = inflater.inflate(R.layout.custom_spinner_layout, null);

        TextView custom_channel_type = myView.findViewById(R.id.custom_channel_type);

        custom_channel_type.setText(list.get(position).getName());
        return myView;
    }
}
