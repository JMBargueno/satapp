package com.dmtroncoso.satapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dmtroncoso.satapp.common.SharedPreferencesManager;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.model.UserResponse;
import com.dmtroncoso.satapp.retrofit.service.SataService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoggingActivity extends AppCompatActivity {

    Button btnLogin;
    SataService service;
    EditText edtEmail, edtPassword;
    TextView txtRegistrarse;
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging);

        btnLogin = findViewById(R.id.buttonLogin);
        edtEmail = findViewById(R.id.editTextEmail);
        edtPassword = findViewById(R.id.editTextPassword);

        txtRegistrarse = findViewById(R.id.textViewRegistrarse);

        txtRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(LoggingActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edtEmail.getText().toString().isEmpty() && !edtPassword.getText().toString().isEmpty()) {

                    if (token.isEmpty()) {
                        //Llamada al servicio
                        service = ServiceGenerator.createService(SataService.class, edtEmail.getText().toString(), edtPassword.getText().toString());

                        Call<UserResponse> call = service.login();
                        call.enqueue(new Callback<UserResponse>() {
                            @Override
                            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                                if (response.isSuccessful()) {
                                    Intent intentMainActivity = new Intent(LoggingActivity.this, MainActivity.class);
                                    startActivity(intentMainActivity);

                                    token = response.body().getToken();
                                    SharedPreferencesManager.setSomeStringValue("token", token);

                                } else {
                                    Toast.makeText(LoggingActivity.this, "Email y/o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserResponse> call, Throwable t) {
                                Toast.makeText(LoggingActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Intent intentMainActivity = new Intent(LoggingActivity.this, MainActivity.class);
                        startActivity(intentMainActivity);
                    }
                }else{
                    Toast.makeText(LoggingActivity.this, "Uno de los campos está sin rellenar", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
