package com.example.backendapptetris;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.backendapptetris.REST.JavalinApi;
import com.example.backendapptetris.game.GameMain;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private String studienr, password, userFirstName;
    private EditText userNameEditText, passwordEditText;
    private Button loginButton;
    private JavalinApi javalinApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
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

        Call<String> call = javalinApi.createPost(studienr, password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()){
                    userFirstName = response.body();
                    launchGame();
                } else {
                    Toast.makeText(MainActivity.this, "Forkert brugernavn eller adgangskode", Toast.LENGTH_LONG).show();
                    System.out.println("Responskode: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

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
