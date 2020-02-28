package com.dmtroncoso.satapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.common.SharedPreferencesManager;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    String resultScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    @Override
    public void handleResult(Result result) {
        resultScanner = result.getText();
        Log.v("Scanner", result.getText());
        Toast.makeText(this, resultScanner, Toast.LENGTH_SHORT).show();
        scannerView.stopCamera();

        //Convertimos el resultado en un array de strings
        convertResultToArray(resultScanner);
    }

    public String[] convertResultToArray(String textScanned){

        String[] parts = textScanned.split("-");

        return parts;
    }

    @Override
    protected void onPause() {
        super.onPause();

        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}
