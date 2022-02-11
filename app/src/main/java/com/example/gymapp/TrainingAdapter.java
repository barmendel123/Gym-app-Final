package com.example.gymapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder> {
    boolean isPopUp;
    ArrayList<Training> fullTraining;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public TrainingAdapter(ArrayList<Training> fullTraining, boolean isPopUp, Context context) {
        this.fullTraining = fullTraining;
        this.context = context;
        this.isPopUp = isPopUp;
        sharedPreferences = context.getSharedPreferences(MainActivity.SHAREPREFENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.full_training_device, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtName.setText(fullTraining.get(position).getName());
        holder.txtCalories.setText(String.valueOf(fullTraining.get(position).getCloriesBurned()));
        holder.txtTime.setText(fullTraining.get(position).getTimeOfTrain() + "Minutes");
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
        Glide.with(context).load(fullTraining.get(position).getImageOfTrain()).apply(requestOptions).into(holder.imageTrain);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPopUp){
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.pop_up_devices, null);




                    RecyclerView rView = dialogView.findViewById(R.id.popUpRecyleView);
                    DeviceAdapter deviceAdapter = new DeviceAdapter(fullTraining.get(position).getDeviceArrayList(), context);
                    rView.setAdapter(deviceAdapter);
                    rView.setLayoutManager(new LinearLayoutManager(context));



                    builder.setView(dialogView);
                    builder.setCancelable(true);
                    builder.show();
                }
                else{
                    editor.putInt(AddTraining.INDEX, position);
                    editor.apply();

                    Toast.makeText(context, "you chose train number " + position, Toast.LENGTH_SHORT).show();


                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fullTraining.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
         ImageView imageTrain;
         TextView txtName, txtCalories, txtTime;
         ConstraintLayout constraintLayout;

         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             this.imageTrain = itemView.findViewById(R.id.imageTrain);
             this.txtName = itemView.findViewById(R.id.txtName);
             this.txtCalories = itemView.findViewById(R.id.txtCalories);
             this.txtTime = itemView.findViewById(R.id.txtTime);
             this.constraintLayout = itemView.findViewById(R.id.LayOutTraining);
         }

     }

}
