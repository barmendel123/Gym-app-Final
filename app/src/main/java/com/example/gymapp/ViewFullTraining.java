package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ViewFullTraining extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    public static final int DAY_TIME = 24*60*60*1000;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    String uID;
    Button btnDateShow;
    RecyclerView recycleViewId;
    ArrayList<InformationUploadTrain> informationUploadTrainArrayList;
    ArrayList<InformationUploadTrain> fullArraysList;
    FullTraningAdapter fullTraningAdapter;
    private static final String TAG = "ViewFullTraining";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trainings);
        databaseReference = FirebaseDatabase.getInstance().getReference(AddTraining.FULL_TRAINING);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uID = firebaseUser.getUid();
        informationUploadTrainArrayList = new ArrayList<>();
        fullArraysList = new ArrayList<>();
        recycleViewId = findViewById(R.id.recycleViewId);
        readFullTrain();
        btnDateShow= findViewById(R.id.btnDateShow);
        Context context = this;

        btnDateShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, (DatePickerDialog.OnDateSetListener) context, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

    }



    private void readFullTrain(){
        databaseReference = databaseReference.child(uID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: " + snapshot.toString());
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    InformationUploadTrain informationUploadTrain = dataSnapshot.getValue(InformationUploadTrain.class);
                    informationUploadTrainArrayList.add(informationUploadTrain);
                    fullArraysList.add(informationUploadTrain);
                }

                Log.d(TAG, "onDataChange: " + informationUploadTrainArrayList.size());
                putInRecicleView();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void putInRecicleView(){
       fullTraningAdapter = new FullTraningAdapter(informationUploadTrainArrayList, this);
        recycleViewId.setAdapter(fullTraningAdapter);
        recycleViewId.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth, 0,0,0);
        long timeStart = calendar.getTimeInMillis();
        long timeEnd = timeStart+DAY_TIME;
        informationUploadTrainArrayList.clear();
        for(int i = 0; i < fullArraysList.size(); i++)
        {
            if(fullArraysList.get(i).getTime() >= timeStart && fullArraysList.get(i).getTime() < timeEnd)
            {
                informationUploadTrainArrayList.add(fullArraysList.get(i));
            }

        }
        fullTraningAdapter.notifyDataSetChanged();

    }
}