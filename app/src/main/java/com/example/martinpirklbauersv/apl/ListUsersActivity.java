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
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

/*
xml fil för denna aktivitet är "activity_listusers"
Beskriving:
Hit kommer Administratören efter han klickar in på "Lista användare".
Här finns det en spinner där Admin får välja mellan olika roller.
beroende på roll så visas de olika användare.



Klasser:
alla klasser använder ip ifrån "strings.xml"
all indata tas emot som jsonsträngar som jag lägger in i arrayer.
all utdata skickas som strängar

GetUsersActivity
Beskrivning:
Här hämtas a nvändarnasefternamn och läggs in i en lista

IN:
användares Roll


UT:
data som man får tillbaka är användares ID och Efternamn.

indata:
Admin kan välja vilka användare han vill se utifrån roll via en spinner.

utdata:
Admins Användarnamn och Roll Bekräftelse skriv ut på skärmen.
Användares ID och Efternamn skrivs ut på skärmen

*/

    public class ListUsersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

        private DrawerLayout myDrawerLayout;

        private TextView AnvändarID, AnvändarRoll;
        private LinearLayout RmButlayout, IDButLayout;
        String Roller;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_listusers);

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
                                            Intent CalenderActivity = new Intent(ListUsersActivity.this,SetNarvaroActivity.class);
                                            startActivity(CalenderActivity);
                                            break;

                                        case R.id.nav_Narvaro:
                                            Intent CalenderReadActivity = new Intent(ListUsersActivity.this,ListNarvaroActivity.class);
                                            startActivity(CalenderReadActivity);
                                            break;

                                        case R.id.nav_regUsersApl:
                                            Intent  APLRegUsers = new Intent(ListUsersActivity.this, ConnectUsersToAPLActivity.class);
                                            startActivity( APLRegUsers);
                                            break;

                                        case R.id.nav_regUsers:
                                            Intent  RegUsersActivity = new Intent(ListUsersActivity.this, RegisterUsersActivity.class);
                                            startActivity( RegUsersActivity);
                                            break;

                                        case R.id.nav_listUsers:
                                            Intent  userListActivity = new Intent(ListUsersActivity.this, ListUsersActivity.class);
                                            startActivity( userListActivity);
                                            break;

                                        case R.id.    navl_logOut:
                                            Intent  MainActivity = new Intent(ListUsersActivity.this, LoginActivity.class);
                                            startActivity( MainActivity);
                                            break;
                                    }

                                    menuItem.setChecked(true);

                                    myDrawerLayout.closeDrawers();

                                    return true;
                                }
                            });

            String s = getIntent().getStringExtra("AnvandarID");
            String r = getIntent().getStringExtra("Role");

            //ANVÄNDARNAMN OCH ROLL I DRAWERN
            View nameView = navView.getHeaderView(0);

            AnvändarID = (TextView) nameView.findViewById(R.id.userName);
            AnvändarID.setText(s);

            AnvändarRoll = (TextView) nameView.findViewById(R.id.userRoll);
            AnvändarRoll.setText(r);

            RmButlayout = (LinearLayout) findViewById(R.id.RMLL);
            IDButLayout = (LinearLayout) findViewById(R.id.IDLL);

            Spinner roller = (Spinner)findViewById(R.id.spinner);
            String[] arraySpinner1 = new String[] {
                    "Admin", "Handledare", "Elever", "Lärare", "Kansli"
            };

            ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, arraySpinner1);
            adapter4.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
            roller.setAdapter(adapter4);
            roller.setOnItemSelectedListener(this);

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

            if (parent.getId() == R.id.spinner) {
                IDButLayout.removeAllViewsInLayout();
                RmButlayout.removeAllViewsInLayout();
                Roller = (String) parent.getItemAtPosition(pos);

                String method = "hämtaUserdata";
                ListUsersActivity.GetUsersActivity GetUsersActivity = new ListUsersActivity.GetUsersActivity(this);
                GetUsersActivity.execute(method, Roller);
            }
        }
        public void onNothingSelected(AdapterView<?> arg0) {

        }

        @Override
        public void onRestart(){

            super.onRestart();
            IDButLayout.removeAllViewsInLayout();
            RmButlayout.removeAllViewsInLayout();

            String method = "hämtaUserdata";
            ListUsersActivity.GetUsersActivity GetUsersActivity = new ListUsersActivity.GetUsersActivity(this);
            GetUsersActivity.execute(method, Roller);
        }

        private class GetUsersActivity extends AsyncTask<String, Void, String> {

            AlertDialog alertDialog;
            Context ctx;
            View v;

            GetUsersActivity(Context ctx)

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
                String login_url = "http://"+getResources().getString(R.string.ip) +"/APL-APP/APL_PHP/APL_AdminListUsers_Role.php";

                String method = params[0];
                String Roller = params[1];
                if (method.equals("hämtaUserdata")) {

                    try {
                        URL url = new URL(login_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String data = URLEncoder.encode("Roller", "UTF-8") + "=" + URLEncoder.encode(Roller, "UTF-8");
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

                    String Enamn = null;
                    try {
                        Enamn = obj.getString("Enamn");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String sAnvadarID = null;
                    try {
                        sAnvadarID = obj.getString("AnvandarID");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    final String finalSAnvadarID = sAnvadarID;

                    ImageView RMButton = new ImageView(ListUsersActivity.this);
                    RMButton.setImageResource(R.drawable.ic_launcher_background);


                    Button IDButton = new Button(ListUsersActivity.this);
                    IDButton.setTextSize(10);
                    IDButton.setBackgroundColor(Color.TRANSPARENT);
                    IDButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(ctx, UppdateUsersActivity.class);
                            intent.putExtra("AnvandarID", finalSAnvadarID);
                            startActivity(intent);
                        }
                    });

                    IDButLayout.addView(IDButton);
                    IDButton.setText(Enamn);

                    int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
                    int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());

                    RMButton.setPadding(0,16,0,0);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);

                    RmButlayout.addView(RMButton, lp);
                }
            }
        }
    }