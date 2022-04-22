package com.example.tripexpensemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.tripexpensemanager.Adaptor.AdaptorSelecetPersonWithCheckBoxDialog;
import com.example.tripexpensemanager.Adaptor.AdaptorSelectePerson;
import com.example.tripexpensemanager.Model.CategoryModel;
import com.example.tripexpensemanager.Model.PersonModel;
import com.example.tripexpensemanager.Model.TrasactionModel;
import com.example.tripexpensemanager.Model.TripModel;

public class ActivityAddExpense extends AppCompatActivity {
    // variable declaration
    private EditText descriptionET, enterAmountET;
    private ImageView addCategory;
    private CheckBox checkbox;
    private LinearLayout selectCategoryLinearLayout;
    private TextView dateTV, selectCategoryTextView, selectPerson1TextView, selectPerson2TextView, nameSelectedPerson,errorTextView;
    private ImageView calenderDialoImageView;
    private ImageView expenseByAdd;
    private LinearLayout selectPersonLinearLayout;
    private Spinner spinnerPerson2;
    private ImageView closeExpense;
    private Button saveExpenseBTN;
    private ImageView selectCategoryImageView;
    private ImageView selectPerson1ImageView;
    private ImageView selectPerson2ImageView;

    Boolean clickCategory = false;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(ActivityAddExpense.this);
    String category ;
    int perPersonPayment;
    TripModel currentTripModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        currentTripModel = MainActivity.tripModelList.get(ActivityTripDetails.position);
        Intent intent = getIntent();
        bindView();
        onClickHandler();

