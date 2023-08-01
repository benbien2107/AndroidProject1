package com.example.logins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;



public class RideActivity extends AppCompatActivity{

    private Toolbar myToolbar;
    private TextView carView, titleView, dateTimeView;

    private ImageView  image_carView, image_driverView;

    private ImageButton bottom_sheetView1, bottom_sheetView2;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);
        myToolbar = findViewById(R.id.my_toolbar);
        carView = findViewById(R.id.car_item);
        titleView=  findViewById(R.id.title);
        dateTimeView = findViewById(R.id.datetime);
        image_carView = findViewById(R.id.carview);
        image_driverView = findViewById((R.id.pic_profile));
        bottom_sheetView1 = findViewById(R.id.more1_btn);
        bottom_sheetView2 = findViewById(R.id.more2_btn);

        Intent intent = getIntent();
        String s_car = intent.getStringExtra("car");
        String s_title = intent.getStringExtra("title");
        Toast.makeText(this,"check Ride title: " + s_title,Toast.LENGTH_SHORT).show();
        String s_id = intent.getStringExtra("id");
        String s_datetime = intent.getStringExtra("dateTime");
        int s_image = intent.getIntExtra("image",0);
//        String s_image = intent.getStringExtra("image")
        carView.setText(s_car);
        titleView.setText(s_title);
        dateTimeView.setText(s_datetime);
        image_carView.setImageResource(s_image);
        image_driverView.setImageResource(R.drawable.tyler_mugshot);


        RideActivity activity = this;
        myToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.baseline_arrow_back_24));
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
            }
        });

        bottom_sheetView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialogFragment();
            }
        });
        bottom_sheetView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialogFragment();
            }
        });

    }
    /**
     * showing bottom sheet dialog
     */
    public void showBottomSheetDialogFragment() {
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());


    }

}