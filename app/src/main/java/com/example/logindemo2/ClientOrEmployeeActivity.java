package com.example.logindemo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ClientOrEmployeeActivity extends AppCompatActivity {

    Button btnClient;
    Button btnEmployee;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_employee);

        btnClient = (Button) findViewById(R.id.btnClient);
        btnEmployee = (Button) findViewById(R.id.btnEmployee);

        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ClientOrEmployeeActivity.this, MainActivity.class);
                intent.putExtra("user", "client");
                startActivity(intent);

            }
        });

        btnEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ClientOrEmployeeActivity.this, MainActivity.class);
                intent.putExtra("user", "employee");
                startActivity(intent);
            }
        });
    }
}
