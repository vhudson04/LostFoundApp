package com.example.lostfoundapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lostfoundapp.data.DatabaseHelper;
import com.example.lostfoundapp.model.Item;


public class CreateAdvert extends AppCompatActivity {

    DatabaseHelper db;
    Uri selectedImageUri;
    ImageView previewImage;
    private ActivityResultLauncher<String> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_advert);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DatabaseHelper(this);

        EditText itemInput = findViewById(R.id.itemInput);
        EditText nameInput = findViewById(R.id.nameInput);
        EditText phoneInput = findViewById(R.id.phoneInput);
        EditText descriptionInput = findViewById(R.id.descriptionInput);
        EditText locationInput = findViewById(R.id.locationInput);
        Button saveButton = findViewById(R.id.saveButton);
        RadioGroup postTypeGroup = findViewById(R.id.postTypeGroup);
        Button uploadImageButton = findViewById(R.id.uploadImageButton);
        previewImage = findViewById(R.id.previewImage);

        //image picker
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),uri -> {
            if (uri != null)
            {
                selectedImageUri = uri;
                previewImage.setImageURI(uri);
            }
        });

        uploadImageButton.setOnClickListener(v -> {
            imagePickerLauncher.launch("image/*");
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = itemInput.getText().toString();
                String nameOfPoster = nameInput.getText().toString();
                String phone = phoneInput.getText().toString();
                String description = descriptionInput.getText().toString();
                String location = locationInput.getText().toString();

                //Radio button options
                int selectedId = postTypeGroup.getCheckedRadioButtonId();
                if (selectedId == -1)
                {
                    Toast.makeText(CreateAdvert.this, "Please select a post type", Toast.LENGTH_SHORT).show();
                    return;
                }

                //image upload is required for advert
                if (selectedImageUri == null) {
                    Toast.makeText(CreateAdvert.this,
                            "Please upload an image",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedRadio = findViewById(selectedId);
                String postType = selectedRadio.getText().toString();

                //timestamp
                long timestamp = System.currentTimeMillis();

                Item item = new Item(itemName, nameOfPoster, phone, description, location);
                item.setPostType(postType);
                item.setDatePosted(timestamp);
                item.setImageUri(selectedImageUri.toString());

                long result = db.insertAdvert(item);
                if (result > 0)
                {
                    Toast.makeText(CreateAdvert.this, "Item added successfully!", Toast.LENGTH_SHORT).show();
                    Intent moveIntent = new Intent(CreateAdvert.this, LostFoundItems.class);
                    startActivity(moveIntent);
                }
                else
                {
                    Toast.makeText(CreateAdvert.this, "Save error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}