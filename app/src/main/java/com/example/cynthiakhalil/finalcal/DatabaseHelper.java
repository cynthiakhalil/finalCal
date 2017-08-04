package com.example.cynthiakhalil.finalcal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cynthia.Khalil on 7/6/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    //name of the database
    public static final String DATABASE_NAME = "event.db";

    //name of the tables
    public static final String TB_EVENT = "event";



    //common column names
    public static final String ID = "id";


    //EVENT table column names
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String COLOR = "color";
    public static final String HOURDUR = "hourdur";
    public static final String MINDUR = "mindur";



    //EVENT table create statement
    private static final String CREATE_TB_EVENT = "CREATE TABLE "
            + TB_EVENT + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME
            + " NVARCHAR," + DATE + " NVARCHAR," + COLOR + " NAVCHAR,"+ HOURDUR + " NAVCHAR,"+ MINDUR + " NAVCHAR" +")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TB_EVENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TB_EVENT);

        onCreate(db);
    }

    //CRUD ops

    /*
     * Creating tables
     */
    public long createEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(ID, Integer.toString(event.getID()));
        values.put(NAME, event.getData().toString());
        values.put(DATE, Long.toString(event.getTimeInMillis()));
        values.put(COLOR, Integer.toString(event.getColor()));
        values.put(HOURDUR, Integer.toString(event.getDurationHour()));
        values.put(MINDUR, Integer.toString(event.getDurationMin()));

        // insert row
        long user_id = db.insert(TB_EVENT, null, values);

        return user_id;
    }

    /*
     * fetching one instance from a table
     */

    public Event getEvent(long e_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TB_EVENT + " WHERE "
                + ID + " = " + e_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Event event = new Event();
        event.setID(c.getInt(c.getColumnIndex(ID)));
        event.setData((c.getString(c.getColumnIndex(NAME))));
        event.setTimeInMillis(c.getLong(c.getColumnIndex(DATE)));
        event.setColor(c.getInt(c.getColumnIndex(COLOR)));
        event.setDurationHour(c.getInt(c.getColumnIndex(HOURDUR)));
        event.setDurationMin(c.getInt(c.getColumnIndex(MINDUR)));

        return event;
    }



    /*
     * fetching all instances from a table
     */

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TB_EVENT;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Event event = new Event();
                event.setID(c.getInt(c.getColumnIndex(ID)));
                event.setData((c.getString(c.getColumnIndex(NAME))));
                event.setTimeInMillis(c.getLong(c.getColumnIndex(DATE)));
                event.setColor(c.getInt(c.getColumnIndex(COLOR)));
                event.setDurationHour(c.getInt(c.getColumnIndex(HOURDUR)));
                event.setDurationMin(c.getInt(c.getColumnIndex(MINDUR)));

                // adding to todo list
                events.add(event);
            } while (c.moveToNext());
        }

        return events;
    }



    /*
     * Updating tables
     */

    public int updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID, Integer.toString(event.getID()));
        values.put(NAME, event.getData().toString());
        values.put(DATE, Long.toString(event.getTimeInMillis()));
        values.put(COLOR, Integer.toString(event.getColor()));
        values.put(HOURDUR, Integer.toString(event.getDurationHour()));
        values.put(MINDUR, Integer.toString(event.getDurationMin()));

        // updating row
        return db.update(TB_EVENT, values, ID + " = ?",
                new String[] { String.valueOf(event.getID()) });
    }



    /*
     * Deleting from tables
     */
    public void deleteEvent(long e_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TB_EVENT, ID + " = ?",
                new String[] { String.valueOf(e_id) });
    }


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
