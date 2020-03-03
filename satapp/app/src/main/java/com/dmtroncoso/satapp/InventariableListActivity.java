package com.dmtroncoso.satapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.dmtroncoso.satapp.viewmodel.InventariableViewModel;

public class InventariableListActivity extends AppCompatActivity {
    InventariableViewModel inventariableViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inventariableViewModel = new ViewModelProvider(this).get(InventariableViewModel.class);
        setContentView(R.layout.activity_inventariable_list);
    }
}
