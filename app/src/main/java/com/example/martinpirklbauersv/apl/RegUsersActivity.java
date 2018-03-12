package com.example.martinpirklbauersv.apl;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class RegUsersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText FnamnField, EnamnField, LösenordField, TelefonnummerField, MailadressField;
    String Fnamn, Enamn, lösenord, Telefonnummer, Email, Role, str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_users);

        FnamnField = (EditText) findViewById(R.id.Förnamn);
        EnamnField = (EditText) findViewById(R.id.Efternamn);
        LösenordField = (EditText) findViewById(R.id.Lösenord);
        TelefonnummerField = (EditText) findViewById(R.id.Telefonnummer);
        MailadressField = (EditText) findViewById(R.id.Mailadress);

        Spinner s = (Spinner) findViewById(R.id.Roller);
        s.setOnItemSelectedListener(this);
        String[] arraySpinner = new String[]{
                "Admin", "Handledare", "Elev", "Lärare", "Kansli"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        s.setAdapter(adapter);


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
        lösenord = LösenordField.getText().toString();
        Telefonnummer = TelefonnummerField.getText().toString();
        Email = MailadressField.getText().toString();
        Role = str;


        String method = "mataInData";
        RegUsersActivity.GetDataActivity GetDataActivity = new RegUsersActivity.GetDataActivity(this);
        GetDataActivity.execute(method, Fnamn, Enamn, lösenord, Telefonnummer, Email, Role);
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

            String reg_url = "http://10.0.2.2/APL-APP/APL_PHP/APL_AdminCreateUsers.php";
            String method = params[0];
            String Fnamn = params[1];
            String Enamn = params[2];
            String Lösenord = params[3];
            String Telefonnummer = params[4];
            String Email = params[5];
            String Role = params[6];


            if (method.equals("mataInData")) {

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

