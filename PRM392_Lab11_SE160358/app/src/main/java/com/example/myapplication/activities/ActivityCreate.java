package com.example.myapplication.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Trainee;
import com.example.myapplication.TraineeRepository;
import com.example.myapplication.TraineeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCreate extends AppCompatActivity {

    private TraineeService service;
    EditText etName, etEmail, etPhone, etGender;
    Button btnSave, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etGender = findViewById(R.id.etGender);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> save());
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        service = TraineeRepository.getTraineeService();
    }

    private void save() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String gender = etGender.getText().toString();

        Trainee trainee = new Trainee(name, email, phone, gender);

        try {
            Call<Trainee> call = service.createTrainee(trainee);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                    if (response.body() != null) {
                        Toast.makeText(ActivityCreate.this, "Save successfully", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Trainee> call, Throwable t) {
                    Toast.makeText(ActivityCreate.this, "Save failed", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }
}