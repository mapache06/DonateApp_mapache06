package com.example.donateapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterUser extends AppCompatActivity {
    private EditText userName;

//Aqui solo se le solicita el nombre de usuario al usuario a registrae y se guuarda en una propiedad del usuario


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_usuario);

        userName = (EditText) findViewById(R.id.editUserName);


    }



    public void nextUser(View v){
        Intent i = new Intent(this, RegisterEmail.class);
        Bundle extras = getIntent().getExtras();
        Persona obj = extras.getParcelable("persona");
        obj.userName = userName.getText().toString();

        i.putExtra("persona",obj);

        startActivity(i);

    }
}
