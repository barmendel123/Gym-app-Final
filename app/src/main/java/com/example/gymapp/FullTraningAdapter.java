package com.example.gymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FullTraningAdapter extends RecyclerView.Adapter<FullTraningAdapter.ViewHolder> {
    ArrayList<InformationUploadTrain> informationUploadTrainArrayList;
    Context context;

    public FullTraningAdapter(ArrayList<InformationUploadTrain> informationUploadTrainArrayList, Context context) {
        this.informationUploadTrainArrayList = informationUploadTrainArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.full_training_show, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
        Glide.with(context).load(informationUploadTrainArrayList.get(position).getImageLink()).apply(requestOptions).into(holder.ImPicture);
        holder.tvNameTrain.setText(informationUploadTrainArrayList.get(position).getTraining().getName());
        holder.tvTrainDate.setText(getDate(informationUploadTrainArrayList.get(position).getTime()));
        holder.tcCaloriesTrain.setText(String.valueOf(informationUploadTrainArrayList.get(position).getTraining().getCloriesBurned()));
        holder.tvCaloriesEaten.setText(String.valueOf(informationUploadTrainArrayList.get(position).getCaloriesEaten()));



    }

    private String getDate(Long ms){
        String format = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);
        return simpleDateFormat.format(calendar.getTime());
    }

    @Override
    public int getItemCount() {
        return informationUploadTrainArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ImPicture;
        TextView tvNameTrain;
        TextView tvTrainDate;
        TextView tcCaloriesTrain;
        TextView tvCaloriesEaten;

        public ViewHolder(View view){
            super(view);
            this.ImPicture = view.findViewById(R.id.ImPicture);
            this.tvNameTrain = view.findViewById(R.id.tvNameTrain);
            this.tvTrainDate = view.findViewById(R.id.tvTrainDate);
            this.tcCaloriesTrain = view.findViewById(R.id.tcCaloriesTrain);
            this.tvCaloriesEaten = view.findViewById(R.id.tvCaloriesEaten);


        }
    }

}
