package com.dragontwister.billbase;

import android.content.DialogInterface;
import android.icu.lang.UCharacterEnums;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.security.spec.ECField;
import java.util.Calendar;

public class DeleteInfo extends AppCompatActivity {
    DBHandler dbHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_info);
        setTitle("Delete Info");
        dbHandler = new DBHandler(this);
    }

    public void onClickDeleteIndividual(View view){

    }

    public void onClickDeleteAll(View view){
        String month = dbHandler.month[Calendar.getInstance().get(Calendar.MONTH)];

        new AlertDialog.Builder(this)
                .setTitle("WARNING!")
                .setMessage("Are you sure you want to delete all data for " +
                month + "?")
                .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            dbHandler.deleteAllData();
                            showToast("ALL DATA HAS BEEN DELETED SUCCESSFULLY");
                        } catch (Exception e) {
                            showToast("Something Went Wrong. Data was not deleted!");
                        }
                    }
                })
                .setNegativeButton("CANCEL", null)
                .create()
                .show();
    }

    public void showToast(String message){
        Toast.makeText(DeleteInfo.this, message, Toast.LENGTH_LONG).show();
    }
}
