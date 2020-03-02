package com.example.daily;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class parties extends AppCompatActivity {
    ListView listView;
    ListView listView1;
    private ProgressDialog progressDialog;
    String[] webchrz = new String[100];
    String[] webchrz1 = new String[100];
    String[] webchrz2 = new String[100];
    String[] webchrz3 = new String[100];
    String[] custA = new String[100];
    String[] famA = new String[100];
    String[] famP= new String[100];
    String[] price= new String[100];
    String[] fname= new String[100];
    String[] faddress= new String[100];String[] id= new String[100];
    String[] user= new String[100];

    String uname="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parties);
        Intent intent =  getIntent();
        uname  = intent.getStringExtra("username");
        listView = (ListView) findViewById(R.id.listView);

        TextView emptyText = (TextView)findViewById(android.R.id.empty);

        emptyText.setVisibility(View.VISIBLE);
        listView.setEmptyView(emptyText);



        String ServerURL = "https://learnfriendly.000webhostapp.com/pary.php";
        getJSON(ServerURL,uname);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Object clickItemObj = adapterView.getAdapter().getItem(index);
                Toast.makeText(parties.this, "You clicked " + famP[index], Toast.LENGTH_SHORT).show();
                Toast.makeText(parties.this, " "+faddress[index], Toast.LENGTH_SHORT).show();
if(Integer.parseInt(id[index])!=0) {
    Intent intent2 = new Intent(parties.this, partiesDisplay.class);
    intent2.putExtra("id", webchrz[index]);
    intent2.putExtra("name", webchrz1[index]);
    intent2.putExtra("item", famA[index]);
    intent2.putExtra("quantity", custA[index]);
    intent2.putExtra("price", famP[index]);
    intent2.putExtra("total", price[index]);
    intent2.putExtra("date", fname[index]);
    intent2.putExtra("pay", faddress[index]);
    intent2.putExtra("pend", id[index]);
    intent2.putExtra("user", user[index]);

    startActivity(intent2);
}
else{
    Intent intent2 = new Intent(parties.this, NoPending.class);
    intent2.putExtra("id", webchrz[index]);
    intent2.putExtra("name", webchrz1[index]);
    intent2.putExtra("item", famA[index]);
    intent2.putExtra("quantity", custA[index]);
    intent2.putExtra("price", famP[index]);
    intent2.putExtra("total", price[index]);
    intent2.putExtra("date", fname[index]);
    intent2.putExtra("pay", faddress[index]);
    intent2.putExtra("pend", id[index]);
    intent2.putExtra("user", user[index]);

    startActivity(intent2);

}
            }
        });
    }
    private void getJSON(final String urlWebService,final String unamek) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(parties.this, "Loading Data","Please Wait....", true);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String post_data = URLEncoder.encode("user","UTF-8")+"="+ URLEncoder.encode(unamek,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
//start

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

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        webchrz = new String[jsonArray.length()];
        webchrz1 = new String[jsonArray.length()];
        webchrz2 = new String[jsonArray.length()];webchrz3 = new String[jsonArray.length()];
        custA = new String[jsonArray.length()];
        famA = new String[jsonArray.length()];
        famP = new String[jsonArray.length()];
        price = new String[jsonArray.length()];
        fname = new String[jsonArray.length()];
        faddress = new String[jsonArray.length()];
        id  = new String[jsonArray.length()];

        user  = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            webchrz[i] = obj.getString("id");
            webchrz1[i] = obj.getString("name");
            famA[i] = obj.getString("item");
            custA[i] = obj.getString("quantity");
            famP[i] = obj.getString("price");
            price[i] = obj.getString("total");
            fname[i] = obj.getString("date");
            faddress[i] = obj.getString("pay");
            id[i] = obj.getString("pend");
            user[i] = obj.getString("user");
            //webchrz1[i] = obj.getString("price");
            //webchrz3[i] = obj.getString("item");
            webchrz2[i] = "\""+webchrz1[i]+"\"  Pending amount: "+id[i]+" ₹";
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, webchrz2);
        listView.setAdapter(arrayAdapter);

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
