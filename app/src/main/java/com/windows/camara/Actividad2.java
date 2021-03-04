package com.windows.camara;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Actividad2 extends AppCompatActivity {

    private ListView lista;
    private ImageView imagen;
    private String[] archivos;
    private ArrayAdapter<String>adaptador1 ;
    private ArrayList<Foto> fotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad2);

        File dir = getExternalFilesDir(null);
        archivos = dir.list();
        adaptador1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, archivos);
        lista = (ListView) findViewById(R.id.listView1);
        lista.setAdapter(adaptador1);

        imagen = (ImageView) findViewById(R.id.imageView1);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                Bitmap bitmap1 = BitmapFactory.decodeFile(getExternalFilesDir(null) + "/" + archivos[arg2]);

                imagen.setImageBitmap(bitmap1);
            }
        });

    }


}