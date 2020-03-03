package com.dmtroncoso.satapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.tickets.NewTicketActivity;

public class MainActivity extends AppCompatActivity {

    static final int SCANNER_CODE = 5;
    Button btnScanner, btnTicket, btnListInventoriable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScanner = findViewById(R.id.buttonScan);
        btnTicket = findViewById(R.id.buttonTicket);

        btnTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApp.getContext(), NewTicketActivity.class));
            }
        });
        btnListInventoriable = findViewById(R.id.buttonListInventoriable);
        btnListInventoriable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApp.getContext(), InventariableListActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.barCode:
                startActivityForResult(new Intent(MyApp.getContext(), QRScannerActivity.class), SCANNER_CODE);
                break;

            case R.id.calendarEvent:
                startActivity(new Intent(MyApp.getContext(), CalendarActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCANNER_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String[] parts = data.getStringArrayExtra("result");
                Log.v("Scanner", parts[0]);
                Log.v("Scanner", parts[1]);
            }
            }
        }
    }