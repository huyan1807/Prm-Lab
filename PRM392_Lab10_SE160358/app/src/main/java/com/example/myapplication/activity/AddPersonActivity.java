package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.example.myapplication.AppDatabase;
import com.example.myapplication.AppExecutors;


import com.example.myapplication.Constants;
import com.example.myapplication.R;
import com.example.myapplication.person.Person;

public class AddPersonActivity extends AppCompatActivity {

    private EditText edtFirstName, edtLastName;
    private Button btnSave;
    private AppDatabase appDatabase;
    private AppExecutors appExecutors;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add person");
        }

        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        btnSave = findViewById(R.id.btnSave);

        appDatabase = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, Constants.APP_DATABASE)
                .fallbackToDestructiveMigration()
                .build();

        appExecutors = AppExecutors.getInstance();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePerson();
            }
        });
    }

    private void savePerson() {
        String firstName = edtFirstName.getText().toString().trim();
        String lastName = edtLastName.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            Toast.makeText(this, "Please enter both first and last names", Toast.LENGTH_SHORT).show();
            return;
        }

        long uid = System.currentTimeMillis();
        Person person = new Person(firstName, lastName);
        person.setUid(uid);

        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.personDao().insert(person);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddPersonActivity.this, "Person saved successfully!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AddPersonActivity.this, PersonActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
