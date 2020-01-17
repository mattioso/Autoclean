package uk.co.appoly.autoclean;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookingConfirmActivity extends AppCompatActivity {

    private String date;
    private int price;
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postcode;

    private String timeSlot;
    private Boolean waterSupply;
    private Boolean powerSupply;

    private ArrayList<Integer> serviceIds;

    private TextView nameTextView;
    private TextView priceTextView;
    private TextView dateTextView;
    private TextView addressLine1TextView;
    private TextView addressLine2TextView;
    private TextView cityTextView;
    private TextView postcodeTextView;
    private TextView timeSlotTextView;
    private TextView waterSupplyTextView;
    private TextView powerSupplyTextView;

    private Button returnButton;

    private RecyclerView recyclerView;
    private servicesConfirmRecyclerViewAdapter adapter;

    private ArrayList<ServiceModel> selectedServices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirm);

        Bundle bundle = getIntent().getExtras();
        importFormInfo(bundle);
        setUIElements();

        returnButton.setOnClickListener((v) -> {
            Intent intent = new Intent(this, DateSelectActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void setRecycler() {

        List<ServiceModel> allServices = ServiceDatabase.getServiceTypes();

        for (int i : serviceIds) {
            selectedServices.add(allServices.get(i));
        }

        recyclerView = findViewById(R.id.bookingConfirmRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new servicesConfirmRecyclerViewAdapter(this, selectedServices);

        recyclerView.setAdapter(adapter);

    }

    private void importFormInfo(Bundle bundle) {

        date = bundle.getString("date");
        price = bundle.getInt("price");

        name = bundle.getString("name");
        addressLine1 = bundle.getString("addressLine1");
        addressLine2 = bundle.getString("addressLine2");
        city = bundle.getString("city");
        postcode = bundle.getString("postcode");

        timeSlot = bundle.getString("timeSlot");
        waterSupply = bundle.getBoolean("waterSupply");
        powerSupply = bundle.getBoolean("powerSupply");

        serviceIds = bundle.getIntegerArrayList("serviceIds");

    }

    private void setUIElements() {

        nameTextView = findViewById(R.id.nameTextView);
        priceTextView = findViewById(R.id.priceTextView);
        dateTextView = findViewById(R.id.selectedDateTextView);
        addressLine1TextView = findViewById(R.id.addressLine1TextView);
        addressLine2TextView = findViewById(R.id.addressLine2TextView);
        cityTextView = findViewById(R.id.cityTextView);
        postcodeTextView = findViewById(R.id.postcodeTextView);
        timeSlotTextView = findViewById(R.id.timeSlotTextView);
        waterSupplyTextView = findViewById(R.id.waterSupplyTextView);
        powerSupplyTextView = findViewById(R.id.powerSupplyTextView);
        returnButton = findViewById(R.id.returnButton);

        nameTextView.setText(name);
        priceTextView.setText("Price: £" + price);
        dateTextView.setText(date);
        addressLine1TextView.setText(addressLine1);
        addressLine2TextView.setText(addressLine2);
        cityTextView.setText(city);
        postcodeTextView.setText(postcode);
        timeSlotTextView.setText(timeSlot);
        waterSupplyTextView.setText("Water: " + boolChange(waterSupply));
        powerSupplyTextView.setText("Power: " + boolChange(powerSupply));

        if (addressLine2.equals("")) addressLine2TextView.setVisibility(View.GONE);

        setRecycler();

    }

    private String boolChange(Boolean bool) {
        if (bool) return "Supplied";
        return "Not supplied";
    }


}
