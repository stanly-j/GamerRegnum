package com.example.gamerregnum;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamerregnum.Utilidades.Utilidades;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListApps extends AppCompatActivity {

    Button BTNAddScan;
    List<AppList> installedApps;
    AppAdapter installedAppAdapter;
    ListView list_apps;

    String SelecAppName;
    Drawable SelecAppIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_apps);

        list_apps = findViewById(R.id.List_Apps);
        BTNAddScan = findViewById(R.id.BTNAddScan);
        BTNAddScan.setEnabled(false);
        installedApps = getInstalledApps();
        installedAppAdapter = new AppAdapter(ListApps.this, installedApps);
        list_apps.setAdapter(installedAppAdapter);
        btnagregar();
    }

    public void btnagregar(){
        list_apps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                AppList modal = installedApps.get(i);
                if(modal.isSelect()){
                    modal.setSelect(false);
                }
                else{
                    modal.setSelect(true);
                }
                installedApps.set(i, modal);
                installedAppAdapter.updateRecords(installedApps);
                BTNAddScan.setEnabled(true);
            }
        });
    }

    private List<AppList> getInstalledApps() {
        PackageManager pm = getPackageManager();
        List<AppList> apps = new ArrayList<AppList>();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        for (PackageInfo p : packs) {
            if ((!isSystemPackage(p))) {
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                String packages = p.applicationInfo.packageName;
                apps.add(new AppList(false, appName, icon, packages));
            }
        }
        return apps;
    }


    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }


    public void btnAddScan(View view)
    {
        Intent i = new Intent(this, MyFavoriteApps.class);
        int cant = installedApps.size();
        for ( int a = 0; a < cant; a++){
            AppList modal = installedApps.get(a);
            if (modal.isSelect()){
                SelecAppName = installedApps.get(a).name;
                SelecAppIcon = installedApps.get(a).icon;
                AddDatabase();
            }
        }
        startActivity(i);
    }

    private void AddDatabase(){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = Bitmap.createBitmap(SelecAppIcon.getIntrinsicWidth(), SelecAppIcon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        SelecAppIcon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        SelecAppIcon.draw(canvas);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
        byte[] byteArray = stream.toByteArray();

        ConexionSQLifeHelper conn = new ConexionSQLifeHelper(this, "db_listapps", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_ICON, byteArray);
        values.put(Utilidades.CAMPO_NAME, SelecAppName);

        Long idResult = db.insert(Utilidades.TABLA_NAME,Utilidades.CAMPO_NAME, values);
        //Toast.makeText(getApplicationContext(), "Result is: "+ idResult, Toast.LENGTH_SHORT).show();
        db.close();
    }

    public class AppAdapter extends BaseAdapter {
        public LayoutInflater layoutInflater;
        public List<AppList> listStorage;

        public AppAdapter(Context context, List<AppList> customizedListView) {
            layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listStorage = customizedListView;
        }

        @Override
        public int getCount() {
            return listStorage.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder listViewHolder;
            if(convertView == null){
                listViewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.installed_app_list, parent, false);
                listViewHolder.checkBox = convertView.findViewById(R.id.idCheckBox);
                listViewHolder.textInListView = convertView.findViewById(R.id.list_app_name);
                listViewHolder.imageInListView = convertView.findViewById(R.id.app_icon);
                listViewHolder.packageInListView= convertView.findViewById(R.id.app_package);
                convertView.setTag(listViewHolder);
            }
            else{
                listViewHolder = (ViewHolder)convertView.getTag();
            }

            listViewHolder.textInListView.setText(listStorage.get(position).getName());
            listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
            listViewHolder.packageInListView.setText(listStorage.get(position).getPackages());

            AppList modal = listStorage.get(position);

            if(modal.isSelect()){
                listViewHolder.checkBox.setBackgroundResource(R.drawable.check);
            }
            else {
                listViewHolder.checkBox.setBackgroundResource(R.drawable.checked);
            }

            return convertView;
        }

        public void updateRecords(List<AppList> users){
            this.listStorage = users;
            notifyDataSetChanged();
        }

        class ViewHolder{
            ImageView checkBox;
            TextView textInListView;
            ImageView imageInListView;
            TextView packageInListView;
        }
    }

    public class AppList {
        private boolean isSelect;
        private String name;
        Drawable icon;
        private String packages;

        public AppList(boolean isSelect, String name, Drawable icon, String packages) {
            this.isSelect = isSelect;
            this.name = name;
            this.icon = icon;
            this.packages = packages;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public String getPackages() {
            return packages;
        }

        public void setPackages(String packages) {
            this.packages = packages;
        }
    }

}