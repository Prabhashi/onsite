package com.example.tjr.onsite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Const;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.model.json.Comment;

import java.util.List;

/**
 * Created by TJR on 2/24/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    private List<Comment> comments;
    private LayoutInflater inflater;
    private Context context;
    private CommentAdapter adapter;

    public CommentAdapter(List<Comment> comments, LayoutInflater inflater, Context context) {
        this.comments = comments;
        this.inflater = inflater;
        this.context = context;
        this.adapter = this;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.comment_list_item,parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.commenterName.setText(comment.getCommentor().getFullName());
        holder.commentBody.setText(comment.getCommentBody());

        //set image to the comment
        ImageLoader imageLoader = MyVolley.getImageLoader();
        imageLoader.get(comment.getCommentor().getProfilePicUrl(),ImageLoader.getImageListener( holder.commenterImage,0,0));
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder{
        TextView commenterName, commentBody;
        ImageView commenterImage;

        public CommentHolder(View itemView) {
            super(itemView);
            commenterName = (TextView)  itemView.findViewById(R.id.txt_issue_home_user_name);
            commentBody = (TextView) itemView.findViewById(R.id.txt_issue_home_comment_body);
            commenterImage =(ImageView)itemView.findViewById(R.id.im_issue_home_commenter_photo);
        }
    }
}
