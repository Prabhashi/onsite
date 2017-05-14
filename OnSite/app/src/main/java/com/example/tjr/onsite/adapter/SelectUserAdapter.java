package com.example.tjr.onsite.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.model.json.User;
import com.example.tjr.onsite.ui.common.SelectUserActivity;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by TJR on 3/22/2017.
 */

public class SelectUserAdapter extends RecyclerView.Adapter<SelectUserAdapter.UserViewHolder> implements Filterable{

    public SelectUserActivity context;
    public ArrayList<User> users;
    private LayoutInflater inflater;

    public SelectUserAdapter(SelectUserActivity context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listitem_usersearch,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final User user = users.get(position);
        holder.fullNameText.setText(user.getFullName());
        holder.usernameText.setText(user.getUsername());

        Glide
                .with(context)
                .load(user.getProfilePicUrl().replace("localhost", Const.BASE_IP))
                .centerCrop()
                .placeholder(R.drawable.noavatar92)
                .crossFade()
                .into(holder.profileImage);

       holder.container.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent data = new Intent();
               data.putExtra("username", user.getFullName());
               data.putExtra("imageUrl", user.getProfilePicUrl().replace("localhost",Const.BASE_IP));
               context.setResult(RESULT_OK, data);
               context.finish();
           }
       });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public Filter getFilter() {
        return new UserFilter(this,users);
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        TextView usernameText,fullNameText;
        ImageView profileImage;
        View container;
        public UserViewHolder(View itemView) {
            super(itemView);
            usernameText = (TextView) itemView.findViewById(R.id.txt_listusers_username);
            fullNameText = (TextView) itemView.findViewById(R.id.txt_listusers_fullname);
            profileImage = (ImageView) itemView.findViewById(R.id.img_listusers_profile);
            container = itemView;

        }
    }
}
