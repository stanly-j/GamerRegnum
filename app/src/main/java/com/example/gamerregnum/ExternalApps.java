package com.example.gamerregnum;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.gamerregnum.Utilidades.Utilidades;

import java.io.ByteArrayOutputStream;

public class ExternalApps extends AppCompatActivity {

    Spinner List_Platform;
    EditText EditName;
    String ItemSelectPlatform;
    Drawable ICON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_apps);

        List_Platform = findViewById(R.id.SpinnerPlatform);
        EditName = findViewById(R.id.NameGame);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.listplatform,android.R.layout.simple_spinner_item);
        List_Platform.setAdapter(adapter);

        List_Platform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ItemSelectPlatform = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(ExternalApps.this,"El resultado es: "+ ItemSelectPlatform,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    public void btnAdd(View view){
        Intent i = new Intent(this, MyFavoriteApps.class);
        String SelectName = EditName.getText().toString();
        ConexionSQLifeHelper conn = new ConexionSQLifeHelper(this, "db_listapps", null, 1);

        switch (ItemSelectPlatform){
            case "PC":
                ICON  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.pc);
                try{
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap bitmap = ((BitmapDrawable)ICON).getBitmap();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
                    byte[] byteArray = stream.toByteArray();

                    SQLiteDatabase db = conn.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(Utilidades.CAMPO_ICON, byteArray);
                    values.put(Utilidades.CAMPO_NAME, SelectName);

                    Long idResult = db.insert(Utilidades.TABLA_NAME, Utilidades.CAMPO_NAME, values);
                    Toast.makeText(getApplicationContext(), "Resultado es: "+ idResult, Toast.LENGTH_SHORT).show();
                    db.close();
                }catch (Exception e){
                    Toast.makeText(this, "Error no existe",Toast.LENGTH_SHORT).show();
                }
                break;

            case "Play Station":
                ICON  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.playstation1);
                try{
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap bitmap = ((BitmapDrawable)ICON).getBitmap();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
                    byte[] byteArray = stream.toByteArray();

                    SQLiteDatabase db = conn.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(Utilidades.CAMPO_ICON, byteArray);
                    values.put(Utilidades.CAMPO_NAME, SelectName);

                    Long idResult = db.insert(Utilidades.TABLA_NAME, Utilidades.CAMPO_NAME, values);
                    Toast.makeText(getApplicationContext(), "Resultado es: "+ idResult, Toast.LENGTH_SHORT).show();
                    db.close();
                }catch (Exception e){
                    Toast.makeText(this, "Error no existe",Toast.LENGTH_SHORT).show();
                }
                break;

            case "Xbox":
                ICON  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.xbox);
                try{
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap bitmap = ((BitmapDrawable)ICON).getBitmap();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
                    byte[] byteArray = stream.toByteArray();

                    SQLiteDatabase db = conn.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(Utilidades.CAMPO_ICON, byteArray);
                    values.put(Utilidades.CAMPO_NAME, SelectName);

                    Long idResult = db.insert(Utilidades.TABLA_NAME, Utilidades.CAMPO_NAME, values);
                    Toast.makeText(getApplicationContext(), "Resultado es: "+ idResult, Toast.LENGTH_SHORT).show();
                    db.close();
                }catch (Exception e){
                    Toast.makeText(this, "Error no existe",Toast.LENGTH_SHORT).show();
                }
                break;

            case "Nintendo Switch":
                ICON  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.nintendo);
                try{
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap bitmap = ((BitmapDrawable)ICON).getBitmap();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
                    byte[] byteArray = stream.toByteArray();

                    SQLiteDatabase db = conn.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(Utilidades.CAMPO_ICON, byteArray);
                    values.put(Utilidades.CAMPO_NAME, SelectName);

                    Long idResult = db.insert(Utilidades.TABLA_NAME, Utilidades.CAMPO_NAME, values);
                    Toast.makeText(getApplicationContext(), "Resultado es: "+ idResult, Toast.LENGTH_SHORT).show();
                    db.close();
                }catch (Exception e){
                    Toast.makeText(this, "Error no existe",Toast.LENGTH_SHORT).show();
                }
                break;

        }

        startActivity(i);
    }

}