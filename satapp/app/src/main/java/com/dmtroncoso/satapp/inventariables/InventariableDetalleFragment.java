package com.dmtroncoso.satapp.inventariables;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.common.Constantes;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.common.SharedPreferencesManager;
import com.dmtroncoso.satapp.retrofit.model.InventariableResponse;

public class InventariableDetalleFragment extends Fragment {

    private InventariableViewModel inventariableViewModel;
    private ImageView imgInv;
    private EditText etName, etTipo, etDescripcion;
    private Button btnSave;
    private String inventariableId;
    private SharedPreferencesManager sharedPreferencesManager;

    public static InventariableDetalleFragment newInstance() {
        return new InventariableDetalleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inventariableViewModel = new ViewModelProvider(getActivity()).get(InventariableViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_new_inv, container, false);
        imgInv = v.findViewById(R.id.imageviewInventariable);
        etName = v.findViewById(R.id.nombreInv);
        etTipo = v.findViewById(R.id.tipoInv);
        etDescripcion = v.findViewById(R.id.descripcionInv);
        btnSave = v.findViewById(R.id.buttonAdd);

        btnSave.setOnClickListener(view -> {
            Toast.makeText(getActivity(), "Click on save", Toast.LENGTH_SHORT).show();
        });

        inventariableViewModel.getInventariableById(inventariableId).observe(getActivity(), new Observer<InventariableResponse>() {
            @Override
            public void onChanged(InventariableResponse inventariableResponse) {
                etName.setText(inventariableResponse.getNombre());
                etDescripcion.setText(inventariableResponse.getDescripcion());
                etTipo.setText(inventariableResponse.getTipo());

                if(inventariableResponse.getImagen()!=null) {
                    GlideUrl glideUrl = new GlideUrl(Constantes.URL_BASE + inventariableResponse.getImagen()
                            ,new LazyHeaders.Builder()
                            .addHeader("Authorization", "Bearer " + sharedPreferencesManager.getSomeStringValue("token"))
                            .build());
                    Glide
                            .with(MyApp.getContext())
                            .load(glideUrl)
                            .into(imgInv);
                }
            }
        });

        return v;
    }

}
