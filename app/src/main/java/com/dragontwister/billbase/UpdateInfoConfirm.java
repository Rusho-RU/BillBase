package com.dragontwister.billbase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateInfoConfirm extends AppCompatActivity {
    public final static String KEY ="dragon.twister";

    DBHandler dbHandler;
    EditText editRentFee, editGasBill, editEUnit;
    String flatNo, rentFee, gasBill, eUnit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_info_confirm);
        setTitle("Confirm Update");
        Intent intent = getIntent();
        flatNo = intent.getStringExtra(KEY);

        dbHandler = new DBHandler(this);

        editRentFee = findViewById(R.id.editText_updateConfirm_rentFee);
        editGasBill = findViewById(R.id.editText_updateConfirm_gasBill);
        editEUnit = findViewById(R.id.editText_updateConfirm_eUnit);

        rentFee = editRentFee.getText().toString();
        gasBill = editGasBill.getText().toString();
        eUnit = editEUnit.getText().toString();
    }

    public void onClickUpdate(View view){
        Toast.makeText(UpdateInfoConfirm.this, flatNo + " " +rentFee+" "+gasBill+" "+eUnit, Toast.LENGTH_LONG).show();
        dbHandler.updateData(flatNo, rentFee, gasBill, eUnit);
    }
}
