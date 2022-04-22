package com.example.tripexpensemanager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import com.example.tripexpensemanager.Model.CategoryModel;
import com.example.tripexpensemanager.Model.PersonModel;
import com.example.tripexpensemanager.Model.TrasactionModel;
import com.example.tripexpensemanager.Model.TripModel;

public class DataBaseHelper extends SQLiteOpenHelper {
    // database name
    public  static String DATABASE_NAME = "Trip";
    // table name
    String TABLE_NAME_TRIP = "TRIP";
    // column names
    String COL_PRIMARY_ID = "PRIMARY_ID";
    String COL_TRIP_NAME = "TRIP_NAME";
    String COL_DESCRIPTION = "DESCRIPTION";
    String COL_DATE = "DATE";
    String COLUMN_TIMESTAMP_TRIP = "TIMESTAMP_TRIP";

    // table name
    String TABLE_NAME_PERSON = "PERSON";
    // column names
    String COL_TRIP_ID = "TRIP_ID";
    String COL_PERSON_ID = "PRIMARY_ID";
    String COL_NAME = "NAME";
    String COL_AMOUNT_DEBIT = "AMOUNT_DEBIT";
    String COL_AMOUNT_CREADIT = "AMOUNT_CREADIT";
    String COL_ADMIN = "ADMIN";
    String COLUMN_TIMESTAMP_PERSON = "TIMESTAMP_PERSON";

    //table name
    String TABLE_NAME_CATEGORY = "CATEGORY";
    // column names
    String COL_CATEGORY = "CATEGORY";
    String COL_CATEGORY_ID = "CATEGORY_ID";
    String COLUMN_TIMESTAMP_CATEGORY = "TIMESTAMP_CATEGORY";

    //table name
    String TABLE_NAME_TRASACTION = "TRASACTION";
    // column names
    String COL_TRASACTION_ID = "TRASACTION_ID";
    String COL_DESCRIPTION_PURCHESE = "DESCRIPTION_PURCHESE";
    String COL_PAY_AMOUNT = "PAY_AMOUNT";
    String COLUMN_TIMESTAMP_TRASACTION = "TIMESTAMP_PERSON";



    // creating table
    String CREATE_TABLE_TRIP =
            "CREATE TABLE " + TABLE_NAME_TRIP + "("
                    + COL_PRIMARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_TRIP_NAME + " TEXT,"
                    + COL_DESCRIPTION + " TEXT,"
                    + COL_DATE + " STRING,"
                    + COLUMN_TIMESTAMP_TRIP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";
    // creating table
    String CREATE_TABLE_PERSON =
            "CREATE TABLE " + TABLE_NAME_PERSON + "("
                    + COL_PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_NAME + " TEXT,"
                    + COL_AMOUNT_DEBIT + " INTEGER,"
                    + COL_AMOUNT_CREADIT + " INTEGER,"
                    + COL_TRIP_ID + " INTEGER,"
                    + COL_ADMIN + " TEXT,"
                    + COLUMN_TIMESTAMP_PERSON + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";
    // creating table
    String CREATE_TABLE_CATEGORY =
            "CREATE TABLE " + TABLE_NAME_CATEGORY + "("
                    + COL_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_CATEGORY + " TEXT,"
                    + COLUMN_TIMESTAMP_CATEGORY + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";
    // creating table
    String CREATE_TABLE_TRASACTION =
            "CREATE TABLE " + TABLE_NAME_TRASACTION + "("
                    + COL_TRASACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_TRIP_ID + " INTEGER,"
                    + COL_DESCRIPTION_PURCHESE + " TEXT,"
                    + COL_CATEGORY + " TEXT,"
                    + COL_DATE + " STRING,"
                    + COL_PAY_AMOUNT + " INTEGER,"
                    + COLUMN_TIMESTAMP_TRASACTION + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";



