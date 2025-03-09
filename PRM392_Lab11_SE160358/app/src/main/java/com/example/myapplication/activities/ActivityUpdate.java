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

public class ActivityUpdate extends AppCompatActivity {

    private TraineeService service;
    EditText etId, etName, etEmail, etPhone, etGender;
    Button btnUpdate, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etGender = findViewById(R.id.etGender);
        btnUpdate = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        btnUpdate.setOnClickListener(v -> updateTrainee());
        btnBack.setOnClickListener(v -> finish());

        service = TraineeRepository.getTraineeService();
    }

    private void updateTrainee() {
        String id = etId.getText().toString();
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String gender = etGender.getText().toString();

        if (id.isEmpty()) {
            Toast.makeText(ActivityUpdate.this, "ID is required", Toast.LENGTH_SHORT).show();
            return;
        }

        Trainee trainee = new Trainee(name, email, phone, gender);

        try {
            Call<Trainee> call = service.updateTrainee(Integer.parseInt(id), trainee);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                    if (response.body() != null) {
                        Toast.makeText(ActivityUpdate.this, "Update successful", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ActivityUpdate.this, "Update failed", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Trainee> call, Throwable t) {
                    Toast.makeText(ActivityUpdate.this, "Update failed", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }
}
