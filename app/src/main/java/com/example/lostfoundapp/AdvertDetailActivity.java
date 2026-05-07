package com.example.lostfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.lostfoundapp.data.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdvertDetailActivity extends AppCompatActivity {

    DatabaseHelper db;
    int itemId;
    TextView title, nameOfPoster, phone, description, location, date;
    Button removeButton, advertsListButton;
    ImageView advertImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_detail);
        //EdgeToEdge.enable(this);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        db = new DatabaseHelper(this);

        title = findViewById(R.id.titleText);
        nameOfPoster = findViewById(R.id.nameText);
        phone = findViewById(R.id.phoneText);
        description = findViewById(R.id.descriptionText);
        location = findViewById(R.id.locationText);
        date = findViewById(R.id.dateText);
        removeButton = findViewById(R.id.removeButton);
        advertsListButton = findViewById(R.id.advertsListButton);
        advertImage = findViewById(R.id.advertImage);


        Intent intent = getIntent();
        itemId = intent.getIntExtra("item_id", -1);

        String postType = intent.getStringExtra("post_type");
        String itemName = intent.getStringExtra("item_name");
        String imageUri = intent.getStringExtra("image_uri");

        //Image loading with Glide
        Glide.with(this).load(imageUri).into(advertImage);

        //setting advert information text
        title.setText(postType + ": " + itemName);
        nameOfPoster.setText(intent.getStringExtra("name_of_poster"));
        phone.setText(intent.getStringExtra("phone"));
        description.setText(intent.getStringExtra("description"));
        location.setText(intent.getStringExtra("location"));

        //formatting timestamp
        long timestamp = intent.getLongExtra("date_posted", 0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        date.setText(sdf.format(new Date(timestamp)));

        //remove advert
        removeButton.setOnClickListener(v -> {
            db.removeAdvert(itemId);
            Toast.makeText(this, "Advert removed", Toast.LENGTH_SHORT).show();
            finish(); //return to dashboard
        });

        //returning to adverts list
        advertsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveintent = new Intent(AdvertDetailActivity.this, LostFoundItems.class);
                startActivity(moveintent);
            }
        });

    }
}