package com.example.tjr.onsite.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tjr.onsite.R;
import com.example.tjr.onsite.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TJR on 3/15/2017.
 */
public class CustomAdapterList extends ArrayAdapter<User> implements Filterable {
   public ArrayList<User> Users, tempUser, suggestions;

    public  CustomAdapterList(Context context, ArrayList<User> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.Users = objects;
        this.tempUser = new ArrayList<User>(objects);
        this.suggestions = new ArrayList<User>(objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_user, parent, false);
        }
        TextView txtUser = (TextView) convertView.findViewById(R.id.txt_list_user_fullname);
        TextView txtUsername = (TextView) convertView.findViewById(R.id.txt_list_user_username);
        if (txtUser != null)
            txtUser.setText(user.fullName);
        if (txtUser!= null )
            txtUsername.setText(user.username);
        // Now assign alternate color for rows
        if (position % 2 == 0)
            convertView.setBackgroundColor(Color.rgb(230,230,230));
        else
            convertView.setBackgroundColor(Color.rgb(220,220,220));

        return convertView;
    }


    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            User user = (User) resultValue;
            return user.fullName;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (User people : tempUser) {
                    if (people.fullName.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<User> c = (ArrayList<User>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (User cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
        }
    };
}