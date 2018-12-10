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

public class SignUpActivity extends AppCompatActivity {

    private EditText first_name;
    private EditText last_name;
    private EditText email;
    private EditText password;
    private EditText confirm_password;
    private Button sign_up_button;
    private ProgressBar sign_up_progress;
    private static FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        sign_up_button = findViewById(R.id.sign_up_button);
        sign_up_progress = findViewById(R.id.sign_up_progress);
        mAuth = FirebaseAuth.getInstance();


        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_name_str = first_name.getText().toString().trim();
                String last_name_str = last_name.getText().toString().trim();
                String email_str = email.getText().toString().trim();
                String password_str = password.getText().toString().trim();
                String confirm_password_str = confirm_password.getText().toString().trim();

                if(validDataInputs(first_name_str, last_name_str, email_str, password_str, confirm_password_str)){

                    sign_up_progress.setVisibility(View.VISIBLE);

                    mAuth.createUserWithEmailAndPassword(email_str, password_str)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            sign_up_progress.setVisibility(View.GONE);

                            if(task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this, "Account creation successful.", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUpActivity.this, LoggedInActivity.class));
                                finish();
                            }else{
                                Toast.makeText(SignUpActivity.this, "Failed to created account. Try again.", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            }
        });

    }

    private boolean validDataInputs(String first_name_str, String last_name_str, String email_str, String password_str, String confirm_password_str) {
        boolean isValid = true;

        if(TextUtils.isEmpty(first_name_str)){
            Toast.makeText(SignUpActivity.this, "Enter first name", Toast.LENGTH_SHORT).show();
            isValid = false;
        }else if(TextUtils.isEmpty(last_name_str)){
            Toast.makeText(SignUpActivity.this, "Enter last name", Toast.LENGTH_SHORT).show();
            isValid = false;
        }else if(TextUtils.isEmpty(email_str)){
            Toast.makeText(SignUpActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
            isValid = false;
        }else if(TextUtils.isEmpty(password_str)){
            Toast.makeText(SignUpActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
            isValid = false;
        }else if(TextUtils.isEmpty(confirm_password_str)){
            Toast.makeText(SignUpActivity.this, "Confirm password", Toast.LENGTH_SHORT).show();
            isValid = false;
        }else if(password_str.length() < 6){
            Toast.makeText(SignUpActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
            isValid = false;
        }else if(!TextUtils.equals(password_str, confirm_password_str)){
            Toast.makeText(SignUpActivity.this, "Passwords don't match.", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }
}
