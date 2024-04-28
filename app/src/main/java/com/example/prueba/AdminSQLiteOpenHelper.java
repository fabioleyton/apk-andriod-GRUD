package com.example.prueba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    // Definición de la clase AdminSQLiteOpenHelper que hereda de SQLiteOpenHelper
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Método invocado cuando se crea la base de datos por primera vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE clientes(documento integer primary key, nombre text, correo text, telefono integer)");
    }

    // Método invocado cuando la versión de la base de datos cambia
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Se elimina la tabla 'clientes' si existe
        db.execSQL("DROP TABLE IF EXISTS clientes");
        // Se crea nuevamente la tabla 'clientes' con la misma estructura pero actualizada
        db.execSQL("CREATE TABLE clientes (documento integer primary key, nombre text, correo text, telefono integer)");
    }
}
