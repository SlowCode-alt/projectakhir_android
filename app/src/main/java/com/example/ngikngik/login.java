package com.example.ngikngik;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    private EditText etEmail, etPassword;
    TextView textViewLogin;
    Button move;
    private Button btnLogin;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView imageViewShowHidePw = findViewById(R.id.imageView_show_hide_pw);
        imageViewShowHidePw.setImageResource(R.drawable.tutupmatapw);
        imageViewShowHidePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowHidePw.setImageResource(R.drawable.tutupmatapw);
                }else {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePw.setImageResource(R.drawable.tampilmatapw);
                }
            }
        });
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Ambil input dari EditText
//                String emailInput = etEmail.getText().toString();
//                String passwordInput = etPassword.getText().toString();
//
//                // Logika verifikasi login
//                if (emailInput.equals(email) && passwordInput.equals(password)) {
//                    // Kirim data dari EditText ke Dashboard
//                    Intent dashboardIntent = new Intent(login.this, Dashboard.class);
//                    dashboardIntent.putExtra("fullname", getIntent().getStringExtra("fullname"));
//                    dashboardIntent.putExtra("username", getIntent().getStringExtra("username"));
//                    dashboardIntent.putExtra("email", emailInput);  // Data dari EditText
//                    dashboardIntent.putExtra("gender", getIntent().getStringExtra("gender"));
//                    dashboardIntent.putExtra("birthdate", getIntent().getStringExtra("birthdate"));
//                    dashboardIntent.putExtra("address", getIntent().getStringExtra("address"));
//                    dashboardIntent.putExtra("phone", getIntent().getStringExtra("phone"));
//
//                    startActivity(dashboardIntent);
//                    finish(); // Tutup LoginActivity
//                } else {
//                    Toast.makeText(login.this, "Email atau Password salah", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        // Dapatkan data yang dikirim dari RegisterActivity
//        Intent intent = getIntent();
//        String email = intent.getStringExtra("email");
//        String password = intent.getStringExtra("password");
//        if (email != null) {
//            etEmail.setText(email);
//        }


        etEmail = (EditText) findViewById(R.id.etEmailLogin);
        etPassword = (EditText) findViewById(R.id.etPasswordLogin);
        btnLogin = (Button) findViewById(R.id.btn_login2);
        progressDialog = new ProgressDialog(login.this);
        textViewLogin = findViewById(R.id.txt_donthaveaccount);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), register.class);
                startActivity(intent);
            }

        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                CheckLogin(email, password);
            }
        });
    }
        public void CheckLogin(final String email, final String password){
            if(checkNetworkConnection()){
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_LOGIN_URL,
                        new Response.Listener<String>() {
                            @Override
                                public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String resp = jsonObject.getString("server_response");
                                    if(resp.equals("[{\"status\":\"OK\"}]")) {
                                        Toast.makeText(getApplicationContext(), "Login berhasil", Toast.LENGTH_SHORT).show();
                                        Intent dashboardIntent = new Intent(login.this, Dashboard.class);
                                        dashboardIntent.putExtra("email", email);
                                        startActivity(dashboardIntent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        params.put("password", password);
                        return params;
                    }
                };

                VolleyConnection.getInstance(login.this).addToRequestQue(stringRequest);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.cancel();
                    }
                }, 2000);
            }else {
                Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
            }
        }
        // Inisialisasi EditText untuk email dan password



    private boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected() );
    }
}
