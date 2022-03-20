package com.example.ejercicio2_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.ejercicio2_1.Models.SQLiteConexion;
import com.example.ejercicio2_1.Models.Transacciones;

import java.lang.reflect.Field;

public class ActivityListarVideo extends AppCompatActivity {

    Spinner listVideo;
    VideoView videoViewer;
    Button btnAtras, btnVerVideo;
    private String[] lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_video);

        videoViewer = (VideoView) findViewById(R.id.aclVideoViwer);
        btnAtras = (Button) findViewById(R.id.aclbtnAtras);
        btnVerVideo = (Button) findViewById(R.id.aclbtnVerVideo);
        listVideo = (Spinner) findViewById(R.id.aclSpinner);
        lista=fileList();

        ArrayAdapter<String> adapter=new ArrayAdapter<>( this, android.R.layout.simple_spinner_item,lista);
        listVideo.setAdapter(adapter);

        btnVerVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verVideo(view);
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),ActivirVideo.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

    }

    public void verVideo(View v)
    {
        int pos=listVideo.getSelectedItemPosition();
        videoViewer.setVideoPath(getFilesDir()+"/"+lista[pos]);
        videoViewer.start();
    }

}