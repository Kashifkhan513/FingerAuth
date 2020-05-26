package com.polaris.fingerauth;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    TextView tvMesg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMesg = findViewById(R.id.tvMessage);

        BiometricManager biometricManager = BiometricManager.from(this);

        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                tvMesg.setText("Use FingerPrint Sensor to Login...");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                tvMesg.setText("Device dont have a Fingerprint...");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                tvMesg.setText("Biometric sensor is currently unavailable...");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                tvMesg.setText("Device have dont any Fingerprint saved,please check your setting...");
                break;


        }

        Executor executor= ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt=new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(MainActivity.this, "Error ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "Login Success ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(MainActivity.this, "Login Failed ", Toast.LENGTH_LONG).show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo=new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setSubtitle("Use Finger to login")
                .setNegativeButtonText("Cancel")
                .build();

        biometricPrompt.authenticate(promptInfo);


    }


}
