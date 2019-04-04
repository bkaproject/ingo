package com.cavalerie.vlc123.gogogo.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cavalerie.vlc123.gogogo.model.Reservation;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class TravelManager extends SQLiteOpenHelper{

    public static final String DB_name = "travel_db";
    public static final int DB_version = 1;
    public static final String TABLE_NAME = "Travel";
    public static final String DATE = "jourVoyage";
    public static final String START = "lieuDepart";
    public static final String END = "lieuArret";
    public static final String HSTART = "heureDepart";
    public static final String HEND = "heureArrivee";
    public static final String PRICE = "prix";
    public static final String PLACE = "place";

    //request
    public static final String travel_table = "create table " + TABLE_NAME +
                                             "( " + DATE + " text," +
                                                    START + " text," +
                                                    END + " text," +
                                                    HSTART + " text," +
                                                    HEND + " text," +
                                                    PLACE + " text," +
                                                    PRICE + " text);";

    public static final String drop_travel = "drop table if exists "+ TABLE_NAME + ";";

    public TravelManager(Context context)
    {
        super(context,
              DB_name,
              null,
              DB_version );

        Log.d("Result", "Database created...");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(travel_table);
        Log.d("Result", "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL(drop_travel);
        Log.d("Result", "Database updated");
    }

    public static void putInformation(String jourVoyage, String lieuDepart, String lieuArret, String heureDepart, String heureArrivee, String place, String prix, SQLiteDatabase sqLiteDatabase)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, jourVoyage);
        contentValues.put(START, lieuDepart);
        contentValues.put(END, lieuArret);
        contentValues.put(HSTART, heureDepart);
        contentValues.put(HEND, heureArrivee);
        contentValues.put(PLACE, place);
        contentValues.put(PRICE, prix);

        long c = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        Log.d("Result", "row inserted");
    }

    public static Cursor getInformation(SQLiteDatabase sqLiteDatabase)
    {
        String[] projection = {DATE, START, END, HSTART, HEND, PLACE, PRICE};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, projection,null,null,null,null,null,null);
        return cursor;
    }
}
