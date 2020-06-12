package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GirisActivity extends AppCompatActivity {
    EditText email, sifre;
    Button button;
    String emails = "eve.holt@reqres.in";
    String sifres = "pistol";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        email = findViewById(R.id.adSoyad);
        sifre = findViewById(R.id.sifre);
        button = findViewById(R.id.giris);
        email.setText(emails);
        sifre.setText(sifres);
    }

    public void giris(View v) {
        String Semail = email.getText().toString();
        String Ssifre = sifre.getText().toString();
        if (Ssifre.equals(sifres) && Semail.equals(emails)) {
            Intent i = new Intent(GirisActivity.this, rvActivity.class);
            startActivity(i);
            finish();

        } else {
            Toast.makeText(this, "Bilgileri Kontrol Ediniz", Toast.LENGTH_SHORT).show();
        }
    }
    public void Kayit(View v) {
        Intent i = new Intent(GirisActivity.this, LoginActivity.class);
        startActivity(i);
        finish();

    }
    }

