package com.example.tjr.onsite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tjr.onsite.R;

/**
 * Created by TJR on 3/21/2017.
 */

public class DashboardAdapter extends BaseAdapter {
    private final int[] imageIds;
    private Context context;
    private final String[] optionNames;

    public DashboardAdapter(Context context, String[] optionNames, int[] imageIds) {
        this.context = context;
        this.optionNames = optionNames;
        this.imageIds= imageIds;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // inflate view
            gridView = inflater.inflate(R.layout.griditem_option, null);

            // set textview value
            TextView textView = (TextView) gridView
                    .findViewById(R.id.txt_griditem_optionname);
            textView.setText(optionNames[position]);

            // set image based on option
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.img_griditem_optionimage);

            textView.setText(optionNames[position]);
            imageView.setImageResource(imageIds[position]);

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return optionNames.length;
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
