package com.example.techchat;
import android.content.Intent;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techchat.ChatActivity;
import com.example.techchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    EditText editTextEmail, editTextPassword,firstName,lastName;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myusers = database.getReference("users");

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


        LinearLayout l1=findViewById(R.id.layoutsignup);
        l1.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_in));

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signup).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 4) {
            editTextPassword.setError("Minimum lenght of password should be 4");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    createMessageGroup();
                    myusers.child(editTextEmail.getText().toString().split("@")[0]).child("userdetails").setValue(new User(firstName.getText().toString(),lastName.getText().toString(),editTextEmail.getText().toString()));
                    finish();

                    CurrentData.loginEmail=editTextEmail.getText().toString();
                    CurrentData.name=firstName.getText().toString()+" "+lastName.getText().toString();

                    startActivity(new Intent(SignUpActivity.this, MainWindow.class));
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    private void createMessageGroup() {
       myusers.child(editTextEmail.getText().toString().split("@")[0]).child("message").push().setValue(new Message());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup:
                registerUser();
                break;

            case R.id.login:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}