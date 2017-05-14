package com.example.tjr.onsite.ui.common;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tjr.onsite.R;
import com.example.tjr.onsite.adapter.MessageAdapter;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.controllers.MessageController;
import com.example.tjr.onsite.model.json.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {
    public MessageController controller;
    private RecyclerView recyclerView;
    public MessageAdapter messageAdapter;
    private Button sendButton;
    public EditText msgBodyTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        controller = new MessageController(this);
        recyclerView =(RecyclerView) findViewById(R.id.rec_list_message);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        msgBodyTxt = (EditText)findViewById(R.id.edit_message_message);
        sendButton = (Button) findViewById(R.id.btn_message_send);
        messageAdapter = new MessageAdapter(new ArrayList<Message>(),this);
        recyclerView.setAdapter(messageAdapter);

        final String receiver = getIntent().getStringExtra("receiver");
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Map<String, String> map = new HashMap<>();
                map.put("senderId", Globals.userId + "");
                map.put("messageBody", msgBodyTxt.getText().toString());
                map.put("recieverUsername", receiver);
                controller.sendMessge(map);
            }
        });
    }
}
