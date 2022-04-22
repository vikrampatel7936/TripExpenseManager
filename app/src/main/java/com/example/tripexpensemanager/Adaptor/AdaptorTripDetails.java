package com.example.tripexpensemanager.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.tripexpensemanager.ActivityTripDetails;
import com.example.tripexpensemanager.MainActivity;
import com.example.tripexpensemanager.Model.TripModel;
import com.example.tripexpensemanager.R;
import com.example.tripexpensemanager.TripDataAdd;

public class AdaptorTripDetails extends RecyclerView.Adapter<AdaptorTripDetails.MainViewHolder> {
    //variable declaration
    List<TripModel> tripModelList;
    Context context;
    int[] ids = new int[]{R.drawable.trip,R.drawable.trip1,R.drawable.trip2,R.drawable.trip3,R.drawable.trip4 };

    public AdaptorTripDetails(List<TripModel> tripModelList, Context context) {
        this.tripModelList = tripModelList;
        this.context = context;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{

        ImageView tripImageIV;
        TextView tripNameTV,dateTV,rsTV;
        LinearLayout tripLinearLayout;

        public MainViewHolder(@NonNull View i) {
            super(i);
            //finding view by id
            tripImageIV = i.findViewById(R.id.tripImageIV);
            tripNameTV = i.findViewById(R.id.tripNameTV);
            dateTV = i.findViewById(R.id.dateTV);
            rsTV = i.findViewById(R.id.rsTV);
            tripLinearLayout = i.findViewById(R.id.tripLinearLayout);

        }
    }
    // create function view holder
    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate this view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_trip,parent,false);
        //setting item view of holder
        MainViewHolder mainViewHolder = new MainViewHolder(view);
        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //setting name to text view
        TripModel tripModel = tripModelList.get(position);
        final int idTrip = tripModel.getPrimaryId();
        if ((idTrip+1)%5 == 0) {
            holder.tripImageIV.setImageResource(ids[4]);
        } else if ((idTrip+1)%4 == 0) {
            holder.tripImageIV.setImageResource(ids[3]);
        } else if ((idTrip+1)%3 == 0) {
            holder.tripImageIV.setImageResource(ids[2]);
        } else if ((idTrip+1)%2 == 0) {
            holder.tripImageIV.setImageResource(ids[1]);
        } else {
            holder.tripImageIV.setImageResource(ids[0]);
        }

        holder.tripNameTV.setText(tripModel.getTripName());
        holder.dateTV.setText(tripModel.getDate());
        holder.rsTV.setText(String.valueOf(tripModel.getDebitMoney())+"$ Balance");
        holder.tripLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityTripDetails.class);
                intent.putExtra("TRIP_POSITION",position);
                context.startActivity(intent);
            }
        });
    }
    //getting total size
    @Override
    public int getItemCount() {
        return tripModelList.size();
    }


}
