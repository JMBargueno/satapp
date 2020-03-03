package com.dmtroncoso.satapp.tickets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dmtroncoso.satapp.CalendarActivity;
import com.dmtroncoso.satapp.LoggingActivity;
import com.dmtroncoso.satapp.QRScannerActivity;
import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.model.TicketResponse;
import com.dmtroncoso.satapp.retrofit.service.SataService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTicketActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;
    static final int SCANNER_CODE = 5;
    EditText etTitle, etDescription;
    ImageView imgTicket;
    Button btnNewTicket, btnAddImages;
    List<MultipartBody.Part> listUri = new ArrayList<>();
    private SataService servicio;
    String idInventario = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);

        servicio = ServiceGenerator.createServiceTicket(SataService.class);

        getViews();
        btnAddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });
        ticketNoImages();
    }

    private void getViews() {
        etTitle = findViewById(R.id.editTextTicketTitulo);
        etDescription = findViewById(R.id.editTextTicketDescription);
        imgTicket = findViewById(R.id.imageViewFoto1);
        btnNewTicket = findViewById(R.id.buttonNewTicket);
        btnAddImages = findViewById(R.id.buttonAddImages);
    }

    private void ticketNoImages(){
        btnNewTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etTitle.getText().toString().isEmpty()){
                    etTitle.setError("El título no puede estar vacío");
                }else if(etDescription.getText().toString().isEmpty()){
                    etDescription.setError("La descripción no puede estar vacía");
                }
                

                Bundle bundle = getIntent().getExtras();
                if (bundle != null)
                {
                    idInventario = bundle.getString("idInventario");

                    RequestBody titulo = RequestBody.create(etTitle.getText().toString() , MultipartBody.FORM);
                    RequestBody descripcion = RequestBody.create(etDescription.getText().toString() , MultipartBody.FORM);
                    RequestBody inventariable = RequestBody.create(idInventario , MultipartBody.FORM);

                    Call<TicketResponse> newTicketWithoutUri = servicio.nuevoTicketQR(null,titulo,descripcion, inventariable);
                    newTicketWithoutUri.enqueue(new Callback<TicketResponse>() {
                        @Override
                        public void onResponse(Call<TicketResponse> call, Response<TicketResponse> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(NewTicketActivity.this, "Nuevo ticket registrado", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        }
                        @Override
                        public void onFailure(Call<TicketResponse> call, Throwable t) {
                            Toast.makeText(NewTicketActivity.this, "Se produjo un error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    RequestBody titulo = RequestBody.create(etTitle.getText().toString() , MultipartBody.FORM);
                    RequestBody descripcion = RequestBody.create(etDescription.getText().toString() , MultipartBody.FORM);

                    Call<TicketResponse> newTicketWithoutUri = servicio.nuevoTicketQR(null,titulo,descripcion, null);
                    newTicketWithoutUri.enqueue(new Callback<TicketResponse>() {
                        @Override
                        public void onResponse(Call<TicketResponse> call, Response<TicketResponse> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(NewTicketActivity.this, "Nuevo ticket registrado", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        }
                        @Override
                        public void onFailure(Call<TicketResponse> call, Throwable t) {
                            Toast.makeText(NewTicketActivity.this, "Se produjo un error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                if(idInventario != null){

                }else{

                }
            }
        });
    }

    private void uploadImages(final List<Uri> fileUris) {
        btnNewTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etTitle.getText().toString().isEmpty()){
                    etTitle.setError("El título no puede estar vacío");
                }else if(etDescription.getText().toString().isEmpty()){
                    etDescription.setError("La descripción no puede estar vacía");
                }else if (!fileUris.isEmpty()) {

                    try {
                        for (int i = 0; i < fileUris.size(); i++) {
                            InputStream inputStream = getContentResolver().openInputStream(fileUris.get(i));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                            int cantBytes;
                            byte[] buffer = new byte[1024 * 4];

                            while ((cantBytes = bufferedInputStream.read(buffer, 0, 1024 * 4)) != -1) {
                                baos.write(buffer, 0, cantBytes);
                            }
                            RequestBody requestFile =
                                    RequestBody.create(
                                            baos.toByteArray() , MediaType.parse(getContentResolver().getType(fileUris.get(i))));

                            MultipartBody.Part body =
                                    MultipartBody.Part.createFormData("fotos", "fotos" + i, requestFile);
                            listUri.add(body);
                        }


                        Bundle bundle = getIntent().getExtras();
                        if (bundle != null){

                            idInventario = bundle.getString("idInventario");

                            RequestBody inventariable = RequestBody.create(idInventario , MultipartBody.FORM);
                            RequestBody titulo = RequestBody.create(etTitle.getText().toString() , MultipartBody.FORM);
                            RequestBody descripcion = RequestBody.create(etDescription.getText().toString() , MultipartBody.FORM);

                            Call<TicketResponse> callNewTicket = servicio.nuevoTicketQR(listUri, titulo, descripcion, inventariable);
                            callNewTicket.enqueue(new Callback<TicketResponse>() {
                                @Override
                                public void onResponse(Call<TicketResponse> call, Response<TicketResponse> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(NewTicketActivity.this, "Nuevo ticket registrado", Toast.LENGTH_SHORT).show();
                                        /*Intent intent = new Intent(NewTicketActivity.this, LoggingActivity.class);
                                        startActivity(intent);*/
                                        onBackPressed();
                                    } else {
                                        Log.e("Upload error", response.errorBody().toString());
                                    }
                                }

                                @Override
                                public void onFailure(Call<TicketResponse> call, Throwable t) {
                                    Toast.makeText(NewTicketActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else{
                            RequestBody titulo = RequestBody.create(etTitle.getText().toString() , MultipartBody.FORM);
                            RequestBody descripcion = RequestBody.create(etDescription.getText().toString() , MultipartBody.FORM);

                            Call<TicketResponse> callNewTicket = servicio.nuevoTicketQR(listUri, titulo, descripcion, null);
                            callNewTicket.enqueue(new Callback<TicketResponse>() {
                                @Override
                                public void onResponse(Call<TicketResponse> call, Response<TicketResponse> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(NewTicketActivity.this, "Nuevo ticket registrado", Toast.LENGTH_SHORT).show();
                                        /*Intent intent = new Intent(NewTicketActivity.this, LoggingActivity.class);
                                        startActivity(intent);*/
                                        onBackPressed();
                                    } else {
                                        Log.e("Upload error", response.errorBody().toString());
                                    }
                                }

                                @Override
                                public void onFailure(Call<TicketResponse> call, Throwable t) {
                                    Toast.makeText(NewTicketActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(listUri.isEmpty()) {
                    ticketNoImages();
                }
            }
        });

    }

    private void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar Imagen"), READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ClipData clipData = data.getClipData();
            ArrayList<Uri> fileUris = new ArrayList<>();
            if (clipData != null) {
                for(int i=0;i<clipData.getItemCount();i++){
                    ClipData.Item item = clipData.getItemAt(i);
                    fileUris.add(item.getUri());
                }
                //Uriselected , obtenemos la primera imagen
                Glide
                        .with(this)
                        .load(fileUris.get(0))
                        .into(imgTicket);

                uploadImages(fileUris);
            }
        }
    }
}
