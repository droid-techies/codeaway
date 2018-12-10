package com.ruzaki.codeaway;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login_button;
    private Button sign_up_button;
    private ProgressBar login_progress;
    private static FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_button = findViewById(R.id.login_button);
        sign_up_button = findViewById(R.id.sign_up_button);
        login_progress = findViewById(R.id.login_progress);
        mAuth = FirebaseAuth.getInstance();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_str = username.getText().toString().trim();
                String password_str = password.getText().toString().trim();

                if(isUserNamePasswordValid(username_str, password_str)){
                    login_progress.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(username_str, password_str)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    login_progress.setVisibility(View.GONE);

                                    if(task.isSuccessful()){
                                        Toast.makeText(LoginActivity.this, "Login successful.", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(LoginActivity.this, LoggedInActivity.class));
                                        finish();
                                    }else{
                                        Toast.makeText(LoginActivity.this, "Username or password is invalid.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
    }

    private boolean isUserNamePasswordValid(String username_str, String password_str) {
        boolean is_valid = true;

        if(TextUtils.isEmpty(username_str)){
            Toast.makeText(getApplicationContext(), "Username is blank.", Toast.LENGTH_SHORT).show();
            is_valid = false;
        }else if (TextUtils.isEmpty(password_str)){
            Toast.makeText(getApplicationContext(), "Password is blank.", Toast.LENGTH_SHORT).show();
            is_valid = false;
        }

        return is_valid;
    }
}
