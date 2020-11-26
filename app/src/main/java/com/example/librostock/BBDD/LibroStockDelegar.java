package com.example.librostock.BBDD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class LibroStockDelegar {
    private static String NOMBRE_BBDD = LibroStockConstantes.NOMBRE_BBDD;
    private static int VERSION_BBDD = LibroStockConstantes.VERSION_BBDD;

    //MÉTODOS
    public ArrayList<String> traerGeneros(Context contexto){
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);
        SQLiteDatabase bd = adm.getWritableDatabase();

        //TABLA GÉNEROS
        //db.execSQL("CREATE TABLE GENEROS (Id INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT) ");

        ArrayList<String> generos = new ArrayList<>();
        String consulta = "SELECT NOMBRE FROM GENEROS ORDER BY NOMBRE";

        Cursor fila = null;
        try{
            Log.e("CONSULTA: ", consulta);
            fila = bd.rawQuery(consulta, null);
            if (fila.getCount() > 0){
                while (fila.moveToNext()){
                    generos.add(fila.getString(0));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert fila != null;
            fila.close();
            bd.close();
        }
        return generos;
    }

    public ArrayList<String> traerPaises(Context contexto){
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);
        SQLiteDatabase bd = adm.getWritableDatabase();

        //TABLA PAISES
        //db.execSQL("CREATE TABLE PAISES (Id INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT) ");

        ArrayList<String> paises = new ArrayList<>();
        String consulta = "SELECT NOMBRE FROM PAISES ORDER BY NOMBRE";

        Cursor fila = null;
        try{
            Log.e("CONSULTA: ", consulta);
            fila = bd.rawQuery(consulta, null);
            if (fila.getCount() > 0){
                while (fila.moveToNext()){
                    paises.add(fila.getString(0));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert fila != null;
            fila.close();
            bd.close();
        }
        return paises;
    }

}
