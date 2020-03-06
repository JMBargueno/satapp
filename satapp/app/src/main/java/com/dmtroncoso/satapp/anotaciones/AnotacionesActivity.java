package com.dmtroncoso.satapp.anotaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.service.SataService;
import com.dmtroncoso.satapp.tickets.Anotaciones;
import com.dmtroncoso.satapp.tickets.Ticket;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnotacionesActivity extends AppCompatActivity implements AnotacionesFragment.OnListFragmentInteractionListener, CustomDialogListener {
    FloatingActionButton fbtnAdd;
    SataService service;
    Anotacion anotacion;
    TextView txtTitulo, txtDesc, txtAsignado, dateCreate, dateUpdate, txtNameUser, txtEmailUser, txtRoleUser, txtValidatedUser, txtCreateUser, txtUpdateUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotaciones);
        fbtnAdd = findViewById(R.id.floatAddAnot);
        txtTitulo = findViewById(R.id.textViewTituloTDetalle);
        txtDesc = findViewById(R.id.textViewDescripcionTDetalle);
        txtAsignado = findViewById(R.id.textViewAsignadoTDetalle);
        dateCreate = findViewById(R.id.textViewCreadoTDetalle);
        dateUpdate = findViewById(R.id.textViewUpdateTDetalle);
        txtNameUser = findViewById(R.id.textViewNameTDetalle);
        txtEmailUser = findViewById(R.id.textViewEmailTDetalle);
        txtRoleUser = findViewById(R.id.textViewRoleTDetalle);
        txtValidatedUser = findViewById(R.id.textViewValidatedTDetalle);
        txtCreateUser = findViewById(R.id.textViewCreateUserTDetalle);
        txtUpdateUser = findViewById(R.id.textViewUpdateUserTDetalle);

        service = ServiceGenerator.createServiceAnotacion(SataService.class);

        Call<Ticket> call = service.getTicketById(getIntent().getExtras().get("intentIdTicket").toString());
        call.enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                if(response.isSuccessful()){
                    Ticket myTicket = response.body();
                    txtTitulo.setText(myTicket.getTitulo());
                    txtDesc.setText(myTicket.getDescripcion());
                    txtAsignado.setText(myTicket.getEstado());
                    dateCreate.setText(myTicket.getCreatedAt());
                    dateUpdate.setText(myTicket.getUpdatedAt());

                    //User
                    txtNameUser.setText(myTicket.getCreadoPor().getName());
                    txtEmailUser.setText(myTicket.getCreadoPor().getEmail());
                    txtRoleUser.setText("Rol : " + myTicket.getCreadoPor().getRole());
                    txtValidatedUser.setText("Validado : " + myTicket.getCreadoPor().getValidated().toString());
                    txtCreateUser.setText(myTicket.getCreadoPor().getCreatedAt());
                    txtUpdateUser.setText(myTicket.getCreadoPor().getUpdatedAt());

                }else{

                }
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {

            }
        });

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
