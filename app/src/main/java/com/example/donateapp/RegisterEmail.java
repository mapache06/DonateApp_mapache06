package com.example.donateapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class RegisterEmail extends AppCompatActivity {
    private EditText eMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_correo);

        eMail = (EditText) findViewById(R.id.editCorreo);
    }

    public void nextCorreo(View v){
        Intent i = new Intent(this, RegisterPassword.class);
        Bundle extras = getIntent().getExtras();
        Persona obj = extras.getParcelable("persona");
        obj.eMail = eMail.getText().toString();

        i.putExtra("persona",obj);

        startActivity(i);

    }
}
