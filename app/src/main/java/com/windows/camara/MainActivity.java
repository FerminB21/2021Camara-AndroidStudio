package com.windows.camara;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCamara;
    Button btnLista;
    Button btnGaleria;
    ImageView imageView;

    public static final int FOTO = 0;
    public static final int GALERIA=1;
    private String rutaFoto;
    private Uri fotoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        btnCamara = findViewById(R.id.btnCamara);
        btnGaleria = findViewById(R.id.btnlista);
        btnGaleria = findViewById(R.id.btbGaleria);
        btnLista=findViewById(R.id.btnlista);

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALERIA);
            }
        });
        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento1 = new Intent(MainActivity.this, Actividad2.class);
                startActivity(intento1);
            }
        });



        btnCamara.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnCamara) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {


                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            225);
                }


                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {

                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            226);
                }
            } else {
                tomarFoto();
            }
        }
    }

    private void tomarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, FOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FOTO && resultCode == RESULT_OK) {

                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
                try {
                    String timeStamp = new SimpleDateFormat("s").format(new Date());
                    String nombreArchivo = "JPEG_" + timeStamp + "_";
                    File directorio = getExternalFilesDir(null);
                    File imagen = File.createTempFile(nombreArchivo, ".jpg", directorio);
                    rutaFoto = imagen.getAbsolutePath();
                    Uri imageUri = data.getData();
                    FileOutputStream out = new FileOutputStream(imagen);//Guarda la imagen en la ruta de la variable dest
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    Toast.makeText(getApplicationContext(), "guardada en" + imagen,
                            Toast.LENGTH_LONG).show();
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }


        }else if(resultCode == RESULT_OK && requestCode == GALERIA) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

}