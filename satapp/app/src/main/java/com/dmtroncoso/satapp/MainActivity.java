package com.dmtroncoso.satapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.model.TicketResponse;
import com.dmtroncoso.satapp.retrofit.service.SataService;
import com.dmtroncoso.satapp.tickets.NewTicketActivity;
import com.dmtroncoso.satapp.tickets.TicketActivity;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    static final int SCANNER_CODE = 5;

    Button btnScanner, btnTicket, btnGoTicket, btnInv;
    CardView cvTicket, cvPc, cvUser;
    SataService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        cvTicket = findViewById(R.id.cardViewTicket);
        cvPc = findViewById(R.id.cardViewPc);
        cvUser = findViewById(R.id.cardViewUser);

        cvTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApp.getContext(), TicketActivity.class));
            }
        });

        cvPc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApp.getContext(), NewInvActivity.class));
            }
        });

        //form POST ticket
        //startActivity(new Intent(MyApp.getContext(), NewTicketActivity.class));


        service = ServiceGenerator.createServiceTicket(SataService.class);



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
                String parts = data.getStringExtra("result");
                //Toast.makeText(this, parts, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, NewTicketActivity.class);
                intent.putExtra("idInventario", parts);
                startActivity(intent);
            }
            }
        }
    }