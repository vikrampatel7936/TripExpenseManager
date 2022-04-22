package com.example.tripexpensemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.example.tripexpensemanager.Adaptor.AdaptorHistory;
import com.example.tripexpensemanager.Adaptor.AdaptorViewPerson;
import com.example.tripexpensemanager.Model.PersonModel;
import com.example.tripexpensemanager.Model.TrasactionModel;
import com.example.tripexpensemanager.Model.TripModel;

public class ActivityTripDetails extends AppCompatActivity {

    private final String TAG = this.getClass().getName();

    public static int position;

    RecyclerView recyclerViewPersonDetails,recyclerViewHistory;
    public  static RecyclerView.Adapter adapter,adapterHistory;
    RecyclerView.LayoutManager layoutManager,layoutManagerHistory;

    private ImageView tripImageIV;
    //adding images to array
    int[] ids = new int[]{R.drawable.trip,R.drawable.trip1,R.drawable.trip2,R.drawable.trip3,R.drawable.trip4 };

    TripModel currentTripModel;
    ArrayList<PersonModel> arrayListPersonModel = new ArrayList<>();
    List<TrasactionModel> trasactionModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        tripImageIV = findViewById(R.id.tripImageIV);

        int tripPosition = getIntent().getIntExtra("TRIP_POSITION", -1);
        position = tripPosition;
        currentTripModel = MainActivity.tripModelList.get(tripPosition);
        setTitle(currentTripModel.getTripName());
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        trasactionModelList.addAll(dataBaseHelper.getAllTrasaction());
        //Set different Image every time new trip is added
        int idTrip = currentTripModel.getPrimaryId();
        if ((idTrip+1)%5 == 0) {
            tripImageIV.setImageResource(ids[4]);
        } else if ((idTrip+1)%4 == 0) {
            tripImageIV.setImageResource(ids[3]);
        } else if ((idTrip+1)%3 == 0) {
            tripImageIV.setImageResource(ids[2]);
        } else if ((idTrip+1)%2 == 0) {
            tripImageIV.setImageResource(ids[1]);
        } else {
            tripImageIV.setImageResource(ids[0]);
        }

        recyclerViewPersonDetails = findViewById(R.id.recyclerViewPersonDetails);
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);

        adapter = new AdaptorViewPerson(arrayListPersonModel,currentTripModel.getTotleAmount(),this);
        // standard layout manager
        layoutManager = new LinearLayoutManager(this);

        adapterHistory = new AdaptorHistory(trasactionModelList,this);
        layoutManagerHistory = new LinearLayoutManager(this);

        recyclerViewPersonDetails.setAdapter(adapter);
        recyclerViewPersonDetails.setLayoutManager(layoutManager);

        recyclerViewHistory.setAdapter(adapterHistory);
        recyclerViewHistory.setLayoutManager(layoutManagerHistory);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewHistory.getContext(), LinearLayoutManager.VERTICAL);
        recyclerViewHistory.addItemDecoration(dividerItemDecoration);
        getData();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(new Intent("RELOAD_DATA"));
        getData();
        adapter.notifyDataSetChanged();
        adapterHistory.notifyDataSetChanged();
    }

    void getData(){
        arrayListPersonModel.clear();
        trasactionModelList.clear();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        arrayListPersonModel.addAll(dataBaseHelper.getAllGoalPerson(currentTripModel.getPrimaryId()));
        trasactionModelList.addAll(dataBaseHelper.getAllTrasaction());
        adapter.notifyDataSetChanged();
        adapterHistory.notifyDataSetChanged();
    }
}
