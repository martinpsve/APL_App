package com.example.martinpirklbauersv.apl;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.Arrays;
import java.util.Objects;

public class CalenderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

private TextView Tstartdag;
private TextView Tslutdag;
private TextView dMåndag, dTisdag, dOnsdag, dTorsdag, dFredag;
private TextView tMåndag, tTisdag, tOnsdag, tTorsdag, tFredag;
private ImageView iMåndag, iTisdag, iOnsdag, iTorsdag, iFredag;
String stWeek, stMonN, stTisN, stOnsN, stTorN, stFreN;
String Datum, DagarID, test;
String Fnamn, Enamn;
String method;
int start = 9;
int slut = 11;
int dag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        Tstartdag = (TextView) findViewById(R.id.textView10);
        Tslutdag = (TextView) findViewById(R.id.textView11);

        tMåndag = (TextView) findViewById(R.id.textView12);
        tTisdag = (TextView) findViewById(R.id.textView13);
        tOnsdag = (TextView) findViewById(R.id.textView14);
        tTorsdag = (TextView) findViewById(R.id.textView16);
        tFredag = (TextView) findViewById(R.id.textView15);

        dMåndag = (TextView) findViewById(R.id.textView9);
        dTisdag = (TextView) findViewById(R.id.textView18);
        dOnsdag = (TextView) findViewById(R.id.textView19);
        dTorsdag = (TextView) findViewById(R.id.textView17);
        dFredag = (TextView) findViewById(R.id.textView20);

        Spinner s = (Spinner) findViewById(R.id.spinner2);
        s.setOnItemSelectedListener(this);

        String[] arraySpinner1 = new String[2];
        int antal = (slut - start) + 1;
        for (int vecka = start, i = 0; vecka < slut; vecka++, i++) {
            arraySpinner1[i] = "" + vecka;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner1);
        adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        s.setAdapter(adapter);


        Spinner U = (Spinner)findViewById(R.id.UserSpinner);
        String[] arraySpinner7 = new String[] {
                "Fnman"
        };

        ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner7);
        adapter7.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        U.setAdapter(adapter7);
        U.setOnItemSelectedListener(this);


        Spinner M = (Spinner)findViewById(R.id.måndagspinner);
        String[] arraySpinner2 = new String[] {
                "val","Närvarande", "EjNärvarande"
        };

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        M.setAdapter(adapter2);
        M.setOnItemSelectedListener(this);

        Spinner T = (Spinner)findViewById(R.id.tidagspinner);
        String[] arraySpinner3 = new String[] {
                "val","Närvarande", "EjNärvarande"
        };

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        T.setAdapter(adapter3);
        T.setOnItemSelectedListener(this);

        Spinner O = (Spinner)findViewById(R.id.onsdagspinner);
        String[] arraySpinner4 = new String[] {
                "val","Närvarande", "EjNärvarande"
        };

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner4);
        adapter4.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        O.setAdapter(adapter4);
        O.setOnItemSelectedListener(this);

        Spinner To = (Spinner)findViewById(R.id.torsdagspinner);
        String[] arraySpinner5 = new String[] {
                "val","Närvarande", "EjNärvarande"
        };

        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner5);
        adapter5.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        To.setAdapter(adapter5);
        To.setOnItemSelectedListener(this);

        Spinner F = (Spinner)findViewById(R.id.fredagspinner);
        String[] arraySpinner6 = new String[] {
                "val","Närvarande", "EjNärvarande"
        };

        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner6);
        adapter6.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        F.setAdapter(adapter6);
        F.setOnItemSelectedListener(this);

        method = "hämtadata";
        CalenderActivity.GetDataActivity GetDataActivity = new CalenderActivity.GetDataActivity(this);
        GetDataActivity.execute(method);


        method = "hämtadata";
        CalenderActivity.GetDataActivity2 GetDataActivity2 = new CalenderActivity.GetDataActivity2(this);
        GetDataActivity2.execute(method);

    }

    public void onItemSelected(AdapterView<?> parent, View v,
                               int pos, long id) {
        iMåndag = (ImageView) findViewById(R.id.MåndagView);
        iTisdag = (ImageView) findViewById(R.id.TisdagView);
        iOnsdag = (ImageView) findViewById(R.id.OnsdagView);
        iTorsdag = (ImageView) findViewById(R.id.TorsdagView);
        iFredag = (ImageView) findViewById(R.id.FredagView);

        if (parent.getId() == R.id.spinner2) {
            stWeek = (String) parent.getItemAtPosition(pos);

            method = "hämtadata";
            CalenderActivity.GetDataActivity1 GetDataActivity1 = new CalenderActivity.GetDataActivity1(this);
            GetDataActivity1.execute(method, stWeek);

        }

        else if (parent.getId() == R.id.måndagspinner) {

            stMonN = (String) parent.getItemAtPosition(pos);
            String DagarID = dMåndag.getText().toString();

            if (Objects.equals(stMonN, "Närvarande")) {

                iMåndag.setBackgroundColor(Color.GREEN);
                Tstartdag.setText(stMonN);

                test = Tstartdag.getText().toString();

                String method = "mataInData";
                CalenderActivity.SendDataActivity SendDataActivity = new CalenderActivity.SendDataActivity(this);
                SendDataActivity.execute(method, test, DagarID);


            }
            else if (Objects.equals(stMonN, "EjNärvarande")) {

                iMåndag.setBackgroundColor(Color.RED);
                Tstartdag.setText(stMonN);

                Tstartdag.setText(stMonN);

                test = Tstartdag.getText().toString();

                String method = "mataInData";
                CalenderActivity.SendDataActivity SendDataActivity = new CalenderActivity.SendDataActivity(this);
                SendDataActivity.execute(method, test, DagarID);

            }

        }

        else if (parent.getId() == R.id.tidagspinner) {

            stTisN = (String) parent.getItemAtPosition(pos);
            String DagarID = dTisdag.getText().toString();

            if (Objects.equals(stTisN, "Närvarande")) {

                iTisdag.setBackgroundColor(Color.GREEN);
                Tstartdag.setText(stTisN);

                test = Tstartdag.getText().toString();


                String method = "mataInData";
                CalenderActivity.SendDataActivity SendDataActivity = new CalenderActivity.SendDataActivity(this);
                SendDataActivity.execute(method, test, DagarID);

            }
            else if (Objects.equals(stTisN, "EjNärvarande")) {
                iTisdag.setBackgroundColor(Color.RED);
                Tstartdag.setText(stTisN);

                test = Tstartdag.getText().toString();


                String method = "mataInData";
                CalenderActivity.SendDataActivity SendDataActivity = new CalenderActivity.SendDataActivity(this);
                SendDataActivity.execute(method, test, DagarID);

            }
        }

        else if (parent.getId() == R.id.onsdagspinner) {

            stOnsN = (String) parent.getItemAtPosition(pos);
            String DagarID = dOnsdag.getText().toString();

            if (Objects.equals(stOnsN, "Närvarande")) {

                iOnsdag.setBackgroundColor(Color.GREEN);
                Tstartdag.setText(stOnsN);

                test = Tstartdag.getText().toString();


                String method = "mataInData";
                CalenderActivity.SendDataActivity SendDataActivity = new CalenderActivity.SendDataActivity(this);
                SendDataActivity.execute(method, test, DagarID);

            }
            else if (Objects.equals(stOnsN, "EjNärvarande")) {
                iOnsdag.setBackgroundColor(Color.RED);
                Tstartdag.setText(stOnsN);

                test = Tstartdag.getText().toString();


                String method = "mataInData";
                CalenderActivity.SendDataActivity SendDataActivity = new CalenderActivity.SendDataActivity(this);
                SendDataActivity.execute(method, test, DagarID);

            }
        }

        else if (parent.getId() == R.id.torsdagspinner) {

            stTorN = (String) parent.getItemAtPosition(pos);
            String DagarID = dTorsdag.getText().toString();

            if (Objects.equals(stTorN, "Närvarande")) {

                iTorsdag.setBackgroundColor(Color.GREEN);
                Tstartdag.setText(stTorN);

                test = Tstartdag.getText().toString();


                String method = "mataInData";
                CalenderActivity.SendDataActivity SendDataActivity = new CalenderActivity.SendDataActivity(this);
                SendDataActivity.execute(method, test, DagarID);

            }
            else if (Objects.equals(stTorN, "EjNärvarande")) {
                iTorsdag.setBackgroundColor(Color.RED);
                Tstartdag.setText(stTorN);

                test = Tstartdag.getText().toString();


                String method = "mataInData";
                CalenderActivity.SendDataActivity SendDataActivity = new CalenderActivity.SendDataActivity(this);
                SendDataActivity.execute(method, test, DagarID);

            }
        }

        else if (parent.getId() == R.id.fredagspinner) {

            stFreN = (String) parent.getItemAtPosition(pos);
            String DagarID = dFredag.getText().toString();

            if (Objects.equals(stFreN, "Närvarande")) {

                iFredag.setBackgroundColor(Color.GREEN);
                Tstartdag.setText(stFreN);

                test = Tstartdag.getText().toString();


                String method = "mataInData";
                CalenderActivity.SendDataActivity SendDataActivity = new CalenderActivity.SendDataActivity(this);
                SendDataActivity.execute(method, test, DagarID);

            }
            else if (Objects.equals(stFreN, "EjNärvarande")) {
                iFredag.setBackgroundColor(Color.RED);
                Tstartdag.setText(stFreN);

                test = Tstartdag.getText().toString();


                String method = "mataInData";
                CalenderActivity.SendDataActivity SendDataActivity = new CalenderActivity.SendDataActivity(this);
                SendDataActivity.execute(method, test, DagarID);

            }
        }

    }

    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void onClickClose(View view) {
        finish();
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

            String login_url = "http://10.0.2.2/APL-APP/APL_PHP/APL_ADMINGetAPLWeeks.php";
            String method = params[0];
            if (method.equals("hämtadata")) {

                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    //String data = URLEncoder.encode("Anvandarnamn", "UTF-8") + "=" + URLEncoder.encode(Anvandarnamn, "UTF-8");
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

                String startDag = null;
                try {
                    startDag = obj.getString("Week(startdag)");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String slutdag = null;
                try {
                    slutdag = obj.getString("Week(slutdag)");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Tstartdag.setText(startDag);
                Tslutdag.setText(slutdag);
            }

        }
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

            String login_url = "http://10.0.2.2/APL-APP/APL_PHP/APL_AdminGetNarvaroDaysFromWeek.php";
            String method = params[0];
            String stWeek = params[1];
            if (method.equals("hämtadata")) {

                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("week", "UTF-8") + "=" + URLEncoder.encode(stWeek, "UTF-8");
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


            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject obj = null;
                try {
                    obj = jsonarray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DagarID = null;
                try {
                    DagarID = obj.getString("DagarID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Datum = null;
                try {
                    Datum = obj.getString("Datum");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (i == 0){

                    tMåndag.setText("Måndag" + Datum);
                    dMåndag.setText("" + DagarID);

                }
                if(i == 1){
                    tTisdag.setText("Tisdag" + Datum);
                    dTisdag.setText("" + DagarID);
                }
                if(i == 2){
                    tOnsdag.setText("Onsdag" + Datum);
                    dOnsdag.setText("" + DagarID);
                }
                if(i == 3){
                    tTorsdag.setText("Torsdag" + Datum);
                    dTorsdag.setText("" + DagarID);
                }
                if(i == 4){
                    tFredag.setText("Fredag" + Datum);
                    dFredag.setText("" + DagarID);
                }


            }

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

            String login_url = "http://10.0.2.2/APL-APP/APL_PHP/APL_GetUsersFromNarvaro.php";
            String method = params[0];
            if (method.equals("hämtadata")) {

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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Fnamn = null;
                try {
                    Fnamn = obj.getString("Fnamn");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }
    }

    private class SendDataActivity extends AsyncTask<String, Void, String> {


        AlertDialog alertDialog;
        Context ctx;
        View v;

        SendDataActivity(Context ctx)

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

            String reg_url = "http://10.0.2.2/APL-APP/APL_PHP/APL_HandledareSetNarvaro.php";
            String method = params[0];
            String test = params[1];
            String DagarID = params[2];


            if (method.equals("mataInData")) {

                try {
                    URL url = new URL(reg_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    //httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("Narvarande", "UTF-8") + "=" + URLEncoder.encode(test, "UTF-8") + "&" +
                            URLEncoder.encode("DagarID", "UTF-8") + "=" + URLEncoder.encode(DagarID, "UTF-8");


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
