package com.dmtroncoso.satapp.anotaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.retrofit.model.anotaciones.Notas;
import com.dmtroncoso.satapp.tickets.Anotaciones;

public class AnotacionesActivity extends AppCompatActivity implements anotacionesFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotaciones);
    }

    @Override
    public void onListFragmentInteraction(Anotaciones item) {

    }
}
