package com.dmtroncoso.satapp.inventariables;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.common.Constantes;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.common.SharedPreferencesManager;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.model.Inventariable;
import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.retrofit.service.SataService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventariableDetalleActivity extends AppCompatActivity {

    String idInv;
    ImageView imgInv;
    TextView nombre,tipo,descripcion,ubicacion;
    InventariableViewModel inventariableViewModel;
    SataService service = ServiceGenerator.createServiceTicket(SataService.class);
    SharedPreferencesManager sharedPreferencesManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventariable_detalle);

        imgInv = findViewById(R.id.fotoDet);
        nombre = findViewById(R.id.nombreInv);
        tipo = findViewById(R.id.tipoInv);
        descripcion = findViewById(R.id.descInv);
        ubicacion = findViewById(R.id.ubInv);


        idInv = getIntent().getExtras().getString("invent").toString();

        getOneInventariable(idInv);



        //modelDetallesInventariable = new ViewModelProvider(this).get(InventariableDetalleViewModel.class);

        /*modelDetallesInventariable.getInventariable2(idInv).observe(InventariableDetalleActivity.this,new Observer<Inventariable>() {
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

        });*/

    }

    public MutableLiveData<Inventariable> getOneInventariable(String idInv){
        final MutableLiveData<Inventariable> data = new MutableLiveData<>();
        Call<Inventariable> call = service.getInventariableId(idInv);

        call.enqueue(new Callback<Inventariable>() {
            @Override
            public void onResponse(Call<Inventariable> call, Response<Inventariable> response) {
                if(response.isSuccessful()){
                    nombre.setText(response.body().getNombre());
                    tipo.setText(response.body().getTipo());
                    descripcion.setText(response.body().getDescripcion());
                    ubicacion.setText(response.body().getUbicacion());
                    if(response.body().getImagen()!=null) {
                        GlideUrl glideUrl = new GlideUrl(Constantes.URL_BASE + response.body().getImagen()
                                ,new LazyHeaders.Builder()
                                .addHeader("Authorization", "Bearer " + sharedPreferencesManager.getSomeStringValue("token"))
                                .build());
                        Glide
                                .with(MyApp.getContext())
                                .load(glideUrl)
                                .into(imgInv);
                    }

                }else{
                    Toast.makeText(MyApp.getContext(), "Ha surgido un problema", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Inventariable> call, Throwable t) {

            }
        });

        return data;

    }
}
