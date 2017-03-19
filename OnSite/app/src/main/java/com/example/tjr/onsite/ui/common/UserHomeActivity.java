package com.example.tjr.onsite.ui.common;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.tjr.onsite.R;
import com.example.tjr.onsite.app.Globals;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.model.DataSource;
import com.example.tjr.onsite.model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UserHomeActivity extends AppCompatActivity {

    ImageView editFullNameImage;
    ImageView editUsernameImage;
    ImageView editEmailImage;
    ImageView editPhoneImage;

    EditText fullNameEditText;
    EditText usernameEditText;
    EditText emailEditText;
    EditText phoneEditText;


    TextView rateUserTextView;
    private ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        Intent i = getIntent();
        int id = i.getIntExtra("userId",0);
        DataSource.retrieveUserData(this,id);
        editFullNameImage = (ImageView)findViewById(R.id.img_userhome_edit);
        editUsernameImage = (ImageView)findViewById(R.id.img_userhome_edit_username);
        editEmailImage = (ImageView)findViewById(R.id.img_userhome_edit_email);
        editPhoneImage = (ImageView)findViewById(R.id.img_userhome_edit_phone);
        profilePicture = (ImageView) findViewById(R.id.img_userhome_profile);


        fullNameEditText = (EditText) findViewById(R.id.edit_userhome_fullname);
        usernameEditText = (EditText) findViewById(R.id.edit_userhome_username);
        emailEditText = (EditText) findViewById(R.id.edit_userhome_email);
        phoneEditText = (EditText) findViewById(R.id.edit_userhome_phone);
        rateUserTextView = (TextView) findViewById(R.id.txt_userhome_rate);



        if(i.getIntExtra("userId",0) == Globals.userId)
            hideEditButtons();
        else
            addEditActionListeners();
    }

    private void hideEditButtons() {
        editFullNameImage.setVisibility(View.INVISIBLE);
        editUsernameImage.setVisibility(View.INVISIBLE);
        editEmailImage.setVisibility(View.INVISIBLE);
        editPhoneImage.setVisibility(View.INVISIBLE);


    }

    private void addEditActionListeners(){
        editFullNameImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullNameEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
        //TODO update the rest
    }

    public void updateUserData(User u){

        fullNameEditText.setText(u.fullName);
        usernameEditText.setText(u.username);
        emailEditText.setText(u.email);
        phoneEditText.setText(u.phone);

        ImageLoader imageLoader = MyVolley.getImageLoader();
        imageLoader.get(u.imageUrl,ImageLoader.getImageListener(profilePicture,0,0));

    }
}
