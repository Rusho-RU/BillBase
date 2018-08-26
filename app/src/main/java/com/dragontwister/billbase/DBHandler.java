package com.dragontwister.billbase;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.IntegerRes;

import java.sql.Statement;
import java.util.Calendar;

public class DBHandler extends SQLiteOpenHelper {
    public static final String[] month = new String[] {
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    };

    public static String DATABASE_NAME = "year" + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)) + ".db";
    public static String TABLE_NAME = month[Calendar.getInstance().get(Calendar.MONTH)];
    public static final String COL_1 = "Flat_No";
    public static final String COL_2 = "Rent_Fee";
    public static final String COL_3 = "Gas_Bill";
    public static final String COL_4 = "Electricity_Unit";
    public static final String COL_5 = "Total_Bill";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COL_1 + " INTEGER PRIMARY KEY NOT NULL, " +
                COL_2 + " INTEGER NOT NULL, " +
                COL_3 + " INTEGER NOT NULL, " +
                COL_4 + " INTEGER NOT NULL, " +
                COL_5 + " INTEGER NOT NULL" + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public int insertData(Integer _flatNo, Integer _rentFee, Integer _gasBill, Integer _eUnit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String total, flatNo, rentFee, gasBill, eUnit;

        total = Integer.toString(_rentFee+_gasBill+_eUnit);
        flatNo = _flatNo.toString();
        rentFee = _rentFee.toString();
        gasBill = _gasBill.toString();
        eUnit = _eUnit.toString();

        if(getDataByQuery(flatNo) != null)
            return 1;

        values.put(COL_1, flatNo);
        values.put(COL_2, rentFee);
        values.put(COL_3, gasBill);
        values.put(COL_4, eUnit);
        values.put(COL_5, total);

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
        Cursor cursor = db.rawQuery("SELECT FLat_No, Rent_Fee, Gas_Bill, Electricity_Unit, Total_Bill FROM " + TABLE_NAME +
                " WHERE Flat_No = ?", new String[] { flatNo });
        return cursor;
    }

    public void updateData(String _flatNo, String _rentFee, String _gasBill, String _eUnit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1, _flatNo);
        values.put(COL_2, _rentFee);
        values.put(COL_3, _gasBill);
        values.put(COL_4, _eUnit);

        db.update(TABLE_NAME, values, "Flat_No = ?", new String[] { _flatNo });
    }
}
