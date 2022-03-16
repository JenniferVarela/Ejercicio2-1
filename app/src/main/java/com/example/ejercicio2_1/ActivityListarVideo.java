package com.example.ejercicio2_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.ejercicio2_1.Models.SQLiteConexion;
import com.example.ejercicio2_1.Models.Transacciones;

import java.lang.reflect.Field;

public class ActivityListarVideo extends AppCompatActivity {

    SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase,null,1);
    SQLiteDatabase db = conexion.getWritableDatabase();

    TextView txtmytext;
    ListView listVideo;
    VideoView videoViewer;
    Button btnAtras;
    SimpleCursorAdapter mSCA;
    //String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_video);

        txtmytext = (TextView) findViewById(R.id.txtmytext);
        listVideo = (ListView) findViewById(R.id.videolist);
        videoViewer = (VideoView) findViewById(R.id.videoView);

        addVideosFromRawResourceToDB();


    }

    @Override
    protected void onResume() {
        super.onResume();
        manageListView(); //<<<<<<<<<< rebuild and redisplay the List of Videos (in case they have changed)
    }

    /**
     *  Setup or Refresh the ListView adding the OnItemClick and OnItemLongClick listeners
     */
    private void manageListView() {
        //mCsr = mDBHlpr.getVideos();
        String sql = "SELECT * FROM videos";
        Cursor cursor = db.rawQuery(sql, new String[] {});

        // Not setup so set it up
        if (sql == null) {
            // Instantiate the SimpleCursorAdapter
            mSCA = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_1, // Use stock layout
                    cursor, // The Cursor with the list of videos
                    new String[]{Transacciones.videoPath}, // the column (columns)
                    new int[]{android.R.id.text1}, // the view id(s) into which the column(s) data will be placed
                    0
            );
            listVideo.setAdapter(mSCA); // Set the adpater for the ListView
            /**
             * Add The Long Click Listener (will delete the video row from the DB (NOT the video))
             */
            listVideo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    //mDBHlpr.deleteVideoFromDB(id);
                    manageListView(); // <<<<<<<<<< refresh the ListView as data has changed
                    return true;
                }
            });
            listVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //setCurrentVideo(cursor.getString(cursor.getColumnIndex(Transacciones.videoPath)));
                }
            });
        } else {
            mSCA.swapCursor(cursor); //<<<<<<<<<< apply the changed Cursor
        }
    }

    private void setCurrentVideo(String path) {

        videoViewer.setVideoURI(
                Uri.parse(
                        "android.resource://" + getPackageName() + "/" + String.valueOf(
                                getResources().getIdentifier(
                                        path,
                                        "raw",
                                        getPackageName())
                        )
                )
        );
        videoViewer.start();
    }


    private void addVideosFromRawResourceToDB() {
        Field[] fields=R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){
            String path = fields[count].getName();
            ContentValues cv = new ContentValues();
            cv.put(Transacciones.videoPath,path);
            Log.i("Raw Asset: ", fields[count].getName());
//            db.insert(Transacciones.tblVideos,fields[count].getName());
            db.insert(Transacciones.tblVideos,Transacciones.id,cv);
        }
    }
}