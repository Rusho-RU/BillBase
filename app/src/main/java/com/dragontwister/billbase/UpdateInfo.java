package com.dragontwister.billbase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

public class UpdateInfo extends AppCompatActivity {
    EditText editFlatNo;
    public final static String KEY ="dragon.twister";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_info);
        setTitle("Update Info");
        editFlatNo = findViewById(R.id.editText_updateInfo_flatNo);
    }

    public void OnclickSelect(View view){
        Intent intent = new Intent(this, UpdateInfoConfirm.class);
        intent.putExtra(KEY, editFlatNo.getText().toString());
        startActivity(intent);
    }
}
