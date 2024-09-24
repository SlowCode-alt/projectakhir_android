package com.example.ngikngik;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;


public class register extends AppCompatActivity {

    private EditText etBirthdate;
    private EditText etFullname, etUsername, etId, etEmail, etPassword, etVerificationPassword, etAddress;
    private Spinner spinnerGender;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize fields
        etFullname = findViewById(R.id.etFullname);
        etUsername = findViewById(R.id.etUsername);
        etId = findViewById(R.id.etId);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etVerificationPassword = findViewById(R.id.etVerificationPassword);
        etBirthdate = findViewById(R.id.etBirthdate);
        etAddress = findViewById(R.id.etAddress);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnRegister = findViewById(R.id.btnRegister);

        // Set up the date picker for birthdate field
        etBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        // Handle Register button click
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collect and validate data
                String fullname = etFullname.getText().toString();
                String username = etUsername.getText().toString();
                String id = etId.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String verifyPassword = etVerificationPassword.getText().toString();
                String birthdate = etBirthdate.getText().toString();
                String address = etAddress.getText().toString();
                String gender = spinnerGender.getSelectedItem().toString();

                // Example: Basic validation (extend as needed)
                if (fullname.isEmpty() || username.isEmpty() || id.isEmpty() ||
                        password.isEmpty() || birthdate.isEmpty() || address.isEmpty()) {
                    Toast.makeText(register.this, "Please fill all fields correctly.", Toast.LENGTH_SHORT).show();
                }else if (!email.endsWith("@gmail.com")){
                    Toast.makeText(register.this, "Email must be a valid @gmail.com address.", Toast.LENGTH_SHORT).show();
                } else if  (!password.equals(verifyPassword)){
                    Toast.makeText(register.this, "Verification code does not match.", Toast.LENGTH_SHORT).show();
                } else {
                    // Register the user (extend with actual registration logic)
                    Toast.makeText(register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etBirthdate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }
}