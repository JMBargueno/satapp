package com.dmtroncoso.satapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dmtroncoso.satapp.common.MyApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
                startActivity(new Intent(MyApp.getContext(), QRScannerActivity.class));
                break;

            case R.id.calendarEvent:
                startActivity(new Intent(MyApp.getContext(), CalendarActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
