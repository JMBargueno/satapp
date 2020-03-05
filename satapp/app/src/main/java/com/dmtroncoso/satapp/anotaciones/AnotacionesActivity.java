package com.dmtroncoso.satapp.anotaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.model.anotaciones.Notas;
import com.dmtroncoso.satapp.retrofit.service.SataService;
import com.dmtroncoso.satapp.tickets.Anotaciones;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnotacionesActivity extends AppCompatActivity implements anotacionesFragment.OnListFragmentInteractionListener, CustomDialogListener {
    FloatingActionButton fbtnAdd;
    SataService service;
    Anotacion anotacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotaciones);
        fbtnAdd = findViewById(R.id.floatAddAnot);

        service = ServiceGenerator.createServiceAnotacion(SataService.class);

        fbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnotacionDialogfragment anotacionDialogfragment = new AnotacionDialogfragment();
                anotacionDialogfragment.show(getSupportFragmentManager(), null);
                anotacionDialogfragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
            }
        });

    }

    @Override
    public void onListFragmentInteraction(Anotaciones item) {

    }

    @Override
    public void submittedinformation(String bodyNota) {

        anotacion = new Anotacion(getIntent().getExtras().get("intentIdTicket").toString(), bodyNota);

        Call<ResponseBody> call = service.createAnotacion(anotacion);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AnotacionesActivity.this, "Anotación guardada", Toast.LENGTH_SHORT).show();
                }else{

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AnotacionesActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
