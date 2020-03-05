package com.dmtroncoso.satapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import androidx.cardview.widget.CardView;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.inventariables.InventariableListActivity;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.service.SataService;
import com.dmtroncoso.satapp.tickets.NewTicketActivity;
import com.dmtroncoso.satapp.tickets.TicketActivity;

import com.dmtroncoso.satapp.viewmodel.UserViewModel;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dmtroncoso.satapp.common.Constants.USUARIOS_SIN_VALIDAR;


public class MainActivity extends AppCompatActivity {

    static final int SCANNER_CODE = 5;
    CardView cvTicket, cvPc, cvUser;

    SataService service;
    UserViewModel userViewModel;

    Button btnScanner, btnTicket, btnListInventoriable;
    Button btnGoTicket;
    Button btnInv, btnUNV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cvTicket = findViewById(R.id.cardViewTicket);
        cvPc = findViewById(R.id.cardViewPc);
        cvUser = findViewById(R.id.cardViewUser);


//        btnTicket = findViewById(R.id.buttonTicket);

  //      btnGoTicket = findViewById(R.id.buttonGoTicket);
    //    btnUNV = findViewById(R.id.buttonUsNV);



        cvTicket.setOnClickListener(new View.OnClickListener()

            {

                @Override
                public void onClick (View v){
                startActivity(new Intent(MyApp.getContext(), TicketActivity.class));
            }
            });


        cvPc.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                startActivity(new Intent(MyApp.getContext(), InventariableListActivity.class));
                //startActivity(new Intent(MyApp.getContext(), NewInvActivity.class));
            }
            });


        btnUNV.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                startActivity(new Intent(MyApp.getContext(), ListUserActivity.class));
            }
            });


            service =ServiceGenerator.createServiceTicket(SataService.class);
            userViewModel =new ViewModelProvider(this).get(UserViewModel.class);

            checkUnvalidatedUsers();
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.main_menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){

            switch (item.getItemId()) {
                case R.id.barCode:
                    startActivityForResult(new Intent(MyApp.getContext(), QRScannerActivity.class), SCANNER_CODE);
                    break;

                case R.id.calendarEvent:
                    //startActivity(new Intent(MyApp.getContext(), CalendarActivity.class));
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == SCANNER_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    String parts = data.getStringExtra("result");
                    //Toast.makeText(this, parts, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, NewTicketActivity.class);
                    intent.putExtra("idInventario", parts);
                    startActivity(intent);
                }
            }
        }

        public void checkUnvalidatedUsers() {
            if (userViewModel.getListUserNoVal().getValue() != null) {

                int numUnvalidated = userViewModel.getListUserNoVal().getValue().size();
                Log.d("NUMVALIDATED", String.valueOf(numUnvalidated));

                AlertDialog.Builder builderFinish = new AlertDialog.Builder(this);
                builderFinish.setPositiveButton("¡Vale!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });

                builderFinish.setCancelable(true);
                builderFinish.setMessage(USUARIOS_SIN_VALIDAR + numUnvalidated);
                builderFinish.show();

            }

        }


    }
    //form POST ticket
    //startActivity(new Intent(MyApp.getContext(), NewTicketActivity.class));



