package com.example.backendapptetris.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.backendapptetris.R;

public class GameMain extends AppCompatActivity {

    private TextView shootSomeBirds;
    private String studienr, userFirstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamemain);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_gamemain);

        shootSomeBirds = findViewById(R.id.shootSomeBirds);
        Intent intent = getIntent();
        studienr = intent.getStringExtra("studienr");
        userFirstName = intent.getStringExtra("navn");

        shootSomeBirds.setText("Skyd nogle fugle, " + userFirstName + "!");


        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameMain.this, GameActivity.class);
                intent.putExtra("navn", userFirstName);
                intent.putExtra("studienr", studienr);
                startActivity(intent);
            }
        });

        TextView highScoreTxt = findViewById(R.id.highScoreTxt);
        highScoreTxt.setText("HighScore: 0");

    }
}