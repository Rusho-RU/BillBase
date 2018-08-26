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

public class PerUnitCost extends AppCompatActivity {
    TextView textView;
    Button btn;
    Integer perUnitCost;
    SharedPreferences sp;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.per_unit_cost);
        setTitle("Per Unit Cost");

        textView = findViewById(R.id.textView_perUnitCost);
        btn = findViewById(R.id.button_perUnitCost_changeValue);

        sp = getSharedPreferences("global_values", Activity.MODE_PRIVATE);
        perUnitCost = sp.getInt("per_unit_cost", -1);

        if(perUnitCost != -1) {
            textView.setText("Current Value: " + perUnitCost.toString());
            btn.setText("Change Value");
        } else{
            textView.setText("No value is added yet.");
            btn.setText("Add Value");
        }
    }

    public void onClickChangeUnitCost(View view){
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
                        perUnitCost = Integer.parseInt(_perUnitCost);

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("per_unit_cost", perUnitCost);
                        editor.apply();

                        textView.setText("Current Value: " + perUnitCost.toString());
                        btn.setText("Change Value");
                    }
                })
                .create()
                .show();
    }
}
