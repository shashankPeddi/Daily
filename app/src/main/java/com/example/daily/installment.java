package com.example.daily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class installment extends AppCompatActivity {
Spinner spinner;
    String mlogin;
    Spinner spinner1;
    String installment="yes";
    EditText ph;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installment);
        ph = (EditText)findViewById(R.id.phone);
        Intent intent=getIntent();
        mlogin  = intent.getStringExtra("username");
        setTitle("WholeSale...");
        spinner = (Spinner)findViewById(R.id.planets_spinner);
        spinner1 = (Spinner)findViewById(R.id.planets_spinner1);
        List<String> categories = new ArrayList<String>();
        categories.add("Weeks");
        categories.add("Months");categories.add("Years");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);



        List<String> categories1 = new ArrayList<String>();
        categories1.add("1");
        categories1.add("2");categories1.add("3");categories1.add("4");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter1);

    }

    public void next(View view) {
        Intent i = new Intent(installment.this,NotifyWholesale.class);
        i.putExtra("username",mlogin.toString());

        i.putExtra("phone",ph.getText().toString());
        //i.putExtra("installment",installment.toString());
        startActivity(i);
    }
}
