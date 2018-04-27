package com.example.martinpirklbauersv.apl;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/*
xml fil för denna aktivitet är "activity_handledare"
Beskriving:
Här har vi en funktion för handledaren att sätta närvaro på elever.

Hit kommer handledaren efter han loggar in via loginsidan(LoginActivity).
Här finns det 2 olika spinners, en för elever och en för veckor.
Det finns även Dagarna upplistade från måndag til fredag med en knapp
för "ja" och en för "nej" för varjje veckodag.

Handledaren kan välja mellan elever och veckor och välja
med hjälp av knapparna om eleven är närvarande eller inte


Klasser:
alla klasser använder ip ifrån "strings.xml"
all indata tas emot som jsonsträngar som jag lägger in i arrayer.
all utdata skickas som strängar

GetUsersWithLoginInfoActivity

Beskrivning:
hämtar information om användaren som loggade in

IN:
användarnamn från den som loggade in

UT:
för och efternamn.

GetNuvarandeYearMonthDayActivity
Beskrivning:
hämtar nuvarande år, månad och dag

IN:
ingen indata.

UT:
Nuvarande år månad och dag

GetUsersFromIDActivity
Beskrivning:
hämtar alla användare som har närvaro på samma arbetsplats som deras handledare

IN:
ingen indata

UT:
användarID på alla användare

GetAPLWeeksActivity
Beskrivning:
hämtar veckorna som ingår i ens apl-period

IN:
användarID på den valda person från spinner

UT:
APL veckor som personen är registrerad på

GetDaysFromWeekActivity
Beskrivning:
hämtar dagarna från vald vecka och elev

IN;
användarID på den valda eleven från spinner och vecka från antigen nuvarande vecka eller från valt item från spinner

UT:
datum på de olika veckodagarna.
närvarocheck på de olika veckodagarna beroende på användareID
närvaroID på de olika veckodagarna beroende på användareID

SendDataActivity
Beskrivning:
skickar närvaro status om vald elev och dag

IN:
 stMonN status på närvaro om man är närvarande eller frånvarande och vilken veckodag
 NarvaroRaknare beroende vilken dag
 sEnamn: namn på personen som är vald från spinner

UT:
ingen utdata


*/

