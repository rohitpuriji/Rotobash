package com.tech.rotobash.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tech.rotobash.R;

import java.util.ArrayList;
import java.util.Base64;

/**
 * Created by sombirbisht on 17/1/18.
 */

public class ChannelSpinnerForPay extends BaseAdapter{
    LayoutInflater inflater = null;
    ArrayList<String> list;
    Context context;

    public ChannelSpinnerForPay(Context context, ArrayList<String> list) {
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


        View myView = inflater.inflate(R.layout.custom_spinner_layout, null);

        TextView custom_channel_type = myView.findViewById(R.id.custom_channel_type);

        custom_channel_type.setText(list.toString());
        return myView;
    }
}
