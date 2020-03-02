package com.example.daily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Display extends AppCompatActivity {
    ArrayList<String> listData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        TextView t = findViewById(R.id.t);
        TextView t1 = findViewById(R.id.t1);
        TextView t2 = findViewById(R.id.t2);
        Intent in = getIntent();
       Bundle extras = getIntent().getExtras();
        //int[] arrayB = extras.getIntArray("list");
        String[] arrayB=extras.getStringArray("list");
        String[] arrayC=extras.getStringArray("list1");
      // listData = i.getStringArrayListExtra("list");
       // t.setText(listData+" ");
        String k=in.getStringExtra("list");

        //k= extras.getStringArray("list");
        t.setText(k+"");
        t1.setText(Arrays.toString(arrayC)+"");
//        Toast.makeText(this, "main:"+k.length, Toast.LENGTH_SHORT).show();
   //     Toast.makeText(this, "main:"+arrayC.length, Toast.LENGTH_SHORT).show();
        int j=0;
       // t2.setText(Arrays.toString(k));
    }
}