package com.example.martinpirklbauersv.apl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Spinner;
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
xml fil för denna aktivitet är "activity_registerusers"
Beskriving:
Hit kommer Administratören efter han klickar in på "registrera användare som ligger i sido drawern".
Här får administratören möjlighetet att skapa en användare med förnamn, efternamn, lösenord, mailaddress och telefonnummer.


Klasser:
alla klasser använder ip ifrån "strings.xml"
all indata tas emot som jsonsträngar som jag lägger in i arrayer.
all utdata skickas som strängar

GetAnvandareActivity
Beskrivning:
här hämtas användarnamn på alla användare för att kontrollera så att användarnamn blir unika

IN:
ingen data matas in:

UT:
data som man får tillbaka är användares användarnamn

CreateUsersActivity
Beskrivning:
Här skickas den nya användaren in i databasen.

IN:
förnamn, efternamn, lösenord, telefonnummer, mailadress matas in till databasen.

UT:
ingen utdata kommer tillbaka

indata:
Admin kan mata in förnamn, efternamn, lösenord, telefonnummer, mailadress

utdata:
Admins Användarnamn och Roll Bekräftelse skriv ut på skärmen.

*/

public class RegisterUsersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DrawerLayout myDrawerLayout;

    private EditText FnamnField, EnamnField, LösenordField, TelefonnummerField, MailadressField;
    String Fnamn, Enamn, Användarnamn, lösenord, Telefonnummer, Email, Role, str;
    String sAnvandarNamn;
    int i = 0;
    ArrayList<String> AList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerusers);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        myDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener
                (
                        new NavigationView.OnNavigationItemSelectedListener()
                        {

                            @Override
                            public boolean onNavigationItemSelected(MenuItem menuItem)
                            {
                                switch (menuItem.getItemId()){


                                    case R.id. nav_setNarvaro:
                                        Intent CalenderActivity = new Intent(RegisterUsersActivity.this,SetNarvaroActivity.class);
                                        startActivity(CalenderActivity);
                                        break;

                                    case R.id.nav_Narvaro:
                                        Intent CalenderReadActivity = new Intent(RegisterUsersActivity.this,ListNarvaroActivity.class);
                                        startActivity(CalenderReadActivity);
                                        break;

                                    case R.id.nav_regUsersApl:
                                        Intent  APLRegUsers = new Intent(RegisterUsersActivity.this, ConnectUsersToAPLActivity.class);
                                        startActivity( APLRegUsers);
                                        break;

                                    case R.id.nav_regUsers:
                                        Intent  RegUsersActivity = new Intent(RegisterUsersActivity.this, RegisterUsersActivity.class);
                                        startActivity( RegUsersActivity);
                                        break;

                                    case R.id.nav_listUsers:
                                        Intent  userListActivity = new Intent(RegisterUsersActivity.this, ListUsersActivity.class);
                                        startActivity( userListActivity);
                                        break;

                                    case R.id.    navl_logOut:
                                        Intent  MainActivity = new Intent(RegisterUsersActivity.this, LoginActivity.class);
                                        startActivity( MainActivity);
                                        break;
                                }


                                menuItem.setChecked(true);
                                myDrawerLayout.closeDrawers();
                                return true;
                            }
                        });

        FnamnField = (EditText) findViewById(R.id.FnamnField);
        EnamnField = (EditText) findViewById(R.id.EnamnField);
        LösenordField = (EditText) findViewById(R.id.LösenordField);
        TelefonnummerField = (EditText) findViewById(R.id.TelefonnummerField);
        MailadressField = (EditText) findViewById(R.id.MailadressField);

        Spinner s = (Spinner) findViewById(R.id.Roller);
        s.setOnItemSelectedListener(this);
        String[] arraySpinner = new String[]{
                "Admin", "Handledare", "Elev", "Lärare", "Kansli"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        s.setAdapter(adapter);

        String method = "hämtaAnvändaredata";
        RegisterUsersActivity.GetAnvandareActivity GetAnvandareActivity = new RegisterUsersActivity.GetAnvandareActivity(this);
        GetAnvandareActivity.execute(method);
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

        str = (String ) parent.getItemAtPosition(pos);

    }

    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void onClickClose(View view) {
        finish();
    }

    public void DataTooDB(View view) {

        Fnamn = FnamnField.getText().toString();
        Enamn = EnamnField.getText().toString();

        Användarnamn = Fnamn.substring(0,3) + Enamn.substring(0,3);

        while (AList.contains(Användarnamn)) {

            i++;
            Användarnamn = Fnamn.substring(0,3) + Enamn.substring(0,3) + "" + i;

        }

        lösenord = LösenordField.getText().toString();
        Telefonnummer = TelefonnummerField.getText().toString();
        Email = MailadressField.getText().toString();
        Role = str;
        /*
        Toast toast = Toast.makeText(this, "" + Användarnamn, Toast.LENGTH_LONG);
        toast.show();
*/
        String method = "MataInUser";
        RegisterUsersActivity.CreateUsersActivity CreateUsersActivity = new RegisterUsersActivity.CreateUsersActivity(this);
        CreateUsersActivity.execute(method, Fnamn, Enamn, Användarnamn, lösenord, Telefonnummer, Email, Role);
    }
    @Override
    public void onRestart(){

        super.onRestart();

        String method = "hämtaAnvändaredata";
        RegisterUsersActivity.GetAnvandareActivity GetAnvandareActivity = new RegisterUsersActivity.GetAnvandareActivity(this);
        GetAnvandareActivity.execute(method);
    }

    private class GetAnvandareActivity extends AsyncTask<String, Void, String> {

        AlertDialog alertDialog;
        Context ctx;
        View v;

        GetAnvandareActivity(Context ctx)

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
            String login_url = "http://" + getResources().getString(R.string.ip) + "/APL-APP/APL_PHP/APL_AdminGetUserName.php";

            String method = params[0];
            if (method.equals("hämtaAnvändaredata")) {

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

                sAnvandarNamn = null;
                try {
                    sAnvandarNamn = obj.getString("Anvandarnamn");
                    AList.add(sAnvandarNamn);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class CreateUsersActivity extends AsyncTask<String, Void, String> {

        AlertDialog alertDialog;
        Context ctx;
        View v;

        CreateUsersActivity(Context ctx)

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

            String reg_url = "http://"+getResources().getString(R.string.ip) +"/APL-APP/APL_PHP/APL_AdminCreateUsers.php";
            String method = params[0];
            String Fnamn = params[1];
            String Enamn = params[2];
            String Användarnamn = params[3];
            String Lösenord = params[4];
            String Telefonnummer = params[5];
            String Email = params[6];
            String Role = params[7];

            if (method.equals("MataInUser")) {

                try {
                    URL url = new URL(reg_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    //httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("Fnamn", "UTF-8") + "=" + URLEncoder.encode(Fnamn, "UTF-8") + "&" +
                            URLEncoder.encode("Enamn", "UTF-8") + "=" + URLEncoder.encode(Enamn, "UTF-8") + "&" +
                            URLEncoder.encode("Anvandarnamn", "UTF-8") + "=" + URLEncoder.encode(Användarnamn, "UTF-8") + "&" +
                            URLEncoder.encode("Losenord", "UTF-8") + "=" + URLEncoder.encode(Lösenord, "UTF-8") + "&" +
                            URLEncoder.encode("Telefonnummer", "UTF-8") + "=" + URLEncoder.encode(Telefonnummer, "UTF-8") + "&" +
                            URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8") + "&" +
                            URLEncoder.encode("Role", "UTF-8") + "=" + URLEncoder.encode(Role, "UTF-8");
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

