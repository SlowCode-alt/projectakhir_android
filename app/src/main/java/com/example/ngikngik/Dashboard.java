package com.example.ngikngik;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Dashboard extends AppCompatActivity {
    private TextView tvFullname, tvUsername, tvEmail, tvGender, tvAddress, tvTanggalLahir, tvPhone;
    private Button btnlogout;
    Dialog dialog;
    private Button btndialoglogout, btndialogcancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dialog = new Dialog(Dashboard.this);
        dialog.setContentView(R.layout.logout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.customdialog));
        dialog.setCancelable(false);

        btndialoglogout = dialog.findViewById(R.id.btndialoglogout);
        btndialogcancel = dialog.findViewById(R.id.btndialogcancel);

        btndialogcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btndialoglogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, login.class);
                startActivity(intent);
                Toast.makeText(Dashboard.this,"Logout Berhasil!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnlogout=findViewById(R.id.btn_logout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        // Inisialisasi TextView
        tvFullname = findViewById(R.id.tvFullname);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvGender = findViewById(R.id.tvGender);
        tvAddress = findViewById(R.id.tvAddress);
        tvPhone = findViewById(R.id.tvPhone);
        tvTanggalLahir = findViewById(R.id.tvTanggal);

        // Dapatkan data yang dikirim dari RegisterActivity
        Intent intent = getIntent();
        String fullname = intent.getStringExtra("fullname");
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String gender = intent.getStringExtra("gender");
        String birthdate = intent.getStringExtra("birthdate");
        String address = intent.getStringExtra("address");
        String phone = intent.getStringExtra("phone");
        String tanggal = intent.getStringExtra("tanggal");

        // Tampilkan data di TextView
        tvFullname.setText("Full Name: " + fullname);
        tvUsername.setText("Username: " + username);
        tvEmail.setText("Email: " + email);
        tvGender.setText("Gender: " + gender);
        tvAddress.setText("Address: " + address);
        tvPhone.setText("Phone: " + phone);
        tvTanggalLahir.setText("Tanggal Lahir: " + birthdate);
        return false;
    }
}