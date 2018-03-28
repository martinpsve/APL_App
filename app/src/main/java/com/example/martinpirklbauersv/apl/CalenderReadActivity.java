package com.example.martinpirklbauersv.apl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;

public class CalenderReadActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

        String Fnamn, Enamn, AnvandarID, Narvarande, Datum;
        String sFnamn;
        String method;
    private LinearLayout test;
        private TextView närvarolista;
        private TextView tMåndag, tTisdag, tOnsdag, tTorsdag, tFredag,testingNarvaro;

        int UserID;


        ArrayAdapter<String> adapter1;
        ArrayList<Integer> IDList = new ArrayList<Integer>();
        ArrayList<String> fList = new ArrayList<String>();
        ArrayList<String> eList = new ArrayList<String>();
        ArrayList<Integer> NList = new ArrayList<Integer>();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            fList.add("Användare");
            setContentView(R.layout.activity_calender_read);

            närvarolista = (TextView) findViewById(R.id.textView21);

            tMåndag = (TextView) findViewById(R.id.test1);

            testingNarvaro = (TextView) findViewById(R.id.textView27);


            Spinner N = (Spinner)findViewById(R.id.spinner4);

            adapter1 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, fList);
            adapter1.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
            N.setAdapter(adapter1);
            N.setOnItemSelectedListener(this);


            method = "hämtaUserdata";
            CalenderReadActivity.GetDataActivity1 GetDataActivity1 = new CalenderReadActivity.GetDataActivity1(this);
            GetDataActivity1.execute(method);


        }

        public void onItemSelected(AdapterView<?> parent, View v,
                                   int pos, long id) {


            if (parent.getId() == R.id.spinner4) {

                sFnamn = (String) parent.getItemAtPosition(pos);


                if (!sFnamn.equals("Användare")) {
                    UserID = IDList.get(pos - 1);
                    Toast toast = Toast.makeText(this, "" + UserID, Toast.LENGTH_LONG);
                    toast.show();

                }


                method = "hämtaNärvarodata";
                CalenderReadActivity.GetDataActivity2 GetDataActivity2 = new CalenderReadActivity.GetDataActivity2(this);
                GetDataActivity2.execute(method, "" + UserID);



            }


        }
        public void onNothingSelected(AdapterView<?> arg0) {

        }

        private class GetDataActivity1 extends AsyncTask<String, Void, String> {


            AlertDialog alertDialog;
            Context ctx;
            View v;

            GetDataActivity1(Context ctx)

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
                String login_url = "http://192.168.216.46/APL-APP/APL_PHP/APL_GetUsersFromNarvaro.php";

                String method = params[0];
                if (method.equals("hämtaUserdata")) {

                    try {
                        URL url = new URL(login_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        //String data = URLEncoder.encode("AnvandarID", "UTF-8") + "=" + URLEncoder.encode(AnvandarID, "UTF-8");
                        //bufferedWriter.write(data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                        String response = "";
                        String line = "";
                        while ((line = bufferedReader.readLine()) != null) {
                            response += line;
                        }
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();
                        return response;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return null;

            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);

            }

            @Override
            protected void onPostExecute(String result) {

                JSONArray jsonarray = null;
                try {
                    jsonarray = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject obj = null;
                    try {
                        obj = jsonarray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Enamn = null;
                    try {
                        Enamn = obj.getString("Enamn");
                        eList.add(Enamn);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Fnamn = null;
                    try {
                        Fnamn = obj.getString("Fnamn");
                        fList.add(Fnamn);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    AnvandarID = null;
                    try {
                        AnvandarID = obj.getString("AnvandarID");
                        IDList.add(Integer.valueOf(AnvandarID));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                Spinner N = (Spinner)findViewById(R.id.spinner4);
                N.invalidate();
                N.setSelection(0);

            }
        }

    private class GetDataActivity2 extends AsyncTask<String, Void, String> {


        AlertDialog alertDialog;
        Context ctx;
        View v;

        GetDataActivity2(Context ctx)

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
            String login_url = "http://192.168.216.46/APL-APP/APL_PHP/APL_listNarvaro.php";

            String method = params[0];
            String UserID = params[1];
            if (method.equals("hämtaNärvarodata")) {

                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("AnvandarID", "UTF-8") + "=" + URLEncoder.encode(UserID, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String response = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String result) {

            JSONArray jsonarray = null;
            try {
                jsonarray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

try {
    for (int i = 0; i < jsonarray.length(); i++) {
        JSONObject obj = null;
        try {
            obj = jsonarray.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Narvarande = null;
        try {
            Narvarande = obj.getString("Narvarande");
            NList.add(Integer.valueOf(Narvarande));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Datum = null;
        try {
            Datum = obj.getString("Datum");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (i == 1) {
            tMåndag.setText("Måndag" + Datum);
            testingNarvaro.setText("måndag" + Narvarande);

        }

    }
}  catch (Exception r){

}
            närvarolista.setText("oj" + NList);
        }
    }
    }