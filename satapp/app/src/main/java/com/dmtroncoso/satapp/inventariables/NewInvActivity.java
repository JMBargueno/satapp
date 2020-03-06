package com.dmtroncoso.satapp.inventariables;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.service.SataService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewInvActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;
    TextView textViewCrearInv;
    Button btnAdd;
    EditText edtName, editType, editDesc, editUbc;
    ImageView imgInv;
    Uri uriSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_inv);

        textViewCrearInv = findViewById(R.id.textView);
        btnAdd = findViewById(R.id.buttonInv);
        edtName = findViewById(R.id.nombreInv);
        editType = findViewById(R.id.tipoInv);
        editDesc = findViewById(R.id.descripcionInv);
        editUbc = findViewById(R.id.ubicacionInv);
        imgInv = findViewById(R.id.imageviewInventariable);

        uriSelected = null;

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (uriSelected != null) {

                        SataService service = ServiceGenerator.createServiceInventariable(SataService.class);

                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uriSelected);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                            int cantBytes;
                            byte[] buffer = new byte[1024 * 4];

                            while ((cantBytes = bufferedInputStream.read(buffer, 0, 1024 * 4)) != -1) {
                                baos.write(buffer, 0, cantBytes);
                            }


                            RequestBody requestFile =
                                    RequestBody.create(
                                            MediaType.parse(getContentResolver().getType(uriSelected)), baos.toByteArray());


                            MultipartBody.Part body =
                                    MultipartBody.Part.createFormData("imagen", "imagen", requestFile);


                            RequestBody nombre = RequestBody.create(edtName.getText().toString(),MultipartBody.FORM);
                            RequestBody tipo = RequestBody.create(editType.getText().toString(),MultipartBody.FORM);
                            RequestBody descripcion = RequestBody.create(editDesc.getText().toString(),MultipartBody.FORM);
                            RequestBody ubicacion = RequestBody.create(editUbc.getText().toString(),MultipartBody.FORM);

                            Call<ResponseBody> uploadInventariable = service.uploadInventariable(body, nombre, tipo, descripcion, ubicacion);

                            uploadInventariable.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(NewInvActivity.this, "Equipo registrado", Toast.LENGTH_SHORT).show();
                                        //Intent intent = new Intent(NewInvActivity.this, LoggingActivity.class);
                                        //startActivity(intent);
                                        onBackPressed();
                                    } else {
                                        Log.e("Upload error", response.errorBody().toString());
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(NewInvActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                                }
                            });


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }

        });

        imgInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });


    }
    public void performFileSearch(){

        //Intent para ir a la galería de fotos
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        //Filtra por aquellos archivos que se puedan abrir
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        //Filtra por image
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                //Uriselected , obtenemos la imagen
                Glide
                        .with(this)
                        .load(uri)
                        .apply(RequestOptions.bitmapTransform(new CropCircleTransformation()))
                        .into(imgInv);
                uriSelected = uri;
                imgInv.setEnabled(true);
            }
        }
    }
}

