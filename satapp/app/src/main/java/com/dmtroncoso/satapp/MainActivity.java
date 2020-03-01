package com.dmtroncoso.satapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.tickets.NewTicketActivity;

public class MainActivity extends AppCompatActivity {

    Button btnTicket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTicket = findViewById(R.id.btnTicket);

        btnTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApp.getContext(), NewTicketActivity.class));
            }
        });
    }
}
