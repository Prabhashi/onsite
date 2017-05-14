package com.example.tjr.onsite.adapter;

import android.widget.Filter;

import com.example.tjr.onsite.model.json.User;

import java.util.ArrayList;

/**
 * Created by TJR on 3/22/2017.
 */

public class UserFilter extends Filter {
    SelectUserAdapter adapter;
    ArrayList<User> filterList;

    public UserFilter(SelectUserAdapter adapter, ArrayList<User> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<User> filteredUsers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getFullName().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredUsers.add(filterList.get(i));
                }
            }

            results.count=filteredUsers.size();
            results.values=filteredUsers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.users= (ArrayList<User>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
