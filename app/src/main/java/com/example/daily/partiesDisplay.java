package com.example.daily;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class partiesDisplay extends AppCompatActivity {
    private static final String TAG = "partiesDisplay";
    private File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
TextView party;
TextView items;
EditText rec;
TextView pending;
TextView date;
String pname="";
String id="";
String user = "";

    String item="";
    String pend = "";
    String dt="";
    Button update;
    String dk="";
    final String ServerURL = "https://learnfriendly.000webhostapp.com/ssupdate.php";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parties_display);
        ActivityCompat.requestPermissions(partiesDisplay.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        rec=(EditText)findViewById(R.id.receivedAmount);
        update =(Button)findViewById(R.id.update);
        items  = (TextView)findViewById(R.id.item);
        party = (TextView)findViewById(R.id.pname);
        pending = (TextView)findViewById(R.id.pending);
        date = (TextView)findViewById(R.id.date);

        Intent intent = getIntent();
        pname = intent.getStringExtra("name").toString();
        user = intent.getStringExtra("user").toString();
        id = intent.getStringExtra("id").toString();
        item = intent.getStringExtra("item").toString();
        pend = intent.getStringExtra("pend").toString();
        dt = intent.getStringExtra("date").toString();
dk = intent.getStringExtra("date").toString();
        items.setText("Purchased items: "+item);
        pending.setText("Pending Amount: "+pend+" ₹");
        party.setText("party name: "+pname);
date.setText(""+dt);
        String edit = rec.getText().toString();
       // Toast.makeText(partiesDisplay.this, ""+edit, Toast.LENGTH_SHORT).show();
update.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {



        if (!isNetworkConnected()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(partiesDisplay.this);
            //  builder.setTitle("Farmer_Customer Collaboration");
            builder.setMessage("You don't have an active internet connection...")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int kk) {
                            Toast.makeText(partiesDisplay.this, "Please check your connection...", Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {

             if(rec.getText().toString().isEmpty()){
                AlertDialog.Builder builder = new AlertDialog.Builder(partiesDisplay.this);

                builder.setMessage("Shouldn't be empty...")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int kk) {
                                rec.setError("Shouldn't be empty...");
                                rec.findFocus();
                                //  Toast.makeText(Second.this, "Please enter within Quantity...", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

             else  if(Integer.parseInt(rec.getText().toString())==0)
             {
                 AlertDialog.Builder builder = new AlertDialog.Builder(partiesDisplay.this);

                 builder.setMessage("Shouldn't be zero...")
                         .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int kk) {
                                 rec.setError("Shouldn't be zero...");
                                 rec.findFocus();
                                 //  Toast.makeText(Second.this, "Please enter within Quantity...", Toast.LENGTH_SHORT).show();
                             }
                         });
                 AlertDialog alert = builder.create();
                 alert.show();
             }

            else {
                 int r = Integer.parseInt(rec.getText().toString());
                 int p = Integer.parseInt(pend);

                 if (r > p) {
                     AlertDialog.Builder builder = new AlertDialog.Builder(partiesDisplay.this);

                     builder.setMessage("Recieved is Greater than the pending...")
                             .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialogInterface, int kk) {
                                     rec.setError("Recieved is Greater than the pending...");
                                     //  Toast.makeText(Second.this, "Please enter within Quantity...", Toast.LENGTH_SHORT).show();
                                 }
                             });
                     AlertDialog alert = builder.create();
                     alert.show();

                 }
                 else{
                p=p-r;
                getJSON(ServerURL,id,user,String.valueOf(p));}
            }
        }
    }
});
    }

    private void getJSON(final String urlWebService,final String idk,final String userk,final String reck) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(partiesDisplay.this, "Loading Data","Please Wait....", true);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                try {
                    Toast.makeText(partiesDisplay.this, "Updated Sucessfully"+s, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(partiesDisplay.this, AfterLogin.class);
                    intent.putExtra("username",user);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
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
                    String post_data = URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(idk,"UTF-8")+"&"
                            +URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(userk,"UTF-8")+"&"
                            +URLEncoder.encode("rec","UTF-8")+"="+URLEncoder.encode(reck,"UTF-8");
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
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    private void createPdfWrapper() throws FileNotFoundException, DocumentException {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }else {
            createPdf();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),pname+"Report.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();

        //String name= GetSet.companyName.toString();
        document.addTitle("Details of "+pname);

        document.add(new Paragraph(user.toString() + "'s" + " company" + "\n\n" + "Items purchased: " + item + "\n\n"));

        document.add(new Paragraph("Party name: " + pname + "\n\n"));

        document.add(new Paragraph("Amount to be paid:  "+pend+" Rupees(s)"));

        document.close();

        previewPdf();
    }

    private void previewPdf() {
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");

            startActivity(intent);
        }else{
            Toast.makeText(this,"Download a PDF Viewer to see the generated PDF",Toast.LENGTH_SHORT).show();
        }
    }

    public void create(View view) {
        try {
            Toast.makeText(this, "Creating Invoice..please wait()", Toast.LENGTH_SHORT).show();
            createPdfWrapper();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    public void share(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hii,\n\n My dear Friend this is from "+user.toString() + "'s" + " company" + "\n\n" + "Items purchased: " + item + "\n\n"
                        +"Party name: " + pname + "\n\n"+"Purchased on: "+dk.toString()+"\n\n"+
                        ""+pending.getText().toString()+"Rupees(s)"
        );
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent,"Share via");

        startActivity(sendIntent);
    }

}
