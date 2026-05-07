package com.example.lostfoundapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.lostfoundapp.model.Item;
import com.example.lostfoundapp.util.Util;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ITEM_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " ("
                + Util.ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.ITEM_NAME + " TEXT, "
                + Util.NAME_OF_POSTER + " TEXT, "
                + Util.PHONE + " TEXT, "
                + Util.DESCRIPTION + " TEXT, "
                + Util.LOCATION + " TEXT, "
                + Util.POST_TYPE + " TEXT, "
                + Util.IMAGE_URI + " TEXT, "
                + Util.DATE_POSTED + " INTEGER)";


        //db = new DatabaseHelper(this);
        db.execSQL(CREATE_ITEM_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_ITEM_TABLE = "DROP TABLE IF EXISTS " + Util.TABLE_NAME;
        db.execSQL(DROP_ITEM_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(db);

    }

    //inserting new row of advert data to db
    public long insertAdvert(Item item)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //item_id is primary key and automatically incremented
        contentValues.put(Util.ITEM_NAME, item.getItem_name());
        contentValues.put(Util.NAME_OF_POSTER, item.getName_of_poster());
        contentValues.put(Util.PHONE, item.getPhone());
        contentValues.put(Util.DESCRIPTION, item.getDescription());
        contentValues.put(Util.LOCATION, item.getLocation());
        contentValues.put(Util.POST_TYPE, item.getPostType());
        contentValues.put(Util.IMAGE_URI, item.getImageUri());
        contentValues.put(Util.DATE_POSTED, item.getDatePosted());

        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }

    //used for search/filter function
    public Cursor searchAdverts(String searchText)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Util.TABLE_NAME
                + " WHERE " + Util.ITEM_NAME + " LIKE ? "
                + " ORDER BY " + Util.DATE_POSTED + " DESC";
        //searches from item names and ordered from newest to oldest

        String[] args = new String[]{"%" + searchText + "%"}; //searches and matches partial words
        return db.rawQuery(query, args);
    }

    public Cursor getAllAdverts()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Util.TABLE_NAME +
                " ORDER BY " + Util.DATE_POSTED + " DESC";

        return db.rawQuery(query, null);
    }

    public void removeAdvert(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME, Util.ITEM_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public boolean advertExists(String itemName, String phone)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                Util.TABLE_NAME,
                new String[]{Util.ITEM_ID},
                Util.ITEM_NAME + "=? and " + Util.PHONE + "=?",
                new String[] {itemName, phone}, null, null, null);
        int numberOfRows = cursor.getCount();
        cursor.close();
        db.close();

        if (numberOfRows > 0) //means we have item in db
            return true;
        else
            return false;
    }
}
