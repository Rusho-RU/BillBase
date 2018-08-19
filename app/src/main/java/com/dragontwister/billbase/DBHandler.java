package com.dragontwister.billbase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Statement;
import java.util.Calendar;

public class DBHandler extends SQLiteOpenHelper {
    private static final String[] month = new String[] {
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    };

    public static String DATABASE_NAME = "year" + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)) + ".db";
    public static String TABLE_NAME = month[Calendar.getInstance().get(Calendar.MONTH)];
    public static final String COL_1 = "Flat_no";
    public static final String COL_2 = "Rent_Fee";
    public static final String COL_3 = "Gas_Bill";
    public static final String COL_4 = "Electricity_Bill";
    public static final String COL_5 = "Total_Bill";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COL_1 + " INTEGER PRIMARY KEY, " +
                COL_2 + " INTEGER, " +
                COL_3 + " INTEGER, " +
                COL_4 + " INTEGER, " +
                COL_5 + " INTEGER" + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(Integer flatNo, Integer rentFee, Integer gasBill, Integer electricityBill){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_1, flatNo);
        values.put(COL_2, rentFee);
        values.put(COL_3, gasBill);
        values.put(COL_4, electricityBill);
        values.put(COL_5, rentFee+gasBill+electricityBill);

        long result = db.insert(TABLE_NAME, null, values);

        return result!=-1;
    }
}
