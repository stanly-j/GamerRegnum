package com.example.gamerregnum;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.gamerregnum.Utilidades.Utilidades;

public class ConexionSQLifeHelper extends SQLiteOpenHelper {

    public ConexionSQLifeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREATE_TABLE_ListApps);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int VersionAntigua, int VersionNueva) {
        db.execSQL("DROP TABLE IF EXISTS listapps");
        onCreate(db);
    }

    public SQLiteDatabase deleteAll(String itemSelectFavorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Utilidades.TABLA_NAME, "name='" + itemSelectFavorite + "'", null);
        db.close();
        return db;
    }

}
