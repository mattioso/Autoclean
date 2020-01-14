package uk.co.appoly.autoclean;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class DateSelectActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        datePicker = findViewById(R.id.datePicker);
        submitButton = findViewById(R.id.submitButton);

        int currentDay = datePicker.getDayOfMonth();
        int currentMonth = datePicker.getMonth() + 1;
        int currentYear = datePicker.getYear();

        submitButton.setOnClickListener((v) -> {

             int day = datePicker.getDayOfMonth();
             int month = datePicker.getMonth() + 1;
             int year = datePicker.getYear();

            if (DateDatabase.checkDate(day, month, year, currentDay, currentMonth, currentYear)) {

                //Move to new activity

                Intent taskIntent = new Intent(this, DataCollectionActivity.class);
                taskIntent.putExtra(Constants.INTENT_DAY, day);
                taskIntent.putExtra(Constants.INTENT_MONTH, month);
                taskIntent.putExtra(Constants.INTENT_YEAR, year);
                startActivity(taskIntent);

            } else {
                Toast.makeText(this, "Data is unavailable", Toast.LENGTH_LONG).show();
            }

        });

    }
}
