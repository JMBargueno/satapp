package com.dmtroncoso.satapp.anotaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.retrofit.model.anotaciones.Notas;
import com.dmtroncoso.satapp.tickets.Anotaciones;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AnotacionesActivity extends AppCompatActivity implements anotacionesFragment.OnListFragmentInteractionListener, CustomDialogListener {
    FloatingActionButton fbtnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotaciones);
        fbtnAdd = findViewById(R.id.floatAddAnot);

        fbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnotacionDialogfragment anotacionDialogfragment = new AnotacionDialogfragment();
                anotacionDialogfragment.show(getSupportFragmentManager(), null);
            }
        });

    }

    @Override
    public void onListFragmentInteraction(Anotaciones item) {

    }

    @Override
    public void submittedinformation(String bodyNota) {
        Toast.makeText(this, bodyNota, Toast.LENGTH_SHORT).show();
    }
}
