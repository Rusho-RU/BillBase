package com.dragontwister.billbase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GasBill extends AppCompatActivity {
    TextView textView;
    Button btn;
    Integer gasBill;
    SharedPreferences sp;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gas_bill);
        setTitle("Gas Bill");

        textView = findViewById(R.id.textView_gasBill);
        btn = findViewById(R.id.button_gasBill);

        sp = getSharedPreferences("global_values", Activity.MODE_PRIVATE);
        gasBill = sp.getInt("gas_bill", -1);

        if(gasBill!= -1) {
            textView.setText("Current Value: " + gasBill.toString());
            btn.setText("Change Value");
        } else{
            textView.setText("No value is added yet.");
            btn.setText("Add Value");
        }

    }

    public void onClickChangeGas(View view){
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        new AlertDialog.Builder(this)
                .setTitle("Enter new Per Unit Cost")
                .setView(editText)
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String _perUnitCost = editText.getText().toString();
                        gasBill = Integer.parseInt(_perUnitCost);

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("gas_bill", gasBill);
                        editor.apply();

                        textView.setText("Current Value: " + gasBill.toString());
                        btn.setText("Change Value");
                    }
                })
                .create()
                .show();
    }
}
