package com.example.gamerregnum.Utilidades;


import java.sql.Blob;

public class Utilidades {

    public static final String TABLA_NAME = "listapps";
    public static final String CAMPO_ICON = "icon";
    public static final String CAMPO_NAME = "name";

    public static final String CREATE_TABLE_ListApps = "CREATE TABLE "+TABLA_NAME+" ("+CAMPO_ICON+" BLOB, "+CAMPO_NAME+" STRING)";
}
