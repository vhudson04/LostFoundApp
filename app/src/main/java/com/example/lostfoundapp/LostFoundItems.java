package com.example.lostfoundapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostfoundapp.data.DatabaseHelper;
import com.example.lostfoundapp.model.Item;
import com.example.lostfoundapp.util.Util;

import java.util.ArrayList;
import java.util.List;

public class LostFoundItems extends AppCompatActivity {
    DatabaseHelper db;
    private RecyclerView recyclerView;
    private Button homeButton;
    private SearchView searchView;
    List<Item> advertList;
    AdvertAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lost_found_items);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        homeButton = findViewById(R.id.homeButton);
        searchView = findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadAllAdverts();

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(LostFoundItems.this, MainActivity.class);
                startActivity(homeIntent);
                //returning to MainActivity (Home) screen
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor cursor;

                if (newText.isEmpty()){
                    cursor = db.getAllAdverts();
                } else {
                    cursor = db.searchAdverts(newText);
                }

                List<Item> filteredList = convertCursorToList(cursor);
                adapter.updateList(filteredList);

                return true;
            }
        });

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadAllAdverts();
    }

    private void loadAllAdverts()
    {
        Cursor cursor = db.getAllAdverts();
        advertList = convertCursorToList(cursor);
        if (adapter == null)
        {
            adapter = new AdvertAdapter(advertList);
            recyclerView.setAdapter(adapter);
        }
        else
        {
            adapter.updateList(advertList);
        }

    }

    private List<Item> convertCursorToList(Cursor cursor)
    {
        List<Item> list = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            do {
                Item item = new Item();
                item.setItem_id(cursor.getInt(cursor.getColumnIndexOrThrow(Util.ITEM_ID)));
                item.setItem_name(cursor.getString(cursor.getColumnIndexOrThrow(Util.ITEM_NAME)));
                item.setName_of_poster(cursor.getString(cursor.getColumnIndexOrThrow(Util.NAME_OF_POSTER)));
                item.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(Util.PHONE)));
                item.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(Util.DESCRIPTION)));
                item.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(Util.LOCATION)));
                item.setPostType(cursor.getString(cursor.getColumnIndexOrThrow(Util.POST_TYPE)));
                item.setImageUri(cursor.getString(cursor.getColumnIndexOrThrow(Util.IMAGE_URI)));
                item.setDatePosted(cursor.getLong(cursor.getColumnIndexOrThrow(Util.DATE_POSTED)));

                list.add(item);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
}