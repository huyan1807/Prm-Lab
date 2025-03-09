package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityEmailVerification extends AppCompatActivity {

    private AppCompatButton btnRegister;
    private EditText editTextTextEmailAddress, editTextPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_email_verification);
        init();

        btnRegister.setOnClickListener(v -> {
            String email = editTextTextEmailAddress.getText().toString();
            String password = editTextPassword.getText().toString();

            if (!email.isEmpty() && !password.isEmpty()) {
                btnRegister.setEnabled(false);
                btnRegister.setText("Loading...");

                signUpUser(email, password);
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signUpUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            if (user.isEmailVerified()) {
                                Toast.makeText(ActivityEmailVerification.this, "Login successful. Redirecting...", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ActivityEmailVerification.this, HomeActivity.class));
                                finish();
                            } else {
                                user.sendEmailVerification()
                                        .addOnCompleteListener(emailTask -> {
                                            if (emailTask.isSuccessful()) {
                                                Toast.makeText(ActivityEmailVerification.this, "Verification email sent. Please check your email.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(ActivityEmailVerification.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                            }
                                            resetButton();
                                        });
                            }
                        }
                    } else {
                        signInUser(email, password);
                    }
                });
    }

    private void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            // Redirect to HomeActivity
                            Toast.makeText(ActivityEmailVerification.this, "Login successful. Redirecting...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ActivityEmailVerification.this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(ActivityEmailVerification.this, "Please verify your email before logging in.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ActivityEmailVerification.this, "Authentication failed. Check your credentials.", Toast.LENGTH_SHORT).show();
                    }
                    resetButton();
                });
    }

    private void resetButton() {
        btnRegister.setEnabled(true);
        btnRegister.setText("Register");
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }
}
