package com.example.martinpirklbauersv.apl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

/*
xml fil för denna aktivitet är "activity_login"
Beskriving:
Här har vi en funktion för att logga in.

Klasser:
alla klasser använder ip ifrån "strings.xml"
all indata tas emot som jsonsträngar som jag lägger in i arrayer.
all utdata skickas som strängar

CheckLoginActivity
Beskrivning:
här kontrolleras inloggning på användarna

IN:
I denna klass matas användarens användarnamn. lösenord in.
roll väljs via en spinner med de olika rollerna.

UT:
data som man får tillbaka är antingen en 1a eller 0a.
blir det en 1a loggas man in.
blir det en 0a händer inget.

indata:
Användaren skriver in användarnamn och lösenord hen har.
Användaren väljer vilken roll hen har via en spinner.

utdata:
användarens ID och roll skickas vidare till olika intent beroende på roll.

*/

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText userNameField, passwordField;
    String Anvandarnamn, Losenord, Role;
    String Roll;
    int Roll1 = 1;
    int Roll2 = 2;
    int Roll3 = 3;
    int Roll4 = 4;
    int Roll5 = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameField = (EditText) findViewById(R.id.userName);
        passwordField = (EditText) findViewById(R.id.Password);

        Spinner s = (Spinner) findViewById(R.id.spinner);
        s.setOnItemSelectedListener(this);
        String[] arraySpinner = new String[]{
                "Admin", "Handledare", "Elev", "Lärare", "Kansli"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        s.setAdapter(adapter);
        s.setSelection(1);

    }

    public void onItemSelected(AdapterView<?> parent, View v,
                               int pos, long id) {

        switch (pos) {
            case 0:
                Roll = String.valueOf(Integer.valueOf(Roll1));
                break;
            case 1:
                Roll = String.valueOf(Integer.valueOf(Roll2));
                break;
            case 2:
                Roll = String.valueOf(Integer.valueOf(Roll3));
                break;
            case 3:
                Roll = String.valueOf(Integer.valueOf(Roll4));
                break;
            case 4:
                Roll = String.valueOf(Integer.valueOf(Roll5));
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void userLogin(View view) {

            Anvandarnamn = userNameField.getText().toString();
            Losenord = passwordField.getText().toString();
            Role = Roll;
        String method = "login";
        LoginActivity.CheckLoginActivity CheckLoginActivity = new LoginActivity.CheckLoginActivity(this);
        CheckLoginActivity.execute(method, Anvandarnamn, Losenord, Role);

    }

    private class CheckLoginActivity extends AsyncTask<String, Void, String> {

        AlertDialog alertDialog;
        Context ctx;
        View v;

        CheckLoginActivity(Context ctx)

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

            String login_url = "http://"+getResources().getString(R.string.ip) +"/APL-APP/APL_PHP/APL_loginFunction.php";
            String method = params[0];
            if (method.equals("login")) {
                String Anvandarnamn = params[1];
                String Losenord = params[2];
                String Role = params[3];
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("Anvandarnamn", "UTF-8") + "=" + URLEncoder.encode(Anvandarnamn, "UTF-8") + "&" +
                            URLEncoder.encode("Losenord", "UTF-8") + "=" + URLEncoder.encode(Losenord, "UTF-8") + "&" +
                            URLEncoder.encode("Role", "UTF-8") + "=" + URLEncoder.encode(Role, "UTF-8");
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

            result = result.substring(0,1);

            if (Integer.parseInt(result) == 1) {

                if (Objects.equals(Role, "1")) {
                    Intent intent = new Intent(ctx, AdminActivity.class);
                    intent.putExtra("AnvandarID", Anvandarnamn);
                    intent.putExtra("Role", Role);
                    startActivity(intent);
                }

                if (Objects.equals(Role, "2")) {
                    Intent intent = new Intent(ctx, HandledareActivity.class);
                    intent.putExtra("AnvandarID", Anvandarnamn);
                    intent.putExtra("Role", Role);
                    startActivity(intent);
                }

                if (Objects.equals(Role, "3")) {
                    Intent intent = new Intent(ctx, ElevActivity.class);
                    intent.putExtra("AnvandarID", Anvandarnamn);
                    intent.putExtra("Role", Role);
                    startActivity(intent);
                }

                if (Objects.equals(Role, "4")) {
                    Intent intent = new Intent(ctx, LarareActivity.class);
                    intent.putExtra("AnvandarID", Anvandarnamn);
                    intent.putExtra("Role", Role);
                    startActivity(intent);
                }

                if (Objects.equals(Role, "5")) {
                    Intent intent = new Intent(ctx, KansliActivity.class);
                    intent.putExtra("AnvandarID", Anvandarnamn);
                    intent.putExtra("Role", Role);
                    startActivity(intent);
                }
            }
                else {

                }
        }
    }
}