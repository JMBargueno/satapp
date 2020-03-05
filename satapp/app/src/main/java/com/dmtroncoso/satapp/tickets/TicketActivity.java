package com.dmtroncoso.satapp.tickets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.common.MyApp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TicketActivity extends AppCompatActivity implements TicketFragment.OnListFragmentInteractionListener {

    FloatingActionButton fabAddTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_main);
        fabAddTicket = findViewById(R.id.floatingActionButtonAddTicket);

        fabAddTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApp.getContext(), NewTicketActivity.class));
            }
        });
    }

    @Override
    public void onListFragmentInteraction(Ticket item) {
        
    }
}
