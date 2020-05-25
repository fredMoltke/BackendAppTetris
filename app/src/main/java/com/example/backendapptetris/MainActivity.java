package com.example.backendapptetris;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.backendapptetris.REST.JavalinApi;
import com.example.backendapptetris.data.Bruger;
import com.example.backendapptetris.data.Spiller;
import com.example.backendapptetris.game.GameMain;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private String studienr, password, userFirstName;
    private EditText userNameEditText, passwordEditText;
    private Button loginButton;
    public static JavalinApi javalinApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://18.219.143.210:8080/")
                .build();

        javalinApi = retrofit.create(JavalinApi.class);



    }

    public void login(View v){
        studienr = userNameEditText.getText().toString();
        password = passwordEditText.getText().toString();

        createPost(studienr, password);
    }

    private void createPost(String studienr, String password){
        Spiller spiller = new Spiller(studienr, password, "");
        spiller.setFornavn("");
        Call<Bruger> call = javalinApi.login(spiller);
        call.enqueue(new Callback<Bruger>() {
            @Override
            public void onResponse(Call<Bruger> call, Response<Bruger> response) {
                if (response.isSuccessful()){
                    Bruger bruger = response.body();
                    if (bruger != null){
                        userFirstName = bruger.fornavn;
                    } else {
                        Toast.makeText(MainActivity.this, "Fejl: kunne ikke hente bruger info", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    launchGame();
                } else {
                    Toast.makeText(MainActivity.this, "Forkert brugernavn eller adgangskode", Toast.LENGTH_LONG).show();
                    System.out.println("Responskode: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Bruger> call, Throwable t) {
            }
        });
    }

    private void launchGame(){
        Intent intent = new Intent(this, GameMain.class);
        intent.putExtra("studienr", studienr);
        intent.putExtra("navn", userFirstName);
        startActivity(intent);
        finish();
    }
}
