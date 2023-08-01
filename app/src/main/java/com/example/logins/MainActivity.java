package com.example.logins;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.widget.Toast;



import android.Manifest;

import com.example.logins.chat.ChatFragment;
import com.example.logins.users.UserProfileActivity;
import com.example.logins.util.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar myToolbar;
    private AlertDialog.Builder builder;
    private BottomNavigationView bottomNavigationView;
    private final String FILE_NAME = "User_info";
    private MapFragment mapFragment;
    private RidesFragment ridesFragment;
    private ReservationsFragment reservationsFragment;
    private ChatFragment chatFragment;
    private FirebaseAuth auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        builder = new AlertDialog.Builder(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Rides");
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        mapFragment = new MapFragment();
        ridesFragment = new RidesFragment();
        reservationsFragment = new ReservationsFragment();
        chatFragment = new ChatFragment();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null){
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.map){
                    replaceFragment(mapFragment);
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Map");
                    return true;
                }
                else if (item.getItemId() == R.id.rides){
                    replaceFragment(ridesFragment);
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Rides");
                    return true;
                }
                else if (item.getItemId() == R.id.reservations){
                    replaceFragment(reservationsFragment);
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Reservations");
                    return true;
                }
                else if (item.getItemId() == R.id.chat){
                    replaceFragment(chatFragment);
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Chat");
                    return true;
                }
                else
                    return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.rides);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        // first parameter is the file for icon and second one is menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // We are using switch case because multiple icons can be kept
        if  (item.getItemId() == R.id.logout_btn) {
            MainActivity activity = this;
//            builder.setMessage("Alert of log out") .setTitle("Log out");

            builder.setMessage("Do you want to log out?").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(),"You have chosen to log out",Toast.LENGTH_SHORT).show();
                    LogOut();
                    Intent intent = new Intent(activity,LoginActivity.class);
                    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity(intent);
                    finish();
                }
            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Toast.makeText(getApplicationContext(),"You have chosen not to log out",
                            Toast.LENGTH_SHORT).show();
                }
            });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Logout");
            alert.show();
        }
        if (item.getItemId() == R.id.btn_profile){
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void LogOut(){
        FirebaseAuth.getInstance().signOut();
        SharedPreferences pref = getApplicationContext().getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.KEY_IS_LOGGED_IN, false);
        editor.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

}