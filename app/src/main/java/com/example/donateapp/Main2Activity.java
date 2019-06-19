package com.example.donateapp;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
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
    private BottomNavigationView bottom_nav;
    private Persona obj = new Persona();
    private Bundle bundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle extras = getIntent().getExtras();
        final Persona obj = extras.getParcelable("persona");
        Fragment f = new FHome();









        bottom_nav = findViewById(R.id.nav_view);

        cambiarFragment(f);
        bottom_nav.setSelectedItemId(R.id.nav_Home);



        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                        f.setArguments(bundle);
                        bundle.putParcelable("x",obj);

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

    }