public class HandledareActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView tMåndag, tTisdag, tOnsdag, tTorsdag, tFredag;
    private TextView TWMåndag, TWTisdag, TWOnsdag, TWTorsdag, TWFredag;
    String sMåndag, sTisdag, sOnsdag, sTorsdag, sFredag;
    private TextView tRole, tUserID;

    private ImageView iMonGreenChecked, iMonGreenUnChecked, iMonRedChecked, iMonRedUnChecked;
    private ImageView iTisGreenChecked, iTisGreenUnChecked, iTisRedChecked, iTisRedUnChecked;
    private ImageView iOnsGreenChecked, iOnsGreenUnChecked, iOnsRedChecked, iOnsRedUnChecked;
    private ImageView iTorGreenChecked, iTorGreenUnChecked, iTorRedChecked, iTorRedUnChecked;
    private ImageView iFreGreenChecked, iFreGreenUnChecked, iFreRedChecked, iFreRedUnChecked;

    private TextView CurrentTime;

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
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        U.setAdapter(adapter7);
        U.setOnItemSelectedListener(this);

        s = (Spinner) findViewById(R.id.spinner2);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, antallist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

        iMonGreenChecked = (ImageView) findViewById(R.id.MGreenChecked);
        iMonGreenUnChecked = (ImageView) findViewById(R.id.MGreenUnChecked);
        iMonRedChecked = (ImageView) findViewById(R.id.MRedChecked);
        iMonRedUnChecked = (ImageView) findViewById(R.id.MRedUnChecked);

        iTisGreenChecked = (ImageView) findViewById(R.id.TGreenChecked);
        iTisGreenUnChecked = (ImageView) findViewById(R.id.TGreenUnChecked);
        iTisRedChecked = (ImageView) findViewById(R.id.TRedChecked);
        iTisRedUnChecked = (ImageView) findViewById(R.id.TRedUnChecked);

        iOnsGreenChecked = (ImageView) findViewById(R.id.OGreenChecked);
        iOnsGreenUnChecked = (ImageView) findViewById(R.id.OGreenUnChecked);
        iOnsRedChecked = (ImageView) findViewById(R.id.ORedChecked);
        iOnsRedUnChecked = (ImageView) findViewById(R.id.ORedUnChecked);

        iTorGreenChecked = (ImageView) findViewById(R.id.ToGreenChecked);
        iTorGreenUnChecked = (ImageView) findViewById(R.id.ToGreenUnChecked);
        iTorRedChecked = (ImageView) findViewById(R.id.ToRedChecked);
        iTorRedUnChecked = (ImageView) findViewById(R.id.ToRedUnChecked);

        iFreGreenChecked = (ImageView) findViewById(R.id.FGreenChecked);
        iFreGreenUnChecked = (ImageView) findViewById(R.id.FGreenUnChecked);
        iFreRedChecked = (ImageView) findViewById(R.id.FRedChecked);
        iFreRedUnChecked = (ImageView) findViewById(R.id.FRedUnChecked);

        iMonGreenChecked.setEnabled(false);
        iMonGreenUnChecked.setEnabled(false);
        iMonRedChecked.setEnabled(false);
        iMonRedUnChecked.setEnabled(false);

        iTisGreenChecked.setEnabled(false);
        iTisGreenUnChecked.setEnabled(false);
        iTisRedChecked.setEnabled(false);
        iTisRedUnChecked.setEnabled(false);

        iOnsGreenChecked.setEnabled(false);
        iOnsGreenUnChecked.setEnabled(false);
        iOnsRedChecked.setEnabled(false);
        iOnsRedUnChecked.setEnabled(false);

        iTorGreenChecked.setEnabled(false);
        iTorGreenUnChecked.setEnabled(false);
        iTorRedChecked.setEnabled(false);
        iTorRedUnChecked.setEnabled(false);

        iFreGreenChecked.setEnabled(false);
        iFreGreenUnChecked.setEnabled(false);
        iFreRedChecked.setEnabled(false);
        iFreRedUnChecked.setEnabled(false);
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

            if (!stWeek.equals("Veckor")) {

                iMonGreenUnChecked.setEnabled(true);
                iMonRedUnChecked.setEnabled(true);
                iMonRedChecked.setVisibility(View.INVISIBLE);
                iMonGreenChecked.setVisibility(View.INVISIBLE);

                iTisGreenUnChecked.setEnabled(true);
                iTisRedUnChecked.setEnabled(true);
                iTisRedChecked.setVisibility(View.INVISIBLE);
                iTisGreenChecked.setVisibility(View.INVISIBLE);

                iOnsGreenUnChecked.setEnabled(true);
                iOnsRedUnChecked.setEnabled(true);
                iOnsRedChecked.setVisibility(View.INVISIBLE);
                iOnsGreenChecked.setVisibility(View.INVISIBLE);

                iTorGreenUnChecked.setEnabled(true);
                iTorRedUnChecked.setEnabled(true);
                iTorRedChecked.setVisibility(View.INVISIBLE);
                iTorGreenChecked.setVisibility(View.INVISIBLE);

                iFreGreenUnChecked.setEnabled(true);
                iFreRedUnChecked.setEnabled(true);
                iFreRedChecked.setVisibility(View.INVISIBLE);
                iFreGreenChecked.setVisibility(View.INVISIBLE);
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

    public void  clickEventMåndagGreenChecked(View v)
    {
        iMonGreenChecked.setVisibility(View.VISIBLE);
        iMonGreenUnChecked.setVisibility(View.INVISIBLE);
        iMonRedChecked.setVisibility(View.INVISIBLE);
        iMonRedUnChecked.setVisibility(View.VISIBLE);

        String NarvaroRaknare = sMåndag;
        stMonN = "Närvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stMonN, NarvaroRaknare, sEnamn);
    }

    public void clickEventMåndagRedChecked(View v)
    {

        iMonRedChecked.setVisibility(View.VISIBLE);
        iMonRedUnChecked.setVisibility(View.INVISIBLE);
        iMonGreenChecked.setVisibility(View.INVISIBLE);
        iMonGreenUnChecked.setVisibility(View.VISIBLE);
        String NarvaroRaknare = sMåndag;
        stMonN = "EjNärvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stMonN, NarvaroRaknare, sEnamn);
    }

    public void  clickEventTisdagGreenChecked(View v)
    {
        iTisGreenChecked.setVisibility(View.VISIBLE);
        iTisGreenUnChecked.setVisibility(View.INVISIBLE);
        iTisRedChecked.setVisibility(View.INVISIBLE);
        iTisRedUnChecked.setVisibility(View.VISIBLE);

        String NarvaroRaknare = sTisdag;
        stTisN = "Närvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stTisN, NarvaroRaknare, sEnamn);
    }

    public void clickEventTisdagRedChecked(View v)
    {

        iTisRedChecked.setVisibility(View.VISIBLE);
        iTisRedUnChecked.setVisibility(View.INVISIBLE);
        iTisGreenChecked.setVisibility(View.INVISIBLE);
        iTisGreenUnChecked.setVisibility(View.VISIBLE);
        String NarvaroRaknare = sTisdag;
        stTisN = "EjNärvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stTisN, NarvaroRaknare, sEnamn);
    }

    public void  clickEventOnsdagGreenChecked(View v)
    {
        iOnsGreenChecked.setVisibility(View.VISIBLE);
        iOnsGreenUnChecked.setVisibility(View.INVISIBLE);
        iOnsRedChecked.setVisibility(View.INVISIBLE);
        iOnsRedUnChecked.setVisibility(View.VISIBLE);

        String NarvaroRaknare = sOnsdag;
        stOnsN = "Närvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stOnsN, NarvaroRaknare, sEnamn);
    }

    public void clickEventOnsdagRedChecked(View v)
    {

        iOnsRedChecked.setVisibility(View.VISIBLE);
        iOnsRedUnChecked.setVisibility(View.INVISIBLE);
        iOnsGreenChecked.setVisibility(View.INVISIBLE);
        iOnsGreenUnChecked.setVisibility(View.VISIBLE);

        String NarvaroRaknare = sOnsdag;
        stOnsN = "EjNärvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stOnsN, NarvaroRaknare, sEnamn);
    }

    public void  clickEventTorsdagGreenChecked(View v)
    {
        iTorGreenChecked.setVisibility(View.VISIBLE);
        iTorGreenUnChecked.setVisibility(View.INVISIBLE);
        iTorRedChecked.setVisibility(View.INVISIBLE);
        iTorRedUnChecked.setVisibility(View.VISIBLE);

        String NarvaroRaknare = sTorsdag;
        stTorN = "Närvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stTorN, NarvaroRaknare, sEnamn);
    }

    public void clickEventTorsdagRedChecked(View v)
    {

        iTorRedChecked.setVisibility(View.VISIBLE);
        iTorRedUnChecked.setVisibility(View.INVISIBLE);
        iTorGreenChecked.setVisibility(View.INVISIBLE);
        iTorGreenUnChecked.setVisibility(View.VISIBLE);

        String NarvaroRaknare = sTorsdag;
        stTorN = "EjNärvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stTorN, NarvaroRaknare, sEnamn);
    }

    public void  clickEventFredagGreenChecked(View v)
    {
        iFreGreenChecked.setVisibility(View.VISIBLE);
        iFreGreenUnChecked.setVisibility(View.INVISIBLE);
        iFreRedChecked.setVisibility(View.INVISIBLE);
        iFreRedUnChecked.setVisibility(View.VISIBLE);

        String NarvaroRaknare = sFredag;
        stFreN = "Närvarande";

        String method = "mataInData";
        HandledareActivity.SendDataActivity SendDataActivity = new HandledareActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stFreN, NarvaroRaknare, sEnamn);
    }

    public void clickEventFredagRedChecked(View v)
    {

        iFreRedChecked.setVisibility(View.VISIBLE);
        iFreRedUnChecked.setVisibility(View.INVISIBLE);
        iFreGreenChecked.setVisibility(View.INVISIBLE);
        iFreGreenUnChecked.setVisibility(View.VISIBLE);

        String NarvaroRaknare = sFredag;
        stFreN = "EjNärvarande";

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

            // alertDialog = new AlertDialog.Builder(ctx).create();
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

                iMonGreenUnChecked.setVisibility(View.VISIBLE);
                iMonRedUnChecked.setVisibility(View.VISIBLE);

                iMonRedChecked.setVisibility(View.INVISIBLE);
                iMonRedUnChecked.setVisibility(View.INVISIBLE);
                iMonGreenChecked.setVisibility(View.INVISIBLE);
                iMonGreenUnChecked.setVisibility(View.INVISIBLE);

                iTisGreenUnChecked.setVisibility(View.VISIBLE);
                iTisRedUnChecked.setVisibility(View.VISIBLE);

                iTisRedChecked.setVisibility(View.INVISIBLE);
                iTisRedUnChecked.setVisibility(View.INVISIBLE);
                iTisGreenChecked.setVisibility(View.INVISIBLE);
                iTisGreenUnChecked.setVisibility(View.INVISIBLE);

                iOnsGreenUnChecked.setVisibility(View.VISIBLE);
                iOnsRedUnChecked.setVisibility(View.VISIBLE);

                iOnsRedChecked.setVisibility(View.INVISIBLE);
                iOnsRedUnChecked.setVisibility(View.INVISIBLE);
                iOnsGreenChecked.setVisibility(View.INVISIBLE);
                iOnsGreenUnChecked.setVisibility(View.INVISIBLE);

                iTorGreenUnChecked.setVisibility(View.VISIBLE);
                iTorRedUnChecked.setVisibility(View.VISIBLE);

                iTorRedChecked.setVisibility(View.INVISIBLE);
                iTorRedUnChecked.setVisibility(View.INVISIBLE);
                iTorGreenChecked.setVisibility(View.INVISIBLE);
                iTorGreenUnChecked.setVisibility(View.INVISIBLE);

                iFreGreenUnChecked.setVisibility(View.VISIBLE);
                iFreRedUnChecked.setVisibility(View.VISIBLE);

                iFreRedChecked.setVisibility(View.INVISIBLE);
                iFreRedUnChecked.setVisibility(View.INVISIBLE);
                iFreGreenChecked.setVisibility(View.INVISIBLE);
                iFreGreenUnChecked.setVisibility(View.INVISIBLE);

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

                            iMonGreenChecked.setVisibility(View.VISIBLE);
                            iMonGreenUnChecked.setVisibility(View.INVISIBLE);
                            iMonRedChecked.setVisibility(View.INVISIBLE);
                            iMonRedUnChecked.setVisibility(View.VISIBLE);
                        }
                        if (Objects.equals(Narvarande, "0")) {

                            iMonGreenChecked.setVisibility(View.INVISIBLE);
                            iMonGreenUnChecked.setVisibility(View.VISIBLE);
                            iMonRedChecked.setVisibility(View.VISIBLE);
                            iMonRedUnChecked.setVisibility(View.INVISIBLE);
                        }
                        if (Objects.equals(Narvarande, "null")) {

                            iMonGreenChecked.setVisibility(View.INVISIBLE);
                            iMonGreenUnChecked.setVisibility(View.VISIBLE);
                            iMonRedChecked.setVisibility(View.INVISIBLE);
                            iMonRedUnChecked.setVisibility(View.VISIBLE);
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

                            iTisGreenChecked.setVisibility(View.VISIBLE);
                            iTisGreenUnChecked.setVisibility(View.INVISIBLE);
                            iTisRedChecked.setVisibility(View.INVISIBLE);
                            iTisRedUnChecked.setVisibility(View.VISIBLE);
                        }
                        if (Objects.equals(Narvarande, "0")) {

                            iTisGreenChecked.setVisibility(View.INVISIBLE);
                            iTisGreenUnChecked.setVisibility(View.VISIBLE);
                            iTisRedChecked.setVisibility(View.VISIBLE);
                            iTisRedUnChecked.setVisibility(View.INVISIBLE);
                        }
                        if (Objects.equals(Narvarande, "null")) {

                            iTisGreenChecked.setVisibility(View.INVISIBLE);
                            iTisGreenUnChecked.setVisibility(View.VISIBLE);
                            iTisRedChecked.setVisibility(View.INVISIBLE);
                            iTisRedUnChecked.setVisibility(View.VISIBLE);
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

                            iOnsGreenChecked.setVisibility(View.VISIBLE);
                            iOnsGreenUnChecked.setVisibility(View.INVISIBLE);
                            iOnsRedChecked.setVisibility(View.INVISIBLE);
                            iOnsRedUnChecked.setVisibility(View.VISIBLE);
                        }
                        if (Objects.equals(Narvarande, "0")) {

                            iOnsGreenChecked.setVisibility(View.INVISIBLE);
                            iOnsGreenUnChecked.setVisibility(View.VISIBLE);
                            iOnsRedChecked.setVisibility(View.VISIBLE);
                            iOnsRedUnChecked.setVisibility(View.INVISIBLE);
                        }
                        if (Objects.equals(Narvarande, "null")) {

                            iOnsGreenChecked.setVisibility(View.INVISIBLE);
                            iOnsGreenUnChecked.setVisibility(View.VISIBLE);
                            iOnsRedChecked.setVisibility(View.INVISIBLE);
                            iOnsRedUnChecked.setVisibility(View.VISIBLE);
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

                            iTorGreenChecked.setVisibility(View.VISIBLE);
                            iTorGreenUnChecked.setVisibility(View.INVISIBLE);
                            iTorRedChecked.setVisibility(View.INVISIBLE);
                            iTorRedUnChecked.setVisibility(View.VISIBLE);
                        }
                        if (Objects.equals(Narvarande, "0")) {

                            iTorGreenChecked.setVisibility(View.INVISIBLE);
                            iTorGreenUnChecked.setVisibility(View.VISIBLE);
                            iTorRedChecked.setVisibility(View.VISIBLE);
                            iTorRedUnChecked.setVisibility(View.INVISIBLE);
                        }
                        if (Objects.equals(Narvarande, "null")) {

                            iTorGreenChecked.setVisibility(View.INVISIBLE);
                            iTorGreenUnChecked.setVisibility(View.VISIBLE);
                            iTorRedChecked.setVisibility(View.INVISIBLE);
                            iTorRedUnChecked.setVisibility(View.VISIBLE);
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

                            iFreGreenChecked.setVisibility(View.VISIBLE);
                            iFreGreenUnChecked.setVisibility(View.INVISIBLE);
                            iFreRedChecked.setVisibility(View.INVISIBLE);
                            iFreRedUnChecked.setVisibility(View.VISIBLE);
                        }
                        if (Objects.equals(Narvarande, "0")) {

                            iFreGreenChecked.setVisibility(View.INVISIBLE);
                            iFreGreenUnChecked.setVisibility(View.VISIBLE);
                            iFreRedChecked.setVisibility(View.VISIBLE);
                            iFreRedUnChecked.setVisibility(View.INVISIBLE);
                        }
                        if (Objects.equals(Narvarande, "null")) {

                            iFreGreenChecked.setVisibility(View.INVISIBLE);
                            iFreGreenUnChecked.setVisibility(View.VISIBLE);
                            iFreRedChecked.setVisibility(View.INVISIBLE);
                            iFreRedUnChecked.setVisibility(View.VISIBLE);
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