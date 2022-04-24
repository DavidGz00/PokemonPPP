package com.grupo2.pokemon.implementacion;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.grupo2.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioSQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Pokemon.db";
    private static final int DATABASE_VERSION = 1;

    public RepositorioSQLite(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Pokemon ("+
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "NOMBRE TEXT NOT NULL, "+
                "DESCRIPTION TEXT NOT NULL,"+
                "IMAGEN TEXT NOT NULL);");

        Pokemon p1 = new Pokemon("Charmander", "", "");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    @SuppressLint("Range")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Optional<Pokemon> get(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(
                PokemonPregunta.EntradaPregunta.NOMBRE_TABLA,
                null,
                "id=?",
                new String[]{id+""},
                null,
                null,
                null
        );
        String nombre=null, foto=null;
        Pokemon pokemon=null;
        while(c.getCount() > 0 && c.moveToNext()){
            nombre = c.getString(c.getColumnIndex(PokemonPregunta.EntradaPregunta.NOMBRE));
            foto = c.getString(c.getColumnIndex(PokemonPregunta.EntradaPregunta.FOTO));
        }

        if(nombre !=null && foto !=null)
            pokemon = new Pokemon("Charmander", "", "");
        return Optional.of(pokemon);
    }


    public List<Pokemon> getAll() {
        List<Pokemon> pokemonList = new ArrayList<Pokemon>();

        return pokemonList;
    }

    public Pokemon getPokemon(){
        Pokemon pokemon = new Pokemon("","","");
        return pokemon;
    }


    public void update(Pokemon pokemon) {

    }


    public void delete(Pokemon pokemon) {

    }


    public void save(Pokemon pokemon) {
        this.save(pokemon, null);
    }
    // creamos nuevo método save() que se usa internamente en el onCreate
    private void save(Pokemon pokemon, SQLiteDatabase db) {
        if(db == null)
            db = getWritableDatabase();
        // Contenedor de valores
        ContentValues values = new ContentValues();
        // Pares clave-valor
        values.put(PokemonPregunta.EntradaPregunta.NOMBRE, pokemon.getName());
        // Insertar...
        db.insert(PokemonPregunta.EntradaPregunta.NOMBRE_TABLA, null, values);
    }
}

class PokemonPregunta {
    /* contructor privado parar que no se pueda instanciar la clase
    accidentalmente */
    private PokemonPregunta() {}
    public static class EntradaPregunta implements BaseColumns {
        public static final String NOMBRE_TABLA = "Pokemon";
        public static final String NOMBRE = "nombre";
        public static final String FOTO = "foto";
        public static final String DESCRIPCION = "descripcion";
    }
}
