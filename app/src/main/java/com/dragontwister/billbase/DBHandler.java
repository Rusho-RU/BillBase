package com.dragontwister.billbase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Calendar;

public class DBHandler extends SQLiteOpenHelper {
    public static final String[] month = new String[] {
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    };

    public static String DATABASE_NAME = "year" + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)) + ".db";
    public static String TABLE_NAME = month[Calendar.getInstance().get(Calendar.MONTH)];
    public static final String COL_1 = "Flat_No";
    public static final String COL_2 = "Rent_Fee";
    public static final String COL_3 = "Electricity_Unit";
    public static final String COL_4 = "Total_Bill";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COL_1 + " INTEGER PRIMARY KEY NOT NULL, " +
                COL_2 + " INTEGER NOT NULL, " +
                COL_3 + " INTEGER NOT NULL, " +
                COL_4 + " INTEGER NOT NULL" + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public int insertData(Integer flatNo, Integer rentFee, Integer eUnit, Integer total){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(getDataByQuery(flatNo.toString()).getCount() > 0)
            return 1;

        values.put(COL_1, flatNo);
        values.put(COL_2, rentFee);
        values.put(COL_3, eUnit);
        values.put(COL_4, total);

        long result = db.insert(TABLE_NAME, null, values);

        if(result == -1) return -1;
        else return 0;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public Cursor getDataByQuery(String flatNo){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Flat_No, Rent_Fee, Electricity_Unit, Total_Bill FROM " + TABLE_NAME +
                " WHERE Flat_No = ?", new String[] { flatNo });
        return cursor;
    }

    public void updateData(Integer flatNo, Integer rentFee, Integer eUnit, Integer total){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, rentFee);
        values.put(COL_3, eUnit);
        values.put(COL_4, total);

        db.update(TABLE_NAME, values, "Flat_No = ?", new String[] { flatNo.toString() });
    }

    public void deleteData(String flatNo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "Flat_No=?", new String[]{ flatNo });
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "1=1", null);
    }
}
