package com.example.logins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logins.util.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.HashMap;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText email;
    private TextInputEditText password;
    private Button login_btn;
    private TextView reg_link;
    private FirebaseAuth mAuth;


    private final String FILE_NAME = "User_info";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton();
    }
    protected void LoginButton (){
        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        login_btn = findViewById(R.id.login_btn);
        reg_link = findViewById(R.id.register_link);
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences pref = getApplicationContext().getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        if (pref.getBoolean(Constants.KEY_IS_LOGGED_IN, false)){
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity(i);
        }
        else {
            login_btn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            isValidLogInDetails();
                            mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(LoginActivity.this, "User and Password is correct", Toast.LENGTH_LONG).show();
                                                stateLoginTrue();
                                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(i);
                                            } else{
                                                Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                        }
                    }
            );
            closeKeyboard();
        }

        reg_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);
            }
        });

    }
    private void closeKeyboard()
    {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = this.getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }

    private void stateLoginTrue (){
        SharedPreferences pref = getApplicationContext().getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.KEY_IS_LOGGED_IN, true);
        editor.commit();
    }

    private boolean isValidLogInDetails() {
        if (password.getText().toString().trim().isEmpty()) {
            showToast("Enter your password");
            return false;
        } else if (email.getText().toString().trim().isEmpty()) {
            showToast("Enter your email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
            showToast("Enter a valid Email");
            return false;
        } else {
            return true;
        }
    }
    private void showToast ( String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }



}