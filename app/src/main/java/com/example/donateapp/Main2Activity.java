package com.example.donateapp;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.donateapp.fragments_menu.FHome;
import com.example.donateapp.fragments_menu.FLocation;
import com.example.donateapp.fragments_menu.FMessage;
import com.example.donateapp.fragments_menu.FUser;

public class Main2Activity extends AppCompatActivity {
    private TextView mTextMessage;
    private BottomNavigationView nav_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //ImageView imagen = (ImageView) findViewById(R.id.im1);
        //int color = Color.parseColor("#E61E24");
        //imagen.setColorFilter(color);



/*
        nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment  f = new Fragment();
                switch(menuItem.getItemId())
                {
                    case R.id.nav_Home:

                        f = new FHome();
                        cambiarFragment(f);

                        return true;

                    case R.id.nav_Location:
                        f = new FLocation();
                        cambiarFragment(f);

                        return true;

                    case R.id.nav_Message:
                        f = new FMessage();
                        cambiarFragment(f);

                        return true;

                    case R.id.nav_More:


                        return true;

                    case R.id.nav_User:
                        f = new FUser();
                        cambiarFragment(f);

                        return true;
                }

                return false;
            }
        });
    }



    private void cambiarFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
*/
    }
}
