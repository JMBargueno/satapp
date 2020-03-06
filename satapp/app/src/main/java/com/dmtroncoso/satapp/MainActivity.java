package com.dmtroncoso.satapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmtroncoso.satapp.common.Constants;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.common.SharedPreferencesManager;
import com.dmtroncoso.satapp.inventariables.InventariableListActivity;
import com.dmtroncoso.satapp.inventariables.InventariableViewModel;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.model.User;
import com.dmtroncoso.satapp.retrofit.service.SataService;
import com.dmtroncoso.satapp.tickets.NewTicketActivity;
import com.dmtroncoso.satapp.tickets.TicketActivity;
import com.dmtroncoso.satapp.viewmodel.UserViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int SCANNER_CODE = 5;
    CardView cvTicket, cvPc, cvUser;
    ImageView buttonProfile;

    SataService service;
    UserViewModel userViewModel;
    String loggedRole;
    TextView usuariosPorValidar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cvTicket = findViewById(R.id.cardViewTicket);
        cvPc = findViewById(R.id.cardViewPc);
        cvUser = findViewById(R.id.cardViewUser);
        buttonProfile = findViewById(R.id.profile);
        usuariosPorValidar = findViewById(R.id.textViewUsuariosPorValidar);

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApp.getContext(),ProfileActivity.class));
            }
        });

        cvTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApp.getContext(), TicketActivity.class));
            }
        });
        cvPc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApp.getContext(), InventariableListActivity.class));
                //startActivity(new Intent(MyApp.getContext(), NewInvActivity.class));
            }
        });

        cvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApp.getContext(), ListUserActivity.class));
            }
        });

        //form POST ticket
        //startActivity(new Intent(MyApp.getContext(), NewTicketActivity.class));

        service = ServiceGenerator.createServiceTicket(SataService.class);
        userViewModel = new ViewModelProvider(MainActivity.this).get(UserViewModel.class);
        loggedRole = SharedPreferencesManager.getSomeStringValue("loggedRole");

        if (loggedRole.equals("admin") ) {
            checkNonValidated();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    public void checkNonValidated() {
        userViewModel.getListUserNoVal().observe(MainActivity.this, new Observer<List<User>>() {


            @Override
            public void onChanged(List<User> usersUnvalidated) {

                if (!usersUnvalidated.isEmpty()) {
                    usuariosPorValidar.setVisibility(View.VISIBLE);

                    /*AlertDialog.Builder builderFinish = new AlertDialog.Builder(MainActivity.this);
                    builderFinish.setPositiveButton("¡Vale!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });
                    builderFinish.setCancelable(true);
                    builderFinish.setMessage(Constants.USUARIOS_SIN_VALIDAR);
                    builderFinish.show();*/
                }
            }
        });

    }
}