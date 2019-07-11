package com.example.donateapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterNombre extends AppCompatActivity {
    private TextView name, lastName;
    private Button next;

    //Aqui solo se le solicita el nombre a usuario a registrae y se guuarda en una propiedad del usuario
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_nombre);

        name =(EditText) findViewById(R.id.editName);
        lastName = (EditText) findViewById(R.id.editLastName);
        next = (Button) findViewById(R.id.btnNextMostr);
    }

    //si clickea siguiente abre la nueva activity
    public void nextNombre(View v){
        Intent i = new Intent(this, RegisterUser.class);

        Persona obj = new Persona();
        obj.name = name.getText().toString();
        obj.lastName = lastName.getText().toString();

        i.putExtra("persona", obj);
        startActivity(i);


    }
}
