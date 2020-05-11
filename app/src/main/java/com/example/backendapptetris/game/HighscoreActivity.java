package com.example.backendapptetris.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.backendapptetris.MainActivity;
import com.example.backendapptetris.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HighscoreActivity extends AppCompatActivity {

    private static final String TAG = "TetrisActivity";
    private static final String KEY_NAVN = "navn";
    private static final String KEY_SCORE = "score";

    private Button playAgain, saveScore, logOut;
    private TextView gameOver, playerScoreTextView;
    private String studienr, userFirstName;
    private Integer playerScore;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        playAgain = findViewById(R.id.playAgainButton);
        saveScore = findViewById(R.id.saveScoreButton);
        gameOver = findViewById(R.id.gameOverText);
        playerScoreTextView = findViewById(R.id.playerScoreText);
        logOut = findViewById(R.id.logOutButton);

        // Henter spiller data fra intents, skal bruge studienr og navn til databasen
        Intent intent = getIntent();
        studienr = intent.getStringExtra("studienr");
        userFirstName = intent.getStringExtra("navn");
        playerScore = intent.getIntExtra("score", 0);

        playerScoreTextView.setText("Score: " + playerScore);

    }

    // Onclick på Gem Highscore knappen
    public void saveScore(View v) {

        // Opretter Firebase dokument med brugerens studienr som ID
        Map<String, Object> score = new HashMap<>();
        score.put(KEY_NAVN, userFirstName);
        String scoreString = playerScore.toString();
        score.put(KEY_SCORE, scoreString);
        db.collection("Highscores").document(studienr).set(score)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    // Kode der kører hvis scoren gemmes uden problemer i databasen
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(HighscoreActivity.this, "Highscore gemt.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {

            // Kode der kører hvis der opstod et problem og scoren ikke kunne gemmes i databasen.
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HighscoreActivity.this, "Fejl: Kunne ikke gemme Highscore", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });
    }
    public void playAgain(View v){
        Intent intent = new Intent(this, GameMain.class);
        intent.putExtra("studienr", studienr);
        intent.putExtra("navn", userFirstName);
        startActivity(intent);
        finish();
    }

    public void logOut(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
