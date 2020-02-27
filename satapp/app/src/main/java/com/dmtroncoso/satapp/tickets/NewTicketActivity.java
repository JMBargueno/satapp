package com.dmtroncoso.satapp.tickets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.common.SharedPreferencesManager;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.model.Ticket;
import com.dmtroncoso.satapp.retrofit.model.TicketResponse;
import com.dmtroncoso.satapp.retrofit.service.SataService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTicketActivity extends AppCompatActivity implements View.OnClickListener{
    private SataService servicio;
    private ServiceGenerator serviceGenerator;
    EditText etTitle, etDescription;
    Button btnNewTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);

        retrofitInit();
        getViews();
        events();
    }

    private void events() {
        btnNewTicket.setOnClickListener(this);
    }

    private void retrofitInit() {
        serviceGenerator = ServiceGenerator.createServiceWithToken(ServiceGenerator.class);
        servicio = serviceGenerator.getSataService();
    }

    private void getViews() {
        etTitle = findViewById(R.id.editTextTicketTitulo);
        etDescription = findViewById(R.id.editTextTicketDescription);
        btnNewTicket = findViewById(R.id.buttonNewTicket);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id==R.id.buttonNewTicket){
            newTicket();
        }
    }

    private void newTicket() {

        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();

        if(title.isEmpty()){
            etTitle.setError("El título no puede estar vacío");
        }else if(description.isEmpty()){
            etDescription.setError("La descripción no puede estar vacía");
        }else{
            Ticket t = new Ticket(title,description);

            Call<TicketResponse> call =servicio.nuevoTicket(t);

            call.enqueue(new Callback<TicketResponse>() {
                @Override
                public void onResponse(Call<TicketResponse> call, Response<TicketResponse> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(NewTicketActivity.this, "El ticket se creó correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(NewTicketActivity.this, "Se produjo un error.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TicketResponse> call, Throwable t) {
                    Toast.makeText(NewTicketActivity.this, "Se produjo un problema de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
