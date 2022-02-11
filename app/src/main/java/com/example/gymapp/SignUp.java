package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    public static final String USERS = "USERS";
    EditText etEmail;
    EditText etPassword;
    EditText etAge;
    EditText etWeight;
    EditText etHeight;
    Button btnSignIn;
    Button btnLogin;
    String mail, pass, height, weight, age;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        etAge = findViewById(R.id.etAge);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        btnLogin = findViewById(R.id.btnLogIn);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail = etEmail.getText().toString();
                pass = etPassword.getText().toString();
                height = etHeight.getText().toString();
                weight = etWeight.getText().toString();
                age = etAge.getText().toString();
                if(!mail.equals("") || !pass.equals("") || !height.equals("") || !weight.equals("") || !age.equals("") ){
                    user = new User(height, weight, age);
                    signUpUser();
                }
            }
        });
    }

    private void signUpUser(){
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(), "Success " + firebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                    putInDataBase(firebaseUser.getUid());


                } else{
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void putInDataBase(String uId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(USERS);
        reference.child(uId).setValue(user);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


}

