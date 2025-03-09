package com.example.myapplication.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.myapplication.R;
import com.example.myapplication.Trainee;
import com.example.myapplication.TraineeAdapter;
import com.example.myapplication.TraineeRepository;
import com.example.myapplication.TraineeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityList extends AppCompatActivity {

    private TraineeService service;
    private RecyclerView recyclerView;
    private TraineeAdapter adapter;
    private EditText etSearch;
    private Button btnSearch, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);

        service = TraineeRepository.getTraineeService();
        loadAllTrainees();

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        btnSearch.setOnClickListener(v -> searchTraineeById());
    }

    private void loadAllTrainees() {
        Call<List<Trainee>> call = service.getAllTrainees();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Trainee>> call, Response<List<Trainee>> response) {
                if (response.body() != null) {
                    adapter = new TraineeAdapter(response.body(), ActivityList.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(ActivityList.this, "Failed to load trainees", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Trainee>> call, Throwable t) {
                Toast.makeText(ActivityList.this, "Failed to load trainees", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchTraineeById() {
        String id = etSearch.getText().toString();
        if (id.isEmpty()) {
            Toast.makeText(this, "Enter ID to search", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<Trainee> call = service.getTrainee(Integer.parseInt(id));
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                if (response.body() != null) {
                    // Display the found trainee in the list
                    adapter = new TraineeAdapter(List.of(response.body()), ActivityList.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(ActivityList.this, "Trainee not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Trainee> call, Throwable t) {
                Toast.makeText(ActivityList.this, "Search failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
