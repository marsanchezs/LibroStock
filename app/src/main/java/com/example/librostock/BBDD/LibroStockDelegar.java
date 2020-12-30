package com.example.librostock.BBDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.librostock.Objetos.Autor;
import com.example.librostock.Objetos.Libro;
import com.example.librostock.Objetos.ReporteLibrosxAutor;
import com.example.librostock.Objetos.ReporteLibrosxGenero;
import com.example.librostock.Objetos.ReporteLibrosxPais;

import java.util.ArrayList;

import static android.database.DatabaseUtils.sqlEscapeString;

public class LibroStockDelegar {
    private static String NOMBRE_BBDD = LibroStockConstantes.NOMBRE_BBDD;
    private static int VERSION_BBDD = LibroStockConstantes.VERSION_BBDD;

    //MÉTODOS
    public String actualizarLibro(Context contexto, Libro libro, String tituloActual,
                                  String autorActual, String generoActual){
        //TABLA LIBROS
        //db.execSQL("CREATE TABLE LIBROS (Id INTEGER PRIMARY KEY AUTOINCREMENT, TITULO TEXT, AUTOR TEXT, GENERO TEXT) ");
        String respuesta = "OK";
        String tit = tituloActual.replaceAll("'","''");
        String aut = autorActual.replaceAll("'","''");
        String gen = generoActual.replaceAll("'","''");
        String condicion = "TITULO = '" + tit + "' AND AUTOR = '"+aut+"' AND GENERO = '"+gen+"'";
        System.out.println("CONDICIÓN WHERE: "+condicion);
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);

        String titulo = libro.getTitulo().replaceAll("'","\\'");
        String autor = libro.getAutor().replaceAll("'","\\'");
        String genero = libro.getGenero().replaceAll("'","\\'");

