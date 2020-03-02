package com.example.daily;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText UsernameEt,PasswordEt;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsernameEt = findViewById(R.id.user);
        PasswordEt = findViewById(R.id.pwd);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = UsernameEt.getText().toString();

                String password = PasswordEt.getText().toString();

                String type = "login";
                if(!isNetworkConnected())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    //  builder.setTitle("Farmer_Customer Collaboration");
                    builder.setMessage("You don't have an active internet connection...")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int kk) {
                                    Toast.makeText(MainActivity.this, "Please check your connection...", Toast.LENGTH_SHORT).show();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else
                {
                    BackgroundWorker backgroundWorker = new BackgroundWorker(MainActivity.this);
                    backgroundWorker.execute(type, username, password);
                }
            }
        });

        /*Intent intent = getIntent();
        container = (LinearLayout)findViewById(R.id.container);


       try {
            quantity = intent.getStringExtra("quantity").toString();
        }
        catch (Exception e){
            Toast.makeText(this, " "+e, Toast.LENGTH_SHORT).show();
        }
     GetSet g = null;
        //GetSet  globalClass = (GetSet) getApplicationContext();

      try{quantity  =  g.getScore();}catch (Exception e){}
/*try {
    quantity = g.getQuantity();
}
catch (Exception e){
    Toast.makeText(this, " "+e, Toast.LENGTH_SHORT).show();
}
*/
        /*
if(quantity!="q"){

    LayoutInflater layoutInflater =
            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    final View addView = layoutInflater.inflate(R.layout.row, null);
    TextView textOut = (TextView)addView.findViewById(R.id.textout);
    textOut.setText(" "+quantity);
    Button buttonRemove = (Button)addView.findViewById(R.id.remove);
    buttonRemove.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            ((LinearLayout)addView.getParent()).removeView(addView);
        }});

    container.addView(addView);
}*/
    }

    public void newUser(View view) {
    }


    // item = intent.getStringExtra("item");


  /*  public void Move(View view) {
        Intent i = new Intent(MainActivity.this,Second.class);
        startActivity(i);
    }

    public void Party(View view) {
        Intent i = new Intent(MainActivity.this,parties.class);
        startActivity(i);
    }*/
  private boolean isNetworkConnected() {
      ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

      return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
  }
}
