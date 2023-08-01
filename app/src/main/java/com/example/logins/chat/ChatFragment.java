package com.example.logins.chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.logins.R;
import com.example.logins.users.User;
import com.example.logins.users.UserListActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {

    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FloatingActionButton btn ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_chat, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.chat_recycle);
        // Inflate the layout for this fragment
        List<User> items = new ArrayList<User>();
        items.add(new User("John wick","john.wick@email.com",R.drawable.a));
        items.add(new User("Robert j","robert.j@email.com",R.drawable.b));
        items.add(new User("James Gunn","james.gunn@email.com",R.drawable.c));
        items.add(new User("Ricky tales","rickey.tales@email.com",R.drawable.d));
        items.add(new User("Micky mose","mickey.mouse@email.com",R.drawable.e));
        items.add(new User("Pick War","pick.war@email.com",R.drawable.f));
        items.add(new User("Leg piece","leg.piece@email.com",R.drawable.g));
        items.add(new User("Apple Mac","apple.mac@email.com",R.drawable.g));
        items.add(new User("John wick","john.wick@email.com",R.drawable.a));
        items.add(new User("Robert j","robert.j@email.com",R.drawable.b));
        items.add(new User("James Gunn","james.gunn@email.com",R.drawable.c));
        items.add(new User("Ricky tales","rickey.tales@email.com",R.drawable.d));
        items.add(new User("Micky mose","mickey.mouse@email.com",R.drawable.e));
        items.add(new User("Pick War","pick.war@email.com",R.drawable.f));
        items.add(new User("Leg piece","leg.piece@email.com",R.drawable.g));
        items.add(new User("Apple Mac","apple.mac@email.com",R.drawable.g));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ChatCustomAdapter(getActivity().getApplicationContext(),items));
        btn = view.findViewById(R.id.user_list);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UserListActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}

