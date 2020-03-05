package com.dmtroncoso.satapp.inventariables;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmtroncoso.satapp.R;

import okhttp3.ResponseBody;

public class InventariableDetalleActivity extends AppCompatActivity {

    int idInv;
    ImageView imgInv;
    TextView nombre,tipo,descripcion,ubicacion;
    InventariableViewModel modelDetallesInventariable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventariable_detalle);

        imgInv = findViewById(R.id.fotoDet);
        nombre = findViewById(R.id.nombreInv);
        tipo = findViewById(R.id.tipoInv);
        descripcion = findViewById(R.id.descInv);
        ubicacion = findViewById(R.id.ubicacionInv);

        Bundle extras = getIntent().getExtras();
        idInv = extras.getInt("invent");

        modelDetallesInventariable = new ViewModelProvider(this).get(InventariableViewModel.class);

        modelDetallesInventariable.getIdInventoriableSeleccionado(idInv).observe(new Observer<ResponseBody>());
    }
}
