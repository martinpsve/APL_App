package com.example.martinpirklbauersv.apl;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class APLRegUsers extends AppCompatActivity {


    private EditText AnvandarID, DagarID, PeriodID, ArbetsplatsID;
    String sNarvarande, sAnvandarID, sDagarID, sPeriodID, sArbetsplatsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplreg_users);


        AnvandarID = (EditText) findViewById(R.id.AnvandarID);
        DagarID = (EditText) findViewById(R.id.DagarID);
        PeriodID = (EditText) findViewById(R.id.PeriodID);
        ArbetsplatsID = (EditText) findViewById(R.id.ArbetsplatsID);

    }

    public void onClickClose(View view) {
        finish();
    }

    public void DataTooDB(View view) {

        sAnvandarID = AnvandarID.getText().toString();
        sDagarID = DagarID.getText().toString();
        sPeriodID = PeriodID.getText().toString();
        sArbetsplatsID = ArbetsplatsID.getText().toString();



        String method = "mataInData";
        APLRegUsers.GetDataActivity GetDataActivity = new APLRegUsers.GetDataActivity(this);
        GetDataActivity.execute(method, sAnvandarID, sDagarID, sPeriodID, sArbetsplatsID);
    }

    private class GetDataActivity extends AsyncTask<String, Void, String> {


        AlertDialog alertDialog;
        Context ctx;
        View v;

        GetDataActivity(Context ctx)

        {
            this.ctx = ctx;
            this.v = v;
        }

        @Override
        protected void onPreExecute() {
            //   alertDialog = new AlertDialog.Builder(ctx).create();
            // alertDialog.setTitle("Login Information....");
        }

        @Override
        protected String doInBackground(String... params) {

            String reg_url = "http://"+getResources().getString(R.string.ip) +"/APL-APP/APL_PHP/APL_AdminCreateNarvaro.php";
            String method = params[0];
            String sAnvandarID = params[1];
            String sDagarID = params[2];
            String sPeriodID = params[3];
            String sArbetsplatsID = params[4];

            if (method.equals("mataInData")) {

                try {
                    URL url = new URL(reg_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    //httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("AnvandarID", "UTF-8") + "=" + URLEncoder.encode(sAnvandarID, "UTF-8") + "&" +
                            URLEncoder.encode("DagarID", "UTF-8") + "=" + URLEncoder.encode(sDagarID, "UTF-8") + "&" +
                            URLEncoder.encode("PeriodID", "UTF-8") + "=" + URLEncoder.encode(sPeriodID, "UTF-8") + "&" +
                            URLEncoder.encode("ArbetsplatsID", "UTF-8") + "=" + URLEncoder.encode(sArbetsplatsID, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    String response = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                    bufferedReader.close();
                    IS.close();



                    //httpURLConnection.connect();
                    httpURLConnection.disconnect();
                    return response;
                    // return "Registration Success...";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {

        }
    }

}
