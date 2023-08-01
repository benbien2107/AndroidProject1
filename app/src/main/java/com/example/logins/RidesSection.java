package com.example.logins;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;



public class RidesSection extends  Section{

    private String title;
    private List<Ride> list; // list for the rides
    private ClickListener clickListener;

    public RidesSection(@NonNull final String title, @NonNull final List<Ride> list,
                        @NonNull final ClickListener clickListener) {

        super(SectionParameters.builder()
                .itemResourceId(R.layout.rides_section_item)
                .headerResourceId(R.layout.rides_section_header)
                .build());

        this.title = title;
        this.list = list;
        this.clickListener = clickListener;
    }
    @Override
    public int getContentItemsTotal() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(final View view) {
        return new MyViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder itemHolder = (MyViewHolder) holder;
        Ride item = list.get(position);
        itemHolder.imageView.setImageResource(item.getImageView());
        itemHolder.placeView.setText(item.getPlace());
        itemHolder.carView.setText(item.getCar());
        itemHolder.dateTimeView.setText(item.getDateTime().toString());
        itemHolder.idView.setText(item.getId());


        itemHolder.itemView.setOnClickListener(v ->
                clickListener.onItemRootViewClicked(this, itemHolder.getAdapterPosition(), item)
        );
    }
    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(final View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(final RecyclerView.ViewHolder holder) {
        final HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

        headerHolder.titleView.setText(title);
    }

    public List<Ride> getList() {
        return list;
    }

    public String getTitle(){
        return title;
    }

    interface ClickListener {

        void onItemRootViewClicked(@NonNull final RidesSection section, final int itemAdapterPosition, Ride ride);
        void onItemClick (int position);
    }
}
