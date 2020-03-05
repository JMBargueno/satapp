package com.dmtroncoso.satapp.inventariables;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dmtroncoso.satapp.QRScannerActivity;
import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.model.InventariableResponse;
import com.dmtroncoso.satapp.retrofit.model.PagedList;
import com.dmtroncoso.satapp.retrofit.service.SataService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventariableListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private InventariableViewModel inventariableViewModel;
    private MyInventariableResponseRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private static final int SCANNER_INVENT_CODE = 2;
    Context context;
    SataService service;

    public InventariableListFragment() {
    }

    @SuppressWarnings("unused")
    public static InventariableListFragment newInstance(int columnCount) {
        InventariableListFragment fragment = new InventariableListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        inventariableViewModel = new ViewModelProvider(getActivity()).get(InventariableViewModel.class);
        service = ServiceGenerator.createServiceInventariable(SataService.class);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventariable_list, container, false);

        // Set the adapter

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyInventariableResponseRecyclerViewAdapter(
                    getActivity(),
                    null,
                    inventariableViewModel);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        inventariableViewModel.getInventariableList().observe(getActivity(), new Observer<List<InventariableResponse>>() {
            @Override
            public void onChanged(List<InventariableResponse> inventariables) {
                adapter.setData(inventariables);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.barCode:
                startActivityForResult(new Intent(MyApp.getContext(), QRScannerActivity.class), SCANNER_INVENT_CODE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SCANNER_INVENT_CODE){
            if(requestCode == Activity.RESULT_OK){
                Call<ResponseBody> call = service.getInventariableById(data.getStringExtra("result"));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 200){
                            //Intent intent = new Intent(context, DETAIL.class);
                            //startActivity(INTENTDETAIL);
                            Toast.makeText(context, "Permitido.", Toast.LENGTH_SHORT).show();
                        }else{

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }


    }
}

