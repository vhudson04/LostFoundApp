package com.example.lostfoundapp.model;

public class Item {

    private int item_id;
    private String item_name;
    private String name_of_poster;
    private String phone;
    private String description;
    private String location;
    private String postType;
    private String imageUri;
    private long datePosted;
    private double latitude;
    private double longitude;

    public Item(String item_name, String name_of_poster, String phone, String description, String location) {
        //this.item_id = item_id;
        this.item_name = item_name;
        this.name_of_poster = name_of_poster;
        this.phone = phone;
        this.description = description;
        this.location = location;
        //this.postType = postType;
        //this.datePosted = datePosted;
    }

    public Item() {
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getName_of_poster() {
        return name_of_poster;
    }

    public void setName_of_poster(String name_of_poster) {
        this.name_of_poster = name_of_poster;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getImageUri(){ return imageUri; }
    public void setImageUri(String imageUri){ this.imageUri = imageUri; }

    public long getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(long datePosted) {
        this.datePosted = datePosted;
    }

    public double getLatitude() {return latitude;}

    public void setLatitude(double latitude) {this.latitude = latitude;}

    public double getLongitude() {return longitude;}

    public void setLongitude(double longitude) {this.longitude = longitude;}
}