        String name = intent.getStringExtra("PERSON_NAME");
        selectPerson2TextView.setText(name);
        checkbox.setChecked(true);
    }

    void bindView() {
        //finding view by id
        descriptionET = findViewById(R.id.descriptionET);
        enterAmountET = findViewById(R.id.enterAmountET);
        errorTextView = findViewById(R.id.errorTextView);
        addCategory = findViewById(R.id.addCategory);
        checkbox = findViewById(R.id.checkbox);
        selectCategoryLinearLayout = findViewById(R.id.selectCategoryLinearLayout);
        dateTV = findViewById(R.id.dateTV);
        selectCategoryTextView = findViewById(R.id.selectCategoryTextView);
        selectPerson1TextView = findViewById(R.id.selectPerson1TextView);
        selectPerson2TextView = findViewById(R.id.selectPerson2TextView);
        nameSelectedPerson = findViewById(R.id.nameSelectedPerson);
        calenderDialoImageView = findViewById(R.id.calenderDialoImageView);
        expenseByAdd = findViewById(R.id.expenseByAdd);
        selectPersonLinearLayout = findViewById(R.id.selectPersonLinearLayout);
        closeExpense = findViewById(R.id.closeExpense);
        saveExpenseBTN = findViewById(R.id.saveExpenseBTN);
        selectCategoryImageView = findViewById(R.id.selectCategoryImageView);
        selectPerson1ImageView = findViewById(R.id.selectPerson1ImageView);
        selectPerson2ImageView = findViewById(R.id.selectPerson2ImageView);
        // getting current default date
        String date = new SimpleDateFormat("dd-MMM-yyyy ", Locale.getDefault()).format(new Date());
        dateTV.setText(date);
    }

    void onClickHandler() {
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkbox.isChecked()) {
                    selectCategoryLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    selectCategoryLinearLayout.setVisibility(View.GONE);
                }
            }
        });

        expenseByAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPersonLinearLayout.setVisibility(View.VISIBLE);
                enterAmountET.setVisibility(View.VISIBLE);
            }
        });

        closeExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPersonLinearLayout.setVisibility(View.GONE);
                enterAmountET.setVisibility(View.GONE);
            }
        });

        // adding new category
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dialog popup
                final Dialog addCategoryDialog = new Dialog(ActivityAddExpense.this);
                addCategoryDialog.setContentView(R.layout.custom_dialog_add_category);
                addCategoryDialog.show();

                final EditText nameET;
                TextView cancelTV;
                TextView okTV;

                nameET = addCategoryDialog.findViewById(R.id.nameET);
                cancelTV = addCategoryDialog.findViewById(R.id.cancelTV);
                okTV = addCategoryDialog.findViewById(R.id.okTV);

                cancelTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addCategoryDialog.dismiss();
                    }
                });

                okTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newCategoryName = nameET.getText().toString();
                        dataBaseHelper.insertDataCategory(newCategoryName);
                        addCategoryDialog.dismiss();
                    }
                });
            }
        });

        calenderDialoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Date Picker
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityAddExpense.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Date date = new Date();
                        date.setMonth(month);
                        date.setDate(dayOfMonth);

                        dateTV.setText((new SimpleDateFormat("dd-MMM", Locale.getDefault()).format(date)) + "-" + year);
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        selectCategoryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dialog popup
                final Dialog selectCategoryDialog = new Dialog(ActivityAddExpense.this);
                selectCategoryDialog.setContentView(R.layout.custom_dialog_category);
                selectCategoryDialog.show();
                List<CategoryModel> categoryModelList = dataBaseHelper.getAllCategory();
                String[] stringsArray = new String[categoryModelList.size()];
                for (int i = 0; i < stringsArray.length; i++) {
                    stringsArray[i] = categoryModelList.get(i).getCategory();
                }

                ArrayAdapter adapterNew = new ArrayAdapter<String>(ActivityAddExpense.this, R.layout.card_listview_category, stringsArray);
                ListView listView = (ListView) selectCategoryDialog.findViewById(R.id.listView);
                listView.setAdapter(adapterNew);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        clickCategory = true;
                        errorTextView.setVisibility(View.GONE);
                        TextView textView = view.findViewById(R.id.label);
                        category = textView.getText().toString();
                        selectCategoryTextView.setText(category);
                        selectCategoryDialog.dismiss();

                    }
                });

            }
        });


        selectPerson1ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog popup
                final Dialog selectPersonInDialog = new Dialog(ActivityAddExpense.this);
                selectPersonInDialog.setContentView(R.layout.custom_dialog_selects_persons_with_checkbox);
                selectPersonInDialog.show();
                setSelectedPersonname();
                nameSelectedPerson.setVisibility(View.VISIBLE);

                RecyclerView recyclerViewSelectPersonWithCheckBox;
                RecyclerView.Adapter adapter;
                RecyclerView.LayoutManager layoutManager;
                recyclerViewSelectPersonWithCheckBox = selectPersonInDialog.findViewById(R.id.recyclerViewSelectPersonWithCheckBox);
                adapter = new AdaptorSelecetPersonWithCheckBoxDialog(currentTripModel.getPersonModelList(), new AdaptorSelecetPersonWithCheckBoxDialog.CallBackSelectPersonWithCB() {
                    @Override
                    public void callBack(String name) {
                        setSelectedPersonname();
                    }
                });
                // standard layout manager
                layoutManager = new LinearLayoutManager(ActivityAddExpense.this);
                recyclerViewSelectPersonWithCheckBox.setAdapter(adapter);
                recyclerViewSelectPersonWithCheckBox.setLayoutManager(layoutManager);
            }
        });


        selectPerson2ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dialog popup
                final Dialog selectPersonInDialog = new Dialog(ActivityAddExpense.this);
                selectPersonInDialog.setContentView(R.layout.custom_dialog_select_person);
                selectPersonInDialog.show();

                RecyclerView recyclerViewSelectPerson;
                RecyclerView.Adapter adapter;
                RecyclerView.LayoutManager layoutManager;
                recyclerViewSelectPerson = selectPersonInDialog.findViewById(R.id.recyclerViewSelectPerson);
                adapter = new AdaptorSelectePerson(currentTripModel.getPersonModelList(), ActivityAddExpense.this, new AdaptorSelectePerson.CallBackSelectPerson() {
                    @Override
                    public void callBack(String name) {
                        selectPerson2TextView.setText(name);
                        selectPersonInDialog.dismiss();
                    }
                });
                //standard layout manager
                layoutManager = new LinearLayoutManager(ActivityAddExpense.this);
                recyclerViewSelectPerson.setAdapter(adapter);
                recyclerViewSelectPerson.setLayoutManager(layoutManager);
            }
        });


        selectCategoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dialog popup
                final Dialog selectCategoryDialog = new Dialog(ActivityAddExpense.this);
                selectCategoryDialog.setContentView(R.layout.custom_dialog_category);
                selectCategoryDialog.show();

                List<CategoryModel> categoryModelList = dataBaseHelper.getAllCategory();
                String[] stringsArray = new String[categoryModelList.size()];
                for (int i = 0; i < stringsArray.length; i++) {
                    stringsArray[i] = categoryModelList.get(i).getCategory();
                }

                ArrayAdapter adapterNew = new ArrayAdapter<String>(ActivityAddExpense.this, R.layout.card_listview_category, stringsArray);
                ListView listView = (ListView) selectCategoryDialog.findViewById(R.id.listView);
                listView.setAdapter(adapterNew);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        clickCategory = true;
                        errorTextView.setVisibility(View.GONE);
                        TextView textView = view.findViewById(R.id.label);
                        category = textView.getText().toString();
                        selectCategoryTextView.setText(category);
                        selectCategoryDialog.dismiss();

                    }
                });
            }
        });

        selectPerson1TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog popup
                final Dialog selectPersonInDialog = new Dialog(ActivityAddExpense.this);
                selectPersonInDialog.setContentView(R.layout.custom_dialog_selects_persons_with_checkbox);
                selectPersonInDialog.show();
                setSelectedPersonname();
                nameSelectedPerson.setVisibility(View.VISIBLE);
                RecyclerView recyclerViewSelectPersonWithCheckBox;
                RecyclerView.Adapter adapter;
                RecyclerView.LayoutManager layoutManager;
                recyclerViewSelectPersonWithCheckBox = selectPersonInDialog.findViewById(R.id.recyclerViewSelectPersonWithCheckBox);
                adapter = new AdaptorSelecetPersonWithCheckBoxDialog(currentTripModel.getPersonModelList(), new AdaptorSelecetPersonWithCheckBoxDialog.CallBackSelectPersonWithCB() {
                    @Override
                    public void callBack(String name) {
                        setSelectedPersonname();
                    }
                });
                //standard layout manager
                layoutManager = new LinearLayoutManager(ActivityAddExpense.this);
                recyclerViewSelectPersonWithCheckBox.setAdapter(adapter);
                recyclerViewSelectPersonWithCheckBox.setLayoutManager(layoutManager);
            }
        });

        selectPerson2TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog popup
                final Dialog selectPersonInDialog = new Dialog(ActivityAddExpense.this);
                selectPersonInDialog.setContentView(R.layout.custom_dialog_select_person);
                selectPersonInDialog.show();

                RecyclerView recyclerViewSelectPerson;
                RecyclerView.Adapter adapter;
                RecyclerView.LayoutManager layoutManager;
                recyclerViewSelectPerson = selectPersonInDialog.findViewById(R.id.recyclerViewSelectPerson);
                adapter = new AdaptorSelectePerson(currentTripModel.getPersonModelList(), ActivityAddExpense.this, new AdaptorSelectePerson.CallBackSelectPerson() {
                    @Override
                    public void callBack(String name) {
                        selectPerson2TextView.setText(name);
                        selectPersonInDialog.dismiss();
                    }
                });
                //standard layout manager
                layoutManager = new LinearLayoutManager(ActivityAddExpense.this);
                recyclerViewSelectPerson.setAdapter(adapter);
                recyclerViewSelectPerson.setLayoutManager(layoutManager);
            }
        });

        saveExpenseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidation()){
                    //saving data in database
                    saveData();
                }
            }
        });
    }

    void setSelectedPersonname() {
        StringBuilder name = new StringBuilder("");
        boolean isFirstTime = false;
        for (PersonModel personModel : currentTripModel.getPersonModelList()) {
            if (personModel.getSelectedPersonForPurchese()) {
                if (isFirstTime) {
                    name.append(personModel.getName());
                    isFirstTime = true;
                } else {
                    name.append("\n" + personModel.getName());
                }
            }
        }
        nameSelectedPerson.setText(String.valueOf(name));
    }
    // validation
    boolean isValidation() {
        if (descriptionET.getText().toString().isEmpty()) {
            descriptionET.setError("Please enter description.");
            descriptionET.requestFocus();
            return false;
        }else if (clickCategory == false){
            errorTextView.setVisibility(View.VISIBLE);
            errorTextView.setText("Please select category.");
            return false;
        }else if (enterAmountET.getText().toString().isEmpty()){
            enterAmountET.setError("Please enter amount.");
            enterAmountET.requestFocus();
            return false;
        }
        return true;
    }

    // saving data into database
    void saveData(){
        perPersonPayment = Integer.valueOf(enterAmountET.getText().toString())/currentTripModel.getPersonModelList().size();
        TrasactionModel trasactionModel = new TrasactionModel();
        trasactionModel.setTripId(Integer.valueOf(currentTripModel.getPrimaryId()));
        trasactionModel.setDescription(descriptionET.getText().toString().trim());
        trasactionModel.setCategory(category);
        trasactionModel.setPerPersonPayment(perPersonPayment);
        trasactionModel.setDate(dateTV.getText().toString().trim());
        trasactionModel.setPayAmount(Integer.valueOf(enterAmountET.getText().toString().isEmpty() ? "0": enterAmountET.getText().toString()));

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        long id = dataBaseHelper.insertDataTRASACTION(trasactionModel); //Trip Save In Database
        if (id == -1) {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            return;
        }

        long idUp = dataBaseHelper.updateDataPerson(trasactionModel.getTripId(), trasactionModel.getPerPersonPayment() + currentTripModel.getPersonModelList().get(0).getAmountDebit());

        if (idUp == -1){
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
        //method gets called when you press back button
        onBackPressed();
    }
}
