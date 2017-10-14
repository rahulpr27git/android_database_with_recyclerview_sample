package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.model.DBModel;

import java.util.ArrayList;

/**
 * Created by jbmm0007 on 13/10/17.
 */

public class DBHelperClass extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "demo.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME ="demo";

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";

    public static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + " ( " +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT, " +
            EMAIL + " TEXT" +
            ")";

    public DBHelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public long insertIntoDatabase(String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME,name);
        values.put(EMAIL,email);

        long status = db.insert(TABLE_NAME,null,values);
        db.close();

        return status;
    }

    public int deleteFromDatabse(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int status = db.delete(TABLE_NAME,ID + "= ? ",new String[]{id});
        db.close();

        return status;
    }

    public ArrayList<DBModel> getDataFromDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,null,null,null,null,null);

        ArrayList<DBModel> list = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                DBModel model = new DBModel();
                model.setId(Integer.toString(cursor.getInt(cursor.getColumnIndex(ID))));
                model.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                model.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                list.add(model);
            }
            cursor.close();
        }
        db.close();
        return list;
    }

    public String getLastInsertedId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ID +" from "+TABLE_NAME +" order by "+ID + " desc limit 1",null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToNext();
            return Integer.toString(cursor.getInt(cursor.getColumnIndex(ID)));
        } else {
            return null;
        }
    }
}
