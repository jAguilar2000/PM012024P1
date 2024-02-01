package com.example.pm012024p1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nombres, apellidos, telefono;
    Button BtnProcesar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombres = (EditText) findViewById(R.id.nombres);
        apellidos = (EditText) findViewById(R.id.apellidos);
        telefono = (EditText) findViewById(R.id.telefono);
        BtnProcesar = (Button) findViewById(R.id.BtnProcesar);

        BtnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), nombres.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}