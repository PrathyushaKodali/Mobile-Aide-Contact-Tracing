package com.example.assignment1_app.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.assignment1_app.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        final Button getChatInputButton = findViewById(R.id.chatButton);


        final EditText chatInput = (EditText) findViewById(R.id.chatInputText);
       // final String chatInputStr = chatInput.getText().toString();


        getChatInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chatInputStr = chatInput.getText().toString();
                sendTextData(chatInputStr);
            }
        });


    }

    public void sendTextData(String input){
        System.out.println(input);
    }


}