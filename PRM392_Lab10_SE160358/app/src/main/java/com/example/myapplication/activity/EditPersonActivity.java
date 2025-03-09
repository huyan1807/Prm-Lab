package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.example.myapplication.AppExecutors;

import com.example.myapplication.AppDatabase;
import com.example.myapplication.AppExecutors;
import com.example.myapplication.Constants;
import com.example.myapplication.R;
import com.example.myapplication.person.Person;

public class EditPersonActivity extends AppCompatActivity {
    private EditText edtFirstName;
    private EditText edtLastName;

    private Button btnSave;
    private long personId;

    private Intent intent;
    private AppDatabase database;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_edit_person);

        Toolbar toolbar = findViewById(R.id.toolbarEdit);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Edit");
        }

        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view -> onSaveButtonClicked());

        btnSave.setEnabled(false);

        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constants.APP_DATABASE).build();

        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.INTENT_PERSON_ID)) {
            btnSave.setText("Update person");
            personId = intent.getLongExtra(Constants.INTENT_PERSON_ID, -1);

            AppExecutors.getInstance().diskIO().execute(() -> {
                Person person = database.personDao().loadPersonById(personId);
                runOnUiThread(() -> {
                    populateUI(person);
                    btnSave.setEnabled(true);
                });
            });
        } else {
            btnSave.setEnabled(true);
        }
    }

    private void populateUI(Person person) {
        if (person == null) {
            return;
        }

        edtFirstName.setText(person.getFirstName());
        edtLastName.setText(person.getLastName());
    }

    public void onSaveButtonClicked() {
        String firstName = edtFirstName.getText() != null ? edtFirstName.getText().toString() : "";
        String lastName = edtLastName.getText() != null ? edtLastName.getText().toString() : "";

        if (firstName.isEmpty() || lastName.isEmpty()) {
            Toast.makeText(this, "Both first name and last name are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        final Person person = new Person(firstName, lastName);

        AppExecutors.getInstance().diskIO().execute(() -> {
            if (intent != null && !intent.hasExtra(Constants.INTENT_PERSON_ID)) {
                database.personDao().insert(person);
            } else {
                person.setUid(personId);
                database.personDao().update(person);
            }
            finish();
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
