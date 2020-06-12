package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.afinal.api.ApiClass;
import com.example.afinal.models.ApiPostParam;

import java.util.ArrayList;
import java.util.List;

public class DataAdd extends AppCompatActivity {
    private static final String TAG = DataAdd.class.getSimpleName();

    private ApiClass apiClass = new ApiClass();

    ConstraintLayout ekleBtn;
    EditText adSoyad,meslek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ekleBtn = findViewById(R.id.ekleBtn);
        adSoyad = findViewById(R.id.adSoyad);
        meslek = findViewById(R.id.meslek);
        ekleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ApiPostParam> params = new ArrayList<ApiPostParam>();
                ApiPostParam name = new ApiPostParam("name",adSoyad.getText().toString());
                ApiPostParam job = new ApiPostParam("job",meslek.getText().toString());
                params.add(name);
                params.add(job);

                apiClass.newUser(dataHnd,params);

            }
        });





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    Handler dataHnd = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 505) {
                Log.i(TAG, "Error -> " + msg.obj);
                return;
            }

            AlertDialog alertDialog = new AlertDialog.Builder(DataAdd.this)
                    .setTitle("Dikkat")
                    .setMessage("Başarıyla Eklendi")
                    .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();

                        }
                    })
                    .show();

        }
    };
}