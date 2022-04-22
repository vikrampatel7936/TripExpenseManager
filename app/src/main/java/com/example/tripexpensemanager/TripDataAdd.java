package com.example.tripexpensemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.tripexpensemanager.Adaptor.AdaptorPersonDetails;
import com.example.tripexpensemanager.Model.PersonModel;
import com.example.tripexpensemanager.Model.TripModel;

public class TripDataAdd extends AppCompatActivity {

    private final String TAG = this.getClass().getName();
    //variable declaration
    private EditText nameTripET;
    private EditText descriptionET;
    private TextView dateTV;
    private ImageView addPersonImageView;
    private ImageView calenderDialoImageView;
    private Button saveTripBTN;

    //Recycler View
    private RecyclerView recyclerViewPerson;
    //Layout Manager
    private RecyclerView.LayoutManager layoutManager;
    //Adapter
    private RecyclerView.Adapter adapter;
    private List<PersonModel> personModelList = new ArrayList<>(); //Recycler View Data
    //variable declaration
    EditText nameET;
    EditText amountET;
    TextView cancelTV;
    TextView okTV;

    private String tripNameString, tripDescriptionString, tripDate;

    public static int positionAdmin = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_data_add);
        positionAdmin = -1;

        bindView();
        onClickEventHandler();
        setRecyclerViewPerson();
    }

    void bindView(){
        Log.i(TAG, "Bind view");
        //finding view by id
        nameTripET = findViewById(R.id.nameTripET);
        descriptionET = findViewById(R.id.descriptionET);
        dateTV = findViewById(R.id.dateTV);
        recyclerViewPerson = findViewById(R.id.recyclerViewPerson);
        saveTripBTN = findViewById(R.id.saveTripBTN);
        addPersonImageView = findViewById(R.id.addPersonImageView);
        calenderDialoImageView = findViewById(R.id.calenderDialoImageView);

        //TodayDefualtDate
        String date = new SimpleDateFormat("dd-MMM-yyyy ", Locale.getDefault()).format(new Date());
        dateTV.setText(date);
        //Standard Layout Manager
        layoutManager = new LinearLayoutManager(this);
        adapter = new AdaptorPersonDetails(personModelList , this);//Adapter
    }

    void onClickEventHandler(){ //On Click handler
        Log.i(TAG, "OnClick handler");

        calenderDialoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "DatePicker Dialog");
                //date picker
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(TripDataAdd.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Date date = new Date();
                        date.setMonth(month);
                        date.setDate(dayOfMonth);

                        dateTV.setText((new SimpleDateFormat("dd-MMM", Locale.getDefault()).format(date)) +"-"+year);
                    }
                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        addPersonImageView.setOnClickListener(new View.OnClickListener() { //AddPerson Click
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Add Person Click");
                //Add New Person
                addNewPerson();
            }
        });

        saveTripBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Save Trip Button Clicked");

                if (isValidTripData()){
                    saveData(); //Save Database
                }
            }
        });
    }

    void setRecyclerViewPerson(){
        //RecyclerView Set
        Log.i(TAG, "Recycler View Set");
        recyclerViewPerson.setLayoutManager(layoutManager);
        recyclerViewPerson.setAdapter(adapter);
    }
    //To Add New Person
    void addNewPerson() {
        Log.i(TAG, "Add Person");
        final Dialog addPersonDialog = new Dialog(this); //Add Person Dialog
        addPersonDialog.setContentView(R.layout.custom_dialog_add_person);
        addPersonDialog.show();
        bindDialogView(addPersonDialog); //Link Dialog View (Bind View)

        okTV.setOnClickListener(new View.OnClickListener() { //Ad new Person
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Add Person Start ");
                if (isValidDialogData()) {
                    String personName = nameET.getText().toString().trim();
                    String depositeAmount = amountET.getText().toString().trim();

                    if (depositeAmount.isEmpty()) { //Deposit is 0
                        Log.i(TAG, "deposit Amount is 0");
                        depositeAmount = "0";
                    }

                    PersonModel personModel = new PersonModel();
                    personModel.setName(personName);
                    personModel.setAmountCredit(Integer.valueOf(depositeAmount));
                    personModel.setAmountDebit(0);

                    savePersonData(personModel);
                    addPersonDialog.dismiss();
                }
            }
        });

        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Add Person Dialog Cancel");
                addPersonDialog.dismiss();
            }
        });
    }

    void bindDialogView(Dialog addPersonDialog) {
        Log.i(TAG, "Add Person Dialog Bind View");
        //finding view by id
        nameET = addPersonDialog.findViewById(R.id.nameET);
        amountET = addPersonDialog.findViewById(R.id.amountET);
        cancelTV = addPersonDialog.findViewById(R.id.cancelTV);
        okTV = addPersonDialog.findViewById(R.id.okTV);
    }
    // validation
    boolean isValidDialogData(){
        Log.i(TAG, "Validation of Add Person Dialog Data");
        String personName = nameET.getText().toString().trim();

        if (personName.isEmpty()) {
            Log.w(TAG,"Person Name is empty");
            nameET.setError("Enter person name");
            nameET.requestFocus();
            return  false;
        }

        return  true;
    }
    //Add Person to List of RecycelrView
    void savePersonData(PersonModel personModel) {
        Log.i(TAG, "Add New person in List");
        personModelList.add(personModel);
        adapter.notifyDataSetChanged();
    }

    //TODO: Trip Data
    //Trip Data Validation
    boolean isValidTripData(){
        tripNameString = nameTripET.getText().toString().trim();
        tripDescriptionString = descriptionET.getText().toString().trim();

        if (tripNameString.isEmpty()) {
            Log.w(TAG,"Trip Name is empty");
            nameTripET.setError("Enter trip name");
            nameTripET.requestFocus();
            return  false;
        }

        if (tripDescriptionString.isEmpty()) {
            tripDescriptionString = " ";
        }

        if (personModelList.size() < 2) {
            Toast.makeText(this, "please enter atleast 2 person.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (positionAdmin == -1) {
            Toast.makeText(this, "please Select Admin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    // storing data to database
    void saveData(){
        Log.i(TAG,"Save Data in Database");
        TripModel tripModel =  new TripModel(); //Trip model for save
        tripModel.setTripName(tripNameString);
        tripModel.setDescription(tripDescriptionString);
        tripModel.setDate(dateTV.getText().toString().trim());

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        long id = dataBaseHelper.insertDataTrip(tripModel);
        if (id == -1) {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i =0 ; i < personModelList.size(); i++) {
            personModelList.get(i).setTripId((int) id);
            if (i == positionAdmin) {
                personModelList.get(i).setAdmin("admin");
                Log.i(TAG, "Admin Position "+ i);
            } else {
                personModelList.get(i).setAdmin(" ");
            }
            long person_id = dataBaseHelper.insertDataPerson(personModelList.get(i));

            Log.i(TAG, "Trip id "+id+" Person id "+ person_id);
            if (person_id == -1) {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //method gets called when you press back button
        onBackPressed();
    }
}
