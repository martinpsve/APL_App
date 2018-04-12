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
import java.util.Objects;

public class UppdateUsersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DrawerLayout myDrawerLayout;

    private TextView AnvandarID;
    private LinearLayout IDButLayout;
    private EditText FnamnField, EnamnField, LösenordField, TelefonnummerField, MailadressField;
    private EditText EOnskan, Ematchning, EKlassID;
    private EditText EArbetsplatsID;
    private EditText EUndervisar;
    String UserID, Fnamn, Enamn, lösenord, Telefonnummer, Email, Role, str;
    String Onskan, Matchning, KlassID;
    String ArbetsplatsID;
    String Undervisar;
    String sAnvandarnamn;
    private TextView AnvandarNamn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uppdateusers);

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
                                        Intent CalenderActivity = new Intent(UppdateUsersActivity.this,SetNarvaroActivity.class);
                                        startActivity(CalenderActivity);
                                        break;

                                    case R.id.nav_Narvaro:
                                        Intent CalenderReadActivity = new Intent(UppdateUsersActivity.this,ListNarvaroActivity.class);
                                        startActivity(CalenderReadActivity);
                                        break;

                                    case R.id.nav_regUsersApl:
                                        Intent  APLRegUsers = new Intent(UppdateUsersActivity.this, ConnectUsersToAPLActivity.class);
                                        startActivity( APLRegUsers);
                                        break;

                                    case R.id.nav_regUsers:
                                        Intent  RegUsersActivity = new Intent(UppdateUsersActivity.this, RegisterUsersActivity.class);
                                        startActivity( RegUsersActivity);
                                        break;

                                    case R.id.nav_listUsers:
                                        Intent  userListActivity = new Intent(UppdateUsersActivity.this, ListUsersActivity.class);
                                        startActivity( userListActivity);
                                        break;

                                    case R.id.    navl_logOut:
                                        Intent  MainActivity = new Intent(UppdateUsersActivity.this, LoginActivity.class);
                                        startActivity( MainActivity);
                                        break;
                                }


                                menuItem.setChecked(true);

                                myDrawerLayout.closeDrawers();

                                return true;
                            }
                        });

        FnamnField = (EditText) findViewById(R.id.Narvarande);
        EnamnField = (EditText) findViewById(R.id.AnvandarID);
        LösenordField = (EditText) findViewById(R.id.DagarID);
        TelefonnummerField = (EditText) findViewById(R.id.PeriodID);
        MailadressField = (EditText) findViewById(R.id.Mailadress);

        AnvandarNamn = (TextView) findViewById(R.id.anvandarNamn);

        IDButLayout = (LinearLayout) findViewById(R.id.IDLL);

        AnvandarID = (TextView) findViewById(R.id.text3);
        String i = getIntent().getStringExtra("AnvandarID");
        AnvandarID.setText(i);

        Spinner s = (Spinner) findViewById(R.id.Roller);
        s.setOnItemSelectedListener(this);
        String[] arraySpinner = new String[]{
                "Admin", "Handledare", "Elev", "Lärare", "Kansli"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        s.setAdapter(adapter);

        UserID = AnvandarID.getText().toString();

        String method = "hämtadata";
        UppdateUsersActivity.GetDataActivity GetDataActivity = new UppdateUsersActivity.GetDataActivity(this);
        GetDataActivity.execute(method, UserID);


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

    public void onClickClose(View view) {finish();}

    public void DataTooDB(View view) {

        Fnamn = FnamnField.getText().toString();
        Enamn = EnamnField.getText().toString();
        lösenord = LösenordField.getText().toString();
        Telefonnummer = TelefonnummerField.getText().toString();
        Email = MailadressField.getText().toString();
        try {
        Onskan = EOnskan.getText().toString();
        }
        catch(Exception e){
            Onskan = "";
        }
        try {
            Matchning = Ematchning.getText().toString();
        }
        catch(Exception e){
            Matchning = "";
        }
        try {
            KlassID = EKlassID.getText().toString();
        }
        catch(Exception e){
            KlassID = "";
        }

        try {
            Undervisar = EUndervisar.getText().toString();
        }
        catch(Exception e){
            Undervisar = "";
        }

        try {
            ArbetsplatsID = EArbetsplatsID.getText().toString();
        }
        catch(Exception e){
            ArbetsplatsID = "";
        }


        Role = str;



        String method = "mataInData";
        UppdateUsersActivity.SendUpdateUserActivity SendUpdateUserActivity = new UppdateUsersActivity.SendUpdateUserActivity(this);
        SendUpdateUserActivity.execute(method, UserID, Fnamn, Enamn, lösenord, Telefonnummer, Email, Onskan, Matchning, KlassID, Undervisar, ArbetsplatsID, Role);
    }

    private class SendUpdateUserActivity extends AsyncTask<String, Void, String> {


        AlertDialog alertDialog;
        Context ctx;
        View v;

        SendUpdateUserActivity(Context ctx)

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

            String reg_url = "http://"+getResources().getString(R.string.ip) +"/APL-APP/APL_PHP/APL_AdminUpdateUsers.php";
            String method = params[0];
            String UserID = params[1];
            String Fnamn = params[2];
            String Enamn = params[3];
            String Lösenord = params[4];
            String Telefonnummer = params[5];
            String Email = params[6];
            String Onskan = params[7];
            String Matchning = params[8];
            String KlassID = params[9];
            String Undervisar = params[10];
            String ArbetsplatsID = params[11];
            String Role = params[12];


            if (method.equals("mataInData")) {

                try {
                    URL url = new URL(reg_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    //httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("AnvandarID", "UTF-8") + "=" + URLEncoder.encode(UserID, "UTF-8") + "&" +
                            URLEncoder.encode("Fnamn", "UTF-8") + "=" + URLEncoder.encode(Fnamn, "UTF-8") + "&" +
                            URLEncoder.encode("Enamn", "UTF-8") + "=" + URLEncoder.encode(Enamn, "UTF-8") + "&" +
                            URLEncoder.encode("Losenord", "UTF-8") + "=" + URLEncoder.encode(Lösenord, "UTF-8") + "&" +
                            URLEncoder.encode("Telefonnummer", "UTF-8") + "=" + URLEncoder.encode(Telefonnummer, "UTF-8") + "&" +
                            URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8")+ "&" +
                            URLEncoder.encode("Onskan", "UTF-8") + "=" + URLEncoder.encode(Onskan, "UTF-8") + "&" +
                            URLEncoder.encode("matchning", "UTF-8") + "=" + URLEncoder.encode(Matchning, "UTF-8") + "&" +
                            URLEncoder.encode("KlassID", "UTF-8") + "=" + URLEncoder.encode(KlassID, "UTF-8") + "&" +
                            URLEncoder.encode("Undervisar", "UTF-8") + "=" + URLEncoder.encode(Undervisar, "UTF-8") + "&" +
                            URLEncoder.encode("ArbetsplatsID", "UTF-8") + "=" + URLEncoder.encode(ArbetsplatsID, "UTF-8") + "&" +
                            URLEncoder.encode("Role", "UTF-8") + "=" + URLEncoder.encode(Role, "UTF-8");
                    Log.d("fel", ArbetsplatsID);
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

            String login_url = "http://"+getResources().getString(R.string.ip) +"/APL-APP/APL_PHP/APL_AdminListUsersUpdate.php";
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

            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject obj = null;
                try {
                    obj = jsonarray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String sFörnamn = null;
                try {
                    sFörnamn = obj.getString("Fnamn");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String sEfternamn = null;
                try {
                    sEfternamn = obj.getString("Enamn");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String sLosenord = null;
                try {
                    sLosenord = obj.getString("Losenord");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String sTelefonnummer = null;
                try {
                    sTelefonnummer = obj.getString("Telefonnummer");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String sEmail = null;
                try {
                    sEmail = obj.getString("Email");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String sElevID = null;
                try {
                    sElevID = obj.getString("ElevID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String sOnskan = null;
                try {
                    sOnskan = obj.getString("Onskan");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String sMatchning = null;
                try {
                    sMatchning = obj.getString("matchning");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String sKlassID = null;
                try {
                    sKlassID = obj.getString("KlassID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String sAdminID = null;
                try {
                    sAdminID = obj.getString("AdminID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String sHandledareID = null;
                try {
                    sHandledareID = obj.getString("HandledareID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String sArbetsplatsID = null;
                try {
                    sArbetsplatsID = obj.getString("ArbetsplatsID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String sLärareID = null;
                try {
                    sLärareID = obj.getString("LarareID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String sUndervisar = null;
                try {
                    sUndervisar = obj.getString("Undervisar");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String sKansliID = null;
                try {
                    sKansliID = obj.getString("KansliID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                sAnvandarnamn = null;
                try {
                    sAnvandarnamn = obj.getString("Anvandarnamn");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AnvandarNamn.setText(sAnvandarnamn);

                if (Objects.equals(sElevID, "1")) {

                    EditText test = new EditText(UppdateUsersActivity.this);
                    IDButLayout.addView(test);
                    test.setText(sElevID);

                    EOnskan = new EditText(UppdateUsersActivity.this);
                    IDButLayout.addView(EOnskan);
                    EOnskan.setText(sOnskan);

                    Ematchning = new EditText(UppdateUsersActivity.this);
                    IDButLayout.addView(Ematchning);
                    Ematchning.setText(sMatchning);

                    EKlassID= new EditText(UppdateUsersActivity.this);
                    IDButLayout.addView(EKlassID);
                    EKlassID.setText(sKlassID);
                }

                if (Objects.equals(sAdminID,"1")) {
                    EditText test = new EditText(UppdateUsersActivity.this);
                    IDButLayout.addView(test);
                    test.setText(sAdminID);
                }

                if (Objects.equals(sHandledareID,"1")) {
                    EditText test = new EditText(UppdateUsersActivity.this);
                    IDButLayout.addView(test);
                    test.setText(sHandledareID);

                    EArbetsplatsID = new EditText(UppdateUsersActivity.this);
                    IDButLayout.addView(EArbetsplatsID);
                    EArbetsplatsID.setText(sArbetsplatsID);
                }

                if (Objects.equals(sLärareID,"1")) {
                    EditText test = new EditText(UppdateUsersActivity.this);
                    IDButLayout.addView(test);
                    test.setText(sLärareID);

                    EUndervisar = new EditText(UppdateUsersActivity.this);
                    IDButLayout.addView(EUndervisar);
                    EUndervisar.setText(sUndervisar);
                }

                if (Objects.equals(sKansliID,"1")) {
                    EditText test = new EditText(UppdateUsersActivity.this);
                    IDButLayout.addView(test);
                    test.setText(sKansliID);
                }

                FnamnField.setText(sFörnamn);
                EnamnField.setText(sEfternamn);
                LösenordField.setText(sLosenord);
                TelefonnummerField.setText(sTelefonnummer);
                MailadressField.setText(sEmail);

            }

        }
    }

}
