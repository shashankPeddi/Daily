package com.example.daily;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import static android.Manifest.permission.SEND_SMS;

public class AfterLogin extends AppCompatActivity {
String email;
String installment="no";
    String NotificationId="id";
    String customer= "customer";
    String Pending = "pending";
    String phone;
    public static final int RequestPermissionCode = 7;
    private int notificationId =1;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private AlarmManager alarmManager = null;

    private PendingIntent pendingIntent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        if(CheckingPermissionIsEnabledOrNot())
        {
           // Toast.makeText(AfterLogin.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
        }

        // If, If permission is not enabled then else condition will execute.
        else {

            //Calling method to enable permission.
            RequestMultiplePermission();

        }
        Intent intent=getIntent();
        email = intent.getStringExtra("username");
        NotificationId =intent.getStringExtra("NotificationId").toString();
        customer=intent.getStringExtra("todo").toString();
        Pending =intent.getStringExtra("pending_amount").toString();
        phone = intent.getStringExtra("phone").toString();

        if(!NotificationId.equals("id"))
        {

            Intent intent1 = new Intent(AfterLogin.this, Alaram.class);
            intent1.putExtra("NotifiactionId", notificationId);
            intent1.putExtra("pending_amount", (Pending));
            intent1.putExtra("todo", customer.toString());
            intent1.putExtra("phone", phone.toString());
            final int _id = (int) System.currentTimeMillis();
            PendingIntent alaramIntent = PendingIntent.getBroadcast(AfterLogin.this, _id, intent, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

            long alarmTriggerTime = System.currentTimeMillis() + (15000+5000);

            alarm.set(AlarmManager.RTC_WAKEUP,alarmTriggerTime,alaramIntent);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String hello = "okok";

                    Intent intentk = new Intent(AfterLogin.this, Alaram.class);
                    intentk.putExtra("NotifiactionId", notificationId);
                    intentk.putExtra("todo", customer.toString());
                    intentk.putExtra("pending_amount", String.valueOf(Pending));
                    intentk.putExtra("phone", phone.toString());
                    final int _id = (int) System.currentTimeMillis();
                    PendingIntent alaramIntent = PendingIntent.getBroadcast(AfterLogin.this, _id, intentk, PendingIntent.FLAG_ONE_SHOT);
                    AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

                    long alarmTriggerTime = System.currentTimeMillis() + 15000*2;

                    alarm.set(AlarmManager.RTC_WAKEUP,alarmTriggerTime,alaramIntent);
                }
            }, (5000+5000+5000));
            Toast.makeText(AfterLogin.this, "Done!", Toast.LENGTH_SHORT).show();
        }
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.company,menu);
        return super.onCreateOptionsMenu(menu);
    }
Intent i;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.company) {
            i = new Intent(AfterLogin.this,Company.class);
            i.putExtra("username",email);
            startActivity(i);   }
        return super.onOptionsItemSelected(item);

    }
*/
    public void Move(View view) {
      //  GetSet.companyName="E6_G1_Company";
        Intent intent = new Intent(AfterLogin.this,Second.class);
        intent.putExtra("username",email);
        intent.putExtra("installment",installment.toString());

        startActivity(intent);
    }

    public void Party(View view) {
      //  GetSet.companyName="E6_G1_Company";
        Intent i = new Intent(AfterLogin.this,parties.class);
        i.putExtra("username",email);
        startActivity(i);
    }

    public void Installments(View view) {
        Intent i = new Intent(AfterLogin.this,installment.class);
        i.putExtra("username",email);
        startActivity(i);
    }
    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
       /* ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        CAMERA,
                        RECORD_AUDIO,
                        SEND_SMS,
                        GET_ACCOUNTS
                }, RequestPermissionCode);*/
        ActivityCompat.requestPermissions(AfterLogin.this, new String[]
                {
                        SEND_SMS
                }, RequestPermissionCode);

    }

    // Calling override method.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    /*boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordAudioPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean SendSMSPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean GetAccountsPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    */
                    boolean SendSMSPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;


                    //if (CameraPermission && RecordAudioPermission && SendSMSPermission && GetAccountsPermission) {
                    if (SendSMSPermission) {
                        Toast.makeText(AfterLogin.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(AfterLogin.this,"Permission Denied",Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }
    public boolean CheckingPermissionIsEnabledOrNot() {
        /*int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        */int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        //int ForthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), GET_ACCOUNTS);

        return //FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                //SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                //ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED ;
        //ForthPermissionResult == PackageManager.PERMISSION_GRANTED ;
    }

}
