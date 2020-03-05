package com.dmtroncoso.satapp.viewmodel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.inventariables.InventariableViewModel;
import com.dmtroncoso.satapp.retrofit.model.User;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

public class ModalBottomUserFragment extends BottomSheetDialogFragment {

    private UserViewModel userViewModel;
    private String idUser;
    private String habilitado;

    public static ModalBottomUserFragment newInstance(User user) {
        ModalBottomUserFragment fragment= new ModalBottomUserFragment();
        Bundle args = new Bundle();
        args.putString("user_id", user.getId());
        args.putString("habilitado", user.getValidated().toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            idUser = getArguments().getString("user_id");
            habilitado = getArguments().getString("habilitado");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_bottom_user_fragment, container, false);

        final NavigationView nav = v.findViewById(R.id.navigation_view_bottom_user);
        final MenuItem habilitadoButton = v.findViewById(R.id.action_habilitar);

        if(habilitado.equals("true")){
            habilitadoButton.setTitle("Deshabilitar");
        }else {
            habilitadoButton.setTitle("Habilitar");
        }


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.action_habilitar){

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
