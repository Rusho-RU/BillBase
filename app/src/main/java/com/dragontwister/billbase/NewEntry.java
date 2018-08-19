package com.dragontwister.billbase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewEntry extends AppCompatActivity {
    DBHandler dbHandler;
    EditText editFlatNo, editRentFee, editGasBill, editElectricityUnit;
    Button btnSubmit;

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

        btnSubmit = findViewById(R.id.button_new_entry_submit);

        addData();
    }

    public void addData(){
        btnSubmit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = dbHandler.insertData(
                                Integer.parseInt(editFlatNo.getText().toString()),
                                Integer.parseInt(editRentFee.getText().toString()),
                                Integer.parseInt(editGasBill.getText().toString()),
                                Integer.parseInt(editElectricityUnit.getText().toString())
                        );

                        if(isInserted)
                            Toast.makeText(NewEntry.this, "DATA INSERTED", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(NewEntry.this, "Something went wrong! Data was not inserted!", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
