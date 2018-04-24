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
import java.util.Objects;

/*

Beskriving:
Här har vi en funktion för Administratören att sätta närvaro på elever.

Klasser:


GetUsersFromIDActivity

IN:
ingen indata

UT:
användarID på alla användare

GetAPLWeeksActivity

IN:
användarID på den valda person från spinner

UT:
APL veckor som personen är registrerad på

GetDaysFromWeekActivity

IN;
användarID på den valda person från spinner och vecka från antigen nuvarande vecka eller från valt item från spinner

UT:
datum på de olika veckodagarna.
närvarocheck på de olika veckodagarna beroende på användareID
närvaroID på de olika veckodagarna beroende på användareID

SendDataActivity

IN:

 stMonN status på närvaro om man är närvarande eller frånvarande och vilken veckodag
 NarvaroRaknare beroende vilken dag
 sEnamn: namn på personen som är vald från spinner

UT:
ingen utdata

indata:


utdata:


*/

public class SetNarvaroActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DrawerLayout myDrawerLayout;

    private TextView Tstartdag;
    private TextView Tslutdag;
    String sMåndag, sTisdag, sOnsdag, sTorsdag, sFredag;
    private TextView tMåndag, tTisdag, tOnsdag, tTorsdag, tFredag;
    private ImageView iMåndagEjNärvarande, iTisdagEjNärvarande, iOnsdagEjNärvarande, iTorsdagEjNärvarande, iFredagEjNärvarande;
    private ImageView iMåndagNärvarande, iTisdagNärvarande, iOnsdagNärvarande, iTorsdagNärvarande, iFredagNärvarande;
    String stWeek, stMonN, stTisN, stOnsN, stTorN, stFreN;
    String Narvarande, Datum, NarvaroRaknare;
    String Fnamn, Enamn, AnvandarID;
    String kNamn, klassID;
    String sKlass;
    String sFnamn, sEnman;
    String method;
    String startDag, slutdag;
    int UserID, KlassID;
    Spinner U, s;

    ArrayAdapter<String> adapter7;
    ArrayList<Integer> IDList = new ArrayList<Integer>();
    ArrayList<String> fList = new ArrayList<String>();
    ArrayList<String> eList = new ArrayList<String>();

    ArrayAdapter<String> adapter10;
    ArrayList<Integer> klassIDlist = new ArrayList<Integer>();
    ArrayList<String> KList = new ArrayList<String>();
    ArrayList<String> antallist = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fList.add("Användare");
        KList.add("Klasser");
        antallist.add("Veckor");
        setContentView(R.layout.activity_setnarvaro);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        myDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener
                (
                        new NavigationView.OnNavigationItemSelectedListener() {

                            @Override
                            public boolean onNavigationItemSelected(MenuItem menuItem) {
                                switch (menuItem.getItemId()) {


                                    case R.id.nav_setNarvaro:
                                        Intent CalenderActivity = new Intent(SetNarvaroActivity.this, SetNarvaroActivity.class);
                                        startActivity(CalenderActivity);
                                        break;

                                    case R.id.nav_Narvaro:
                                        Intent CalenderReadActivity = new Intent(SetNarvaroActivity.this, ListNarvaroActivity.class);
                                        startActivity(CalenderReadActivity);
                                        break;

                                    case R.id.nav_regUsersApl:
                                        Intent APLRegUsers = new Intent(SetNarvaroActivity.this, ConnectUsersToAPLActivity.class);
                                        startActivity(APLRegUsers);
                                        break;

                                    case R.id.nav_regUsers:
                                        Intent RegUsersActivity = new Intent(SetNarvaroActivity.this, RegisterUsersActivity.class);
                                        startActivity(RegUsersActivity);
                                        break;

                                    case R.id.nav_listUsers:
                                        Intent userListActivity = new Intent(SetNarvaroActivity.this, ListUsersActivity.class);
                                        startActivity(userListActivity);
                                        break;

                                    case R.id.navl_logOut:
                                        Intent MainActivity = new Intent(SetNarvaroActivity.this, LoginActivity.class);
                                        startActivity(MainActivity);
                                        break;
                                }

                                menuItem.setChecked(true);

                                myDrawerLayout.closeDrawers();

                                return true;
                            }
                        });

        Tstartdag = (TextView) findViewById(R.id.textView10);
        Tslutdag = (TextView) findViewById(R.id.textView11);

        tMåndag = (TextView) findViewById(R.id.textView12);
        tTisdag = (TextView) findViewById(R.id.textView13);
        tOnsdag = (TextView) findViewById(R.id.textView14);
        tTorsdag = (TextView) findViewById(R.id.textView16);
        tFredag = (TextView) findViewById(R.id.textView15);

        Spinner K = (Spinner) findViewById(R.id.spinner3);

        adapter10 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, KList);
        adapter10.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        //K.setEnabled(false);
        K.setAdapter(adapter10);
        K.setOnItemSelectedListener(this);

        U = (Spinner) findViewById(R.id.UserSpinner);

        adapter7 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, fList);
        adapter7.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        U.setEnabled(false);
        U.setAdapter(adapter7);
        U.setOnItemSelectedListener(this);

        s = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, antallist);
        adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);

        s.setEnabled(false);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);

        method = "hämtaKlassdata";
        SetNarvaroActivity.GetClassActivity GetClassActivity = new SetNarvaroActivity.GetClassActivity(this);
        GetClassActivity.execute(method);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                myDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
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
            SetNarvaroActivity.GetAPLWeeksActivity GetAPLWeeksActivity = new SetNarvaroActivity.GetAPLWeeksActivity(this);
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
                SetNarvaroActivity.GetDaysFromWeekActivity GetDaysFromWeekActivity = new SetNarvaroActivity.GetDaysFromWeekActivity(this);
                GetDaysFromWeekActivity.execute(method, stWeek, "" + UserID);
            } catch (Exception e) {

            }
        }

        if (parent.getId() == R.id.spinner3) {

            sKlass = (String) parent.getItemAtPosition(pos);

            if (!sKlass.equals("Klasser")) {
                KlassID = klassIDlist.get(pos - 1);
                U.setEnabled(true);
            }

            method = "hämtaUserdata";
            SetNarvaroActivity.GetUsersWithNarvaroActivity GetUsersWithNarvaroActivity = new SetNarvaroActivity.GetUsersWithNarvaroActivity(this);
            GetUsersWithNarvaroActivity.execute(method, "" + KlassID);
        }
    }

    @Override
    public void onRestart(){

        super.onRestart();

        try {
            method = "hämtadata";
            SetNarvaroActivity.GetDaysFromWeekActivity GetDaysFromWeekActivity = new SetNarvaroActivity.GetDaysFromWeekActivity(this);
            GetDaysFromWeekActivity.execute(method, stWeek, "" + UserID);
        } catch (Exception e) {
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void clickEventMondagEjNärvarande(View v)
    {
        iMåndagEjNärvarande.setBackgroundColor(Color.RED);
        iMåndagNärvarande.setBackgroundColor(Color.rgb(40,75,17));

        String NarvaroRaknare = sMåndag;
        stMonN = "EjNärvarande";

        String method = "mataInData";
        SetNarvaroActivity.SendDataActivity SendDataActivity = new SetNarvaroActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stMonN, NarvaroRaknare, sFnamn);
    }

    public void clickEventMondagNärvarande (View v)
    {
        iMåndagNärvarande.setBackgroundColor(Color.GREEN);
        iMåndagEjNärvarande.setBackgroundColor(Color.rgb(156,5,17));
        String NarvaroRaknare = sMåndag;
        stMonN = "Närvarande";

        String method = "mataInData";
        SetNarvaroActivity.SendDataActivity SendDataActivity = new SetNarvaroActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stMonN, NarvaroRaknare, sFnamn);
    }

    public void clickEventTisdagEjNärvarande(View v)
    {
        iTisdagEjNärvarande.setBackgroundColor(Color.RED);
        iTisdagNärvarande.setBackgroundColor(Color.GRAY);

        String NarvaroRaknare = sTisdag;
        stTisN = "EjNärvarande";

        String method = "mataInData";
        SetNarvaroActivity.SendDataActivity SendDataActivity = new SetNarvaroActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stTisN, NarvaroRaknare, sFnamn);
    }

    public void clickEventTisdagNärvarande (View v)
    {
        iTisdagNärvarande.setBackgroundColor(Color.GREEN);
        iTisdagEjNärvarande.setBackgroundColor(Color.GRAY);
        String NarvaroRaknare = sTisdag;
        stTisN = "Närvarande";

        String method = "mataInData";
        SetNarvaroActivity.SendDataActivity SendDataActivity = new SetNarvaroActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stTisN, NarvaroRaknare, sFnamn);
    }

    public void clickEventOnsdagEjNärvarande(View v)
    {
        iOnsdagEjNärvarande.setBackgroundColor(Color.RED);
        iOnsdagNärvarande.setBackgroundColor(Color.GRAY);

        String NarvaroRaknare = sOnsdag;
        stOnsN = "EjNärvarande";

        String method = "mataInData";
        SetNarvaroActivity.SendDataActivity SendDataActivity = new SetNarvaroActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stOnsN, NarvaroRaknare, sFnamn);

    }

    public void clickEventOnsdagNärvarande (View v)
    {
        iOnsdagNärvarande.setBackgroundColor(Color.GREEN);
        iOnsdagEjNärvarande.setBackgroundColor(Color.GRAY);
        String NarvaroRaknare = sOnsdag;
        stOnsN = "Närvarande";

        String method = "mataInData";
        SetNarvaroActivity.SendDataActivity SendDataActivity = new SetNarvaroActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stOnsN, NarvaroRaknare, sFnamn);
    }

    public void clickEventTorsdagEjNärvarande(View v)
    {
        iTorsdagEjNärvarande.setBackgroundColor(Color.RED);
        iTorsdagNärvarande.setBackgroundColor(Color.GRAY);

        String NarvaroRaknare = sTorsdag;
        stTorN = "EjNärvarande";

        String method = "mataInData";
        SetNarvaroActivity.SendDataActivity SendDataActivity = new SetNarvaroActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stTorN, NarvaroRaknare, sFnamn);
    }

    public void clickEventTorsdagNärvarande (View v)
    {
        iTorsdagNärvarande.setBackgroundColor(Color.GREEN);
        iTorsdagEjNärvarande.setBackgroundColor(Color.GRAY);
        String NarvaroRaknare = sTorsdag;
        stTorN = "Närvarande";

        String method = "mataInData";
        SetNarvaroActivity.SendDataActivity SendDataActivity = new SetNarvaroActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stTorN, NarvaroRaknare, sFnamn);
    }

    public void clickEventFredagEjNärvarande(View v)
    {
        iFredagEjNärvarande.setBackgroundColor(Color.RED);
        iFredagNärvarande.setBackgroundColor(Color.GRAY);

        String NarvaroRaknare = sFredag;
        stFreN = "EjNärvarande";

        String method = "mataInData";
        SetNarvaroActivity.SendDataActivity SendDataActivity = new SetNarvaroActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stFreN, NarvaroRaknare, sFnamn);
    }

    public void clickEventFredagNärvarande (View v)
    {
        iFredagNärvarande.setBackgroundColor(Color.GREEN);
        iFredagEjNärvarande.setBackgroundColor(Color.GRAY);
        String NarvaroRaknare = sFredag;
        stFreN = "Närvarande";

        String method = "mataInData";
        SetNarvaroActivity.SendDataActivity SendDataActivity = new SetNarvaroActivity.SendDataActivity(this);
        SendDataActivity.execute(method, stFreN, NarvaroRaknare, sFnamn);
    }

    public void onClickClose(View view) {
        finish();
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

                    Narvarande = null;
                    try {
                        Narvarande = obj.getString("Narvarande");
                        Log.d("asd2", "1" + Narvarande);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (i == 0) {
                        tMåndag.setText("Måndag" + Datum);
                        sMåndag = NarvaroRaknare;
                        if (Objects.equals(Narvarande, "1")) {
                            iMåndagNärvarande.setBackgroundColor(Color.GREEN);
                            iMåndagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "0")) {
                            iMåndagEjNärvarande.setBackgroundColor(Color.RED);
                            iMåndagNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "")) {
                            iMåndagNärvarande.setBackgroundColor(Color.GRAY);
                            iMåndagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                    }
                    if (i == 1) {
                        tTisdag.setText("Tisdag" + Datum);
                        sTisdag = NarvaroRaknare;

                        if (Objects.equals(Narvarande, "1")) {
                            iTisdagNärvarande.setBackgroundColor(Color.GREEN);
                            iTisdagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "0")) {
                            iTisdagEjNärvarande.setBackgroundColor(Color.RED);
                            iTisdagNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "")) {
                            iTisdagNärvarande.setBackgroundColor(Color.GRAY);
                            iTisdagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                    }
                    if (i == 2) {
                        tOnsdag.setText("Onsdag" + Datum);
                        sOnsdag = NarvaroRaknare;

                        if (Objects.equals(Narvarande, "1")) {
                            iOnsdagNärvarande.setBackgroundColor(Color.GREEN);
                            iOnsdagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "0")) {
                            iOnsdagEjNärvarande.setBackgroundColor(Color.RED);
                            iOnsdagNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "")) {
                            iOnsdagNärvarande.setBackgroundColor(Color.GRAY);
                            iOnsdagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                    }
                    if (i == 3) {
                        tTorsdag.setText("Torsdag" + Datum);
                        sTorsdag = NarvaroRaknare;

                        if (Objects.equals(Narvarande, "1")) {
                            iTorsdagNärvarande.setBackgroundColor(Color.GREEN);
                            iTorsdagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "0")) {
                            iTorsdagEjNärvarande.setBackgroundColor(Color.RED);
                            iTorsdagNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "")) {
                            iTorsdagNärvarande.setBackgroundColor(Color.GRAY);
                            iTorsdagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                    }
                    if (i == 4) {
                        tFredag.setText("Fredag" + Datum);
                        sFredag = NarvaroRaknare;

                        if (Objects.equals(Narvarande, "1")) {
                            iFredagNärvarande.setBackgroundColor(Color.GREEN);
                            iFredagEjNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "0")) {
                            iFredagEjNärvarande.setBackgroundColor(Color.RED);
                            iFredagNärvarande.setBackgroundColor(Color.GRAY);
                        }
                        if (Objects.equals(Narvarande, "")) {
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

    private class GetUsersWithNarvaroActivity extends AsyncTask<String, Void, String> {

        AlertDialog alertDialog;
        Context ctx;
        View v;

        GetUsersWithNarvaroActivity(Context ctx)

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
            String login_url = "http://"+getResources().getString(R.string.ip) +"/APL-APP/APL_PHP/APL_GetUsersFromNarvaro.php";

            String method = params[0];
            String KlassID = params[1];
            if (method.equals("hämtaUserdata")) {

                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("KlassID", "UTF-8") + "=" + URLEncoder.encode(KlassID, "UTF-8");
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

    private class GetClassActivity extends AsyncTask<String, Void, String> {

        AlertDialog alertDialog;
        Context ctx;
        View v;

        GetClassActivity(Context ctx)

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
            String login_url = "http://"+getResources().getString(R.string.ip) +"/APL-APP/APL_PHP/APL_NarvaroGetKlass.php";

            String method = params[0];
            if (method.equals("hämtaKlassdata")) {

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

                kNamn = null;
                try {
                    kNamn = obj.getString("Namn");
                    KList.add(kNamn);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("test","aa" + kNamn);
                klassID = null;
                try {
                    klassID = obj.getString("KlassID");
                    klassIDlist.add(Integer.valueOf(klassID));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Spinner K = (Spinner)findViewById(R.id.spinner3);
            K.invalidate();
            K.setSelection(0);
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

