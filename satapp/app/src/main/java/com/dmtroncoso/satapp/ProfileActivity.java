package com.dmtroncoso.satapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.retrofit.model.User;
import com.dmtroncoso.satapp.viewmodel.UserViewModel;

import okhttp3.ResponseBody;

public class ProfileActivity extends AppCompatActivity {

    ImageView fotoPerfil,fotoRol;
    TextView nombre,rol,createdAt,email;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fotoPerfil = findViewById(R.id.fotoLogged);
        nombre = findViewById(R.id.nombreLogged);
        rol = findViewById(R.id.rolLogged);
        createdAt = findViewById(R.id.createdAt);
        email = findViewById(R.id.emailLogged);
        fotoRol = findViewById(R.id.fotoRol);

        userViewModel = new ViewModelProvider(ProfileActivity.this).get(UserViewModel.class);
        loadLoggedUser();


        /*setChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.updateUser(user.getId(),nameE.getText().toString()).observeForever(new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        user = authLoginUser;
                        nombre.setText(user.getName());
                    }
                });
            }
        });*/

    }

    public void loadLoggedUser(){
        userViewModel.getLoggedUser().observe(ProfileActivity.this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                nombre.setText(user.getName());
                rol.setText(user.getRole());
                createdAt.setText(user.getCreatedAt());
                email.setText(user.getEmail());
                if (user.getPicture() != null) {
                    userViewModel.getImgUsr(user.getId()).observeForever(new Observer<ResponseBody>() {
                        @Override
                        public void onChanged(ResponseBody responseBody) {
                            Bitmap bmp = BitmapFactory.decodeStream(responseBody.byteStream());
                            Glide.with(MyApp.getContext())
                                    .load(bmp)
                                    .circleCrop()
                                    .into(fotoPerfil);
                        }
                    });

                }

                if(user.getRole().equals("user")){
                    Glide.with(MyApp.getContext()).load(R.drawable.ic_user_detal).into(fotoRol);
                }else if (user.getRole().equals("tecnico")){
                    Glide.with(MyApp.getContext()).load(R.drawable.ic_customer_support).into(fotoRol);
                }else if(user.getRole().equals("admin")){
                    Glide.with(MyApp.getContext()).load(R.drawable.ic_support).into(fotoRol);
                }


            }

        });
    }
}
