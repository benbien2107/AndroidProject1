package com.example.logins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.logins.util.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateBearing;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {


    private TextInputEditText name;
    private TextInputEditText password;
    private TextInputEditText email;
    private TextInputEditText confirm;
    private MaterialButton button_reg;
    private MaterialButton button_login;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        name = (TextInputEditText) findViewById(R.id.reg_name);
        password = (TextInputEditText) findViewById(R.id.reg_password);
        email = (TextInputEditText) findViewById(R.id.reg_email);
        confirm = findViewById(R.id.reg_confirm_password);
        button_reg =  findViewById(R.id.button_register);
        button_login = findViewById(R.id.button_login);
        progressBar = findViewById(R.id.progress_bar);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidSignUpDetails()){
                    registerUser();
                }
            }
        });
    }

    private void registerUser() {
        isLoading(true);
        if (mAuth == null){
            return;
        }
        mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),password.getText().toString().trim())
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                           @Override
                                           public void onComplete(@NonNull Task<AuthResult> task) {
                                               if (task.isSuccessful()) {
                                                   FirebaseUser user = mAuth.getCurrentUser();
                                                   assert user != null;
                                                   addToDatabase(name.getText().toString().trim(),
                                                           email.getText().toString().trim(), user.getUid());
                                                   Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                                   startActivity(i);
                                                   finish();
                                               }
                                               else {
                                                   Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                           Toast.LENGTH_SHORT).show();
                                                   isLoading(false);
                                               }
                                           }
                                       });

    }


    private  void addToDatabase (String name, String email, String uid) {
        database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_EMAIL, email);
        user.put(Constants.KEY_NAME, name);
        user.put(Constants.KEY_IMAGE, Constants.PROFILE_DEFAULT);
        database.collection(Constants.KEY_COLLECTION_USERS).document(uid).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    Log.d( "FireStore" , "Successful adding user_ " + uid);
                    }
                });
    }
    private void showToast ( String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }

    private boolean isValidSignUpDetails(){

        if (name.getText().toString().trim().isEmpty()){
            showToast("Enter your name");
            return false;
        }
        else if ( password.getText().toString().trim().isEmpty()){
            showToast("Enter your password");
            return false;
        }
        else if (email.getText().toString().trim().isEmpty()){
            showToast("Enter your email");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){
            showToast("Enter a valid Email");
            return false;
        }
        else if (confirm.getText().toString().trim().isEmpty()){
            showToast("Enter confirm password");
            return false;
        }
        else if (!confirm.getText().toString().trim().equals(password.getText().toString().trim())){
            showToast("Passwords must be the same");
            return false;
        }
        else {
            return true;
        }
    }

    private void isLoading (boolean loading){
        if (loading) {
            button_reg.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            button_reg.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void moreInfo (){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_PASSWORD, password.getText().toString().trim());
        user.put(Constants.KEY_EMAIL, email.getText().toString().trim());
        database.collection(Constants.KEY_COLLECTION_USERS).add(user);
    }
}