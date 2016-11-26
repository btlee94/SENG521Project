package com.example.seng521.seng521project;

/**
 * Created by brand on 2016-11-26.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by brand on 2016-09-28.
 */
public class Storage {
    private static Storage ourInstance = null;
    private SQLiteDatabase db;
    private Context context;
    private StorageHelper dbhelper;
    private static final String DATABASE_NAME = "OBD.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "trips";
    private static final String KEY_NAME = "tripName";
    private static final String KEY_START = "tripStart";
    private static final String KEY_END = "tripEnd";

    private Storage(Context context) {
        this.context = context;

        //Delete old DB for testing purposes; remove for final version
        this.context.deleteDatabase(DATABASE_NAME);
        this.dbhelper = new StorageHelper(this.context);
    }

    public static Storage getInstance(Context context) {
        if(ourInstance == null)
            ourInstance = new Storage(context);
        return ourInstance;
    }

    private boolean checkDBexists(Context context){
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }

    private void openDB () {
        if(db == null)
            db = dbhelper.getWritableDatabase();
        else if (!db.isOpen())
            db = dbhelper.getWritableDatabase();
    }

    private void closeDB(){
        if(db != null)
            if(db.isOpen())
                db.close();
    }

    public void addTrip(Trip trip){
        this.openDB();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, trip.getName());
        values.put(KEY_START, trip.getTimestampStart());
        values.put(KEY_END, trip.getTimestampEnd());
        long id = db.insert(TABLE_NAME, null, values);
        this.closeDB();
    }

    public ArrayList<Trip> getTrips(){
        ArrayList<Trip> trips = new ArrayList<Trip>();
        this.openDB();
        String[] COLUMNS = {KEY_NAME, KEY_START, KEY_END};
        Cursor cursor;
        cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                trips.add(new Trip(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }while(cursor.moveToNext());
        }
        cursor.close();
        this.closeDB();

        return trips;
    }

    public class StorageHelper extends SQLiteOpenHelper{
        private static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        KEY_NAME + " STRING," +
                        KEY_START + " STRING," +
                        KEY_END + " STRING" +
                        ");";

        public StorageHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }
        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    }
}
