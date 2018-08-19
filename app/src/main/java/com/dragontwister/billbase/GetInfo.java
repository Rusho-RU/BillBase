package com.dragontwister.billbase;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class GetInfo extends AppCompatActivity {
    DBHandler dbHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_info);
        setTitle("Get Info");
        dbHandler = new DBHandler(this);
    }

    public void onClickIndividual(View view){
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("Insert Flat Number")
                .setView(editText)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer flatNo = Integer.parseInt(editText.getText().toString());
                        Toast.makeText(GetInfo.this, editText.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                })
                .create()
                .show();
    }

    public void onClickGetAll(View view){
        Cursor allData = dbHandler.getAllData();

        if(allData.getCount() == 0){
            showMessage("ERROR Occured!", "NO DATA FOUND FOR THIS MONTH");
            return;
        }

        StringBuilder buffer = new StringBuilder();

        while(allData.moveToNext()){
            buffer.append("Flat No              : " + allData.getString(0) + "\n");
            buffer.append("Rent Fee           : " + allData.getString(1) + "\n");
            buffer.append("Gas Bill             : " + allData.getString(2) + "\n");
            buffer.append("Electricity Unit : " + allData.getString(3) + "\n");
            buffer.append("Total Bill           : " + allData.getString(4) + "\n\n");
        }

        showMessage("All flat info for " + DBHandler.month[Calendar.getInstance().get(Calendar.MONTH)], buffer.toString());
    }

    public void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}
