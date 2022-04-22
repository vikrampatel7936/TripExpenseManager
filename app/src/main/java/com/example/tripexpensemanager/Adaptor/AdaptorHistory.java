package com.example.tripexpensemanager.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.tripexpensemanager.Model.TrasactionModel;
import com.example.tripexpensemanager.R;

public class AdaptorHistory extends RecyclerView.Adapter<AdaptorHistory.HistoryViewHolder> {
    //variable declaration
    List<TrasactionModel> trasactionModelList;
    Context context;

    public AdaptorHistory(List<TrasactionModel> trasactionModelList, Context context) {
        this.trasactionModelList = trasactionModelList;
        this.context = context;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        TextView description,payAmount,date,category;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            //finding view by id
            description = itemView.findViewById(R.id.description);
            payAmount = itemView.findViewById(R.id.payAmount);
            date = itemView.findViewById(R.id.date);
            category = itemView.findViewById(R.id.category);
        }
    }
    // create function view holder
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate this view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_history_payment,parent,false);
        //setting item view of holder
        HistoryViewHolder historyViewHolder = new HistoryViewHolder(view);
        return historyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        //setting name to text view
        TrasactionModel trasactionModel = trasactionModelList.get(position);

        holder.description.setText(trasactionModel.getDescription());
        holder.payAmount.setText(String.valueOf(trasactionModel.getPayAmount()));
        holder.date.setText(trasactionModel.getDate());
        holder.category.setText(trasactionModel.getCategory());

    }
    //getting total size
    @Override
    public int getItemCount() {
        return trasactionModelList.size();
    }

}
