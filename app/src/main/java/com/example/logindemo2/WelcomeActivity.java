package com.example.logindemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    TextView welcomeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        welcomeUser = (TextView) findViewById(R.id.welcomeUser);

        Bundle extras = getIntent().getExtras();
        String name = null;
        String userType;

        if (extras != null) {
            name = extras.getString("name");
            userType = extras.getString("type");

            welcomeUser.setText("Bienvenue " + name + " vous êtes connecté en tant que " + userType);
        }
    }
}
