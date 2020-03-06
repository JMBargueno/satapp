package com.dmtroncoso.satapp.inventariables;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.common.Constantes;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.common.SharedPreferencesManager;
import com.dmtroncoso.satapp.retrofit.model.InventariableResponse;
import com.dmtroncoso.satapp.retrofit.model.RequestEditInventariable;

public class InventariableDetalleFragment extends Fragment {

    private InventariableViewModel inventariableViewModel;
    private ImageView imgInv;
    private EditText etName, etTipo, etDescripcion, etUbicacion;
    private TextView tvCreated;
    private Button btnSave;
    private String inventariableId;
    private SharedPreferencesManager sharedPreferencesManager;

    public static InventariableDetalleFragment newInstance() {
        return new InventariableDetalleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inventariableId = sharedPreferencesManager.getSomeStringValue("idInventariable");
        inventariableViewModel = new ViewModelProvider(getActivity()).get(InventariableViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_edit_inventariable, container, false);

        imgInv = v.findViewById(R.id.imageviewInventariable);
        etName = v.findViewById(R.id.nombreInvEdit);
        etTipo = v.findViewById(R.id.tipoInvEdit);
        etDescripcion = v.findViewById(R.id.descripcionInvEdit);
        etUbicacion = v.findViewById(R.id.ubicacionInvEdit);
        tvCreated = v.findViewById(R.id.createdAtValue);
        btnSave = v.findViewById(R.id.buttonInvEdit);

        btnSave.setOnClickListener(view -> {
            String tipo = etTipo.getText().toString();
            String nombre = etName.getText().toString();
            String descripcion = etDescripcion.getText().toString();
            String ubicacion = etUbicacion.getText().toString();

            if(nombre.isEmpty()){
                etName.setError("El nombre no puede estar vacío");
            } else if(nombre.isEmpty()){
                etDescripcion.setError("La descripción no puede estar vacía");
            }else {
                RequestEditInventariable req = new RequestEditInventariable(tipo, nombre, descripcion, ubicacion);
                inventariableViewModel.updateInventariable(inventariableId, req);
                getActivity().onBackPressed();
            }
        });

        inventariableViewModel.getInventariableById(inventariableId).observe(getActivity(), new Observer<InventariableResponse>() {
            @Override
            public void onChanged(InventariableResponse inventariableResponse) {
                etName.setText(inventariableResponse.getNombre());
                etDescripcion.setText(inventariableResponse.getDescripcion());
                etTipo.setText(inventariableResponse.getTipo());
                etUbicacion.setText(inventariableResponse.getUbicacion());
                tvCreated.setText(inventariableResponse.getCreatedAt());

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
