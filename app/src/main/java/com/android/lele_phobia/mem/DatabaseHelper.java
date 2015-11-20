package com.android.lele_phobia.mem;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.database.DatabaseUtils.dumpCursorToString;

/**
 * Created by lele_phobia on 18/11/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "note.db";
    public static final String TABLE_NAME_NOTE = "note";
    public static final String COL_1 = "id";
    public static final String COL_2 = "id_user";
    public static final String COL_3 = "note";
    public static final String COL_4 = "data";

    public static final String TABLE_NAME_UTE = "utenti";
    public static final String COL_5 = "id";
    public static final String COL_6 = "username";
    public static final String COL_7 = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME_NOTE +" (id INTEGER PRIMARY KEY AUTOINCREMENT,id_user INTEGER, note TEXT, data TEXT)");
        db.execSQL("create table " + TABLE_NAME_UTE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_UTE);

        onCreate(db);
    }

    public Cursor selNote() {
    SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(
                TABLE_NAME_NOTE,  // The table to query
                null,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                COL_1+" DESC"                                 // The sort order
        );

        return c;
    }



    public boolean insertUte(String username,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL_5,name);
        contentValues.put(COL_6, username);
        contentValues.put(COL_7, password);
        long result = db.insert(TABLE_NAME_UTE,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertNota(int id_user,String nota, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,id_user);
        contentValues.put(COL_3, nota);
        contentValues.put(COL_4, data);
        long result = db.insert(TABLE_NAME_NOTE,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
}

