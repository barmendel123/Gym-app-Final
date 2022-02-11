package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String SHAREPREFENCES = "SHAREPREFENCES";
    public static final String ISFIRSTUSER = "ISFIRSTUSER";
    public static final String DEVICES = "DEVICES";
    public static final String TRAINS = "TRAINS";
    boolean b1, b2;
    Button btnFullTrainings;
    Button btnTrainningDevices, btnAddTrain, btnShowTrain, btnMusicOptopn;
    ArrayList<Device> deviceArrayList;
    ArrayList<Training> trainingArrayList;
    ConstraintLayout constraintLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = ifFirstUser();
        b2 = isSignIn();
        if(b1 || b2){
            SharedPreferences sharedPreferences = getSharedPreferences(SHAREPREFENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(ISFIRSTUSER, false);
            editor.apply();
            goToSignUp();
        }
        btnTrainningDevices = findViewById(R.id.btnTrainngDevices);
        btnFullTrainings = findViewById(R.id.btnFullTraining);
        constraintLayout = findViewById(R.id.BackroundMain);
        btnAddTrain = findViewById(R.id.btnAddTrain);
        btnShowTrain = findViewById(R.id.btnShowTrain);
        btnMusicOptopn = findViewById(R.id.btnMusicOptopn);

        btnTrainningDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TrainingDevices.class);
                startActivity(intent);
            }
        });

        btnMusicOptopn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MusicOption.class);
                startActivity(intent);
            }
        });

        btnFullTrainings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewTraining.class);
                startActivity(intent);
            }
        });

        btnAddTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTraining.class);
                startActivity(intent);
            }


        });

        btnShowTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewFullTraining.class);
                startActivity(intent);

            }
        });


        deviceArrayList = new ArrayList<>();
        deviceArrayList.add(new Device("Chest compression", "https://cdn.shopify.com/s/files/1/0444/3720/9247/products/H02485a6edc624f5e84242d9a3d5fd65ec_1024x1024.png?v=1619265334", "YC5duExwCtw"));
        deviceArrayList.add(new Device("Butterfly", "https://image.shutterstock.com/image-illustration/reduction-arms-simulator-butterfly-3d-260nw-419701336.jpg", "tJJRWTQuEiI"));
        deviceArrayList.add(new Device("Chest compression with dumbles", "https://www.instructor.co.il/wp-content/uploads/2018/11/03081105-Dumbbell-Fly_Chest-FIX_max-POTASH-1024x531.png", "vGczihxOBKM"));
        deviceArrayList.add(new Device("Pull-ups", "https://www.ygl.co.il/media/wysiwyg/bigstock-Gym-Training-11750504.jpg", "zWTDkPO6WQk"));
        deviceArrayList.add(new Device("Lower back", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTOJ79nZgP2NDIDOW4N1xKnrUdgNgWFSbzLbc4a77UBRL6xVmXQgu0RcYow2erXIIaJGQ&usqp=CAU", "lOOatxjH5QM"));
        deviceArrayList.add(new Device("Upper poly", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQqw8c7xNwVWP5bdw3POpS7Mhi5Ml91s7yNvlRf5UwiC-becyz07t50lZV6uU6Efgs4s2Q&usqp=CAU", "KT3EomXvZqc"));
        deviceArrayList.add(new Device("Rowing in a sitting position", "https://www.precor.com/sites/default/files/styles/product_image/public/product/c/-/c-line_seated-row_male_end_0.jpg?itok=vp-vHQ2D", "_TZZyWr-3h8"));
        deviceArrayList.add(new Device("Front hand", "https://www.rocky-sport.com/images/itempics/2673a_large.jpg", "HasKKWmRRH8"));
        deviceArrayList.add(new Device("Front hand with dumbles", "http://cdn.shopify.com/s/files/1/0444/3720/9247/products/IMG_8779_1200x1200.jpg?v=1614618597", "CRCcPnr6y9A"));
        deviceArrayList.add(new Device("Back hand poly", "https://www.instructor.co.il/wp-content/uploads/2019/02/02001103-Cable-Pushdown-with-rope-attachment_Upper-Arms_max-POTASH-582x1024.png", "6i1NTJMC1JI"));
        deviceArrayList.add(new Device("Back hand with dumbles", "https://www.instructor.co.il/wp-content/uploads/2019/11/17331105-Dumbbell-Incline-Two-Arm-Extension_Upper-Arms_medium-300x221.png", "ioLJhNB-7bQ"));
        deviceArrayList.add(new Device("Foot pressure", "https://www.instructor.co.il/wp-content/uploads/2018/08/29511105-Lever-Horizontal-Leg-Press_Thighs_max-1024x475.png", "CJo7H84LJMI"));
        deviceArrayList.add(new Device("Squat legs", "https://www.instructor.co.il/wp-content/uploads/2020/03/25601105-Bench-Full-Squat-female_Hips_medium-POTASH-300x275.png", "wuV8FoclFgg"));
        deviceArrayList.add(new Device("Shoulder press with dumbles", "https://www.instructor.co.il/wp-content/uploads/2019/10/21371105-Dumbbell-Arnold-Press_Shoulders_max-1024x725.png", "ydP-4kSol4I"));
        deviceArrayList.add(new Device("Shoulder press", "https://www.instructor.co.il/wp-content/uploads/2019/11/29621105-Lever-Seated-Hammer-Grip-Shoulder-Press_Shoulder_medium.png", "ydP-4kSol4I"));
        deviceArrayList.add(new Device("Abs workout couch", "https://www.sportcom.co.il/images/itempics/1001.jpg", "Owz11DUhmfM"));
        deviceArrayList.add(new Device("Abs", "https://images.one.co.il/images/d/dmain/ms/gg1478847.jpg", "Owz11DUhmfM"));

        //Glide.with(this).load(R.drawable.wallpaper).into(constraintLayout);



        trainingArrayList = new ArrayList<>();
        trainingArrayList.add(new Training( 500, " Chest and Back" , 90, "https://img.mako.co.il/2015/08/19/DSC_6280_I.jpg",new ArrayList<>( deviceArrayList.subList(0,5))));
        trainingArrayList.add(new Training( 450, " Front hand and shoulders" , 75, "https://images.doctors.co.il/doctors/Articles/70027029/shutterstock_1081663349.jpg?width=750&height=500", new ArrayList<>( deviceArrayList.subList(5,9))));
        trainingArrayList.add(new Training( 350, " Legs and back hand" , 60, "https://www.sports-center.co.il/wp-content/uploads/2019/05/%D7%A2%D7%A9%D7%99%D7%AA%D7%9D-%D7%9E%D7%A0%D7%95%D7%99.jpg", new ArrayList<>( deviceArrayList.subList(9,13))));
        trainingArrayList.add(new Training( 250, " Shoulders and Abs" , 45, "https://www.baitvenoy.co.il/sysvault/docsfiles12/cdabee9741-573c-4a4b-af3d-1c8428235bbd.jpg", new ArrayList<>( deviceArrayList.subList(13,17))));

        saveDevices();
        saveTrain();

    }

    private void goToSignUp(){
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }


    private boolean ifFirstUser(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHAREPREFENCES, MODE_PRIVATE);
        return sharedPreferences.getBoolean(ISFIRSTUSER, true);
    }

    private boolean isSignIn(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        return firebaseUser == null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.free_points, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.SignOutBottun) {
            FirebaseAuth.getInstance().signOut();
            goToSignUp();
            return true;
        }else if(item.getItemId() == R.id.ditailsButton){
            Intent intent = new Intent(getApplicationContext(), ChangeDetails.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);


    }


    public void saveDevices(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHAREPREFENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String Json = gson.toJson(deviceArrayList);
        editor.putString(DEVICES, Json);
        editor.apply();
    }


    public void saveTrain(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHAREPREFENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String Json = gson.toJson(trainingArrayList);
        editor.putString(TRAINS, Json);
        editor.apply();
    }
}