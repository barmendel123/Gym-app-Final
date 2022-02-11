package com.example.gymapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddTraining extends AppCompatActivity {
    public static final int PICIMAGE = 1;
    public static final String INDEX = "INDEX_TRAIN";
    public static final String FULL_TRAINING = "FULL_TRAIN";
    RecyclerView recyclerView;
    SharedPreferences sharedPreferencesIndex;
    Button btnChooseImage;
    Button btnAddCalories;
    Button btnUpload;
    EditText editTextEnterCalories;
    ArrayList<Training> trainingArrayList;
    TrainingAdapter trainingAdapter;
    ActivityResultLauncher activityResultLauncher;
    Bitmap bitmap;
    boolean isImageKept = false;
    InformationUploadTrain informationUploadTrain;
    FirebaseUser firebaseUser;
    String uID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);

        recyclerView = findViewById(R.id.recyclerView);
        btnChooseImage = findViewById(R.id.uploadPic);
        btnAddCalories = findViewById(R.id.btncheck);
        btnUpload = findViewById(R.id.btnUpload);
        editTextEnterCalories = findViewById(R.id.editTextCalories);
        getTraningArrayList();
        sharedPreferencesIndex = getSharedPreferences(MainActivity.SHAREPREFENCES, MODE_PRIVATE);
        putNegativeIndex();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uID = firebaseUser.getUid();

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== RESULT_OK && result.getData() != null){
                    Bundle bundle = result.getData().getExtras();
                    bitmap = (Bitmap) bundle.get("data");
                    isImageKept = true;


                }
                else{
                    isImageKept = false;

                }
            }

        });

        btnAddCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer calories = Integer.valueOf(editTextEnterCalories.getText().toString());
                if(calories < 1500){
                    Toast.makeText(getApplicationContext(), "You should eat more before train", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Very Good", Toast.LENGTH_SHORT).show();
                }

            }
        });

        trainingAdapter = new TrainingAdapter(trainingArrayList, false, this);
        recyclerView.setAdapter(trainingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager())!= null) {
                    activityResultLauncher.launch(intent);
                }
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer calories = Integer.valueOf(editTextEnterCalories.getText().toString());
                Long time = System.currentTimeMillis();
                Integer index = verifyIndexChosen();
                if(calories > 0 && isImageKept && index >= 0){
                    informationUploadTrain = new InformationUploadTrain(trainingArrayList.get(index), calories, time);
                    uploadImageToFireBase();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "You entered wrong details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getTraningArrayList() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHAREPREFENCES, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(MainActivity.TRAINS, "");
        Type type = new TypeToken<ArrayList<Training>>() {
        }.getType();

        trainingArrayList = gson.fromJson(json, type);

    }

    private void putNegativeIndex(){
        SharedPreferences.Editor editor = sharedPreferencesIndex.edit();
        editor.putInt(INDEX, -1);
        editor.apply();
    }

    private int verifyIndexChosen(){
        return sharedPreferencesIndex.getInt(INDEX, -1);

    }

    private void uploadImageToFireBase(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Images/" + uID + "/" + informationUploadTrain.getTime() + ".jpg");
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
        byte[] data = byteArray.toByteArray();


        UploadTask uploadTask = storageReference.putBytes(data);
        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(task.isSuccessful()){
                    return storageReference.getDownloadUrl();
                }
                throw task.getException();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Uri downloadUrl = task.getResult();
                    informationUploadTrain.setImageLink(downloadUrl.toString());
                    putInDataBase();
                }
            }
        });

    }

   private void putInDataBase(){
       DatabaseReference database = FirebaseDatabase.getInstance().getReference(FULL_TRAINING);
       database.child(uID).child(informationUploadTrain.getTime().toString()).setValue(informationUploadTrain);
       Intent intent = new Intent(getApplicationContext(), MainActivity.class);
       startActivity(intent);

    }


}