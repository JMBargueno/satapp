package com.dmtroncoso.satapp.tickets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dmtroncoso.satapp.R;

public class TicketActivity extends AppCompatActivity implements TicketFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_main);
    }

    @Override
    public void onListFragmentInteraction(Ticket item) {
        
    }
}
