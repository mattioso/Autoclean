package uk.co.appoly.autoclean;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DataCollectionActivity extends AppCompatActivity {

    private final int GET_SERVICE_REQUEST = 5000;

    private Spinner timeSlotsSpinner;

    private TextView nameEditText;
    private TextView houseEditText;
    private TextView streetEditText;
    private TextView townEditText;
    private TextView postEditText;

    private Switch waterSwitch;
    private Switch powerSwitch;

    private Button bookButton;
    private Button servicesButton;

    private ArrayList<Integer> selectedServices = new ArrayList<>();
    private ArrayList<String> selectedServicesTitles = new ArrayList<>();
    private ArrayList<Integer> selectedServicesPrice = new ArrayList<>();

    private RecyclerView displayServices;
    private RecyclerView.Adapter mAdapter;

    private String formName;
    private String formAddressLine1;
    private String formAddressLine2;
    private String formTown;
    private String formPostcode;
    private String formTimeSlot;
    private Boolean waterSupply;
    private Boolean powerSupply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection);

        Bundle bundle = getIntent().getExtras();
        int day = bundle.getInt(Constants.INTENT_DAY);
        int month = bundle.getInt(Constants.INTENT_MONTH);
        int year = bundle.getInt(Constants.INTENT_YEAR);

        timeSlotsSpinner = findViewById(R.id.spinner1);

        String[] timeSlots = new String[]{"Slot", "8am", "10am", "12am", "2am", "4am"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeSlots);
        timeSlotsSpinner.setAdapter(adapter);

        nameEditText = findViewById(R.id.nameEditText);
        houseEditText = findViewById(R.id.addressOneEditText);
        streetEditText = findViewById(R.id.addressTwoEditText);
        townEditText = findViewById(R.id.townEditText);
        postEditText = findViewById(R.id.postcodeEditText);

        waterSwitch = findViewById(R.id.waterSwitch);
        powerSwitch = findViewById(R.id.powerSwitch);

        bookButton = findViewById(R.id.bookButton);
        servicesButton = findViewById(R.id.servicesButton);

        displayServices = findViewById(R.id.servicesRecyclerView);

        setGrey(nameEditText);
        setGrey(houseEditText);
        setGrey(streetEditText);
        setGrey(townEditText);
        setGrey(postEditText);
        setGrey(timeSlotsSpinner);
        setGrey(servicesButton);

        colourReset(nameEditText);
        colourReset(houseEditText);
        colourReset(streetEditText);
        colourReset(townEditText);
        colourReset(postEditText);

        displayServices.hasFixedSize();
        displayServices.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        mAdapter = new ServicesDisplayAdapter(this, selectedServicesTitles, selectedServicesPrice);
        displayServices.setAdapter(mAdapter);

        bookButton.setOnClickListener((v) -> validateForm());

        servicesButton.setOnClickListener((v) -> {

            Intent taskIntent = new Intent(this, ServiceSelectionActivity.class);
            taskIntent.putIntegerArrayListExtra(ServiceSelectionActivity.SERVICES, selectedServices);
            startActivityForResult(taskIntent, GET_SERVICE_REQUEST);

        });

        

    }

    private void colourReset(TextView editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setTextViewColour(editText, Color.GRAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String getText(TextView textView) {
        return textView.getText().toString();
    }
    private String getText(Spinner spinner) {
        return spinner.getSelectedItem().toString();
    }

    private void validateForm() {

        formName = getText(nameEditText);
        formAddressLine1 = getText(houseEditText);
        formAddressLine2 = getText(streetEditText);
        formTown = getText(townEditText);
        formPostcode = getText(townEditText);

        formTimeSlot = getText(timeSlotsSpinner);

        waterSupply = waterSwitch.isChecked();
        powerSupply = powerSwitch.isChecked();

        if (formComplete()) {
            //Will move to new activity
            Toast.makeText(this, "HERE", Toast.LENGTH_LONG).show();
        } else {

            if (formName.equals("")) setTextViewColour(nameEditText, Color.RED);
            if (formAddressLine1.equals("")) setTextViewColour(houseEditText, Color.RED);
            if (formAddressLine2.equals("")) setTextViewColour(streetEditText, Color.RED);
            if (formTown.equals("")) setTextViewColour(townEditText, Color.RED);
            if (formPostcode.equals("")) setTextViewColour(postEditText, Color.RED);
            if (formTimeSlot.equals("Slot")) timeSlotsSpinner.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            if (selectedServices.isEmpty()) servicesButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));

        }
    }

    private void setTextViewColour(TextView textView, int colour) {
        textView.setBackgroundTintList(ColorStateList.valueOf(colour));
        textView.setHintTextColor(ColorStateList.valueOf(colour));
    }

    private void setGrey(TextView textView) {
        setTextViewColour(textView, Color.LTGRAY);
    }
    private void setGrey(Button button) {
        button.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
    }
    private void setGrey(Spinner spinner) {
        spinner.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GET_SERVICE_REQUEST) {

                try {
                    ArrayList<Integer> returnedServices = data.getBundleExtra(ServiceSelectionActivity.SERVICE_BUNDLE).getIntegerArrayList(ServiceSelectionActivity.SERVICES);

                    if (returnedServices == null) return;

                    selectedServices = returnedServices;
                    selectedServicesTitles.clear();

                    for(int id : selectedServices) {
                        selectedServicesTitles.add(ServiceDatabase.getServiceTypes().get(id).title);
                        selectedServicesPrice.add(ServiceDatabase.getServiceTypes().get(id).price);
                    }

                    if(!selectedServices.isEmpty()) {
                        setGrey(servicesButton);
                        Log.v("Debug", "HERE");
                    }

                    mAdapter = new ServicesDisplayAdapter(this, selectedServicesTitles, selectedServicesPrice);
                    displayServices.setAdapter(mAdapter);

                } catch (NullPointerException e) {
                    Log.v("Debug", "No services returned");
                }

            }
        }
    }

    private boolean formComplete() {
        return !(formName.equals("") || formAddressLine1.equals("") || formAddressLine2.equals("") || formTown.equals("") || formPostcode.equals("") || formTimeSlot.equals("Slot"));
    }

}
