package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button phoneVerificationButton;
    private Button emailVerificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneVerificationButton = findViewById(R.id.btnPhoneVerification);
        emailVerificationButton = findViewById(R.id.btnEmailVerification);

        phoneVerificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityPhoneVerification.class);
                startActivity(intent);
            }
        });

        emailVerificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityEmailVerification.class);
                startActivity(intent);
            }
        });
    }
}
