package com.example.tripexpensemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.tripexpensemanager.Adaptor.AdaptorPersonDetails;
import com.example.tripexpensemanager.Adaptor.AdaptorTripDetails;
import com.example.tripexpensemanager.Model.CategoryModel;
import com.example.tripexpensemanager.Model.PersonModel;
import com.example.tripexpensemanager.Model.TripModel;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();
    //variable declaration
    FloatingActionButton floatingActionButton;

    Boolean isUpdateCallForMainActivity = false;

    //Recycler View
    RecyclerView recyclerViewTrip;
    //adapter
    RecyclerView.Adapter adapter;
    //layout manager
    RecyclerView.LayoutManager layoutManager;

    public static List<TripModel> tripModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //sharedpreferences allow you to store data as key/value
        SharedPreferences sharedpreferences = getSharedPreferences("CategotyData", Context.MODE_PRIVATE);
        //checking if user is logging for first time or not
        if (!sharedpreferences.getBoolean("isFirstTimeLogin", false)){
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean("isFirstTimeLogin", true);
            editor.apply();

            DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

            dataBaseHelper.insertDataCategory("Entertainment");
            dataBaseHelper.insertDataCategory("Food");
            dataBaseHelper.insertDataCategory("Hotel");
            dataBaseHelper.insertDataCategory("Medical");
            dataBaseHelper.insertDataCategory("Miscellaneous");
            dataBaseHelper.insertDataCategory("Parking");
            dataBaseHelper.insertDataCategory("Shopping");
            dataBaseHelper.insertDataCategory("Toll");
            dataBaseHelper.insertDataCategory("Travel");
        }

        bindView();
        onClickEventHandler();
        setRecyclerViewPerson();
    }

    // click event listener for floating button to add new trip
    void onClickEventHandler(){
        Log.i(TAG, "OnClick handler");
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TripDataAdd.class));
            }
        });

    }

    void bindView(){
        Log.i(TAG, "Bind view");
        //finding view by id
        floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerViewTrip = findViewById(R.id.recyclerViewTrip);

        adapter = new  AdaptorTripDetails(tripModelList,this);
        layoutManager = new GridLayoutManager(this,2);
    }


    void setRecyclerViewPerson(){ //RecyclerView Set
        Log.i(TAG, "Recycler View Set");
        recyclerViewTrip.setAdapter(adapter);
        recyclerViewTrip.setLayoutManager(layoutManager);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDataUpdate();
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                getDataUpdate();
                isUpdateCallForMainActivity = true;
            }
        },new IntentFilter("RELOAD_DATA"));
    }

    void getDataUpdate(){
        if (tripModelList.size() > 0) {
            tripModelList.clear();
        }
        //Add Trip Data in tripModelList
        addAllTripData();
    }

    void addAllTripData(){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        tripModelList.addAll(dataBaseHelper.getAllGoalTrip());

        for (int i = 0; i < tripModelList.size(); i++) {
            tripModelList.get(i).setPersonModelList(dataBaseHelper.getAllGoalPerson(tripModelList.get(i).getPrimaryId()));
            int totalGetMoney = 0;
            int totalUsedmoney = 0;
            for(PersonModel personModel : tripModelList.get(i).getPersonModelList()) {
                totalGetMoney += personModel.getAmountCredit();
                totalUsedmoney += personModel.getAmountDebit();
            }

            tripModelList.get(i).setDebitMoney(totalGetMoney - totalUsedmoney);
            tripModelList.get(i).setTotleAmount(totalGetMoney);
            adapter.notifyItemChanged(i);
        }
        Log.i(TAG,"Data Add In tripModelList");
    }

}
