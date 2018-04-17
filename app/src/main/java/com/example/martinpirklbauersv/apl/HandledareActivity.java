package com.example.martinpirklbauersv.apl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class HandledareActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView tMåndag, tTisdag, tOnsdag, tTorsdag, tFredag;
    private TextView TWMåndag, TWTisdag, TWOnsdag, TWTorsdag, TWFredag;
    String sMåndag, sTisdag, sOnsdag, sTorsdag, sFredag;
    private TextView tRole, tUserID;
    private ImageView iMåndagEjNärvarande, iTisdagEjNärvarande, iOnsdagEjNärvarande, iTorsdagEjNärvarande, iFredagEjNärvarande;
    private ImageView iGreenChecked, iGreenUnChecked, iRedChecked, iRedUnChecked;
    private TextView CurrentTime;
    private ImageView iMåndagNärvarande, iTisdagNärvarande, iOnsdagNärvarande, iTorsdagNärvarande, iFredagNärvarande;
    String stWeek, stMonN, stTisN, stOnsN, stTorN, stFreN;
    String Narvarande, Datum, NarvaroRaknare;
    String Fnamn, Enamn, AnvandarID;
    String sFnamn, sEnamn;
    String method;
    String vecka;
    String CurrentDatum;
    String startDag, slutdag;
    String sAnvandarID;
    int UserID;
    Spinner U, s;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter7;
    ArrayList<Integer> IDList = new ArrayList<Integer>();
    ArrayList<String> fList = new ArrayList<String>();
    ArrayList<String> eList = new ArrayList<String>();

    ArrayList<String> antallist = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eList.add("Användare");
        antallist.add("Veckor");
        setContentView(R.layout.activity_handledare);

        tUserID = (TextView) findViewById(R.id.tUserID);
       // tRole = (TextView) findViewById(R.id.tRole);

        String sUserID = getIntent().getStringExtra("AnvandarID");
        String sUserRole = getIntent().getStringExtra("Role");


        TWMåndag = (TextView) findViewById(R.id.textView32);
        TWTisdag = (TextView) findViewById(R.id.textView31);
        TWOnsdag = (TextView) findViewById(R.id.textView30);
        TWTorsdag = (TextView) findViewById(R.id.textView29);
        TWFredag = (TextView) findViewById(R.id.textView28);

        CurrentTime = (TextView) findViewById(R.id.CurrentTime);
        tMåndag = (TextView) findViewById(R.id.textView12);
        tTisdag = (TextView) findViewById(R.id.textView13);
        tOnsdag = (TextView) findViewById(R.id.textView14);
        tTorsdag = (TextView) findViewById(R.id.textView16);
        tFredag = (TextView) findViewById(R.id.textView15);

        U = (Spinner) findViewById(R.id.UserSpinner);

        adapter7 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, eList);
        adapter7.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        U.setAdapter(adapter7);
        U.setOnItemSelectedListener(this);

        s = (Spinner) findViewById(R.id.spinner2);

        adapter = new ArrayAdapter<String>(this,
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

        GregorianCalendar gr =new GregorianCalendar();
        vecka = String.valueOf(gr.get(Calendar.WEEK_OF_YEAR));

        method = "hämtaNuvarandeÅrMånadDag";
        HandledareActivity.GetNuvarandeYearMonthDayActivity GetNuvarandeYearMonthDayActivity = new HandledareActivity.GetNuvarandeYearMonthDayActivity(this);
        GetNuvarandeYearMonthDayActivity.execute(method);

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

        iGreenChecked = (ImageView) findViewById(R.id.GreenChecked);
        iGreenUnChecked = (ImageView) findViewById(R.id.GreenUnChecked);
        iRedChecked = (ImageView) findViewById(R.id.RedChecked);
        iRedUnChecked = (ImageView) findViewById(R.id.RedUnChecked);

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

        iGreenChecked.setEnabled(false);
        iGreenUnChecked.setEnabled(false);
        iRedChecked.setEnabled(false);
        iRedUnChecked.setEnabled(false);
    }

    public void onItemSelected(AdapterView<?> parent, View v,
                               int pos, long id) {


        if (parent.getId() == R.id.UserSpinner) {


            sEnamn = (String) parent.getItemAtPosition(pos);

                if (!sEnamn.equals("Användare")) {
                    UserID = IDList.get(pos - 1);
                    s.setEnabled(true);
            }

            method = "hämtadata";
            HandledareActivity.GetAPLWeeksActivity GetAPLWeeksActivity = new HandledareActivity.GetAPLWeeksActivity(this);
            GetAPLWeeksActivity.execute(method, "" + UserID);

            try {
                method = "hämtadata";
                HandledareActivity.GetDaysFromWeekActivity GetDaysFromWeekActivity = new HandledareActivity.GetDaysFromWeekActivity(this);
                GetDaysFromWeekActivity.execute(method, vecka, "" + UserID);
            } catch (Exception e) {

            }

        }

        if (parent.getId() == R.id.spinner2) {
            stWeek = (String) parent.getItemAtPosition(pos);

Log.d("veckospinner", "1" + stWeek);
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


                iGreenUnChecked.setEnabled(true);
                iRedUnChecked.setEnabled(true);
                iRedChecked.setVisibility(View.INVISIBLE);
                iGreenChecked.setVisibility(View.INVISIBLE);

            }

            try {
                method = "hämtadata";
                HandledareActivity.GetDaysFromWeekActivity GetDaysFromWeekActivity = new HandledareActivity.GetDaysFromWeekActivity(this);
                GetDaysFromWeekActivity.execute(method, stWeek, "" + UserID);
            } catch (Exception e) {

            }

        }

    }
