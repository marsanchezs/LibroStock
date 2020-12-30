package com.example.librostock.BBDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LibroStockOpenHelper extends SQLiteOpenHelper {
    private static String DB_RUTA = "data/com.example.librostock/databases/";
    private static String DB_NOMBRE = LibroStockConstantes.NOMBRE_BBDD;
    private SQLiteDatabase myDataBase;

    public LibroStockOpenHelper(Context contexto, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(contexto, name, factory, version);
        Context miContexto = contexto;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //TABLA LIBROS
        db.execSQL("CREATE TABLE LIBROS (Id INTEGER PRIMARY KEY AUTOINCREMENT, TITULO TEXT, AUTOR TEXT, GENERO TEXT) ");
        //TABLA AUTORES
        db.execSQL("CREATE TABLE AUTORES (Id INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT, PAIS TEXT, SEXO TEXT) ");
        //TABLA GÉNEROS
        db.execSQL("CREATE TABLE GENEROS (Id INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT) ");
        //TABLA PAISES
        db.execSQL("CREATE TABLE PAISES (Id INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT) ");

        cargarAutores(db);
        cargarGeneros(db);
        cargarPaises(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void cargarAutores(SQLiteDatabase db){
        db.execSQL("INSERT INTO AUTORES (NOMBRE, PAIS, SEXO) VALUES ('Anónimo', 'N/A', 'N/A')");
        db.execSQL("INSERT INTO AUTORES (NOMBRE, PAIS, SEXO) VALUES ('Varios Autores', 'N/A', 'N/A')");
    }

    private void cargarGeneros(SQLiteDatabase db){
        db.execSQL("INSERT INTO GENEROS (NOMBRE) VALUES ('Aventuras')");
        db.execSQL("INSERT INTO GENEROS (NOMBRE) VALUES ('Bélica')");
        db.execSQL("INSERT INTO GENEROS (NOMBRE) VALUES ('Biográfica')");
        db.execSQL("INSERT INTO GENEROS (NOMBRE) VALUES ('Ciencia Ficción')");
        db.execSQL("INSERT INTO GENEROS (NOMBRE) VALUES ('Épica')");
        db.execSQL("INSERT INTO GENEROS (NOMBRE) VALUES ('Erótica')");
        db.execSQL("INSERT INTO GENEROS (NOMBRE) VALUES ('Histórica')");
        db.execSQL("INSERT INTO GENEROS (NOMBRE) VALUES ('Misterio')");
        db.execSQL("INSERT INTO GENEROS (NOMBRE) VALUES ('Negra')");
        db.execSQL("INSERT INTO GENEROS (NOMBRE) VALUES ('Policial')");
        db.execSQL("INSERT INTO GENEROS (NOMBRE) VALUES ('Romántica')");
        db.execSQL("INSERT INTO GENEROS (NOMBRE) VALUES ('Suspenso')");
        db.execSQL("INSERT INTO GENEROS (NOMBRE) VALUES ('Terror')");
    }

    private void cargarPaises(SQLiteDatabase db){
        //AMÉRICA
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Antigua y Barbuda')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Argentina')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Bahamas')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Barbados')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Belice')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Bolivia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Brasil')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Canadá')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Chile')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Colombia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Costa Rica')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Cuba')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Dominica')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Ecuador')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('El Salvador')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Estados Unidos')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Granada')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Guatemala')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Guyana')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Haití')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Honduras')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Jamaica')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('México')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Nicaragua')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Panamá')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Paraguay')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Perú')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('República Dominicana')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('San Cristóbal y Nieves')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('San Vicente y las Granadinas')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Santa Lucía')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Surinam')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Trinidad y Tobago')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Uruguay')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Venezuela')");
        //EUROPA
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Albania')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Alemania')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Andorra')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Armenia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Austria')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Azerbaiyán')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Bélgica')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Bielorrusia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Bosnia y Herzegovina')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Bulgaria')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Chipre')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Ciudad del Vaticano')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Croacia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Dinamarca')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Eslovaquia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Eslovenia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('España')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Estonia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Finlandia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Francia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Georgia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Grecia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Hungría')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Irlanda')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Islandia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Italia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Kazajistán')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Letonia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Liechtenstein')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Lituania')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Luxemburgo')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Macedonia del Norte')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Malta')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Moldavia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Mónaco')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Montenegro')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Noruega')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Países Bajos')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Polonia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Portugal')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Reino Unido')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('República Checa')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Rumania')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Rusia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('San Marino')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Serbia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Suecia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Suiza')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Turquía')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Ucrania')");
        //OCEANÍA
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Australia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Fiyi')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Islas Marshall')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Islas Salomón')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Kiribati')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Micronesia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Nauru')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Nueva Zelanda')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Palaos')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Papúa Nueva Guinea')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Samoa')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Tonga')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Tuvalu')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Vanuatu')");
        //ASIA
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Afganistán')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Arabia Saudita')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Bangladés')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Baréin')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Birmania/Myanmar')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Brunéi')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Bután')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Camboya')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Catar')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('China')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Corea del Norte')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Corea del Sur')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Emiratos Árabes Unidos')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Filipinas')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('India')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Indonesia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Irak')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Irán')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Israel')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Japón')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Jordania')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Kirguistán')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Kuwait')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Laos')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Líbano')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Malasia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Maldivas')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Mongolia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Nepal')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Omán')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Pakistán')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Singapur')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Siria')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Sri Lanka')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Tayikistán')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Tailandia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Timor Oriental')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Turkmenistán')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Uzbekistán')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Vietnam')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Yemen')");
        //ÁFRICA
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Angola')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Argelia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Benín')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Botsuana')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Burkina Faso')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Burundi')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Cabo Verde')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Camerún')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Chad')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Comoras')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Costa de Marfil')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Egipto')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Eritrea')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Etiopía')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Gabón')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Gambia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Ghana')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Guinea')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Guinea-Bisáu')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Guinea Ecuatorial')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Kenia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Lesoto')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Liberia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Libia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Madagascar')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Malaui')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Mali')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Marruecos')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Mauricio')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Mauritania')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Mozambique')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Namibia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Níger')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Nigeria')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('República Centroafricana')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('República del Congo')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('República Democrática del Congo')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Ruanda')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Santo Tomé y Príncipe')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Senegal')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Seychelles')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Sierra Leona')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Somalia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Suazilandia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Sudáfrica')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Sudán')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Sudán del Sur')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Tanzania')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Togo')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Túnez')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Uganda')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Yibuti')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Zambia')");
        db.execSQL("INSERT INTO PAISES (NOMBRE) VALUES ('Zimbabue')");

    }
}
