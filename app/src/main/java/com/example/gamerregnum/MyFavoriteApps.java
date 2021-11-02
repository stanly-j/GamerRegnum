package com.example.gamerregnum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamerregnum.Tablas.List_DB;
import com.example.gamerregnum.Utilidades.Utilidades;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class MyFavoriteApps extends AppCompatActivity {

    private ListAdacter adactador;
    ConexionSQLifeHelper conn;
    String ItemSelectFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite_apps);

        ListView listFavorite = findViewById(R.id.List_Favorite);
        adactador = new ListAdacter(this, GetArrayItemsDB());
        listFavorite.setAdapter(adactador);

        listFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ItemSelectFavorite = adactador.listStorage.get(i).getName();
                AlertDialog.Builder builder = new AlertDialog.Builder(MyFavoriteApps.this);
                builder.setTitle("Do you want to delete the items?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                conn = new ConexionSQLifeHelper(MyFavoriteApps.this, "db_listapps", null, 1);
                                SQLiteDatabase db = conn.deleteAll(ItemSelectFavorite);
                                Toast.makeText(MyFavoriteApps.this,"items removed: "+ ItemSelectFavorite,Toast.LENGTH_SHORT).show();
                                recreate();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                builder.show();
            }
        });

    }

    private ArrayList<List_DB> GetArrayItemsDB(){
        ArrayList<List_DB> listDbs = new ArrayList<>();
        conn = new ConexionSQLifeHelper(this, "db_listapps", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_NAME,null);
            while (cursor.moveToNext()){
                byte[] icon = cursor.getBlob(cursor.getColumnIndex("icon"));
                ByteArrayInputStream imageStream = new ByteArrayInputStream(icon);
                Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                String name = cursor.getString(cursor.getColumnIndex("name"));
                listDbs.add(new List_DB(theImage, name));
            }
        }catch (Exception e){
            Toast.makeText(this, "Error no existe",Toast.LENGTH_SHORT).show();
        }
        return listDbs;
    }

    public void ClickAddApps(View view) {
        startActivity(new Intent(this, TipoAdd.class));
    }


    public class ListAdacter extends BaseAdapter {
        private Context context;
        private ArrayList<List_DB> listStorage;

        public ListAdacter(Context context, ArrayList<List_DB> listStorage) {
            this.context = context;
            this.listStorage = listStorage;
        }

        @Override
        public int getCount() {
            return listStorage.size();
        }

        @Override
        public Object getItem(int i) {
            return listStorage.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            List_DB Item = (List_DB) getItem(i);
            view = LayoutInflater.from(context).inflate(R.layout.favorite_app_list,null);
            ImageView imagen = view.findViewById(R.id.icon_favorite1);
            TextView Name = view.findViewById(R.id.list_app_favorite);

            Drawable d = new BitmapDrawable(getResources(), Item.getIcon());
            imagen.setImageDrawable(d);
            Name.setText(Item.getName());
            return view;
        }
    }


}