/*
    @Override
    public void onRestart(){

        super.onRestart();

    }
*/
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void  clickEventMåndagGreenChecked(View v)
    {
        iGreenChecked.setVisibility(View.VISIBLE);
        iGreenUnChecked.setVisibility(View.INVISIBLE);
        iRedChecked.setVisibility(View.INVISIBLE);
        iRedUnChecked.setVisibility(View.VISIBLE);

        String NarvaroRaknare = sMåndag;
        stMonN = "Närvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stMonN, NarvaroRaknare, sEnamn);

    }

    public void clickEventMåndagRedChecked(View v)
    {

        iRedChecked.setVisibility(View.VISIBLE);
        iRedUnChecked.setVisibility(View.INVISIBLE);
        iGreenChecked.setVisibility(View.INVISIBLE);
        iGreenUnChecked.setVisibility(View.VISIBLE);
        String NarvaroRaknare = sMåndag;
        stMonN = "EjNärvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stMonN, NarvaroRaknare, sEnamn);

    }

    public void clickEventMondagEjNärvarande(View v)
    {
        iMåndagEjNärvarande.setBackgroundColor(Color.RED);
        iMåndagNärvarande.setBackgroundColor(Color.GRAY);


        String NarvaroRaknare = sMåndag;
        stMonN = "EjNärvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stMonN, NarvaroRaknare, sEnamn);

    }

    public void clickEventMondagNärvarande (View v)
    {
        iMåndagNärvarande.setBackgroundColor(Color.GREEN);
        iMåndagEjNärvarande.setBackgroundColor(Color.GRAY);
        String NarvaroRaknare = sMåndag;
        stMonN = "Närvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stMonN, NarvaroRaknare, sEnamn);
    }

    public void clickEventTisdagEjNärvarande(View v)
    {
        iTisdagEjNärvarande.setBackgroundColor(Color.RED);
        iTisdagNärvarande.setBackgroundColor(Color.GRAY);

        String NarvaroRaknare = sTisdag;
        stTisN = "EjNärvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stTisN, NarvaroRaknare, sEnamn);

    }

    public void clickEventTisdagNärvarande (View v)
    {
        iTisdagNärvarande.setBackgroundColor(Color.GREEN);
        iTisdagEjNärvarande.setBackgroundColor(Color.GRAY);
        String NarvaroRaknare = sTisdag;
        stTisN = "Närvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stTisN, NarvaroRaknare, sEnamn);
    }

    public void clickEventOnsdagEjNärvarande(View v)
    {
        iOnsdagEjNärvarande.setBackgroundColor(Color.RED);
        iOnsdagNärvarande.setBackgroundColor(Color.GRAY);

        String NarvaroRaknare = sOnsdag;
        stOnsN = "EjNärvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stOnsN, NarvaroRaknare, sEnamn);

    }

    public void clickEventOnsdagNärvarande (View v)
    {
        iOnsdagNärvarande.setBackgroundColor(Color.GREEN);
        iOnsdagEjNärvarande.setBackgroundColor(Color.GRAY);
        String NarvaroRaknare = sOnsdag;
        stOnsN = "Närvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stOnsN, NarvaroRaknare, sEnamn);
    }

    public void clickEventTorsdagEjNärvarande(View v)
    {
        iTorsdagEjNärvarande.setBackgroundColor(Color.RED);
        iTorsdagNärvarande.setBackgroundColor(Color.GRAY);

        String NarvaroRaknare = sTorsdag;
        stTorN = "EjNärvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stTorN, NarvaroRaknare, sEnamn);

    }

    public void clickEventTorsdagNärvarande (View v)
    {
        iTorsdagNärvarande.setBackgroundColor(Color.GREEN);
        iTorsdagEjNärvarande.setBackgroundColor(Color.GRAY);
        String NarvaroRaknare = sTorsdag;
        stTorN = "Närvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stTorN, NarvaroRaknare, sEnamn);
    }

    public void clickEventFredagEjNärvarande(View v)
    {
        iFredagEjNärvarande.setBackgroundColor(Color.RED);
        iFredagNärvarande.setBackgroundColor(Color.GRAY);

        String NarvaroRaknare = sFredag;
        stFreN = "EjNärvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stFreN, NarvaroRaknare, sEnamn);

    }

    public void clickEventFredagNärvarande (View v)
    {
        iFredagNärvarande.setBackgroundColor(Color.GREEN);
        iFredagEjNärvarande.setBackgroundColor(Color.GRAY);
        String NarvaroRaknare = sFredag;
        stFreN = "Närvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stFreN, NarvaroRaknare, sEnamn);
    }

    public void onClickClose(View view) {
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class GetNuvarandeYearMonthDayActivity extends AsyncTask<String, Void, String> {


        AlertDialog alertDialog;
        Context ctx;
        View v;

        GetNuvarandeYearMonthDayActivity(Context ctx)

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

            String login_url = "http://"+getResources().getString(R.string.ip) +"/APL-APP/APL_PHP/APL_GETCurrentYearMonthDay.php";
            String method = params[0];
            if (method.equals("hämtaNuvarandeÅrMånadDag")) {

                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    //String data = URLEncoder.encode("AnvandarID", "UTF-8") + "=" + URLEncoder.encode(UserID, "UTF-8");
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

                CurrentDatum = null;
                try {
                    CurrentDatum = obj.getString("Datum");
                    Log.d("hämtatime", "1" + CurrentDatum );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            CurrentTime.setText("" + CurrentDatum);



        }
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
                        startDag = obj.getString("Week(startdag, 3)");
                        Log.d("startvecka", "1" + startDag);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    slutdag = null;
                    try {
                        slutdag = obj.getString("Week(slutdag, 3)");
                        Log.d("startvecka", "2" + slutdag);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                int start = Integer.parseInt(startDag);
                int slut = Integer.parseInt(slutdag);


                antallist.clear();
                antallist.add("Veckor");


                for (int vecka = start, i = 0; vecka < slut+1; vecka++, i++) {
                    antallist.add(String.valueOf(vecka));
                }


            int j = 0;

              while (j < antallist.size()){

                  if (Objects.equals(antallist.get(j), vecka)) {
                    s.setSelection(j);
                    break;
                }
                  j++;
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
                    Log.d("veckocheck","1" + stWeek);
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

                tMåndag.setText("Måndag ");
                tTisdag.setText("Tisdag ");
                tOnsdag.setText("Onsdag ");
                tTorsdag.setText("Torsdag ");
                tFredag.setText("Fredag ");


                tMåndag.setBackgroundColor(Color.WHITE);
                tTisdag.setBackgroundColor(Color.WHITE);
                tOnsdag.setBackgroundColor(Color.WHITE);
                tTorsdag.setBackgroundColor(Color.WHITE);
                tFredag.setBackgroundColor(Color.WHITE);


                iMåndagNärvarande.setBackgroundColor(Color.GRAY);
                iMåndagEjNärvarande.setBackgroundColor(Color.GRAY);

                iTisdagNärvarande.setBackgroundColor(Color.GRAY);
                iTisdagEjNärvarande.setBackgroundColor(Color.GRAY);

                iOnsdagNärvarande.setBackgroundColor(Color.GRAY);
                iOnsdagEjNärvarande.setBackgroundColor(Color.GRAY);

                iTorsdagNärvarande.setBackgroundColor(Color.GRAY);
                iTorsdagEjNärvarande.setBackgroundColor(Color.GRAY);

                iFredagNärvarande.setBackgroundColor(Color.GRAY);
                iFredagEjNärvarande.setBackgroundColor(Color.GRAY);

                iMåndagNärvarande.setBackgroundColor(View.INVISIBLE);
                iMåndagEjNärvarande.setBackgroundColor(View.INVISIBLE);

                iTisdagNärvarande.setBackgroundColor(View.INVISIBLE);
                iTisdagEjNärvarande.setBackgroundColor(View.INVISIBLE);

                iOnsdagNärvarande.setBackgroundColor(View.INVISIBLE);
                iOnsdagEjNärvarande.setBackgroundColor(View.INVISIBLE);

                iTorsdagNärvarande.setBackgroundColor(View.INVISIBLE);
                iTorsdagEjNärvarande.setBackgroundColor(View.INVISIBLE);

                iFredagNärvarande.setBackgroundColor(View.INVISIBLE);
                iFredagEjNärvarande.setBackgroundColor(View.INVISIBLE);

                TWMåndag.setText("Ingen Närvaro Krävs");
                TWTisdag.setText("Ingen Närvaro Krävs");
                TWOnsdag.setText("Ingen Närvaro Krävs");
                TWTorsdag.setText("Ingen Närvaro Krävs");
                TWFredag.setText("Ingen Närvaro Krävs");

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
                        Datum = obj.getString("Datumm");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Narvarande = null;
                    try {
                        Narvarande = obj.getString("Narvarande");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if (i == 0) {

                        tMåndag.setText("Måndag " + Datum);
                        sMåndag = NarvaroRaknare;
                        TWMåndag.setText("");


                        if (Objects.equals(CurrentDatum, Datum )) {

                            tMåndag.setBackgroundColor(Color.GREEN);

                        }

                        if (Objects.equals(Narvarande, "1")) {
                            iMåndagNärvarande.setBackgroundColor(Color.GREEN);
                            iMåndagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "0")) {
                            iMåndagEjNärvarande.setBackgroundColor(Color.RED);
                            iMåndagNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "null")) {
                            iMåndagNärvarande.setBackgroundColor(Color.GRAY);
                            iMåndagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }


                    }
                    if (i == 1) {
                        tTisdag.setText("Tisdag " + Datum);
                        sTisdag = NarvaroRaknare;
                        TWTisdag.setText("");

                        if (Objects.equals(CurrentDatum, Datum )) {

                            tTisdag.setBackgroundColor(Color.GREEN);

                        }

                        if (Objects.equals(Narvarande, "1")) {
                            iTisdagNärvarande.setBackgroundColor(Color.GREEN);
                            iTisdagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "0")) {
                            iTisdagEjNärvarande.setBackgroundColor(Color.RED);
                            iTisdagNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "null")) {
                            iTisdagNärvarande.setBackgroundColor(Color.GRAY);
                            iTisdagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                    }
                    if (i == 2) {
                        tOnsdag.setText("Onsdag " + Datum);
                        sOnsdag = NarvaroRaknare;
                        TWOnsdag.setText("");

                        if (Objects.equals(CurrentDatum, Datum )) {

                            tOnsdag.setBackgroundColor(Color.GREEN);

                        }

                        if (Objects.equals(Narvarande, "1")) {
                            iOnsdagNärvarande.setBackgroundColor(Color.GREEN);
                            iOnsdagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "0")) {
                            iOnsdagEjNärvarande.setBackgroundColor(Color.RED);
                            iOnsdagNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "null")) {
                            iOnsdagNärvarande.setBackgroundColor(Color.GRAY);
                            iOnsdagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                    }
                    if (i == 3) {
                        tTorsdag.setText("Torsdag " + Datum);
                        sTorsdag = NarvaroRaknare;
                        TWTorsdag.setText("");

                        if (Objects.equals(CurrentDatum, Datum )) {

                            tTorsdag.setBackgroundColor(Color.GREEN);

                        }

                        if (Objects.equals(Narvarande, "1")) {
                            iTorsdagNärvarande.setBackgroundColor(Color.GREEN);
                            iTorsdagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "0")) {
                            iTorsdagEjNärvarande.setBackgroundColor(Color.RED);
                            iTorsdagNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "null")) {
                            iTorsdagNärvarande.setBackgroundColor(Color.GRAY);
                            iTorsdagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                    }
                    if (i == 4) {
                        tFredag.setText("Fredag " + Datum);
                        sFredag = NarvaroRaknare;
                        TWFredag.setText("");

                        if (Objects.equals(CurrentDatum, Datum )) {

                            tFredag.setBackgroundColor(Color.GREEN);

                        }

                        if (Objects.equals(Narvarande, "1")) {
                            iFredagNärvarande.setBackgroundColor(Color.GREEN);
                            iFredagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "0")) {
                            iFredagEjNärvarande.setBackgroundColor(Color.RED);
                            iFredagNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "null")) {

                            iFredagNärvarande.setBackgroundColor(Color.GRAY);
                            iFredagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
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
            U.setSelection(1);

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