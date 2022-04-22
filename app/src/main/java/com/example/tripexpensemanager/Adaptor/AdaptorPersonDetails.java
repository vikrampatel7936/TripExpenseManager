package com.example.tripexpensemanager.Adaptor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.tripexpensemanager.Model.PersonModel;
import com.example.tripexpensemanager.R;
import com.example.tripexpensemanager.TripDataAdd;

public class AdaptorPersonDetails extends RecyclerView.Adapter<AdaptorPersonDetails.HomeViewHolder> {

    //variable declaration
    List<PersonModel> personModelList;
    Context context;

    public AdaptorPersonDetails(List<PersonModel> personModelList, Context context) {
        this.personModelList = personModelList;
        this.context = context;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        TextView characterTV, personNameTV;
        ImageView menuIV;
        LinearLayout recyclerClickAdmin;


        public HomeViewHolder(@NonNull View i) {
            super(i);
            //finding view by id
            characterTV = i.findViewById(R.id.characterTV);
            personNameTV = i.findViewById(R.id.personNameTV);
            menuIV = i.findViewById(R.id.menuIV);
            recyclerClickAdmin = i.findViewById(R.id.recyclerClickAdmin);
        }
    }
    // create function view holder
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate this view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_add_person, parent, false);
        //setting item view of holder
        HomeViewHolder homeViewHolder = new HomeViewHolder(view);
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //setting name to text view
        final PersonModel personModel = personModelList.get(position);
        holder.characterTV.setText(String.valueOf(personModel.getName().toUpperCase().charAt(0)));
        holder.personNameTV.setText(personModel.getName());

        if (position == TripDataAdd.positionAdmin) {
            holder.personNameTV.setText(personModel.getName() + "(Admin)");
        }
        holder.recyclerClickAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TripDataAdd.positionAdmin = position;
                notifyDataSetChanged();
            }
        });

        holder.menuIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, holder.menuIV);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_person);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                //handle menu1 click
                                final Dialog addPersonDialog = new Dialog(context);
                                //Add Person Dialog
                                addPersonDialog.setContentView(R.layout.custom_dialog_add_person);
                                addPersonDialog.show();
                                final EditText nameET;
                                final EditText amountET;
                                TextView cancelTV;
                                TextView okTV;
                                //finding view by id
                                nameET = addPersonDialog.findViewById(R.id.nameET);
                                amountET = addPersonDialog.findViewById(R.id.amountET);
                                cancelTV = addPersonDialog.findViewById(R.id.cancelTV);
                                okTV = addPersonDialog.findViewById(R.id.okTV);

                                nameET.setText(personModel.getName());
                                amountET.setText(String.valueOf(personModel.getAmountCredit()));

                                okTV.setOnClickListener(new View.OnClickListener() {
                                    //Add new Person
                                    @Override
                                    public void onClick(View v) {
                                        String personName = nameET.getText().toString().trim();
                                        String depositeAmount = amountET.getText().toString().trim();
                                        if (personName.isEmpty()) {
                                            nameET.setError("Enter person name");
                                            nameET.requestFocus();
                                        } else {
                                            if (depositeAmount.isEmpty()) {
                                                //Deposit is 0

                                                depositeAmount = "0";
                                            }
                                            personModelList.get(position).setName(personName);
                                            personModelList.get(position).setAmountCredit(Integer.valueOf(depositeAmount));

                                            notifyItemChanged(position);

                                            addPersonDialog.dismiss();
                                        }
                                    }
                                });

                                cancelTV.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        addPersonDialog.dismiss();
                                    }
                                });
                                return true;
                            case R.id.delete:
                                //handle menu2 click
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                                builder.setMessage("Do you want to delete this person ?").setTitle("Do you want to delete this person ?");

                                //Setting message manually and performing action on button click
                                builder.setMessage("Do you want to close this application ?")
                                        .setCancelable(false)
                                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                personModelList.remove(position);
                                                notifyItemRemoved(position);
                                                dialog.cancel();
                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //  Action for 'NO' Button
                                                dialog.cancel();

                                            }
                                        });
                                //Creating dialog box
                                AlertDialog alert = builder.create();
                                //Setting the title manually
                                alert.setTitle("Are You Sure ??");
                                alert.show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
    }
    //getting total size
    @Override
    public int getItemCount() {
        return personModelList.size();
    }
}