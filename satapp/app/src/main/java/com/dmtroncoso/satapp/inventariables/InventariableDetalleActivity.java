package com.dmtroncoso.satapp.inventariables;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.common.Constantes;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.retrofit.model.Inventariable;

import okhttp3.ResponseBody;

public class InventariableDetalleActivity extends AppCompatActivity {

    int idInv;
    ImageView imgInv;
    TextView nombre,tipo,descripcion,ubicacion;
    InventariableDetalleViewModel modelDetallesInventariable;
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

        modelDetallesInventariable = new ViewModelProvider(this).get(InventariableDetalleViewModel.class);

        modelDetallesInventariable.getInventariable(idInv).observe(InventariableDetalleActivity.this,new Observer<Inventariable>() {
            @Override
            public void onChanged(Inventariable inventariable) {
                if(inventariable != null){
                    nombre.setText(inventariable.getNombre());
                    tipo.setText(inventariable.getTipo());
                    descripcion.setText(inventariable.getDescripcion());
                    ubicacion.setText(inventariable.getUbicacion());
                    if(inventariable.getImagen()!=null) {

                        Glide
                                .with(MyApp.getContext())
                                .load(inventariable.getImagen())
                                .into(imgInv);
                    }
                }
            }

        });
    }
}
