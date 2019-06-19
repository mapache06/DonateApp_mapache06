package com.example.donateapp.fragments_menu;


import android.app.Activity;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.donateapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FHome extends Fragment {

    private FloatingActionButton txt;
    public FHome() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fhome, container, false);

        // Inflate the layout for this fragment
       // View view1 = inflater.inflate(R.layout.activity_main, container, false);

        txt = (FloatingActionButton) view.findViewById(R.id.ImagenAdd);

        int color = Color.parseColor("#FFFFFF");
        txt.setColorFilter(color);

        return view;
    }



}
