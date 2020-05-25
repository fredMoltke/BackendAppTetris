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
import com.example.backendapptetris.REST.JavalinApi;
import com.example.backendapptetris.data.Spiller;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.backendapptetris.MainActivity.javalinApi;

public class HighscoreActivity extends AppCompatActivity {

    private static final String TAG = "TetrisActivity";

    private Button playAgain, saveScore, logOut;
    private TextView gameOver, playerScoreTextView;
    private String studienr, userFirstName;
    private Integer playerScore;

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
        Spiller spiller = new Spiller(studienr, "", playerScore.toString());
        spiller.setFornavn(userFirstName);
        Call<Spiller> call = javalinApi.uploadHighscore(spiller);
        call.enqueue(new Callback<Spiller>() {
            @Override
            public void onResponse(Call<Spiller> call, Response<Spiller> response) {
               if (response.isSuccessful()){
                   Toast.makeText(HighscoreActivity.this, "Score gemt.", Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(HighscoreActivity.this, "Der opstod en fejl. Kunne ikke gemme score.", Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(Call<Spiller> call, Throwable t) {

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
