package com.example.donateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MostrarDatos extends AppCompatActivity {
    TextView name,lastName,UserName,eMail,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);

        Bundle extras = getIntent().getExtras();
        Persona obj = extras.getParcelable("persona");

        name = findViewById(R.id.textName);
        lastName = findViewById(R.id.textLastName);
        UserName = findViewById(R.id.textUserName);
        eMail = findViewById(R.id.textEmail);
        Password = findViewById(R.id.textPassword);


        name.setText(obj.name.toString());
        lastName.setText(obj.lastName.toString());
        UserName.setText(obj.userName.toString());
        eMail.setText(obj.eMail);
        Password.setText(obj.password.toString());



    }
}
