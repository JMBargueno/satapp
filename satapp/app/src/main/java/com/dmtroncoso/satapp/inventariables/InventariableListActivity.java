package com.dmtroncoso.satapp.inventariables;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dmtroncoso.satapp.NewInvActivity;
import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.common.MyApp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InventariableListActivity extends AppCompatActivity {

    FloatingActionButton fabAddInventariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventariable_list);

        fabAddInventariable = findViewById(R.id.floatingActionButtonAddTicket);

        fabAddInventariable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApp.getContext(), NewInvActivity.class));
            }
        });
    }
}
