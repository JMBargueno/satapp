package com.dmtroncoso.satapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dmtroncoso.satapp.common.Constants;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.inventariables.InventariableViewModel;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.model.RequestAsignarTecnico;
import com.dmtroncoso.satapp.tickets.Ticket;
import com.dmtroncoso.satapp.retrofit.model.User;
import com.dmtroncoso.satapp.retrofit.service.SataService;
import com.dmtroncoso.satapp.viewmodel.UserViewModel;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignIncidenceActivity extends AppCompatActivity {
    String id_ticket, nombre_ticket;
    TextView tituloIncidencia;
    EditText emailAsignado;
    Button buttonAsignar;
    String id_usuario_asignado;
    SataService service;

    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_incidence);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        service = ServiceGenerator.createServiceTicket(SataService.class);
        Bundle extras = getIntent().getExtras();
        id_ticket = extras.getString("id_ticket");
        nombre_ticket = extras.getString("nombre_ticket");

        tituloIncidencia = findViewById(R.id.textViewAsignarIncidenciaTitulo);
        emailAsignado = findViewById(R.id.editTextEmailAAsignar);
        buttonAsignar = findViewById(R.id.buttonAsignar);

        tituloIncidencia.setText(nombre_ticket);


        buttonAsignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.getListUserNoVal().observe(AssignIncidenceActivity.this, new Observer<List<User>>() {
                    @Override
                    public void onChanged(List<User> users) {

                        boolean usuario_encontrado = false;
                        for (User user : users) {
                            String emailAsignadoString = emailAsignado.getText().toString();
                            String emailDeUsuarioIterado = user.getEmail();
                            if (emailAsignadoString.equals(emailDeUsuarioIterado)) {
                                usuario_encontrado = true;
                                id_usuario_asignado = user.getId();

                            }
                        }

                        if (usuario_encontrado) {
                            RequestAsignarTecnico requestAsignarTecnico = new RequestAsignarTecnico(id_usuario_asignado);
                            Call<Ticket> callAsignar = service.asignarTicket(id_ticket,requestAsignarTecnico);

                            callAsignar.enqueue(new Callback<Ticket>() {
                                @Override
                                public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                                    Toast.makeText(MyApp.getContext(), "Ticket asignado.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<Ticket> call, Throwable t) {
                                    Toast.makeText(MyApp.getContext(), "Fallo en asignación de ticket", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Toast.makeText(MyApp.getContext(), "Usuario no encontrado.", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }
        });


    }


}
