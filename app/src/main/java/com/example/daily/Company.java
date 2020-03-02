package com.example.daily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Company extends AppCompatActivity {
TextView user;
String username="";
String email="";
    EditText comp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        user  = findViewById(R.id.userName);
        comp = findViewById(R.id.company);
        Intent intent=getIntent();
        email = intent.getStringExtra("username");
        user.setText("    Hi "+email+" you can change your company name here... ");
    }

    public void set(View view) {
        if(comp.getText().toString().isEmpty())
            Toast.makeText(this, "Company name cannot be empty", Toast.LENGTH_SHORT).show();
        else
            {
            username = comp.getText().toString();
                GetSet.companyName = username.toString();
                Intent intent = new Intent(Company.this,AfterLogin.class);
                intent.putExtra("username",email);
                startActivity(intent);
        }
    }
}
