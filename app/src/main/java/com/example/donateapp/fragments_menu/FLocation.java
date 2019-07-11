package com.example.donateapp.fragments_menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.donateapp.R;

/**
 * A simple {@link Fragment} subclass.
 */

//En este fragment se podrá asignar la ubicacion donde se desea entregar el producto a donar
public class FLocation extends Fragment {


    public FLocation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flocation, container, false);
    }

}
