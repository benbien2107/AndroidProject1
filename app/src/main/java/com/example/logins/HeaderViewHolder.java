package com.example.logins;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class HeaderViewHolder extends RecyclerView.ViewHolder{

    final TextView titleView;
    HeaderViewHolder( View view) {
        super(view);
        titleView = view.findViewById(R.id.rides_header);
    }
}
