package com.dmtroncoso.satapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dmtroncoso.satapp.retrofit.model.InventariableResponse;
import com.dmtroncoso.satapp.viewmodel.InventariableViewModel;

import java.util.List;

public class InventariableListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private InventariableViewModel inventariableViewModel;
    MyInventariableResponseRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

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
}

