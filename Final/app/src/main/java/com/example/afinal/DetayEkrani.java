package com.example.afinal;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.afinal.api.ApiClass;
import com.example.afinal.models.User;

public class DetayEkrani extends AppCompatActivity {
    private static final String TAG = DetayEkrani.class.getSimpleName();

    TextView emailApi, adApi;
    TextView emailRv, adRv;
    ImageView resimApi,resimRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay_ekrani);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emailApi = findViewById(R.id.emailApi);
        adApi = findViewById(R.id.adApi);
        resimApi = findViewById(R.id.resimApi);

        emailRv = findViewById(R.id.emailRv);
        adRv = findViewById(R.id.adRv);
        resimRv = findViewById(R.id.resimRv);

        Intent getData = getIntent();

        Bundle extras = getData.getExtras();
        if (extras != null)
        {

            int userId = getData.getIntExtra("UserId",0);
            String fullName = getData.getStringExtra("FullName");
            String image = getData.getStringExtra("Image");
            String email = getData.getStringExtra("Email");
            emailRv.setText(email);
            adRv.setText(fullName);

            Glide
                    .with(DetayEkrani.this)
                    .load(image)
                    .centerCrop()
                    .circleCrop()
                    .into(resimRv);
            ApiClass apiClass = new ApiClass();
            apiClass.getUser(dataHnd,userId);
        }else{
            finish();
        }







    }




    Handler dataHnd = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 505) {
                Log.i(TAG, "Error -> " + msg.obj);
                return;
            }

            User user = (User) msg.obj;

            emailApi.setText("Email : " + user.getEmail());
            adApi.setText(user.getFirstName() + " " + user.getLastName());

            Glide
                    .with(DetayEkrani.this)
                    .load(user.getAvatar())
                    .centerCrop()
                    .circleCrop()
                    .into(resimApi);
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}