package com.example.backendapptetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TetrisActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private Button toHighscoreButton;
    private String studienr;
    private String userFirstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);

        userNameTextView = findViewById(R.id.userNameTextView);
        toHighscoreButton = findViewById(R.id.button2);

        Intent intent = getIntent();
        studienr = intent.getStringExtra("studienr");
        userFirstName = intent.getStringExtra("navn");

        userNameTextView.setText(userFirstName);

    }

    public void openHighscore(View v){
        Intent intent = new Intent(this, HighscoreActivity.class);
        intent.putExtra("studienr", studienr);
        intent.putExtra("navn", userFirstName);
        startActivity(intent);
        finish();
    }

}
