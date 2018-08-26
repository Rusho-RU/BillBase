package com.dragontwister.billbase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewEntry extends AppCompatActivity {
    DBHandler dbHandler;
    EditText editFlatNo, editRentFee, editGasBill, editEUnit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_entry);
        setTitle("New Entry");
        dbHandler = new DBHandler(this);

        editFlatNo = findViewById(R.id.editText_newEntry_flatNo);
        editRentFee = findViewById(R.id.editText_newEntry_rentFee);
        editGasBill = findViewById(R.id.editText_newEntry_gasBill);
        editEUnit = findViewById(R.id.editText_newEntry_perUnitCost);
    }

    public void onClickSubmit(View view){
        String flatNo, rentFee, gasBill, eUnit;
        flatNo = editFlatNo.getText().toString();
        rentFee = editRentFee.getText().toString();
        gasBill = editGasBill.getText().toString();
        eUnit = editEUnit.getText().toString();

        if(flatNo.length() == 0 || rentFee.length() == 0 || gasBill.length() == 0 || eUnit.length() == 0)
            showMessage("ERROR!", "Empty Field is not allowed!");
        else {

            insert(flatNo, rentFee, gasBill, eUnit);
        }
    }

    public void resetData(){
        editFlatNo.getText().clear();
        editRentFee.getText().clear();
        editGasBill.getText().clear();
        editEUnit.getText().clear();
    }

    public void onClickReset(View view){
        editFlatNo.getText().clear();
        editRentFee.getText().clear();
        editGasBill.getText().clear();
        editEUnit.getText().clear();
    }

    public void insert(String flatNo, String rentFee, String gasBill, String eUnit){
        String info = (
                "Flat No              : " + flatNo + "\n") +
                "Rent Fee           : " + rentFee + "\n" +
                "Gas Bill             : " + gasBill + "\n" +
                "Electricity Unit : " + eUnit + "\n\n";

        new AlertDialog.Builder(this)
                .setTitle("Confirm new entry")
                .setMessage(info)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int isInserted = dbHandler.insertData(
                                Integer.parseInt(editFlatNo.getText().toString()),
                                Integer.parseInt(editRentFee.getText().toString()),
                                Integer.parseInt(editGasBill.getText().toString()),
                                Integer.parseInt(editEUnit.getText().toString())
                        );
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
                .setNegativeButton(R.string.cancel, null).show();
    }

    public void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
    
    public void showToast(String message){
        Toast.makeText(NewEntry.this, message, Toast.LENGTH_LONG).show();
    }
}
