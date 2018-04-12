package com.example.martinpirklbauersv.apl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import java.util.ArrayList;

public class HandledareActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView Tstartdag;
    private TextView Tslutdag;
    private TextView dMåndag, dTisdag, dOnsdag, dTorsdag, dFredag;
    private TextView tMåndag, tTisdag, tOnsdag, tTorsdag, tFredag;
    private TextView tRole, tUserID;
    private ImageView iMåndagEjNärvarande, iTisdagEjNärvarande, iOnsdagEjNärvarande, iTorsdagEjNärvarande, iFredagEjNärvarande;
    private ImageView iMåndagNärvarande, iTisdagNärvarande, iOnsdagNärvarande, iTorsdagNärvarande, iFredagNärvarande;
    String stWeek, stMonN, stTisN, stOnsN, stTorN, stFreN;
    String Datum, NarvaroRaknare;
    String Fnamn, Enamn, AnvandarID;
    String sFnamn, sEnman;
    String method;
    String startDag, slutdag;
    String sAnvandarID;
    int UserID;
    Spinner U, s;

    ArrayAdapter<String> adapter7;
    ArrayList<Integer> IDList = new ArrayList<Integer>();
    ArrayList<String> fList = new ArrayList<String>();
    ArrayList<String> eList = new ArrayList<String>();

    ArrayList<String> antallist = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fList.add("Användare");
        antallist.add("Veckor");
        setContentView(R.layout.activity_handledare);

        tUserID = (TextView) findViewById(R.id.tUserID);
        tRole = (TextView) findViewById(R.id.tRole);

        String sUserID = getIntent().getStringExtra("AnvandarID");
        String sUserRole = getIntent().getStringExtra("Role");
        Log.d("asd1" , "1" + sUserRole);

        if (sUserRole == "2"){

            tRole.setText("Handledare");
        }

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


        U = (Spinner) findViewById(R.id.UserSpinner);

        adapter7 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, fList);
        adapter7.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        //U.setEnabled(false);
        U.setAdapter(adapter7);
        U.setOnItemSelectedListener(this);


        s = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, antallist);
        adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        s.setEnabled(false);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);

        method = "hämtaDataOmLoginUser";
        HandledareActivity.GetUsersWithLoginInfoActivity GetUsersWithLoginInfoActivity = new HandledareActivity.GetUsersWithLoginInfoActivity(this);
        GetUsersWithLoginInfoActivity.execute(method, sUserID);

        method = "hämtaUserID";
        HandledareActivity.GetUsersFromIDActivity GetUsersFromIDActivity = new HandledareActivity.GetUsersFromIDActivity(this);
        GetUsersFromIDActivity.execute(method);

        iMåndagEjNärvarande = (ImageView) findViewById(R.id.MåndagEjNärvarandeView);
        iMåndagNärvarande = (ImageView) findViewById(R.id.måndagNärvarandeView);
        iTisdagEjNärvarande = (ImageView) findViewById(R.id.TisdagEjNärvarandeView);
        iTisdagNärvarande = (ImageView) findViewById(R.id.tisdagNärvarandeView);
        iOnsdagEjNärvarande = (ImageView) findViewById(R.id.OnsdagEjNärvarandeView);
        iOnsdagNärvarande = (ImageView) findViewById(R.id.onsdagNärvarandeView);
        iTorsdagEjNärvarande = (ImageView) findViewById(R.id.TorsdagEjNärvarandeView);
        iTorsdagNärvarande = (ImageView) findViewById(R.id.torsdagNärvarandeView);
        iFredagEjNärvarande = (ImageView) findViewById(R.id.FredagEjNärvarandeView);
        iFredagNärvarande = (ImageView) findViewById(R.id.FredagNärvarandeView);

        iMåndagEjNärvarande.setEnabled(false);
        iMåndagNärvarande.setEnabled(false);
        iTisdagEjNärvarande.setEnabled(false);
        iTisdagNärvarande.setEnabled(false);
        iOnsdagEjNärvarande.setEnabled(false);
        iOnsdagNärvarande.setEnabled(false);
        iTorsdagEjNärvarande.setEnabled(false);
        iTorsdagNärvarande.setEnabled(false);
        iFredagEjNärvarande.setEnabled(false);
        iFredagNärvarande.setEnabled(false);
    }

    public void onItemSelected(AdapterView<?> parent, View v,
                               int pos, long id) {


        if (parent.getId() == R.id.UserSpinner) {

            sFnamn = (String) parent.getItemAtPosition(pos);
            //s.setEnabled(true);
            if (!sFnamn.equals("Användare")) {
                UserID = IDList.get(pos - 1);
                s.setEnabled(true);
            }

            method = "hämtadata";
            HandledareActivity.GetAPLWeeksActivity GetAPLWeeksActivity = new HandledareActivity.GetAPLWeeksActivity(this);
            GetAPLWeeksActivity.execute(method, "" + UserID);

        }

        if (parent.getId() == R.id.spinner2) {
            stWeek = (String) parent.getItemAtPosition(pos);


            if (!stWeek.equals("Veckor")) {

                iMåndagEjNärvarande.setEnabled(true);
                iMåndagNärvarande.setEnabled(true);
                iTisdagEjNärvarande.setEnabled(true);
                iTisdagNärvarande.setEnabled(true);
                iOnsdagEjNärvarande.setEnabled(true);
                iOnsdagNärvarande.setEnabled(true);
                iTorsdagEjNärvarande.setEnabled(true);
                iTorsdagNärvarande.setEnabled(true);
                iFredagEjNärvarande.setEnabled(true);
                iFredagNärvarande.setEnabled(true);

            }

            try {
                method = "hämtadata";
                HandledareActivity.GetDaysFromWeekActivity GetDaysFromWeekActivity = new HandledareActivity.GetDaysFromWeekActivity(this);
                GetDaysFromWeekActivity.execute(method, stWeek, "" + UserID);
            } catch (Exception e) {

            }

        }

    }

    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void clickEventMondagEjNärvarande(View v)
    {
        iMåndagEjNärvarande.setBackgroundColor(Color.RED);
        iMåndagNärvarande.setBackgroundColor(Color.rgb(40,75,17));


        String NarvaroRaknare = dMåndag.getText().toString();
        stMonN = "EjNärvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stMonN, NarvaroRaknare, sFnamn);

    }

    public void clickEventMondagNärvarande (View v)
    {
        iMåndagNärvarande.setBackgroundColor(Color.GREEN);
        iMåndagEjNärvarande.setBackgroundColor(Color.rgb(156,5,17));
        String NarvaroRaknare = dMåndag.getText().toString();
        stMonN = "Närvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stMonN, NarvaroRaknare, sFnamn);
    }

    public void clickEventTisdagEjNärvarande(View v)
    {
        iTisdagEjNärvarande.setBackgroundColor(Color.RED);
        iTisdagNärvarande.setBackgroundColor(Color.GRAY);

        String NarvaroRaknare = dTisdag.getText().toString();
        stTisN = "EjNärvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stTisN, NarvaroRaknare, sFnamn);

    }

    public void clickEventTisdagNärvarande (View v)
    {
        iTisdagNärvarande.setBackgroundColor(Color.GREEN);
        iTisdagEjNärvarande.setBackgroundColor(Color.GRAY);
        String NarvaroRaknare = dTisdag.getText().toString();
        stTisN = "Närvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stTisN, NarvaroRaknare, sFnamn);
    }

    public void clickEventOnsdagEjNärvarande(View v)
    {
        iOnsdagEjNärvarande.setBackgroundColor(Color.RED);
        iOnsdagNärvarande.setBackgroundColor(Color.GRAY);

        String NarvaroRaknare = dOnsdag.getText().toString();
        stOnsN = "EjNärvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stOnsN, NarvaroRaknare, sFnamn);

    }

    public void clickEventOnsdagNärvarande (View v)
    {
        iOnsdagNärvarande.setBackgroundColor(Color.GREEN);
        iOnsdagEjNärvarande.setBackgroundColor(Color.GRAY);
        String NarvaroRaknare = dOnsdag.getText().toString();
        stOnsN = "Närvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stOnsN, NarvaroRaknare, sFnamn);
    }

    public void clickEventTorsdagEjNärvarande(View v)
    {
        iTorsdagEjNärvarande.setBackgroundColor(Color.RED);
        iTorsdagNärvarande.setBackgroundColor(Color.GRAY);

        String NarvaroRaknare = dTorsdag.getText().toString();
        stTorN = "EjNärvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stTorN, NarvaroRaknare, sFnamn);

    }

    public void clickEventTorsdagNärvarande (View v)
    {
        iTorsdagNärvarande.setBackgroundColor(Color.GREEN);
        iTorsdagEjNärvarande.setBackgroundColor(Color.GRAY);
        String NarvaroRaknare = dTorsdag.getText().toString();
        stTorN = "Närvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stTorN, NarvaroRaknare, sFnamn);
    }

    public void clickEventFredagEjNärvarande(View v)
    {
        iFredagEjNärvarande.setBackgroundColor(Color.RED);
        iFredagNärvarande.setBackgroundColor(Color.GRAY);

        String NarvaroRaknare = dFredag.getText().toString();
        stFreN = "EjNärvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stFreN, NarvaroRaknare, sFnamn);

    }

    public void clickEventFredagNärvarande (View v)
    {
        iFredagNärvarande.setBackgroundColor(Color.GREEN);
        iFredagEjNärvarande.setBackgroundColor(Color.GRAY);
        String NarvaroRaknare = dFredag.getText().toString();
        stFreN = "Närvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stFreN, NarvaroRaknare, sFnamn);
    }



    public void onClickClose(View view) {
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private class GetAPLWeeksActivity extends AsyncTask<String, Void, String> {


        AlertDialog alertDialog;
        Context ctx;
        View v;

        GetAPLWeeksActivity(Context ctx)

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

            String login_url = "http://"+getResources().getString(R.string.ip) +"/APL-APP/APL_PHP/APL_ADMINGetAPLWeeks.php";
            String method = params[0];
            String UserID = params[1];
            if (method.equals("hämtadata")) {

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

                    startDag = null;
                    try {
                        startDag = obj.getString("Week(startdag)");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    slutdag = null;
                    try {
                        slutdag = obj.getString("Week(slutdag)");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Tstartdag.setText(startDag);
                    Tslutdag.setText(slutdag);
                }

                int start = Integer.parseInt(startDag);
                int slut = Integer.parseInt(slutdag);

                // int antal = (slut - start) + 1;


                for (int vecka = start, i = 0; vecka < slut+1; vecka++, i++) {
                    antallist.add(String.valueOf(vecka));
                    //  arraySpinner1[i] = "" + vecka;
                }
            }catch (Exception w){

            }
        }
    }
    private class GetDaysFromWeekActivity extends AsyncTask<String, Void, String> {


        AlertDialog alertDialog;
        Context ctx;
        View v;

        GetDaysFromWeekActivity(Context ctx)

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

            String login_url = "http://"+getResources().getString(R.string.ip) +"/APL-APP/APL_PHP/APL_AdminGetNarvaroDaysFromWeek.php";
            String method = params[0];
            String stWeek = params[1];
            String UserID = params[2];
            if (method.equals("hämtadata")) {

                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("week", "UTF-8") + "=" + URLEncoder.encode(stWeek, "UTF-8") + "&" +
                            URLEncoder.encode("AnvandarID", "UTF-8") + "=" + URLEncoder.encode(UserID, "UTF-8");
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
                //    e.printStackTrace();
            }

            try {
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject obj = null;
                    try {
                        obj = jsonarray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    NarvaroRaknare = null;
                    try {
                        NarvaroRaknare = obj.getString("NarvaroRaknare");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Datum = null;
                    try {
                        Datum = obj.getString("Datum");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if (i == 0) {
                        tMåndag.setText("Måndag" + Datum);
                        dMåndag.setText("" + NarvaroRaknare);

                    }
                    if (i == 1) {
                        tTisdag.setText("Tisdag" + Datum);
                        dTisdag.setText("" + NarvaroRaknare);
                    }
                    if (i == 2) {
                        tOnsdag.setText("Onsdag" + Datum);
                        dOnsdag.setText("" + NarvaroRaknare);
                    }
                    if (i == 3) {
                        tTorsdag.setText("Torsdag" + Datum);
                        dTorsdag.setText("" + NarvaroRaknare);
                    }
                    if (i == 4) {
                        tFredag.setText("Fredag" + Datum);
                        dFredag.setText("" + NarvaroRaknare);
                    }


                }
            }
            catch (Exception r){

            }

        }
    }

    private class GetUsersFromIDActivity extends AsyncTask<String, Void, String> {


        AlertDialog alertDialog;
        Context ctx;
        View v;

        GetUsersFromIDActivity(Context ctx)

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
            String login_url = "http://"+getResources().getString(R.string.ip) +"/APL-APP/APL_PHP/APL_HandledareListUsers.php";

            String method = params[0];
            if (method.equals("hämtaUserID")) {

                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    //String data = URLEncoder.encode("KlassID", "UTF-8") + "=" + URLEncoder.encode(KlassID, "UTF-8");
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

            try{
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject obj = null;
                    try {
                        obj = jsonarray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    sAnvandarID = null;
                    try {
                        sAnvandarID = obj.getString("AnvandarID");
                        method = "hämtaUserdata";
                        HandledareActivity.GetUsersWithNarvaroActivity GetUsersWithNarvaroActivity = new HandledareActivity.GetUsersWithNarvaroActivity(this);
                        GetUsersWithNarvaroActivity.execute(method, sAnvandarID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }catch (Exception k) {

            }

        }
    }

    private class GetUsersWithNarvaroActivity extends AsyncTask<String, Void, String> {


        AlertDialog alertDialog;
        GetUsersFromIDActivity ctx;
        View v;

        GetUsersWithNarvaroActivity(GetUsersFromIDActivity ctx)

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
            String login_url = "http://"+getResources().getString(R.string.ip) +"/APL-APP/APL_PHP/APL_HandledareGetUserDataFromId.php";

            String method = params[0];
            String sAnvandarID = params[1];
            if (method.equals("hämtaUserdata")) {

                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("AnvandarID", "UTF-8") + "=" + URLEncoder.encode(sAnvandarID, "UTF-8");
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

            try{
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
            }catch (Exception k) {

            }
            Spinner U = (Spinner)findViewById(R.id.UserSpinner);
            U.invalidate();
            U.setSelection(0);


        }
    }

    private class GetUsersWithLoginInfoActivity extends AsyncTask<String, Void, String> {


        AlertDialog alertDialog;
        Context ctx;
        View v;

        GetUsersWithLoginInfoActivity(Context ctx)

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
            String login_url = "http://"+getResources().getString(R.string.ip) +"/APL-APP/APL_PHP/APL_GetDataAboutUser.php";

            String method = params[0];
            String sUserID = params[1];
            if (method.equals("hämtaDataOmLoginUser")) {

                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("Anvandarnamn", "UTF-8") + "=" + URLEncoder.encode(sUserID, "UTF-8");
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

            try{
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
                        Log.d("asd", "1" + Fnamn);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }catch (Exception k) {
            }
            tUserID.setText("" + Fnamn + " " + Enamn);

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

            String reg_url = "http://"+getResources().getString(R.string.ip) +"/APL-APP/APL_PHP/APL_HandledareSetNarvaro.php";
            String method = params[0];
            String Veckodag = params[1];
            String NarvaroRaknare = params[2];

            if (method.equals("mataInData")) {

                try {
                    URL url = new URL(reg_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    //httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("Narvarande", "UTF-8") + "=" + URLEncoder.encode(Veckodag, "UTF-8") + "&" +
                            URLEncoder.encode("NarvaroRaknare", "UTF-8") + "=" + URLEncoder.encode(NarvaroRaknare, "UTF-8");

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