package com.dragontwister.billbase;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateInfoConfirm extends AppCompatActivity {
    public final static String KEY ="dragon.twister";

    DBHandler dbHandler;
    EditText editRentFee, editEUnit;
    String _flatNo, _rentFee, _eUnit, _total;
    Integer flatNo, rentFee, eUnit, total, gasBill, perUnitCost;
    Integer newRentFee, newEUnit, newTotal;
    TextView textRentFee, textEUnit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_info_confirm);
        setTitle("Confirm Update");

        Intent intent = getIntent();
        _flatNo = intent.getStringExtra(KEY);
        flatNo = Integer.parseInt(_flatNo);

        dbHandler = new DBHandler(this);

        gasBill = getValue("gas_bill");
        perUnitCost = getValue("per_unit_cost");

        Cursor cursor = dbHandler.getDataByQuery(_flatNo);

        if (cursor.getCount() == 0) {
            showMessage("ERROR!", "No Entry Found For Flat " + _flatNo);
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        else{
            cursor.moveToFirst();
            _rentFee = cursor.getString(1);
            _eUnit = cursor.getString(2);
            _total = cursor.getString(3);
            newRentFee = rentFee = Integer.parseInt(_rentFee);
            newEUnit = eUnit = Integer.parseInt(_eUnit);
            newTotal = total = Integer.parseInt(_total);
            textRentFee = findViewById(R.id.textView_updateConfirm_rentFee);
            textEUnit = findViewById(R.id.textView_updateConfirm_eUnit);
            textRentFee.setText("Current Rent Fee: " + rentFee.toString());
            textEUnit.setText("Current Meter Reading: " + eUnit.toString());
        }

        editRentFee = findViewById(R.id.editText_updateConfirm_rentFee);
        editEUnit = findViewById(R.id.editText_updateConfirm_eUnit);
    }

    public void onClickUpdate(View view) {
        String str1 = editRentFee.getText().toString();
        String str2 = editEUnit.getText().toString();

        if (str1.length() + str2.length() == 0)
            showMessage("WARNING!", "Both Field Cannot be empty!");
        else if (gasBill == -1 && perUnitCost == -1) {
            showMessage("", "Please insert Gas Bill and Per Unit Cost first!");
            goTo();
        }
        else if (gasBill == -1) {
            showMessage("", "Please insert Gas Bill first!");
            goTo();
        }
        else if (perUnitCost == -1){
            showMessage("", "Please insert Per Unit Cost first!");
            goTo();
        }
        else {
            if(str1.length() > 0)
            newRentFee = Integer.parseInt(str1);

            if(str2.length() > 0)
                newEUnit = Integer.parseInt(str2);

            newTotal = newRentFee + newEUnit*perUnitCost + gasBill;
            update();
        }
    }

    public void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    public Integer getValue(String key){
        SharedPreferences sp = getSharedPreferences("global_values", Activity.MODE_PRIVATE);
        return sp.getInt(key, -1);
    }

    public void goTo(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void update(){
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setMessage("Confirm to Update")
                .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.updateData(flatNo, newRentFee, newEUnit, newTotal);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
