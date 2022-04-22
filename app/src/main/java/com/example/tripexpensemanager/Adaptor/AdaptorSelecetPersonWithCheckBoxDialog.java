package com.example.tripexpensemanager.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.List;

import com.example.tripexpensemanager.Model.PersonModel;
import com.example.tripexpensemanager.R;

public class AdaptorSelecetPersonWithCheckBoxDialog extends RecyclerView.Adapter<AdaptorSelecetPersonWithCheckBoxDialog.PersonSelectHomeViewHolder> {
    //variable declaration
    List<PersonModel> personModelList;
    CallBackSelectPersonWithCB callBackSelectPersonWithCB;


    public AdaptorSelecetPersonWithCheckBoxDialog(List<PersonModel> personModelList, CallBackSelectPersonWithCB callBackSelectPersonWithCB) {
        this.personModelList = personModelList;
        this.callBackSelectPersonWithCB = callBackSelectPersonWithCB;
    }

    public class PersonSelectHomeViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;
        TextView textViewPersonName;


        public PersonSelectHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            //finding view by id
            checkBox =itemView.findViewById(R.id.checkbox);
            textViewPersonName = itemView.findViewById(R.id.textViewPersonName);

        }
    }
    // create function view holder
    @NonNull
    @Override
    public PersonSelectHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate this view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_checkbox_dialog,parent,false);
        //setting item view of holder
        PersonSelectHomeViewHolder personSelectHomeViewHolder = new PersonSelectHomeViewHolder(view);
        return personSelectHomeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PersonSelectHomeViewHolder holder, int position) {
        //setting name to text view
        final PersonModel personModel = personModelList.get(position);
        holder.textViewPersonName.setText(personModel.getName());
        if (personModel.getSelectedPersonForPurchese()) {
            holder.checkBox.setChecked(true);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personModel.getSelectedPersonForPurchese()) {
                    personModel.setSelectedPersonForPurchese(false);
                    callBackSelectPersonWithCB.callBack(personModel.getName());
                } else {
                    personModel.setSelectedPersonForPurchese(true);
                    callBackSelectPersonWithCB.callBack(personModel.getName());
                }
            }
        });

    }
    //getting total size
    @Override
    public int getItemCount() {
        return personModelList.size();
    }

    public interface  CallBackSelectPersonWithCB{
        void callBack(String name);
    }


}

