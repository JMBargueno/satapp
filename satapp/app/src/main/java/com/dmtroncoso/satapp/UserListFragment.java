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
import android.widget.Button;


import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.retrofit.model.User;
import com.dmtroncoso.satapp.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;


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
    Button botonAll,botonNon;
    View view;
    private List listAlluser, listNonValUsers;




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
        view = inflater.inflate(R.layout.fragment_user_no_val_list_list, container, false);


        botonAll = view.findViewById(R.id.boton1);
        botonNon = view.findViewById(R.id.boton2);
        context = view.getContext();
        recyclerView =  view.findViewById(R.id.listUser);

        loadAllUsers();



        botonNon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNoValUsers();
            }
        });

        botonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAllUsers();
            }
        });



        return view;
    }




    public void loadAllUsers(){

        listAlluser = new ArrayList<>();
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(MyApp.getContext()));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            myUserResponseRecyclerViewAdapter= new MyUserResponseRecyclerViewAdapter(listAlluser,userViewModel,getActivity());
            recyclerView.setAdapter(myUserResponseRecyclerViewAdapter);

        userViewModel.getListAllusers().observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> allusers) {
                listAlluser.addAll(allusers);
                myUserResponseRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

    }

    public void loadNoValUsers(){
        listNonValUsers = new ArrayList<>();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(MyApp.getContext()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        myUserResponseRecyclerViewAdapter = new MyUserResponseRecyclerViewAdapter(listNonValUsers, userViewModel, context);
        recyclerView.setAdapter(myUserResponseRecyclerViewAdapter);
        userViewModel.getListUserNoVal().observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                listNonValUsers.addAll(users);
                myUserResponseRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

    }





}
