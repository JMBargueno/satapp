package com.dmtroncoso.satapp.viewmodel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.inventariables.InventariableViewModel;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.model.User;
import com.dmtroncoso.satapp.retrofit.service.SataService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModalBottomUserFragment extends BottomSheetDialogFragment {
    SataService service;
    private UserViewModel userViewModel;
    private String idUser;
    private String habilitado;
    MenuItem habilitadoButton;

    public static ModalBottomUserFragment newInstance(User user) {
        ModalBottomUserFragment fragment = new ModalBottomUserFragment();
        Bundle args = new Bundle();
        args.putString("user_id", user.getId());
        args.putString("habilitado", user.getValidated().toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idUser = getArguments().getString("user_id");
            habilitado = getArguments().getString("habilitado");
            service = ServiceGenerator.createServiceTicket(SataService.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_bottom_user_fragment, container, false);

        final NavigationView nav = v.findViewById(R.id.navigation_view_bottom_user);
        //habilitadoButton = v.findViewById(R.id.action_habilitar);
        final MenuItem verDetalle = v.findViewById(R.id.action_detalle);

        if (habilitado.equals("true")) {
            nav.getMenu().findItem(R.id.action_habilitar).setVisible(false);
        } else {
            nav.getMenu().findItem(R.id.action_habilitar).setVisible(true);
        }


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.action_detalle) {

                }

                if (id == R.id.action_habilitar) {
                    if (habilitado.equals("true")) {
                        //Aqui hay que deshabilitar


                    } else {
                        //Aqui hay que habilitar
                        Call<User> callValidate = service.validateUser(idUser);

                        callValidate.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                Toast.makeText(MyApp.getContext(), "Usuario validado.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(MyApp.getContext(), "Error en la validación de usuario.", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }

                    getDialog().dismiss();
                    return true;
                }
                return false;
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

    }

}
