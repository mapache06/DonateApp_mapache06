package com.example.donateapp.fragments_menu;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.donateapp.Persona;
import com.example.donateapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FUser extends Fragment {
    private TextView Name, Username;
    private Persona obj = new Persona();

    public FUser() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments().getParcelable("x") != null) {
            obj = getArguments().getParcelable("x");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fuser, container, false);
        Name = (TextView) view.findViewById(R.id.fu_Nombre);
        Username = (TextView) view.findViewById(R.id.fu_UserName);


        if(obj.name != null) {
            Name.setText(obj.name.toString());
            Username.setText(obj.userName.toString());
        }




        return view;
    }

}
