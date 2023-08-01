package com.example.logins;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView placeView, carView, idView, dateTimeView;
    ImageView imageView;

    public MyViewHolder(@NonNull View view){
        super(view);
        placeView = view.findViewById(R.id.place);
        carView = view.findViewById(R.id.car);
        idView = view.findViewById(R.id.id_ride);
        dateTimeView = view.findViewById(R.id.dateTime);
        imageView = view.findViewById(R.id.imageview);
    }

}