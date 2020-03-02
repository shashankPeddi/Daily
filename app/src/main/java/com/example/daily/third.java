package com.example.daily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class third extends AppCompatActivity {
    EditText textIn;
    Button buttonAdd;
    String q=null;
    EditText quantity;
    EditText price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        textIn = (EditText)findViewById(R.id.textin);
        buttonAdd = (Button)findViewById(R.id.add);
        quantity = findViewById(R.id.quantity);
        price= findViewById(R.id.price);
    }
    public void Move(View view) {
        /*Intent i = new Intent(third.this,Second.class);
        //GetSet  globalClass=null;
        //globalClass.setScore(textIn.toString());
        GetSet.message = textIn.getText().toString();
        //i.putExtra("quantity",textIn.toString());
        startActivity(i);*/
        Intent resultIntent = new Intent();
        resultIntent.putExtra("quantity", textIn.toString());
        resultIntent.putExtra("quantity", quantity.toString());
        resultIntent.putExtra("quantity", price.toString());
        GetSet.quantity = textIn.getText().toString();
        GetSet.message = quantity.getText().toString();
        GetSet.price = price.getText().toString();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
