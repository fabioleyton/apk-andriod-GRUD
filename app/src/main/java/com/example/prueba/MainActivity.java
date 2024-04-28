package com.example.prueba;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.prueba.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Declaración de variables de EditText y ActivityMainBinding
    private EditText et1, et2, et3, et4;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Inicialización de las variables EditText
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);

    }
    // Método para insertar un nuevo cliente en la base de datos
    public void Insertar(View v){
        AdminSQLiteOpenHelper admim = new AdminSQLiteOpenHelper(this, "administracion", null,1 );
        SQLiteDatabase db = admim.getReadableDatabase();
        String documento = et1.getText().toString();
        String nombre = et2.getText().toString();
        String correo = et3.getText().toString();
        String telefono = et4.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("documento", documento);
        registro.put("nombre", nombre);
        registro.put("correo", correo);
        registro.put("telefono", telefono);
        db.insert("clientes",null,registro);
        db.close();

        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");

        Toast.makeText(this,"Se cargaron los datos de nuevo usuario", Toast.LENGTH_LONG).show();
    }
    // Método para consultar los datos de un cliente en la base de datos
    public void Consultar(View v){
        AdminSQLiteOpenHelper admim =new AdminSQLiteOpenHelper(this, "administracion", null,1 );
        SQLiteDatabase db = admim.getReadableDatabase();
        String doc = et1.getText().toString();

        Cursor fila = db.rawQuery("SELECT nombre, correo, telefono FROM clientes WHERE documento=" + doc,null);

        if (fila.moveToFirst()){
            et2.setText(fila.getString(0));
            et3.setText(fila.getString(1));
            et4.setText(fila.getString(2));
        }else {
            Toast.makeText(this, "No existe un registro con ese documento", Toast.LENGTH_LONG).show();
        }
        db.close();
    }
    // Método para eliminar un cliente de la base de datos
    public void Eliminar(View v){
        AdminSQLiteOpenHelper admim =new AdminSQLiteOpenHelper(this, "administracion", null,1 );
        SQLiteDatabase db = admim.getReadableDatabase();
        String doc = et1.getText().toString();

        int cant = db.delete("clientes", "documento=" + doc,null);
        db.close();

        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");

        if (cant == 1){
            Toast.makeText(this,"Se borro el cliente con docmuento ingresado", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"No existe un registro con ese documento", Toast.LENGTH_SHORT).show();
        }
    }
    // Método para modificar los datos de un cliente en la base de datos
    public void Modificar (View v){
        AdminSQLiteOpenHelper admim = new AdminSQLiteOpenHelper(this, "administracion", null,1 );
        SQLiteDatabase db = admim.getReadableDatabase();
        String doc = et1.getText().toString();
        String nombre = et2.getText().toString();
        String correo = et3.getText().toString();
        String telefono = et4.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("nombre", nombre);
        registro.put("correo", correo);
        registro.put("telefono", telefono);

        int cant = db.update("clientes", registro,"documento=" + doc, null);

        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");

        if (cant == 1){
            Toast.makeText(this,"Se modificaron los datos del cliente", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"No existe un registro con ese documento", Toast.LENGTH_SHORT).show();
        }
    }
    // Método para limpiar los campos de entrada
    public void Limpiar (View v){

        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");

        Toast.makeText(this,"Se limpiaron los campos de registro", Toast.LENGTH_LONG).show();
    }
}