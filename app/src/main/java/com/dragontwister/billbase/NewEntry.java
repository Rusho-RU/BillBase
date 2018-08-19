package com.dragontwister.billbase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewEntry extends AppCompatActivity {
    DBHandler dbHandler;
    EditText editFlatNo, editRentFee, editGasBill, editElectricityUnit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_entry);
        setTitle("New Entry");
        dbHandler = new DBHandler(this);

        editFlatNo = findViewById(R.id.editText_newEntry_flatNo);
        editRentFee = findViewById(R.id.editText_newEntry_rentFee);
        editGasBill = findViewById(R.id.editText_newEntry_gasBill);
        editElectricityUnit = findViewById(R.id.editText_newEntry_perUnitCost);
    }

    public void onClickSubmit(View view){
        String info = ("Flat No.: " + editFlatNo.getText().toString() + "\n") +
                "Rent Fee: " + editRentFee.getText().toString() + "\n" +
                "Gas Bill: " + editGasBill.getText().toString() + "\n" +
                "Electricity Unit: " + editElectricityUnit.getText().toString() + "\n\n";

        new AlertDialog.Builder(this)
                .setTitle("Confirm to add data.")
                .setMessage(info)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isInserted = dbHandler.insertData(
                                Integer.parseInt(editFlatNo.getText().toString()),
                                Integer.parseInt(editRentFee.getText().toString()),
                                Integer.parseInt(editGasBill.getText().toString()),
                                Integer.parseInt(editElectricityUnit.getText().toString())
                        );
                        if(isInserted) {
                            Toast.makeText(NewEntry.this, "DATA INSERTED", Toast.LENGTH_LONG).show();
                            resetData();
                        }
                        else
                            Toast.makeText(NewEntry.this, "Something went wrong! Data was not inserted!", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(R.string.cancel, null).show();
    }

    public void resetData(){
        editFlatNo.getText().clear();
        editRentFee.getText().clear();
        editGasBill.getText().clear();
        editElectricityUnit.getText().clear();
    }

    public void onClickReset(View view){
        editFlatNo.getText().clear();
        editRentFee.getText().clear();
        editGasBill.getText().clear();
        editElectricityUnit.getText().clear();
    }
}
