package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ActivityPhoneVerification extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edtPhone, edtOTP;
    private Button verifyOTPBtn, generateOTPBtn;

    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        mAuth = FirebaseAuth.getInstance();

        edtPhone = findViewById(R.id.idEdtPhoneNumber);
        edtOTP = findViewById(R.id.idEdtOtp);
        verifyOTPBtn = findViewById(R.id.idBtnVerify);
        generateOTPBtn = findViewById(R.id.idBtnGetOtp);

        edtOTP.setVisibility(View.GONE);
        verifyOTPBtn.setVisibility(View.GONE);

        generateOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    Toast.makeText(ActivityPhoneVerification.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {
                    String phone = "+84" + edtPhone.getText().toString();
                    sendVerificationCode(phone);
                }
            }
        });

        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtOTP.getText().toString())) {
                    Toast.makeText(ActivityPhoneVerification.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    verifyCode(edtOTP.getText().toString());
                }
            }
        });
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        verifyOTPBtn.setEnabled(false);
        verifyOTPBtn.setText("Loading...");

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(ActivityPhoneVerification.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(ActivityPhoneVerification.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        resetButtons();
                    }
                });
    }

    private void sendVerificationCode(String number) {
        // Disable the button and set the text to 'Sending OTP...'
        generateOTPBtn.setEnabled(false);
        generateOTPBtn.setText("Sending OTP...");

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(number)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallBack)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            Log.d("ActivityPhoneVerification", "Verification ID: " + verificationId); // Debugging purpose

            edtOTP.setVisibility(View.VISIBLE);
            verifyOTPBtn.setVisibility(View.VISIBLE);

            resetButtons();
            Toast.makeText(ActivityPhoneVerification.this, "OTP sent successfully.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                edtOTP.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            resetButtons();
            Toast.makeText(ActivityPhoneVerification.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        if (verificationId == null || verificationId.isEmpty()) {
            Toast.makeText(ActivityPhoneVerification.this, "Verification ID is null or empty. Please request the OTP again.", Toast.LENGTH_LONG).show();
            Log.e("ActivityPhoneVerification", "Verification ID is null or empty.");
            return;
        }

        verifyOTPBtn.setEnabled(false);
        verifyOTPBtn.setText("Verifying...");

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void resetButtons() {
        generateOTPBtn.setEnabled(true);
        generateOTPBtn.setText("Generate OTP");

        verifyOTPBtn.setEnabled(true);
        verifyOTPBtn.setText("Verify OTP");
    }
}
