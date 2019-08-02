package com.example.donateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class InsertarProducto extends AppCompatActivity {
private Spinner CategoriaSpinner;
    private Spinner CondicionSpinner;
    private Spinner AmSpinner;
    private Spinner PmSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_producto);



        CategoriaSpinner = (Spinner) findViewById(R.id.CategoriaSpinner);
        CondicionSpinner = (Spinner) findViewById(R.id.CondicionSpinner);
        AmSpinner = (Spinner) findViewById(R.id.AmSpinner);
        PmSpinner = (Spinner) findViewById(R.id.PmSpinner);

        ArrayAdapter<CharSequence> adapterCategoria= ArrayAdapter.createFromResource(this,
                R.array.comboCategoria, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterCondicion= ArrayAdapter.createFromResource(this,
                R.array.comboCategoria, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterAm= ArrayAdapter.createFromResource(this,
                R.array.comboHorariosPm, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterPm= ArrayAdapter.createFromResource(this,
                R.array.comboHorariosPm, android.R.layout.simple_spinner_item);

        CategoriaSpinner.setAdapter(adapterCategoria);
        CondicionSpinner.setAdapter(adapterCondicion);
        AmSpinner.setAdapter(adapterAm);
        PmSpinner.setAdapter(adapterPm);
    }
}
