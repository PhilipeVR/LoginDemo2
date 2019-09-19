package com.example.logindemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button btnForLogin;
    EditText nameEditText;
    EditText passwordEditText;
    TextView createNewAccount;
    DatabaseHelper databaseHelper;
    String typeOfUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnForLogin = (Button) findViewById(R.id.btnForLogin);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        createNewAccount = (TextView) findViewById(R.id.createNewAccount);

        databaseHelper = new DatabaseHelper(MainActivity.this);


        nameEditText = (EditText) findViewById(R.id.nameEditText);

        Bundle extras = getIntent().getExtras();
        typeOfUser = null;
        if (extras != null) {
            typeOfUser = extras.getString("user");
        }

        btnForLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExist = databaseHelper.checkUserExist(nameEditText.getText().toString(),passwordEditText.getText().toString(),typeOfUser);

                if (isExist) {
                    Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
                    intent.putExtra("name", nameEditText.getText().toString());
                    if(nameEditText.getText().toString().equals("admin")){
                        typeOfUser = "admin";
                    }
                    intent.putExtra("type",typeOfUser);
                    startActivity(intent);
                }
                else{
                    passwordEditText.setText(null);
                    Toast.makeText(MainActivity.this, "Login Failed Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createNewAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent2 = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent2);
            }
        });


    }
}
