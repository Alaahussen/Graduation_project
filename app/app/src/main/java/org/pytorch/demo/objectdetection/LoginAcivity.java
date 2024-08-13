package org.pytorch.demo.objectdetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAcivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acivity);
        emailEditText = findViewById(R.id.inputEmail);
        passwordEditText = findViewById(R.id.inputPassword);
        TextView registerButton = findViewById(R.id.textViewSignUp);
        TextView passwordRecoveryButton = findViewById(R.id.forgotPassword);
        Button loginButton = findViewById(R.id.btnlogin);
        //Button googleSignInButton = findViewById(R.id.btnGoogle);

        firebaseAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                loginUser(email, password);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAcivity.this, RegisterActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        passwordRecoveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                sendPasswordResetEmail(email);
            }
        });

        /*googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });*/
    }


    private void loginUser(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginAcivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // User logged in successfully
                                Toast.makeText(LoginAcivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginAcivity.this, FirstActivity.class));
                                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                finish();
                            }
                            else {
                                // Login failed
                                if (task.getException() != null) {
                                    String errorMessage = task.getException().getMessage();
                                    if (errorMessage.contains("password")) {
                                        Toast.makeText(LoginAcivity.this, "Incorrect password.", Toast.LENGTH_SHORT).show();
                                    } else if (errorMessage.contains("email")) {
                                        Toast.makeText(LoginAcivity.this, "Incorrect email.", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(LoginAcivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(LoginAcivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
    }
    private void sendPasswordResetEmail(String email) {
        if (email.isEmpty()) {
            // Email is empty, show an error message
            Toast.makeText(LoginAcivity.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Password reset email sent successfully
                                Toast.makeText(LoginAcivity.this, "Password reset email sent.", Toast.LENGTH_SHORT).show();
                            } else {
                                // Failed to send password reset email
                                Toast.makeText(LoginAcivity.this, "Failed to send password reset email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
