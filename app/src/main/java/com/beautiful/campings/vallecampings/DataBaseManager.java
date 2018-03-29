package com.beautiful.campings.vallecampings;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by root on 24-05-17.
 */

public class DataBaseManager {

    /**
     * Definicion de los parametros utilizados en la creacion de la tabla usuario
     */
    public static final String TABLE_NAME = "favorito";
    public static final String USR_ID = "_id";
    public static final String USR_NOM = "nombre";
    public static final String USR_DESCRIP = "descripcion";


    public static final String CREATE_TABLE = "create table " +TABLE_NAME+ " ("
            + USR_ID + " integer primary key autoincrement,"
            + USR_NOM + " text not null,"
            + USR_DESCRIP + " text not null);";

    private DbHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {

        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public ContentValues generarContentValues(String nombre, String descripcion) {
        ContentValues valores = new ContentValues();
        valores.put(USR_NOM, nombre);
        valores.put(USR_DESCRIP, descripcion);
        return valores;
    }

    //Primera opcion para insertar valores en la tabla usuario
    public void insertar(String nombre, String descripcion) {
        db.insert(TABLE_NAME, null, generarContentValues(nombre,descripcion));
    }

    //Segunda opcion para insertar valores en la tabla usuario
 /*   public void insertar2(String nombre, String password) {
        db.execSQL("insert into "+TABLE_NAME+" values (null,'"+nombre+"','"+password+"')");
    }

*/





    public void eliminar(String nombre){
        db.delete(TABLE_NAME,USR_NOM+"?",new String[] {nombre});

    }



}
