package com.example.logins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.logins.util.Constants;

import java.util.ArrayList;

public class ChooseProfileImageAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Integer> list;

    public ChooseProfileImageAdapter(Context context, ArrayList<Integer> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.row_profile_pictures, viewGroup, false);
        ImageView iv = (ImageView) view.findViewById(R.id.imageView);
        iv.setImageResource(list.get(i));
        return view;
    }


}
