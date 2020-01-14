package uk.co.appoly.autoclean;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import static java.security.AccessController.getContext;

public class ServiceSelectionActivity extends AppCompatActivity implements ServicesRecyclerViewAdapter.ItemClickListener{

    private RecyclerView recyclerView;
    private ServicesRecyclerViewAdapter mAdapter;

    private List<ServiceModel> serviceTypes;

    public static String SERVICE_TITLE = "SERVICE_TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_selection);

        recyclerView = findViewById(R.id.servicesView);
        recyclerView.hasFixedSize();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        serviceTypes = ServiceDatabase.getServiceTypes();
        mAdapter = new ServicesRecyclerViewAdapter(getApplicationContext(), serviceTypes);

        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent resultData = new Intent();
        resultData.putExtra(SERVICE_TITLE, serviceTypes.get(position).title);
        setResult(RESULT_OK, resultData);
        finish();
    }
}
