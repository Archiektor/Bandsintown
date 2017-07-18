package com.example.archiektor.testtaskbandsintown;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.archiektor.testtaskbandsintown.DBScheme.Cols.*;
import static com.example.archiektor.testtaskbandsintown.DBScheme.DbTable.*;

public class DBHelper extends SQLiteOpenHelper {

    //private static final String DATABASE_DELETE = "DROP TABLE " + TABLE_NAME;

    private static final String DB_NAME = "bandsDB.db";

    private static final int DB_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    //private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TITLE + TEXT_TYPE + COMMA_SEP
            + DATE + TEXT_TYPE + COMMA_SEP
            + TICKET_URL + TEXT_TYPE + COMMA_SEP
            + COUNTRY + TEXT_TYPE + COMMA_SEP
            + COORDS + TEXT_TYPE + ")";

    DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    static void insertData(SQLiteDatabase db, String title, String date, String ticket, String country, String coords) {

        ContentValues contactValues = new ContentValues();

        contactValues.put(TITLE, title);
        contactValues.put(DATE, date);
        contactValues.put(TICKET_URL, ticket);
        contactValues.put(COUNTRY, country);
        contactValues.put(COORDS, coords);

        db.insert(TABLE_NAME, null, contactValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {

            db.execSQL(DATABASE_CREATE);
        }

        if (oldVersion < 2) {

        }
    }
}
