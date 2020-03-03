package com.dmtroncoso.satapp;

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
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.model.User;
import com.dmtroncoso.satapp.retrofit.service.SataService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;
    TextView textIniciarSesion;
    Button btnRegister;
    EditText edtName, edtEmail, edtPassword, edtConfPassword;
    ImageView imgRegister;
    Uri uriSelected;
    SataService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textIniciarSesion = findViewById(R.id.textViewIniciarSesion);
        btnRegister = findViewById(R.id.buttonRegister);
        edtName = findViewById(R.id.editTextUsernameR);
        edtEmail = findViewById(R.id.editTextEmailR);
        edtPassword = findViewById(R.id.editTextPasswordR);
        edtConfPassword = findViewById(R.id.editTextConfPasswordR);
        imgRegister = findViewById(R.id.imageViewRegister);
        service = ServiceGenerator.createServiceRegister(SataService.class);

        uriSelected = null;

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtConfPassword.getText().toString().equalsIgnoreCase(edtPassword.getText().toString()) && edtConfPassword.getText().toString().length() > 6 && edtPassword.getText().toString().length() > 6) {
                    if (uriSelected != null) {

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
                                    MultipartBody.Part.createFormData("avatar", "avatar", requestFile);


                            RequestBody name = RequestBody.create(MultipartBody.FORM, edtName.getText().toString());
                            RequestBody email = RequestBody.create(MultipartBody.FORM, edtEmail.getText().toString());
                            RequestBody password = RequestBody.create(MultipartBody.FORM, edtPassword.getText().toString());

                            Call<User> callRegister = service.register(body, name, email, password);

                            callRegister.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoggingActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Log.e("Upload error", response.errorBody().toString());
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Toast.makeText(RegisterActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                                }
                            });


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }else if(uriSelected == null) {
                        //SataService service = ServiceGenerator.createServiceRegister(SataService.class);

                        RequestBody name = RequestBody.create(MultipartBody.FORM, edtName.getText().toString());
                        RequestBody email = RequestBody.create(MultipartBody.FORM, edtEmail.getText().toString());
                        RequestBody password = RequestBody.create(MultipartBody.FORM, edtPassword.getText().toString());

                        Call<User> registerWithOutUri = service.register(null, name, email, password);
                        registerWithOutUri.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(RegisterActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    }else{
                    Toast.makeText(RegisterActivity.this, "Contraseñas no coinciden o es demasiado corta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });

    }
    public void performFileSearch(){


        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);


        intent.addCategory(Intent.CATEGORY_OPENABLE);


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
                        .into(imgRegister);
                uriSelected = uri;
                imgRegister.setEnabled(true);
            }
        }
    }
}
