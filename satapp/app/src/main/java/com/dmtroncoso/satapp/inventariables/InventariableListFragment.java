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
    private PagedList<InventariableResponse> inventariables;
    private final int limit = 10;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int currentPage;
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
        currentPage = 0;
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
        Context context = view.getContext();
        inventariables = new PagedList<>();
        inventariables.setResults(new ArrayList<>());

        recyclerView = (RecyclerView) view;
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyInventariableResponseRecyclerViewAdapter(getActivity(),null, inventariableViewModel);
        recyclerView.setAdapter(adapter);

        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int visibleItems = layoutManager.getChildCount();
                int totalItems = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                boolean isLastItem = firstVisibleItemPosition + visibleItems >= totalItems;
                boolean isValidFirstItem = firstVisibleItemPosition >= 0;
                boolean totalIsMoreThanVisible = totalItems >= limit;
                boolean shouldLoadMore = isValidFirstItem && isLastItem && totalIsMoreThanVisible;

                if (shouldLoadMore) loadInventariables(false);
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        currentPage=0;
        loadInventariables(false);
        Toast.makeText(getActivity(), "onResume()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void loadInventariables(final boolean isFirstpage) {
        isLoading = true;
        currentPage++;
        inventariableViewModel.getInventariableList(currentPage, limit).observe(getActivity(), new Observer<List<InventariableResponse>>() {
            @Override
            public void onChanged(List<InventariableResponse> data) {
                if (data != null) {
                    inventariables.getResults().addAll(data);

                    inventariables.getResults().sort((InventariableResponse o1, InventariableResponse o2) -> {
                       return o1.getNombre().compareTo(o2.getNombre());
                    });
                } else {
                    Toast.makeText(getContext(), "No hay más equipos que cargar", Toast.LENGTH_LONG).show();
                }

                adapter.setData(inventariables.getResults().stream().distinct().collect(Collectors.toList()));
                recyclerView.setVisibility(View.VISIBLE);

                isLoading = false;
                isLastPage = currentPage == inventariables.getResults().size();
            }
        });
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

