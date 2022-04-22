package com.example.tripexpensemanager.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.tripexpensemanager.ActivityAddExpense;
import com.example.tripexpensemanager.Model.PersonModel;
import com.example.tripexpensemanager.R;

public class AdaptorSelectePerson extends RecyclerView.Adapter<AdaptorSelectePerson.ViewHolder> {
    //variable declaration
    List<PersonModel> personModelList;
    Context context;
    CallBackSelectPerson callBackSelectPerson;

    public AdaptorSelectePerson(List<PersonModel> personModelList, Context context, CallBackSelectPerson callBackSelectPerson) {
        this.personModelList = personModelList;
        this.context = context;
        this.callBackSelectPerson = callBackSelectPerson;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewPersonName;
        LinearLayout linearPersonName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //finding view by id
            textViewPersonName = itemView.findViewById(R.id.textViewPersonName);
            linearPersonName = itemView.findViewById(R.id.linearPersonName);
        }
    }
    // create function view holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate this view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_selectperson_dialog,parent,false);
        //setting item view of holder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //setting name to text view
        final PersonModel personModel = personModelList.get(position);
        holder.textViewPersonName.setText(personModel.getName());

        holder.linearPersonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackSelectPerson.callBack(personModel.getName());

            }
        });

    }
    //getting total size
    @Override
    public int getItemCount() {
        return personModelList.size();
    }

    public interface  CallBackSelectPerson{
        void callBack(String name);
    }
}

