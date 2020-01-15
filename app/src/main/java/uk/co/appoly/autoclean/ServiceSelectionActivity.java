package uk.co.appoly.autoclean;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ServiceSelectionActivity extends AppCompatActivity implements ServicesRecyclerViewAdapter.ItemClickListener{

    private RecyclerView recyclerView;
    private ServicesRecyclerViewAdapter mAdapter;

    private List<ServiceModel> serviceTypes;

    public static String SERVICE_BUNDLE = "SERVICE_BUNDLE";
    public static String SERVICES = "SERVICES";

    public ArrayList<Integer> returningServices;

    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_selection);

        Bundle bundle = getIntent().getExtras();
        returningServices = bundle.getIntegerArrayList(SERVICES);

        recyclerView = findViewById(R.id.servicesView);
        recyclerView.hasFixedSize();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        serviceTypes = ServiceDatabase.getServiceTypes();
        mAdapter = new ServicesRecyclerViewAdapter(this, serviceTypes, returningServices);

        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);

        returnButton = findViewById(R.id.placeOrderButton);

        returnButton.setOnClickListener((v) -> {

            Bundle extra = new Bundle();
            extra.putIntegerArrayList(SERVICES, returningServices);

            Intent resultData = new Intent();
            resultData.putExtra(SERVICE_BUNDLE, extra);
            setResult(RESULT_OK, resultData);
            finish();

        });

    }

    @Override
    public void onItemClick(View view, int position) {

        if (!returningServices.contains(serviceTypes.get(position).id)) {
            returningServices.add(serviceTypes.get(position).id);
        } else {
            returningServices.remove(returningServices.indexOf(serviceTypes.get(position).id));
        }
    }
}
