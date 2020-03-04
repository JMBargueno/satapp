package com.dmtroncoso.satapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        Button button1 = (Button) findViewById(R.id.boton1);
        Button button2 = (Button) findViewById(R.id.boton1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                Boolean opcion = null;

                args.putBoolean("true",opcion);

                UserListFragment newFragment = new UserListFragment();
                newFragment.setArguments(args);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id., newFragment);
                fragmentTransaction.commit();

            }
        });

    }
}
