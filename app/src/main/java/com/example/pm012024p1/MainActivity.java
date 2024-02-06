package com.example.pm012024p1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;

public class MainActivity extends AppCompatActivity {

    EditText nombres, apellidos, edad, correo, direccion;
    Button BtnProcesar, BtnEliminar;
    String idPersona = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombres = (EditText) findViewById(R.id.nombres);
        apellidos = (EditText) findViewById(R.id.apellidos);
        edad = (EditText) findViewById(R.id.edad);
        correo = (EditText) findViewById(R.id.correo);
        direccion = (EditText) findViewById(R.id.direccion);
        BtnProcesar = (Button) findViewById(R.id.BtnProcesar);
        BtnEliminar  = (Button) findViewById(R.id.BtnEliminar);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            BtnProcesar.setText("Editar");
            BtnEliminar.setVisibility(View.VISIBLE);
            idPersona = intent.getStringExtra("id");
            String nombresPersona = intent.getStringExtra("nombres");
            String apellidosPersona = intent.getStringExtra("apellidos");
            int edadPersona = intent.getIntExtra("edad", 0);
            String correoPersona = intent.getStringExtra("correo");

            // Llena los campos con los datos recuperados
            nombres.setText(nombresPersona);
            apellidos.setText(apellidosPersona);
            edad.setText(String.valueOf(edadPersona));
            correo.setText(correoPersona);
        }

        BtnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent.getExtras() != null){
                    EditarPerson();
                }else{
                    AddPerson();
                }

                ListPerson();
                ///AddPerson();
            }
        });

        BtnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MensajeEliminar();
            }
        });

    }

    private void ListPerson(){
        Intent intent = new Intent(getApplicationContext(), ActivityList.class);
        startActivity(intent);
    }
    private void AddPerson() {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombres, nombres.getText().toString());
        valores.put(Transacciones.apellidos, apellidos.getText().toString());
        valores.put(Transacciones.edad, edad.getText().toString());
        valores.put(Transacciones.correo, correo.getText().toString());
        valores.put(Transacciones.direccion, direccion.getText().toString());

        Long resultado = db.insert(Transacciones.TablePersonas, Transacciones.id, valores);

        Toast.makeText(getApplicationContext(), "Registro Ingresado con exito " + resultado.toString(), Toast.LENGTH_LONG).show();

        db.close();
    }

    private void EditarPerson(){
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombres, nombres.getText().toString());
        valores.put(Transacciones.apellidos, apellidos.getText().toString());
        valores.put(Transacciones.edad, edad.getText().toString());
        valores.put(Transacciones.correo, correo.getText().toString());
        valores.put(Transacciones.direccion, direccion.getText().toString());

        int rowUpdate = db.update(Transacciones.TablePersonas, valores, Transacciones.id + "=?", new String[]{idPersona});

        Toast.makeText(getApplicationContext(), "Registro Editado con exito ", Toast.LENGTH_LONG).show();
        db.close();
    }

    private void MensajeEliminar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Seguro que desea eliminar este registro?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EliminarPerson();
                        ListPerson();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create().show();
    }
    private void EliminarPerson(){
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        int rowDelete = db.delete(Transacciones.TablePersonas, Transacciones.id + "=?", new String[]{idPersona});
        Toast.makeText(getApplicationContext(), "Registro Eliminado ", Toast.LENGTH_LONG).show();
        db.close();

    }
}