package com.dragontwister.billbase;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewEntry extends AppCompatActivity {
    DBHandler dbHandler;
    EditText editFlatNo, editRentFee, editEUnit;
    Integer gasBill, perUnitCost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_entry);
        setTitle("New Entry");
        dbHandler = new DBHandler(this);

        editFlatNo = findViewById(R.id.editText_newEntry_flatNo);
        editRentFee = findViewById(R.id.editText_newEntry_rentFee);
        editEUnit = findViewById(R.id.editText_newEntry_perUnitCost);
        gasBill = getValue("gas_bill");
        perUnitCost = getValue("per_unit_cost");
    }

    public void onClickSubmit(View view){
        String flatNo, rentFee, eUnit;
        flatNo = editFlatNo.getText().toString();
        rentFee = editRentFee.getText().toString();
        eUnit = editEUnit.getText().toString();

        if(flatNo.length() == 0 || rentFee.length() == 0 || eUnit.length() == 0)
            showMessage("ERROR!", "Empty Field is not allowed!");
        else if(gasBill==-1 && perUnitCost==-1)
            showMessage("", "Please insert Gas Bill and Per Unit Cost first!");
        else if(gasBill==-1)
            showMessage("", "Please insert Gas Bill first!");
        else if(perUnitCost==-1)
            showMessage("", "Please insert Per Unit Cost first!");
        else
            insert(flatNo, rentFee, eUnit);
    }

    public void resetData(){
        editFlatNo.getText().clear();
        editRentFee.getText().clear();
        editEUnit.getText().clear();
    }

    public void onClickReset(View view){
        editFlatNo.getText().clear();
        editRentFee.getText().clear();
        editEUnit.getText().clear();
    }

    public void insert(String _flatNo, String _rentFee, String _eUnit){
        String info = (
                "Flat No       : " + _flatNo + "\n") +
                "Rent Fee      : " + _rentFee + "\n" +
                "Meter Reading : " + _eUnit + "\n\n";

        final Integer flatNo, rentFee, eUnit, total;
        flatNo = Integer.parseInt(_flatNo);
        rentFee = Integer.parseInt(_rentFee);
        eUnit = Integer.parseInt(_eUnit);
        total = rentFee + gasBill + perUnitCost*eUnit;


        new AlertDialog.Builder(this)
                .setTitle("Confirm new entry")
                .setMessage(info)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int isInserted = dbHandler.insertData(flatNo, rentFee, eUnit, total);
                        if(isInserted == 0) {
                            showToast("DATA INSERTED!");
                            resetData();
                        }
                        else if(isInserted == -1)
                            showToast("Something went wrong! Data was not inserted!");

                        else
                            showToast("This flat is already inserted for this month!\nTry \"UPDATE INFO\".");
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    public void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
    
    public void showToast(String message){
        Toast.makeText(NewEntry.this, message, Toast.LENGTH_LONG).show();
    }

    public Integer getValue(String key){
        SharedPreferences sp = getSharedPreferences("global_values", Activity.MODE_PRIVATE);
        return sp.getInt(key, -1);
    }
}
