package uk.co.appoly.autoclean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        submitButton = findViewById(R.id.button);

        submitButton.setOnClickListener((v) -> {

            if (LoginDatabase.Login(email.getText().toString(), password.getText().toString())) {
                //Move to new activity

                Intent TaskIntent = new Intent(this, DateSelectActivity.class);
                startActivity(TaskIntent);
                finish();

            } else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
                password.setText("");
            }

        });

    }
}
