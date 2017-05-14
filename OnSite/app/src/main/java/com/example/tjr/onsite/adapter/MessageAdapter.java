package com.example.tjr.onsite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tjr.onsite.R;
import com.example.tjr.onsite.model.json.Message;
import com.example.tjr.onsite.model.Project;

import java.util.List;

/**
 * Created by Raviyaa on 2017-03-22.
 */

public class MessageAdapter extends RecyclerView.Adapter <MessageAdapter.ViewMessageHolder>{
    public List<com.example.tjr.onsite.model.json.Message> messages;
    private LayoutInflater inflater;
    private Context context;
    private MessageAdapter messageAdapter ;


    public MessageAdapter(List<Message> messages,Context context){
        this.messages=messages;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        messageAdapter=this;
    }

    @Override
    public ViewMessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.list_item_message,parent,false);
        return new ViewMessageHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewMessageHolder holder, int position) {
        Message message = messages.get(position);
        holder.sender.setText(message.getSender().getFullName());
        holder.body.setText(message.getMessageBody());
        holder.timeStamp.setText((CharSequence) message.getSentTime().toString());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ViewMessageHolder extends RecyclerView.ViewHolder{
    TextView sender,body,timeStamp;
        public ViewMessageHolder(View itemView) {
            super(itemView);

            sender = (TextView)itemView.findViewById(R.id.txt_message_name);
            body = (TextView)itemView.findViewById(R.id.txt_message_body);
            timeStamp = (TextView)itemView.findViewById(R.id.txt_message_timestamp);

        }
    }
}
