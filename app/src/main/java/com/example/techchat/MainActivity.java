package com.example.techchat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.ConcurrentHashMap;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;
    private String loginUser=null;
    public static CurrentData currentData=new CurrentData();
    public static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DatabaseHandler(this);
        //db.onUpgrade();
       // SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

       // intent intent = new Intent(MainActivity.this, MainWindow.class);
      //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);



        LinearLayout l1=findViewById(R.id.signinlayout);
        l1.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_in));

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

    }
    public void userLogin(View view) {
        String email = editTextEmail.getText().toString().trim();
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
            editTextPassword.setError("Minimum length of password should be 4");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    loginUser=editTextEmail.getText().toString();
                    finish();

                    CurrentData.loginEmail=editTextEmail.getText().toString();
                    //CurrentData.name=firstName.getText().toString()+" "+lastName.getText().toString();


                    Intent intent = new Intent(MainActivity.this, MainWindow.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void userSignUp(View view) {
        finish();
        startActivity(new Intent(this, SignUpActivity.class));
    }
    public String getLoginUser() {
        return loginUser;
    }

    @Override
    protected void onStart () {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            CurrentData.loginEmail=email;
            startActivity(new Intent(this, MainWindow.class));
        }

    }
}
