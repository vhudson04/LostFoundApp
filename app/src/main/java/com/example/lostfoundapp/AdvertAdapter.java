package com.example.lostfoundapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostfoundapp.model.Item;

import java.util.List;

public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.ViewHolder>
{
    private List<Item> itemList;

    public AdvertAdapter(List<Item> itemList)
    {
        this.itemList = itemList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView titleText;

        public ViewHolder(View itemView)
        {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_advert, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);

        String title = item.getPostType() + ": " + item.getItem_name();
        holder.titleText.setText(title);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AdvertDetailActivity.class);

            intent.putExtra("item_id", item.getItem_id());
            intent.putExtra("item_name", item.getItem_name());
            intent.putExtra("post_type", item.getPostType());
            intent.putExtra("name_of_poster", item.getName_of_poster());
            intent.putExtra("phone", item.getPhone());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("location", item.getLocation());
            intent.putExtra("image_uri", item.getImageUri());
            intent.putExtra("date_posted", item.getDatePosted());

            v.getContext().startActivity(intent);
        });
    }
    public void updateList(List<Item> newList)
    {
        this.itemList = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
