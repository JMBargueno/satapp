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


import com.dmtroncoso.satapp.retrofit.model.User;
import com.dmtroncoso.satapp.viewmodel.UserViewModel;

import java.util.List;

import okhttp3.ResponseBody;


public class UserListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private UserViewModel userViewModel;
    private MyUserResponseRecyclerViewAdapter myUserResponseRecyclerViewAdapter;
    RecyclerView recyclerView;
    Context context;
    String userId;
    boolean opciones = true;

    public UserListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static UserListFragment newInstance(int columnCount) {
        UserListFragment fragment = new UserListFragment();
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

        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_no_val_list_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

             myUserResponseRecyclerViewAdapter= new MyUserResponseRecyclerViewAdapter(null,userViewModel,getActivity());
            recyclerView.setAdapter(myUserResponseRecyclerViewAdapter);
        }



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(), "onResume()", Toast.LENGTH_SHORT).show();

        //TODO IMPORTANTE

        if(opciones = false){
        userViewModel.getListUserNoVal().observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                myUserResponseRecyclerViewAdapter.setData(users);
            }
        });}else if(opciones = true){
        userViewModel.getListAllusers().observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> allusers) {
                myUserResponseRecyclerViewAdapter.setData(allusers);
            }
        });}


    }





}
