package com.dmtroncoso.satapp.inventariables;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dmtroncoso.satapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

public class ModalBottomInventariableFragment extends BottomSheetDialogFragment {

    private InventariableViewModel inventariableViewModel;
    private String idInventariableEliminar;

    public static ModalBottomInventariableFragment newInstance(String id) {
        ModalBottomInventariableFragment fragment= new ModalBottomInventariableFragment();
        Bundle args = new Bundle();
        args.putString("inventariable_id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            idInventariableEliminar = getArguments().getString("inventariable_id");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_bottom_inventariable_fragment, container, false);

        final NavigationView nav = v.findViewById(R.id.navigation_view_bottom_user);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.action_delete_inventariable){
                    inventariableViewModel.deleteInventariable(idInventariableEliminar);
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
        inventariableViewModel = new ViewModelProvider(getActivity()).get(InventariableViewModel.class);

    }

}
