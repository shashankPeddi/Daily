package com.example.daily;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotifyWholesale extends AppCompatActivity {
    private int notificationId =1;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private AlarmManager alarmManager = null;

    private PendingIntent pendingIntent = null;
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
    int ph;
    String phonee;
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
        setContentView(R.layout.activity_notify_wholesale);
        Intent intent=getIntent();
        mlogin  = intent.getStringExtra("username").toString();
        //  install = intent.getStringExtra("installment").toString();
        phonee = intent.getStringExtra("phone").toString();
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
        Intent i = new Intent(NotifyWholesale.this,notify.class);
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

                        Toast.makeText(NotifyWholesale.this, ""+total, Toast.LENGTH_SHORT).show();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(NotifyWholesale.this);

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
                getJSON(ServerURL,mlogin.toString(),customer.getText().toString(), Arrays.toString(sitem).toString(),
                        Arrays.toString(squantity).toString(),
                        Arrays.toString(sprice).toString(),String.valueOf(total),
                        date.getText().toString(), spinner.getSelectedItem().toString(),String.valueOf(Pending).toString());
            }

        }

    }

    private void getJSON(final String urlWebService,final String item,final String cquantity,final String fphone,final String faadhar,
                         final String caadhar,final String price,final String fname,final String faddress,final String id) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(NotifyWholesale.this, "Loading Data","Please Wait....", true);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                try {
                    Toast.makeText(NotifyWholesale.this, "Saved sucessfully: "+s, Toast.LENGTH_SHORT).show();
                    if(s.equals("ordered")) {
                        Intent intent = new Intent(NotifyWholesale.this, AfterLogin.class);
                        intent.putExtra("username", mlogin.toString());

                        intent.putExtra("NotificationId", String.valueOf(notificationId));
                        intent.putExtra("todo", customer.getText().toString());
                        intent.putExtra("pending_amount", String.valueOf(Pending));
                        intent.putExtra("phone", phonee);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

//start
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String post_data = URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(item,"UTF-8")+"&"
                            +URLEncoder.encode("customer","UTF-8")+"="+URLEncoder.encode(cquantity,"UTF-8")+"&"
                            +URLEncoder.encode("item","UTF-8")+"="+URLEncoder.encode(fphone,"UTF-8")+"&"
                            +URLEncoder.encode("quantity","UTF-8")+"="+URLEncoder.encode(faadhar,"UTF-8")+"&"
                            +URLEncoder.encode("price","UTF-8")+"="+URLEncoder.encode(caadhar,"UTF-8")+"&"
                            +URLEncoder.encode("total","UTF-8")+"="+URLEncoder.encode(price,"UTF-8")+"&"
                            +URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(fname,"UTF-8")+"&"
                            +URLEncoder.encode("pay","UTF-8")+"="+URLEncoder.encode(faddress,"UTF-8")+"&"
                            +URLEncoder.encode("pend","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
//end
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

}
