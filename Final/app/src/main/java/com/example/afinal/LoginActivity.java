package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.afinal.api.ApiClass;
import com.example.afinal.models.ApiPostParam;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private Boolean giriskont = true;
    TextView txtKayit;
    EditText email,password;
    ConstraintLayout btn;
    String emailL = "eve.holt@reqres.in";
    String passL = "pistol";
    String emailR = "eve.holt@reqres.in";
    String passR = "cityslicka";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        Boolean savedChecked = sharedPreferences.getBoolean("isChecked",false);
        if (savedChecked){
            Intent intent = new Intent(LoginActivity.this,rvActivity.class);
            startActivity(intent);
            finish();
        }
        txtKayit = findViewById(R.id.txtKayit);
        btn = findViewById(R.id.btnUygula);
        password = findViewById(R.id.password);
        email = findViewById(R.id.adSoyad);
        email.setText(emailR);
        password.setText(passR);
        giriskont = true;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (giriskont){
                    Log.i(TAG,"Login -> "+giriskont);
                    Log.i(TAG,"Login -> "+email.getText().toString() + " == "+emailR);
                    Log.i(TAG,"Login -> "+password.getText().toString() + " == "+passR);
                    if (email.getText().toString().equals(emailR) &&  password.getText().toString().equals(passR)){
                        goLoginRegister(giriskont);
                    }else{
                        goError();
                    }
                }else{
                    Log.i(TAG,"Register -> "+!giriskont);
                    Log.i(TAG,"Login -> "+email.getText().toString() + " == "+emailL);
                    Log.i(TAG,"Login -> "+password.getText().toString() + " == "+passL);
                    if (email.getText().toString().equals(emailL) &&  password.getText().toString().equals(passR)){
                        goLoginRegister(giriskont);
                    }else{
                        goError();
                    }
                }
            }
        });
    }
    private void goLoginRegister(Boolean isLogin){
        List<ApiPostParam> params = new ArrayList<ApiPostParam>();
        ApiPostParam name = new ApiPostParam("email",email.getText().toString());
        ApiPostParam job = new ApiPostParam("password",password.getText().toString());
        params.add(name);
        params.add(job);
        ApiClass apiClass = new ApiClass();
        apiClass.loginOrRegister(dataHnd,params,isLogin);
    }
    Handler dataHnd = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 505) {
                Log.i(TAG, "Error -> " + msg.obj);
                goError();
                return;
            }
            if (!msg.obj.equals("false")){
                goMain();
            }else{
                goError();
            }
        }
    };
    public void goMain(){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isChecked",true);
        editor.commit();
        Intent intent = new Intent(LoginActivity.this,rvActivity.class);
        startActivity(intent);
        finish();
    }
    public void goError(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Uyarı")
                .setMessage("Girdiğiniz Bilgiler Yanlış")
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }
}