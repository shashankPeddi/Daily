package com.example.daily;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Second extends AppCompatActivity {

   /* private int notificationId =1;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private AlarmManager alarmManager = null;

    private PendingIntent pendingIntent = null;*/
    String TempPrice,TempTotal,TempDate,TempPend,TempPay,TempItem,TempQuantity,TempName,TempUser;
    String ServerURL = "https://learnfriendly.000webhostapp.com/det.php";
    private ProgressDialog progressDialog;
    String quantity="q";
    String price = "p";
    String item = "i";
    StringBuilder si = new StringBuilder();
    StringBuilder sq = new StringBuilder();
    StringBuilder sp = new StringBuilder();
    String d,r;
    String it="";
    String p="";
    String q="";
    String mlogin;
    int Pending=0;
    int subTotal;
    int total=0;
    //String install;
    TextView to;
    EditText customer,description,received;
    String rr;
    TextView date;
    Button submit;
    int c=1;
    String sitem[] = new String[100];
    String squantity[] = new String[100];
    String sprice[] = new String[100];
//    String stotal[] = new String[100];
//    String sdate[] = new String[100];
//    String sitem[] = new String[100];
    LinearLayout container;
    private static final int RESULT_PICK_CONTACT =1;
    public ArrayList<String> listData = new ArrayList<>();
   public  ArrayList<String> listData1 = new ArrayList<>();
    ArrayList<String> items = new ArrayList<>();
    ArrayList<String> quantities = new ArrayList<>();
    ArrayList<String> prices = new ArrayList<>();
    Spinner spinner;
   //String[] listData = new String[100];
   // String[] listData1 = new String[100];
    ArrayList<String> listData2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent=getIntent();
        mlogin  = intent.getStringExtra("username").toString();
      //  install = intent.getStringExtra("installment").toString();
        Toast.makeText(this, ""+mlogin, Toast.LENGTH_SHORT).show();
         spinner = (Spinner) findViewById(R.id.spin);

         to = (TextView)findViewById(R.id.total);
         customer = findViewById(R.id.cust);
         description=findViewById(R.id.description);
         received= findViewById(R.id.receivedAmount);
         submit = findViewById(R.id.select1);
         date = (TextView)findViewById(R.id.date);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
         date.setText("Date: "+currentDate);
        container = (LinearLayout) findViewById(R.id.container);
        List<String> categories = new ArrayList<String>();
        categories.add("Cash");
        categories.add("Online_payment");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
       /* quantity = GetSet.message;
        if (quantity != null) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView = layoutInflater.inflate(R.layout.row, null);
            TextView textOut = (TextView) addView.findViewById(R.id.textout);
            textOut.append(" " + quantity);
            //textOut.setText(" "+quantity);
            Button buttonRemove = (Button) addView.findViewById(R.id.remove);
            buttonRemove.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ((LinearLayout) addView.getParent()).removeView(addView);
                }
            });

            container.addView(addView);
        }*/
    }
    public void Move(View view) {
        Intent i = new Intent(Second.this,third.class);
        //startActivity(i);
        startActivityForResult(i, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //String quantity = data.getStringExtra("quantity").toString();
                item = GetSet.quantity;
                quantity = GetSet.message;
                price= GetSet.price;
                it = it+item+",";
                q = q+quantity+",";
                p=p+price+",";
                subTotal= Integer.parseInt(quantity)*Integer.parseInt(price);
                total = total + Integer.parseInt(quantity)*Integer.parseInt(price);
                listData.add(quantity);
                items.add(item);
                quantities.add(quantity);
                prices.add(price);
                GetSet.ll.add(quantity);
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                final TextView textOut = (TextView) addView.findViewById(R.id.textout);
                final TextView vv = (TextView) addView.findViewById(R.id.v);
                final TextView sub = (TextView) addView.findViewById(R.id.sub);
                final Button del = (Button)addView.findViewById(R.id.delete);

                //textOut.append(" " + quantity);

                textOut.setText(" "+c+") Item name: "+item+" Quantity: "+quantity+" Price:"+price);
                vv.setText(""+item);
c++;

                Toast.makeText(this, " "+total, Toast.LENGTH_SHORT).show();
               sub.setText("Sub_total:"+subTotal);
to.setText("Total: "+total);


                del.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        String str = vv.getText().toString();
                      /*  items.remove(item);
                        quantities.remove(quantity);
                        prices.remove(price);
                        subTotal = subTotal- Integer.parseInt(sub.getText().toString());
                        listData1.add(str);*/
                    int index =   items.indexOf(str);
                        total = total - Integer.parseInt(prices.get(index))*Integer.parseInt(quantities.get(index));
                    items.remove(index);
                    quantities.remove(index);
                    prices.remove(index);
                   // total =Integer.parseInt(sub.getText().toString());

                        Toast.makeText(Second.this, ""+total, Toast.LENGTH_SHORT).show();
                        to.setText("Total: "+total);
                            ((LinearLayout) addView.getParent()).removeView(addView);
                            //Toast.makeText(Second.this, ""+textOut.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                container.addView(addView);

                registerForContextMenu(container);
            }
            if (resultCode == RESULT_CANCELED) {
                //mTextViewResult.setText("Nothing selected");
            }
        }

    }

    public void Submit(View view) {
String c;
c=customer.getText().toString();
        if(c.length()==0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Second.this);

            builder.setMessage("Please Enter the Customer name...")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int kk) {
                            customer.setError("");
                          //  Toast.makeText(Second.this, "Please enter within Quantity...", Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else{
sitem= new String[items.size()];
            squantity= new String[quantities.size()];
            sprice= new String[prices.size()];
for(int j=0;j<items.size();j++)
    sitem[j] = items.get(j);
            for(int j=0;j<quantities.size();j++)
                squantity[j] = quantities.get(j);
            for(int j=0;j<prices.size();j++)
                sprice[j] = prices.get(j);

            d = description.getText().toString();
            r =  received.getText().toString();
            if(d.length()==0 ||r.length()==0 )
                d="No description";
            if(r.length()==0)
            {
            Pending = total;
            r="0";
            }
            else
            {
                rr= received.getText().toString();
                Pending = total-Integer.parseInt(rr);
            }
            if(Integer.parseInt(r)>total){
                Toast.makeText(this, "Received cannot be greater than total!", Toast.LENGTH_LONG).show();
                received.setError("");
            }
            else{
                GetData();
                InsertData(TempName,TempItem,TempQuantity,TempPrice,TempTotal,TempDate,TempPay,TempPend,TempUser);

            }

        }

    }

    public void GetData(){
TempUser = mlogin.toString();
        TempName= customer.getText().toString();
        //TempItem = it.toString();
TempItem  = Arrays.toString(sitem).toString();
        //TempQuantity = q.toString();
TempQuantity = Arrays.toString(squantity).toString();
        //TempPrice =p.toString();
        TempPrice = Arrays.toString(sprice).toString();

        TempTotal = String.valueOf(total);
        TempDate = date.getText().toString();
        TempPay = spinner.getSelectedItem().toString();
        TempPend = String.valueOf(Pending);
    }
    public void InsertData(final String name,final String itemk, final String quantityk, final  String pricek,final String totalk,
                           final String datek,final String payk,final String pendk,final String user){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String UserHolder  = user;
                String NameHolder =name;
                String ItemHolder = itemk ;
                String QuantityHolder = quantityk ;
                String PriceHolder = pricek;
                String PassHolder = totalk;
                String AddressHolder  = datek;
                String actual = payk;
                String idd = pendk;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("uid",UserHolder));
                nameValuePairs.add(new BasicNameValuePair("customer",NameHolder));
                nameValuePairs.add(new BasicNameValuePair("item",ItemHolder));
                nameValuePairs.add(new BasicNameValuePair("quantity",QuantityHolder));
                nameValuePairs.add(new BasicNameValuePair("price",PriceHolder));
                nameValuePairs.add(new BasicNameValuePair("total",PassHolder));
                nameValuePairs.add(new BasicNameValuePair("date",AddressHolder));
                nameValuePairs.add(new BasicNameValuePair("pay",actual));
                nameValuePairs.add(new BasicNameValuePair("pend",idd));
                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "ordered";
            }

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                progressDialog = ProgressDialog.show(Second.this, "Just a minute","Wait....", true);
            }
            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);
                progressDialog.dismiss();
                Toast.makeText(Second.this, "Saved Sucessfully "+result, Toast.LENGTH_LONG).show();
                if(result.equalsIgnoreCase("ordered")) {
                    /*if(install.equals("yes"))
                    {
                        Intent intent = new Intent(Second.this, Alaram.class);
                        intent.putExtra("NotifiactionId", notificationId);
                        intent.putExtra("pending_amount", String.valueOf(Pending));
                        intent.putExtra("todo", customer.getText().toString());
                        final int _id = (int) System.currentTimeMillis();
                        PendingIntent alaramIntent = PendingIntent.getBroadcast(Second.this, _id, intent, PendingIntent.FLAG_ONE_SHOT);
                        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

                        long alarmTriggerTime = System.currentTimeMillis() + (15000+5000);

                        alarm.set(AlarmManager.RTC_WAKEUP,alarmTriggerTime,alaramIntent);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String hello = "okok";

                                Intent intent = new Intent(Second.this, Alaram.class);
                                intent.putExtra("NotifiactionId", notificationId);
                                intent.putExtra("todo", customer.getText().toString());
                                intent.putExtra("pending_amount", String.valueOf(Pending));
                                final int _id = (int) System.currentTimeMillis();
                                PendingIntent alaramIntent = PendingIntent.getBroadcast(Second.this, _id, intent, PendingIntent.FLAG_ONE_SHOT);
                                AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

                                long alarmTriggerTime = System.currentTimeMillis() + 15000*2;

                                alarm.set(AlarmManager.RTC_WAKEUP,alarmTriggerTime,alaramIntent);
                            }
                        }, (5000+5000));
                        Toast.makeText(Second.this, "Done!", Toast.LENGTH_SHORT).show();
                    }*/
                    String NotificationId="id";
                    String phone="phone";
                    Intent intent = new Intent(Second.this, AfterLogin.class);
                    intent.putExtra("username",mlogin.toString());
                    intent.putExtra("NotificationId", NotificationId);
                    intent.putExtra("todo", customer.toString());
                    intent.putExtra("pending_amount", String.valueOf(Pending));
                    intent.putExtra("phone",phone.toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                finish();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(itemk,quantityk,pricek,totalk,datek,payk,pendk,user);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

int id =item.getItemId();


        return super.onContextItemSelected(item);
    }
}