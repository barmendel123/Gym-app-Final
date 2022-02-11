package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeDetails extends AppCompatActivity {
    User user;
    String strAge, strHeight, strWeight;
    EditText age, height, weight;
    Button btnSignIn;
    FirebaseUser firebaseUser;
    String uID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_details);

        age = findViewById(R.id.etAge);
        height = findViewById(R.id.etHeight);
        weight = findViewById(R.id.etWeight);
        btnSignIn = findViewById(R.id.btnUpdateDetails);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uID = firebaseUser.getUid();
        getInformation();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strAge = age.getText().toString();
                strHeight = height.getText().toString();
                strWeight = weight.getText().toString();
                user = new User(strHeight, strWeight, strAge);
                putInDataBase(uID);
            }
        });
    }

    private void putInDataBase(String uId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(SignUp.USERS);
        reference.child(uId).setValue(user);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void getInformation(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(SignUp.USERS);
        databaseReference = databaseReference.child(uID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user2 = snapshot.getValue(User.class);
                age.setText(String.valueOf(user2.getAge()));
                height.setText(String.valueOf(user2.getHeight()));
                weight.setText(String.valueOf(user2.getWeight()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}