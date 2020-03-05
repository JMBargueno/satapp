package com.dmtroncoso.satapp.anotaciones;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dmtroncoso.satapp.R;

public class AnotacionDialogfragment extends DialogFragment {

    private EditText bodyNota;
    private CustomDialogListener customDialogListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_anotaciones, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bodyNota = view.findViewById(R.id.editTextBodyAnot);
        view.findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = bodyNota.getText().toString().trim();

                if(!body.isEmpty()){
                    customDialogListener.submittedinformation(body);
                    dismiss();
                }
            }
        });
        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    try {
        customDialogListener = (CustomDialogListener) context;
    }catch (ClassCastException e){
        e.printStackTrace();
    }

    }
}
