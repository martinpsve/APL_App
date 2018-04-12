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
import android.view.MenuItem;
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


public class ConnectUsersToAPLActivity extends AppCompatActivity {

    private DrawerLayout myDrawerLayout;

    private EditText AnvandarID, DagarID, PeriodID, ArbetsplatsID;
    String sNarvarande, sAnvandarID, sDagarID, sPeriodID, sArbetsplatsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectuserstoapl);

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
                                        Intent CalenderActivity = new Intent(ConnectUsersToAPLActivity.this,SetNarvaroActivity.class);
                                        startActivity(CalenderActivity);
                                        break;

                                    case R.id.nav_Narvaro:
                                        Intent CalenderReadActivity = new Intent(ConnectUsersToAPLActivity.this,ListNarvaroActivity.class);
                                        startActivity(CalenderReadActivity);
                                        break;

                                    case R.id.nav_regUsersApl:
                                        Intent  APLRegUsers = new Intent(ConnectUsersToAPLActivity.this, ConnectUsersToAPLActivity.class);
                                        startActivity( APLRegUsers);
                                        break;

                                    case R.id.nav_regUsers:
                                        Intent  RegUsersActivity = new Intent(ConnectUsersToAPLActivity.this, RegisterUsersActivity.class);
                                        startActivity( RegUsersActivity);
                                        break;

                                    case R.id.nav_listUsers:
                                        Intent  userListActivity = new Intent(ConnectUsersToAPLActivity.this, ListUsersActivity.class);
                                        startActivity( userListActivity);
                                        break;

                                    case R.id.    navl_logOut:
                                        Intent  MainActivity = new Intent(ConnectUsersToAPLActivity.this, LoginActivity.class);
                                        startActivity( MainActivity);
                                        break;
                                }


                                menuItem.setChecked(true);

                                myDrawerLayout.closeDrawers();

                                return true;
                            }
                        });


        AnvandarID = (EditText) findViewById(R.id.AnvandarID);
        DagarID = (EditText) findViewById(R.id.DagarID);
        PeriodID = (EditText) findViewById(R.id.PeriodID);
        ArbetsplatsID = (EditText) findViewById(R.id.ArbetsplatsID);

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
    public void onClickClose(View view) {
        finish();
    }

    public void DataTooDB(View view) {

        sAnvandarID = AnvandarID.getText().toString();
        sDagarID = DagarID.getText().toString();
        sPeriodID = PeriodID.getText().toString();
        sArbetsplatsID = ArbetsplatsID.getText().toString();



        String method = "mataInData";
        ConnectUsersToAPLActivity.GetDataActivity GetDataActivity = new ConnectUsersToAPLActivity.GetDataActivity(this);
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
