package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.activities.ActivityCreate;
import com.example.myapplication.activities.ActivityList;
import com.example.myapplication.activities.ActivityUpdate;

public class MainActivity extends AppCompatActivity {

    Button btnViewList, btnAddTrainee, btnUpdateTrainee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnViewList = findViewById(R.id.btnViewList);
        btnAddTrainee = findViewById(R.id.btnAddTrainee);
        btnUpdateTrainee = findViewById(R.id.btnUpdateTrainee);

        btnViewList.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityList.class);
            startActivity(intent);
        });

        btnAddTrainee.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityCreate.class);
            startActivity(intent);
        });

        btnUpdateTrainee.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityUpdate.class);
            startActivity(intent);
        });
    }
}