        try (SQLiteDatabase bd = adm.getWritableDatabase()) {
            ContentValues cvLibro = new ContentValues();
            cvLibro.put("TITULO", titulo);
            cvLibro.put("AUTOR", autor);
            cvLibro.put("GENERO", genero);
            bd.update("LIBROS", cvLibro, condicion, null);
        } catch (Exception e) {
            e.printStackTrace();
            respuesta = "ERROR AL ACTUALIZAR EL LIBRO";
        }
        return respuesta;
    }

    public String borrarLibro(Context contexto, String titulo, String autor, String genero){
        //TABLA LIBROS
        //db.execSQL("CREATE TABLE LIBROS (Id INTEGER PRIMARY KEY AUTOINCREMENT, TITULO TEXT, AUTOR TEXT, GENERO TEXT) ");
        String respuesta = "OK";
        String tit = titulo.replaceAll("'","''");
        String aut = autor.replaceAll("'","''");
        String gen = genero.replaceAll("'","''");
        String condicion = "TITULO = '" + tit + "' AND AUTOR = '"+aut+"' " +
                "AND GENERO = '"+gen+"'";
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);

        try (SQLiteDatabase bd = adm.getWritableDatabase()) {
            bd.delete("LIBROS", condicion, null);
        } catch (Exception e) {
            e.printStackTrace();
            respuesta = "NO SE BORRÓ EL LIBRO";
        }
        return respuesta;
    }

    public String validarLibro(Context contexto, Libro libro){
        String respuesta = "";
        LibroStockOpenHelper admin = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);
        SQLiteDatabase  bd = admin.getWritableDatabase();

        //TABLA LIBROS
        //db.execSQL("CREATE TABLE LIBROS (Id INTEGER PRIMARY KEY AUTOINCREMENT, TITULO TEXT, AUTOR TEXT, GENERO TEXT) ");

        String titulo = libro.getTitulo().replaceAll("'","''");
        String autor = libro.getAutor().replaceAll("'","''");
        String genero = libro.getGenero().replaceAll("'","''");

        String consulta = "SELECT * FROM LIBROS WHERE TITULO = '" + titulo + "' " +
                "AND AUTOR = '" + autor + "' AND GENERO = '"+genero+"'";

        Cursor fila = null;
        try{
            Log.e("CONSULTA: ", consulta);
            fila = bd.rawQuery(consulta, null);
            if (fila.getCount() > 0) {
                respuesta = "LIBRO EXISTE";
            }else{
                respuesta = "LIBRO NO EXISTE";
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert fila != null;
            fila.close();
            bd.close();
        }
        return respuesta;
    }

    public String agregarLibro(Context contexto, Libro libro){
        String respuesta = "OK";
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);

        String titulo = libro.getTitulo().replaceAll("'","\\'");
        String autor = libro.getAutor().replaceAll("'","\\'");
        String genero = libro.getGenero().replaceAll("'","\\'");

        try (SQLiteDatabase bd = adm.getWritableDatabase()) {
            ContentValues cvLibro = new ContentValues();
            cvLibro.put("TITULO", titulo);
            cvLibro.put("AUTOR", autor);
            cvLibro.put("GENERO", genero);
            bd.insert("LIBROS", null, cvLibro);
            System.out.println("DATOS LIBRO: " + cvLibro);

        } catch (Exception e) {
            String mensaje = "NO SE GRABÓ EL LIBRO EN LA BASE DE DATOS";
            Log.e("LIBRO", mensaje, e);
            respuesta = mensaje;
        }
        return respuesta;
    }

    public String validarAutor(Context contexto, Autor autor){
        String respuesta = "";
        LibroStockOpenHelper admin = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);
        SQLiteDatabase  bd = admin.getWritableDatabase();

        String nombre = autor.getNombre().replaceAll("'","''");
        String pais = autor.getPais().replaceAll("'","''");
        String sexo = autor.getSexo().replaceAll("'","''");

        String consulta = "SELECT * FROM AUTORES WHERE NOMBRE = '" + nombre + "' " +
                "AND PAIS = '" + pais + "' AND SEXO = '"+sexo+"'";

        Cursor fila = null;
        try{
            Log.e("CONSULTA: ", consulta);
            fila = bd.rawQuery(consulta, null);
            if (fila.getCount() > 0) {
                respuesta = "AUTOR EXISTE";
            }else{
                respuesta = "AUTOR NO EXISTE";
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert fila != null;
            fila.close();
            bd.close();
        }
        return respuesta;
    }

    public String agregarAutor(Context contexto, Autor autor){
        String respuesta = "OK";
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);

        String nombre = autor.getNombre().replaceAll("'","\\'");
        String pais = autor.getPais().replaceAll("'","\\'");
        String sexo = autor.getSexo().replaceAll("'","\\'");

        try (SQLiteDatabase bd = adm.getWritableDatabase()) {
            ContentValues cvAutor = new ContentValues();
            cvAutor.put("NOMBRE", nombre);
            cvAutor.put("PAIS", pais);
            cvAutor.put("SEXO", sexo);
            bd.insert("AUTORES", null, cvAutor);
            System.out.println("DATOS AUTOR: " + cvAutor);

        } catch (Exception e) {
            String mensaje = "NO SE GRABÓ EL AUTOR EN LA BASE DE DATOS";
            Log.e("AUTOR", mensaje, e);
            respuesta = mensaje;
        }
        return respuesta;
    }

    public ArrayList<String> traerAutores(Context contexto){
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);
        SQLiteDatabase bd = adm.getWritableDatabase();

        //TABLA AUTORES
        //db.execSQL("CREATE TABLE AUTORES (Id INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT, PAIS TEXT, SEXO TEXT) ");

        ArrayList<String> autores = new ArrayList<>();
        String consulta = "SELECT NOMBRE FROM AUTORES ORDER BY NOMBRE";

        Cursor fila = null;
        try{
            Log.e("CONSULTA: ", consulta);
            fila = bd.rawQuery(consulta, null);
            if (fila.getCount() > 0){
                while (fila.moveToNext()){
                    autores.add(fila.getString(0));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert fila != null;
            fila.close();
            bd.close();
        }
        return autores;
    }

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

    public ArrayList<String> traerLibro(Context contexto){
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);
        SQLiteDatabase bd = adm.getWritableDatabase();

        ArrayList<String> libro = new ArrayList<>();
        String consulta = "SELECT TITULO, AUTOR FROM LIBROS ORDER BY TITULO";

        Cursor fila = null;
        try{
            Log.e("CONSULTA: ", consulta);
            fila = bd.rawQuery(consulta, null);
            if (fila.getCount() > 0){
                while (fila.moveToNext()){
                    libro.add(fila.getString(0)+"|"+fila.getString(1));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert fila != null;
            fila.close();
            bd.close();
        }
        return libro;
    }

    //REPORTES
    public ArrayList<Libro> reporteGeneral(Context contexto){
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);
        SQLiteDatabase bd = adm.getWritableDatabase();
        //TABLA LIBROS
        //db.execSQL("CREATE TABLE LIBROS (Id INTEGER PRIMARY KEY AUTOINCREMENT, TITULO TEXT, AUTOR TEXT, GENERO TEXT) ");
        ArrayList<Libro> listaLibros = new ArrayList<>();
        String consulta = "SELECT TITULO, AUTOR, GENERO FROM LIBROS ORDER BY TITULO";
        Cursor fila = null;
        try{
            Log.e("CONSULTA: ", consulta);
            fila = bd.rawQuery(consulta, null);

            if (fila.moveToFirst()) {
                do {
                    Libro libro = new Libro();
                    libro.setTitulo(fila.getString(0));
                    libro.setAutor(fila.getString(1));
                    libro.setGenero(fila.getString(2));
                    listaLibros.add(libro);
                } while (fila.moveToNext());
            }

        }catch (Exception e){
            String mensaje = "NO SE OBTUVIERON LOS LIBROS";
            Log.e("ERROR LIBROS", mensaje, e);
        }finally {
            assert fila != null;
            fila.close();
            bd.close();
        }
        return listaLibros;
    }

    public ArrayList<String[]> reporteAutor(Context contexto){
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);
        SQLiteDatabase bd = adm.getWritableDatabase();
        //TABLA LIBROS
        //db.execSQL("CREATE TABLE LIBROS (Id INTEGER PRIMARY KEY AUTOINCREMENT, TITULO TEXT, AUTOR TEXT, GENERO TEXT) ");
        ArrayList<String[]> listaAutor = new ArrayList<>();
        //String consulta = "SELECT AUTOR, COUNT(*) AS TOTAL FROM LIBROS GROUP BY AUTOR ORDER BY TOTAL DESC";
        String consulta = "SELECT AUTOR, TITULO FROM LIBROS ORDER BY AUTOR";
        Cursor fila = null;
        try{
            Log.e("CONSULTA: ", consulta);
            fila = bd.rawQuery(consulta, null);
            if (fila.getCount() > 0){
                while (fila.moveToNext()){
                    listaAutor.add(new String[]{fila.getString(0), fila.getString(1)});
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert fila != null;
            fila.close();
            bd.close();
        }
        return listaAutor;
    }

    public ArrayList<ReporteLibrosxAutor> reporteLibrosxAutor(Context contexto){
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);
        SQLiteDatabase bd = adm.getWritableDatabase();
        //TABLA LIBROS
        //db.execSQL("CREATE TABLE LIBROS (Id INTEGER PRIMARY KEY AUTOINCREMENT, TITULO TEXT, AUTOR TEXT, GENERO TEXT) ");
        ArrayList<String> listaTitulos = new ArrayList<>();
        System.out.println("LISTA TÍTULOS BBDD: "+listaTitulos.toString());
        ArrayList<ReporteLibrosxAutor> listaLibrosxAutor = new ArrayList<>();
        String consulta = "SELECT AUTOR, COUNT(*) AS TOTAL FROM LIBROS GROUP BY AUTOR ORDER BY TOTAL DESC";

        Cursor fila = null;
        try{

            Log.e("CONSULTA: ", consulta);
            fila = bd.rawQuery(consulta, null);
            if (fila.moveToFirst()) {
                do {
                    ReporteLibrosxAutor repLibroxAutor = new ReporteLibrosxAutor();
                    repLibroxAutor.setAutor(fila.getString(0));
                    listaTitulos = traerTitulosRLxA(contexto, fila.getString(0));
                    repLibroxAutor.setTitulos(listaTitulos);
                    repLibroxAutor.setCantidad(fila.getString(1));
                    listaLibrosxAutor.add(repLibroxAutor);
                } while (fila.moveToNext());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert fila != null;
            fila.close();
            bd.close();
        }
        return listaLibrosxAutor;
    }

    private ArrayList<String> traerTitulosRLxA(Context contexto, String autor){
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);
        SQLiteDatabase bd = adm.getWritableDatabase();
        ArrayList<String> listaTitulos = new ArrayList<>();
        String consulta = "SELECT TITULO FROM LIBROS WHERE AUTOR = '" + autor + "' ORDER BY AUTOR";

        Cursor fila = null;
        try{

            Log.e("CONSULTA: ", consulta);
            fila = bd.rawQuery(consulta, null);
            if (fila.getCount() > 0){
                while (fila.moveToNext()){
                    listaTitulos.add(fila.getString(0));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert fila != null;
            fila.close();
            bd.close();
        }
        return listaTitulos;
    }

    public ArrayList<ReporteLibrosxGenero> reporteLibrosxGenero(Context contexto){
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);
        SQLiteDatabase bd = adm.getWritableDatabase();
        //TABLA LIBROS
        //db.execSQL("CREATE TABLE LIBROS (Id INTEGER PRIMARY KEY AUTOINCREMENT, TITULO TEXT, AUTOR TEXT, GENERO TEXT) ");
        ArrayList<String> listaTitulos = new ArrayList<>();
        System.out.println("LISTA TÍTULOS BBDD: "+listaTitulos.toString());
        ArrayList<ReporteLibrosxGenero> listaLibrosxGenero = new ArrayList<>();
        String consulta = "SELECT GENERO, COUNT(*) AS TOTAL FROM LIBROS GROUP BY GENERO ORDER BY TOTAL DESC";

        Cursor fila = null;
        try{

            Log.e("CONSULTA: ", consulta);
            fila = bd.rawQuery(consulta, null);
            if (fila.moveToFirst()) {
                do {
                    ReporteLibrosxGenero repLibroxGenero = new ReporteLibrosxGenero();
                    repLibroxGenero.setGenero(fila.getString(0));
                    listaTitulos = traerTitulosRLxG(contexto, fila.getString(0));
                    repLibroxGenero.setTitulos(listaTitulos);
                    repLibroxGenero.setCantidad(fila.getString(1));
                    listaLibrosxGenero.add(repLibroxGenero);
                } while (fila.moveToNext());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert fila != null;
            fila.close();
            bd.close();
        }
        return listaLibrosxGenero;
    }

    private ArrayList<String> traerTitulosRLxG(Context contexto, String genero){
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);
        SQLiteDatabase bd = adm.getWritableDatabase();
        ArrayList<String> listaTitulos = new ArrayList<>();
        String consulta = "SELECT TITULO FROM LIBROS WHERE GENERO = '" + genero + "' ORDER BY TITULO";

        Cursor fila = null;
        try{

            Log.e("CONSULTA: ", consulta);
            fila = bd.rawQuery(consulta, null);
            if (fila.getCount() > 0){
                while (fila.moveToNext()){
                    listaTitulos.add(fila.getString(0));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert fila != null;
            fila.close();
            bd.close();
        }
        return listaTitulos;
    }

    public ArrayList<ReporteLibrosxPais> reporteLibrosxPais(Context contexto){
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);
        SQLiteDatabase bd = adm.getWritableDatabase();
        //TABLA LIBROS
        //db.execSQL("CREATE TABLE LIBROS (Id INTEGER PRIMARY KEY AUTOINCREMENT, TITULO TEXT, AUTOR TEXT, GENERO TEXT) ");
        ArrayList<String> listaTitulos = new ArrayList<>();
        System.out.println("LISTA TÍTULOS BBDD: "+listaTitulos.toString());
        ArrayList<ReporteLibrosxPais> listaLibrosxPais = new ArrayList<>();
        String consulta = "SELECT AUTORES.PAIS, COUNT(*) AS TOTAL FROM AUTORES\n" +
                "LEFT JOIN LIBROS on AUTORES.NOMBRE = LIBROS.AUTOR GROUP BY PAIS ORDER BY TOTAL DESC";

        Cursor fila = null;
        try{

            Log.e("CONSULTA: ", consulta);
            fila = bd.rawQuery(consulta, null);
            if (fila.moveToFirst()) {
                do {
                    ReporteLibrosxPais repLibroxPais = new ReporteLibrosxPais();
                    repLibroxPais.setPais(fila.getString(0));
                    listaTitulos = traerTitulosRLxP(contexto, fila.getString(0));
                    repLibroxPais.setTitulos(listaTitulos);
                    repLibroxPais.setCantidad(fila.getString(1));
                    listaLibrosxPais.add(repLibroxPais);
                } while (fila.moveToNext());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert fila != null;
            fila.close();
            bd.close();
        }
        return listaLibrosxPais;
    }

    private ArrayList<String> traerTitulosRLxP(Context contexto, String pais){
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);
        SQLiteDatabase bd = adm.getWritableDatabase();
        ArrayList<String> listaTitulos = new ArrayList<>();
        String consulta = "SELECT LIBROS.TITULO FROM LIBROS \n" +
                "LEFT JOIN AUTORES ON LIBROS.AUTOR = AUTORES.NOMBRE\n" +
                "WHERE AUTORES.PAIS = '"+pais+"' ORDER BY TITULO";

        Cursor fila = null;
        try{

            Log.e("CONSULTA: ", consulta);
            fila = bd.rawQuery(consulta, null);
            if (fila.getCount() > 0){
                while (fila.moveToNext()){
                    listaTitulos.add(fila.getString(0));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert fila != null;
            fila.close();
            bd.close();
        }
        return listaTitulos;
    }

}
