package com.example.afinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.afinal.api.ApiClass;
import com.example.afinal.models.DataList;
import com.example.afinal.models.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class rvActivity extends AppCompatActivity {

    private static final String TAG = rvActivity.class.getSimpleName();

    private Button button;
    private RecyclerView recyclerView;
    private AppCompatEditText data;
    private TextView baslik, tvU;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;


    ApiClass apiClass = new ApiClass();


    DataList dataList = (DataList) new DataList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        calistir();

        getList();


    }

    private void getList() {
        apiClass.getDataList(dataHnd);
    }

    private void calistir() {
        data = findViewById(R.id.etİlk);
        baslik = findViewById(R.id.tvBaslik);
        tvU = findViewById(R.id.tvUrl);
        button = findViewById(R.id.btnUygula);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        lManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lManager);
       tikla();
    }

    private void tikla() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GithubSearchQuery();
            }
        });
    }

    public class adaptor extends RecyclerView.Adapter<adaptor.ViewHolder> {
        private List<User> values;

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView img;
            TextView fullName;
            TextView email;
            View layout;

            ViewHolder(View v) {
                super(v);
                layout = v;
                img = v.findViewById(R.id.resim);
                fullName = v.findViewById(R.id.adSoyad);
                email = v.findViewById(R.id.adSoyad);
            }
        }

        adaptor(List<User> myDataset) {
            values = myDataset;
        }

        @Override
        public adaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.satirrv_activity, parent, false);
            ViewHolder tutucu = new ViewHolder(v);
            return tutucu;
        }

        @Override
        public void onBindViewHolder(ViewHolder connect, final int position) {
            final User user = values.get(position);
            connect.fullName.setText(user.getFirstName() + " " + user.getLastName());
            connect.email.setText("Email : " + user.getEmail());

            Glide
                    .with(rvActivity.this)
                    .load(user.getAvatar())
                    .centerCrop()
                    .circleCrop()
                    .into(connect.img);
            connect.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            connect.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toDetail = new Intent(getApplicationContext(), DetayEkrani.class);

                    toDetail.putExtra("FullName", user.getFirstName() + " " + user.getLastName());
                    toDetail.putExtra("Image", user.getAvatar());
                    toDetail.putExtra("Email", "Email : " + user.getEmail());
                    toDetail.putExtra("UserId", user.getId());
                    startActivity(toDetail);
                }
            });
            /*
            connect..setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toDetail = new Intent(getApplicationContext(), DetaySayfasi.class);
                    toDetail.putExtra("HEADER", name);
                    toDetail.putExtra("DESCRIPTION", description);
                    startActivity(toDetail);
                }
            });
            */
        }

        @Override
        public int getItemCount() {
            return values.size();
        }

    }

    @SuppressLint("SetTextI18n")
    private void GithubSearchQuery() {
        String githubQuery = data.getText().toString();
        URL githubSearchUrl = DataClass.buildUrl(githubQuery);
        tvU.setText("URL: " + githubSearchUrl.toString());

        String githubSearchResult = null;

        try {
            githubSearchResult = DataClass.getResponseFromHttpUrl(githubSearchUrl);
            baslik.setText(githubSearchResult);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Hata! \n\n" + e.toString(), Toast.LENGTH_LONG).show();
        }
    }




/*
 runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    });
 */


    Handler dataHnd = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 505) {
                Log.i(TAG, "Error -> " + msg.obj);
                return;
            }
            dataList = (DataList) msg.obj;
            Log.i(TAG, dataList.getUsers().size() + "");
            //rv.getAdapter().notifyDataSetChanged();
            adapter = new adaptor(dataList.getUsers());
            recyclerView.setAdapter(adapter);
        }
    };

    public void ekle(View v) {
        Intent i = new Intent(rvActivity.this, DataAdd.class);
        startActivity(i);
        finish();

    }


}
