package com.example.ventas_vehiculos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class VehiculoActivity extends AppCompatActivity {

    EditText jetplaca,jetmarca,jetmodelo,jetvalor;
        CheckBox jcbactivo;
        ClsOpenHelper admin=new ClsOpenHelper( this, "concensionario.db", null,1);
    String placa,marca,modelo,valor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);


        //  ocultar tt
        getSupportActionBar().hide();

        jetplaca=findViewById(R.id.etplaca);
        jetmarca=findViewById(R.id.etmarca);
        jetmodelo=findViewById(R.id.etmodelo);
        jetvalor=findViewById(R.id.etvalor);
        jcbactivo=findViewById(R.id.cbactivo);


    }

    public void Guardar (View view){

        placa=jetplaca.getText().toString();
        marca=jetmarca.getText().toString();
        modelo=jetmodelo.getText().toString();
        valor=jetvalor.getText().toString();
        if (placa.isEmpty() || marca.isEmpty() || modelo.isEmpty() || valor .isEmpty()){
            Toast.makeText(this, "todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetplaca.requestFocus();
        }
        else {
            SQLiteDatabase database=admin.getReadableDatabase();
            ContentValues registro =new ContentValues();
            registro.put("placa",placa);
            registro.put("marca",marca);
            registro.put("modelo",modelo);
            registro.put("valor", Integer.parseInt(valor));
            long resp=database.insert( "Tblvehiculo", null,registro);

            if (resp>0){
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                 limpiar_campos();

            }
            else {
                Toast.makeText(this, "Error guardado registro", Toast.LENGTH_SHORT).show();
            }
            database.close();
        }

    }

    public void Consultar (View view){
        placa=jetplaca.getText().toString();
        if (placa.isEmpty()){
            Toast.makeText(this, "La placa es requerida", Toast.LENGTH_SHORT).show();
            jetplaca.requestFocus();
        }
        else {
            SQLiteDatabase database=admin.getReadableDatabase();
            Cursor fila=database.rawQuery("select * from TblVehiculo where placa = '" + placa+ "'",null);
            if (fila.moveToNext()){

            }
            else {
                Toast.makeText(this, "Vehiculo no registrado", Toast.LENGTH_SHORT).show();
                database.close();
            }
        }
    }
  private  void  limpiar_campos(){
        jetplaca.setText("");
        jetmarca.setText("");
        jetmodelo.setText("");
        jetvalor.setText("");
        jcbactivo.setChecked(false);
        jetplaca.requestFocus();
  }


   public void  Regresar(View view){
       Intent intmain =new Intent( this,MainActivity.class);
       startActivity(intmain);
   }
}