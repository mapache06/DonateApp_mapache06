package com.example.donateapp;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainPage extends AppCompatActivity {
    private TextView mTextMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //ImageView imagen = (ImageView) findViewById(R.id.im1);
        //int color = Color.parseColor("#E61E24");
        //imagen.setColorFilter(color);
    }

}
