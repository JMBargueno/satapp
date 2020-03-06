package com.dmtroncoso.satapp.inventariables;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dmtroncoso.satapp.R;

public class InventariableDetalleFromMenuActivity extends AppCompatActivity {

    public String idInv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventariable_detalle_from_menu);

        Bundle extras = getIntent().getExtras();
        idInv = extras.getString("invent");
    }
}
