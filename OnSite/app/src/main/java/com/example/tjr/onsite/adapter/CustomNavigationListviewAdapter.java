package com.example.tjr.onsite.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tjr.onsite.R;


public class CustomNavigationListviewAdapter extends BaseAdapter {
    Context context;
    String[] navigationListString;
    Activity activity;

    public CustomNavigationListviewAdapter(Context context, String[] navigationListString, Activity activity) {
        this.context = context;
        this.navigationListString = navigationListString;
        this.activity = activity;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gvContent = inflater.inflate(R.layout.navigation_item_layout, null);

        final TextView fineIDView = (TextView) gvContent.findViewById(R.id.navigationItemDisplay);
        fineIDView.setText(navigationListString[position]);

        gvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(context, ReportBugActivity.class);
                //context.startActivity(i);
                activity.finish();
            }
        });
        return gvContent;
    }

    @Override
    public int getCount() {
        return navigationListString.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
