package uk.co.appoly.autoclean;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class DataCollectionActivity extends AppCompatActivity {

    private final int GET_SERVICE_REQUEST = 5000;

    private TextView dateTextView;
    private TextView combinedPriceTextView;

    private TextView nameEditText;
    private TextView houseEditText;
    private TextView streetEditText;
    private TextView cityEditText;
    private TextView postEditText;

    private Switch waterSwitch;
    private Switch powerSwitch;

    private Spinner timeSlotsSpinner;

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
    private String formCity;
    private String formPostcode;
    private String formTimeSlot;
    private Boolean waterSupply;
    private Boolean powerSupply;

    private String date;
    private int combinedPrice;

    private int day;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection);

        Bundle bundle = getIntent().getExtras();
        day = bundle.getInt(Constants.INTENT_DAY);
        month = bundle.getInt(Constants.INTENT_MONTH);
        year = bundle.getInt(Constants.INTENT_YEAR);

        date = "Selected date: " + dateConverter(day) + "/" + dateConverter(month) + "/" + year;

        dateTextView = findViewById(R.id.dateTextView);
        combinedPriceTextView = findViewById(R.id.combinedPriceTextView);

        nameEditText = findViewById(R.id.nameEditText);
        houseEditText = findViewById(R.id.addressOneEditText);
        streetEditText = findViewById(R.id.addressTwoEditText);
        cityEditText = findViewById(R.id.cityEditText);
        postEditText = findViewById(R.id.postcodeEditText);

        timeSlotsSpinner = findViewById(R.id.spinner1);

        waterSwitch = findViewById(R.id.waterSwitch);
        powerSwitch = findViewById(R.id.powerSwitch);

        bookButton = findViewById(R.id.bookButton);
        servicesButton = findViewById(R.id.servicesButton);

        displayServices = findViewById(R.id.servicesRecyclerView);

        dateTextView.setText(date);

        String[] timeSlots = new String[]{"Slot", "8am", "10am", "12pm", "2pm", "4pm"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeSlots);
        timeSlotsSpinner.setAdapter(adapter);

        setColour(nameEditText, Color.LTGRAY);
        setColour(houseEditText, Color.LTGRAY);
        setColour(streetEditText, Color.LTGRAY);
        setColour(cityEditText, Color.LTGRAY);
        setColour(postEditText, Color.LTGRAY);
        setColour(timeSlotsSpinner, Color.LTGRAY);
        setColour(servicesButton, Color.LTGRAY);
        setColour(waterSwitch, Color.BLACK, Color.RED);
        setColour(powerSwitch, Color.BLACK, Color.RED);

        setPressListener(nameEditText);
        setPressListener(houseEditText);
        setPressListener(streetEditText);
        setPressListener(cityEditText);
        setPressListener(postEditText);
        setPressListener(waterSwitch);
        setPressListener(powerSwitch);

        displayServices.hasFixedSize();
        displayServices.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {return false;}
        });

        mAdapter = new ServicesDisplayAdapter(this, selectedServicesTitles, selectedServicesPrice);
        displayServices.setAdapter(mAdapter);

        bookButton.setOnClickListener((v) -> moveActivity());

        servicesButton.setOnClickListener((v) -> {

            Intent taskIntent = new Intent(this, ServiceSelectionActivity.class);
            taskIntent.putIntegerArrayListExtra(ServiceSelectionActivity.SERVICES, selectedServices);
            startActivityForResult(taskIntent, GET_SERVICE_REQUEST);

        });

        timeSlotsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                setColour(timeSlotsSpinner, Color.LTGRAY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        
    }

    private void setPressListener(TextView editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setColour(editText, Color.LTGRAY);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setPressListener(Switch givenSwitch) {
        givenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) setColour(givenSwitch, Color.BLACK, Color.BLUE);
                else setColour(givenSwitch, Color.RED, Color.RED);
            }
        });
    }

    private String getText(TextView textView) {
        return textView.getText().toString();
    }
    private String getText(Spinner spinner) {
        return spinner.getSelectedItem().toString();
    }

    private void moveActivity() {

        formName = getText(nameEditText);
        formAddressLine1 = getText(houseEditText);
        formAddressLine2 = getText(streetEditText);
        formCity = getText(cityEditText);
        formPostcode = getText(postEditText);

        formTimeSlot = getText(timeSlotsSpinner);

        waterSupply = waterSwitch.isChecked();
        powerSupply = powerSwitch.isChecked();

        if (formComplete()) {
            //Will move to new activity

            Intent intent = new Intent(this, BookingConfirmActivity.class);
            intent.putExtra("date", date);
            intent.putExtra("price", combinedPrice);
            intent.putExtra("name", formName);
            intent.putExtra("addressLine1", formAddressLine1);
            intent.putExtra("addressLine2", formAddressLine2);
            intent.putExtra("city", formCity);
            intent.putExtra("postcode", formPostcode);
            intent.putExtra("timeSlot", formTimeSlot);
            intent.putExtra("services", selectedServices);
            intent.putExtra("waterSupply", waterSupply);
            intent.putExtra("powerSupply", powerSupply);
            intent.putExtra("serviceIds", selectedServices);
            startActivity(intent);


        } else {
            if (formName.equals("")) setColour(nameEditText, Color.RED);
            if (formAddressLine1.equals("")) setColour(houseEditText, Color.RED);
            if (formCity.equals("")) setColour(cityEditText, Color.RED);
            if (formPostcode.equals("")) setColour(postEditText, Color.RED);
            if (formTimeSlot.equals("Slot")) timeSlotsSpinner.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            if (selectedServices.isEmpty()) servicesButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            if (!waterSupply) setColour(waterSwitch, Color.RED, Color.RED);
            if (!powerSupply) setColour(powerSwitch, Color.RED, Color.RED);
        }
    }

    private void setColour(TextView textView, int colour) {
        textView.setBackgroundTintList(ColorStateList.valueOf(colour));
        textView.setHintTextColor(ColorStateList.valueOf(colour));
    }

    private void setColour(Switch changeSwitch, int colour, int switchColour) {
        changeSwitch.setTextColor(colour);
        changeSwitch.setThumbTintList(ColorStateList.valueOf(switchColour));
    }

    private void setColour(Button button, int colour) {
        button.setBackgroundTintList(ColorStateList.valueOf(colour));
    }

    private void setColour(Spinner spinner, int colour) {
        spinner.setBackgroundTintList(ColorStateList.valueOf(colour));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GET_SERVICE_REQUEST) {

                try {
                    ArrayList<Integer> returnedServices = data.getBundleExtra(ServiceSelectionActivity.SERVICE_BUNDLE).getIntegerArrayList(ServiceSelectionActivity.SERVICES);

                    if (returnedServices == null) return;

                    selectedServices = returnedServices;
                    combinedPrice = 0;
                    selectedServicesTitles.clear();

                    for(int id : selectedServices) {
                        selectedServicesTitles.add(ServiceDatabase.getServiceTypes().get(id).title);
                        selectedServicesPrice.add(ServiceDatabase.getServiceTypes().get(id).price);
                        combinedPrice += ServiceDatabase.getServiceTypes().get(id).price;
                    }

                    if(selectedServices.size() > 1) {
                        combinedPriceTextView.setVisibility(View.VISIBLE);
                        combinedPriceTextView.setText("Total: £" + combinedPrice);
                    } else {
                        combinedPriceTextView.setVisibility(View.GONE);
                    }

                    if(!selectedServices.isEmpty()) {
                        setColour(servicesButton, Color.LTGRAY);
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
        return !(formName.equals("") || formAddressLine1.equals("") || formCity.equals("") || formPostcode.equals("") || formTimeSlot.equals("Slot"));
    }

    private String dateConverter(int date) {
        if (date > 9) return Integer.toString(date);
        return "0" + date;
    }

}
