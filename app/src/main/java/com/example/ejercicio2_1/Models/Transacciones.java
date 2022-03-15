package com.example.ejercicio2_1.Models;

public class Transacciones {
    public static final String NameDatabase = "PM01DB";

    public static String tblVideos = "videos";

    public static final String id = "id";
    public static final String video = "video";

    public static final String CreateTableVideo = "CREATE TABLE " + tblVideos+
            "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+"video BLOB)";

    public static final String DropTableVideo = "DROP TABLE " + tblVideos;


}
