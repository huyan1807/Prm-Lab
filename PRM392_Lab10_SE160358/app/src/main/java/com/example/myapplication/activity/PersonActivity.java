package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import com.example.myapplication.AppDatabase;
import com.example.myapplication.AppExecutors;
import com.example.myapplication.Constants;
import com.example.myapplication.R;
import com.example.myapplication.person.Person;
import com.example.myapplication.person.PersonAdapter;

public class PersonActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd;
    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("Home");
        }

        fabAdd = findViewById(R.id.fab);
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(PersonActivity.this, AddPersonActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.rvPerson);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PersonAdapter(this);
        recyclerView.setAdapter(adapter);

        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constants.APP_DATABASE)
                .fallbackToDestructiveMigration()
                .build();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().diskIO().execute(() -> {
                    int position = viewHolder.getAdapterPosition();
                    List<Person> tasks = adapter.getTasks();
                    database.personDao().delete(tasks.get(position));
                    retrieveTasks();
                });
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveTasks();
    }

    private void retrieveTasks() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            final List<Person> persons = database.personDao().getAll();
            Log.v("Logging", "Logging " + persons.size() + " persons");
            runOnUiThread(() -> {
                adapter.setTasks(persons);
                adapter.notifyDataSetChanged();
            });
        });
    }
}
