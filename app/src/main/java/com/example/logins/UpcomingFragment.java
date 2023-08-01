package com.example.logins;

import android.content.Intent;
import android.os.Bundle;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;


public class UpcomingFragment extends Fragment implements RidesSection.ClickListener {


    private SectionedRecyclerViewAdapter sectionedAdapter;

    public UpcomingFragment() {
        // Required empty public constructor
    }

    List<Ride> items;
    List<Ride> upcoming_items;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        sectionedAdapter = new SectionedRecyclerViewAdapter();
        List<Ride> items = new ArrayList<Ride>();
        List<Ride> upcoming_items = new ArrayList<>();
        LocalDateTime now = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }
        items.add(new Ride ("Office" , "Ford Everest \t", now, "AIY123MC (D15)", R.drawable.ford_everest));
        sectionedAdapter.addSection(new RidesSection("In Progress", items, this));
        upcoming_items.add(new Ride ("Home", "Toyata Prado \t" , now, "AGI349MP (D19)",R.drawable.toyota_prado));
        upcoming_items.add(new Ride ("Southe Beach Maputo", "Waiting Vehicle Assignment", now, null, 0 ));
        sectionedAdapter.addSection((new RidesSection("Upcomming", upcoming_items, this)));
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionedAdapter);
        return view;
    }

    @Override
    public void onItemRootViewClicked(@NonNull final RidesSection section, final int itemAdapterPosition,  Ride ride) {
        Toast.makeText(getActivity(),  "click on root item " + itemAdapterPosition, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), RideActivity.class);

        List<Ride> list = section.getList();
        String title = section.getTitle();
        int total_items = section.getSectionItemsTotal();
        String car = ride.getCar();
        String id = ride.getId();
        Toast.makeText(getActivity(), title + "\t" + car +  "\t" + id,Toast.LENGTH_SHORT).show();



// set Fragmentclass Arguments
        intent.putExtra("title", title);
        intent.putExtra("car", ride.getCar());
        intent.putExtra("id", ride.getId());
        intent.putExtra("dateTime", ride.getDateTime().toString());
        intent.putExtra("image", ride.getImageView());


//        intent.putExtra("CAR", i )
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {

    }
}