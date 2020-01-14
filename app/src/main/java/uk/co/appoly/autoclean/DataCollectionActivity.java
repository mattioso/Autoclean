package uk.co.appoly.autoclean;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class DataCollectionActivity extends AppCompatActivity {

    private final int GET_SERVICE_REQUEST = 5000;

    private Spinner timeSlotsSpinner;

    private TextView nameTextView;
    private TextView houseTextView;
    private TextView streetTextView;
    private TextView townTextView;
    private TextView postCodeTextView;

    private Switch waterSwitch;
    private Switch powerSwitch;

    private Button bookButton;
    private Button servicesButton;

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

        nameTextView = findViewById(R.id.nameEditText);
        houseTextView = findViewById(R.id.addressOneEditText);
        streetTextView = findViewById(R.id.addressTwoEditText);
        townTextView = findViewById(R.id.townEditText);
        postCodeTextView = findViewById(R.id.postcodeEditText);

        waterSwitch = findViewById(R.id.waterSwitch);
        powerSwitch = findViewById(R.id.powerSwitch);

        bookButton = findViewById(R.id.bookButton);
        servicesButton = findViewById(R.id.servicesButton);

        bookButton.setOnClickListener((v) -> {

            Dictionary bookingInfo = new Hashtable();
            bookingInfo.put("name", nameTextView.getText().toString());
            bookingInfo.put("line1", houseTextView.getText().toString());
            bookingInfo.put("line2", streetTextView.getText().toString());
            bookingInfo.put("town", townTextView.getText().toString());
            bookingInfo.put("postcode", postCodeTextView.getText().toString());

            bookingInfo.put("timeSlot", timeSlotsSpinner.getSelectedItem().toString());

            bookingInfo.put("waterSupply", String.valueOf(waterSwitch.isChecked()));
            bookingInfo.put("powerSupply", String.valueOf(powerSwitch.isChecked()));


            Toast.makeText(this, "" + bookingInfo, Toast.LENGTH_LONG).show();

        });

        servicesButton.setOnClickListener((v) -> {

            Intent taskIntent = new Intent(this, ServiceSelectionActivity.class);
            startActivityForResult(taskIntent, GET_SERVICE_REQUEST);

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GET_SERVICE_REQUEST) {

                try {
                    ArrayList<String> returnedServices = data.getBundleExtra(ServiceSelectionActivity.SERVICE_BUNDLE).getStringArrayList(ServiceSelectionActivity.SERVICES);

                    if (returnedServices == null) return;

                    for (String s : returnedServices) {
                        Log.v("Debug Test", s);
                    }
                } catch (NullPointerException e) {
                    Log.v("Debug", "No services returned");
                }

            }
        }
    }
}