    // database version
    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //executing to create table in database
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_TRIP);
        db.execSQL(CREATE_TABLE_PERSON);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_TRASACTION);

    }

    // database handling
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TRIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TRASACTION);
        onCreate(db);

    }

    //inserting data to database
    public long insertDataTrip(TripModel tripModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TRIP_NAME,tripModel.getTripName());
        contentValues.put(COL_DESCRIPTION,tripModel.getDescription());
        contentValues.put(COL_DATE, tripModel.getDate());
        long id = db.insert(TABLE_NAME_TRIP, null, contentValues);
        db.close();
        return id;
    }
    //inserting data to database
    public long insertDataPerson(PersonModel personModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME,personModel.getName());
        contentValues.put(COL_AMOUNT_DEBIT,personModel.getAmountDebit());
        contentValues.put(COL_AMOUNT_CREADIT, personModel.getAmountCredit());
        contentValues.put(COL_ADMIN, personModel.getAdmin());
        contentValues.put(COL_TRIP_ID, personModel.getTripId());

        long id = db.insert(TABLE_NAME_PERSON, null, contentValues);
        db.close();
        return id;
    }
    //inserting data to database
    public long insertDataCategory(String categoryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CATEGORY,categoryName);
        long id = db.insert(TABLE_NAME_CATEGORY, null, contentValues);
        db.close();
        return id;
    }
    //inserting data to database
    public long insertDataTRASACTION(TrasactionModel trasactionModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CATEGORY,trasactionModel.getCategory());
        contentValues.put(COL_DESCRIPTION_PURCHESE,trasactionModel.getDescription());
        contentValues.put(COL_DATE,trasactionModel.getDate());
        contentValues.put(COL_PAY_AMOUNT, trasactionModel.getPayAmount());
        long id = db.insert(TABLE_NAME_TRASACTION, null, contentValues);
        db.close();
        return id;
    }
    //inserting data to database
    public long updateDataPerson(int tripId,int newAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_AMOUNT_DEBIT, newAmount);
        String query = "UPDATE "+TABLE_NAME_PERSON+" SET "+COL_AMOUNT_DEBIT+" = "+newAmount+" WHERE "+COL_TRIP_ID+" = "+tripId;
        long id= db.update(TABLE_NAME_PERSON, contentValues, COL_TRIP_ID + " = ?", new String[]{String.valueOf(tripId)});
        return  id;
    }
    //inserting data to database
    public long updateCreditAmountPerson(int tripId,int newAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_AMOUNT_CREADIT, newAmount);
        String query = "UPDATE "+TABLE_NAME_PERSON+" SET "+COL_AMOUNT_CREADIT+" = "+newAmount+" WHERE "+COL_TRIP_ID+" = "+tripId;
        long id= db.update(TABLE_NAME_PERSON, contentValues, COL_TRIP_ID + " = ?", new String[]{String.valueOf(tripId)});
        return  id;
    }
    // deleting specific data from database
    public Integer deleteDataTrip(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_TRIP, "ID=?", new String[]{id});
    }
    // deleting specific data from database
    public Integer deleteDataPerson(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_PERSON, "ID=?", new String[]{id});
    }

    //getting data from database
    @SuppressLint("Range")
    public TripModel getDataTrip(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_TRIP + " WHERE ID='" + id + "'", null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        TripModel tripModel = new TripModel();

        tripModel.setPrimaryId(cursor.getInt(cursor.getColumnIndex(COL_PRIMARY_ID)));
        tripModel.setDescription(cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION)));
        tripModel.setTripName(cursor.getString(cursor.getColumnIndex(COL_TRIP_NAME)));
        tripModel.setDate(cursor.getString(cursor.getColumnIndex(COL_DATE)));
        tripModel.setTimeStampTrip(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP_TRIP)));

        return tripModel;

    }

    //getting data from database
    @SuppressLint("Range")
    public PersonModel getDataPerson(long id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("TEST", String.valueOf(id));
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_PERSON + " WHERE ID='" + id + "'", null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        PersonModel personModel = new PersonModel();

        personModel.setPersonId(cursor.getInt(cursor.getColumnIndex(COLUMN_TIMESTAMP_PERSON)));
        personModel.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
        personModel.setAmountDebit(cursor.getInt(cursor.getColumnIndex(COL_AMOUNT_DEBIT)));
        personModel.setAmountCredit(cursor.getInt(cursor.getColumnIndex(COL_AMOUNT_CREADIT)));
        personModel.setTimeStampTrip(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP_TRIP)));

        return personModel;

    }

    //getting data from database
    @SuppressLint("Range")
    public List<TripModel> getAllGoalTrip() {
        List<TripModel> tripModelList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME_TRIP + " ORDER BY " + COLUMN_TIMESTAMP_TRIP + " ASC", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TripModel tripModel = new TripModel();
                tripModel.setPrimaryId(cursor.getInt(cursor.getColumnIndex(COL_PRIMARY_ID)));
                tripModel.setTripName(cursor.getString(cursor.getColumnIndex(COL_TRIP_NAME)));
                tripModel.setDescription(cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION)));
                tripModel.setDate(cursor.getString(cursor.getColumnIndex(COL_DATE)));

                tripModelList.add(tripModel);
            } while (cursor.moveToNext());
        }

        // close database connection
        db.close();

        // return notes list
        return tripModelList;
    }

    //getting data from database
    @SuppressLint("Range")
    public List<PersonModel> getAllGoalPerson(int tripId) {
        List<PersonModel> personModelList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME_PERSON + " WHERE "+ COL_TRIP_ID +" = "+tripId+" ORDER BY " + COLUMN_TIMESTAMP_PERSON + " DESC", null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PersonModel personModel = new PersonModel();
                personModel.setTripId(cursor.getInt(cursor.getColumnIndex(COL_TRIP_ID)));
                personModel.setPersonId(cursor.getInt(cursor.getColumnIndex(COL_PERSON_ID)));
                personModel.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
                personModel.setAmountDebit(cursor.getInt(cursor.getColumnIndex(COL_AMOUNT_DEBIT)));
                personModel.setAmountCredit(cursor.getInt(cursor.getColumnIndex(COL_AMOUNT_CREADIT)));
                personModel.setAdmin(cursor.getString(cursor.getColumnIndex(COL_ADMIN)));

                personModelList.add(personModel);
            } while (cursor.moveToNext());
        }
        // close database connection
        db.close();
        // return notes list
        return personModelList;
    }

    //getting data from database
    @SuppressLint("Range")
    public List<CategoryModel> getAllCategory() {
        List<CategoryModel> categoryModelList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME_CATEGORY + " ORDER BY " + COLUMN_TIMESTAMP_CATEGORY + " DESC", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setCategoryId(cursor.getInt(cursor.getColumnIndex(COL_CATEGORY_ID)));
                categoryModel.setCategory(cursor.getString(cursor.getColumnIndex(COL_CATEGORY)));
                categoryModelList.add(categoryModel);
            } while (cursor.moveToNext());
        }
        // close database connection
        db.close();
        // return notes list
        return categoryModelList;
    }

    //getting data from database
    @SuppressLint("Range")
    public List<TrasactionModel> getAllTrasaction() {
        List<TrasactionModel> trasactionModelList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME_TRASACTION + " ORDER BY " + COLUMN_TIMESTAMP_TRASACTION + " DESC", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TrasactionModel trasactionModel = new TrasactionModel();
                trasactionModel.setTrasactionId(cursor.getInt(cursor.getColumnIndex(COL_TRASACTION_ID)));
                trasactionModel.setTripId(cursor.getInt(cursor.getColumnIndex(COL_TRIP_ID)));
                trasactionModel.setDescription(cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION_PURCHESE)));
                trasactionModel.setCategory(cursor.getString(cursor.getColumnIndex(COL_CATEGORY)));
                trasactionModel.setDate(cursor.getString(cursor.getColumnIndex(COL_DATE)));
                trasactionModel.setPayAmount(cursor.getInt(cursor.getColumnIndex(COL_PAY_AMOUNT)));
                trasactionModelList.add(trasactionModel);
            } while (cursor.moveToNext());
        }
        // close database connection
        db.close();
        // return notes list
        return trasactionModelList;
    }

}


