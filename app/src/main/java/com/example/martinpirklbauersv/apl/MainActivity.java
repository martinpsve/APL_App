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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText userNameField, passwordField;
    private TextView Roll;
    private TextView Namn;
    private TextView Lösenord;
    String Anvandarnamn, Losenord, Role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameField = (EditText) findViewById(R.id.userName);
        passwordField = (EditText) findViewById(R.id.password);

        Namn = (TextView) findViewById(R.id.statusMessage);
        Lösenord = (TextView) findViewById(R.id.statusMessage2);

        Spinner s = (Spinner) findViewById(R.id.spinner);
        s.setOnItemSelectedListener(this);
        String[] arraySpinner = new String[]{
                "Admin", "Handledare", "Elev", "Lärare", "Kansli"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        s.setAdapter(adapter);

    }

    int Roll1 = 1;
    int Roll2 = 2;
    int Roll3 = 3;
    int Roll4 = 4;
    int Roll5 = 5;

    public void onItemSelected(AdapterView<?> parent, View v,
                               int pos, long id) {

        Roll = (TextView) findViewById(R.id.textView6);

        switch (pos) {
            case 0:
                Roll.setText("" + Roll1);
                break;
            case 1:
                Roll.setText("" + Roll2);
                break;
            case 2:
                Roll.setText("" + Roll3);
                break;
            case 3:
                Roll.setText("" + Roll4);
                break;
            case 4:
                Roll.setText("" + Roll5);
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void userLogin(View view) {

        Anvandarnamn = userNameField.getText().toString();
        Losenord = passwordField.getText().toString();
        Role = Roll.getText().toString();

        String method = "login";
        LoginActivity LoginActivity = new LoginActivity(this);
        LoginActivity.execute(method, Anvandarnamn, Losenord, Role);

    }

    private class LoginActivity extends AsyncTask<String, Void, String> {

        AlertDialog alertDialog;
        Context ctx;
        View v;

        LoginActivity(Context ctx)

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
                            URLEncoder.encode("Losenord", "UTF-8") + "=" + URLEncoder.encode(Losenord, "UTF-8") + "&" + URLEncoder.encode("Role", "UTF-8") + "=" + URLEncoder.encode(Role, "UTF-8");
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

            Lösenord.setText(result);

            if (Integer.parseInt(result) == 1) {
                if (Objects.equals(Role, "1")) {
                    Intent intent = new Intent(ctx, fourthActivity.class);
                    intent.putExtra("AnvandarID", Anvandarnamn);
                    intent.putExtra("Role", Role);
                    startActivity(intent);
                }

                if (Objects.equals(Role, "2")) {
                    Intent intent = new Intent(ctx, ThirdActivity.class);
                    intent.putExtra("AnvandarID", Anvandarnamn);
                    intent.putExtra("Role", Role);
                    startActivity(intent);
                }

                if (Objects.equals(Role, "3")) {
                    Intent intent = new Intent(ctx, SecondActivity.class);
                    intent.putExtra("AnvandarID", Anvandarnamn);
                    intent.putExtra("Role", Role);
                    startActivity(intent);
                }

                if (Objects.equals(Role, "4")) {
                    Intent intent = new Intent(ctx, fifthActivity.class);
                    intent.putExtra("AnvandarID", Anvandarnamn);
                    intent.putExtra("Role", Role);
                    startActivity(intent);
                }

                if (Objects.equals(Role, "5")) {
                    Intent intent = new Intent(ctx, sixthActivity.class);
                    intent.putExtra("AnvandarID", Anvandarnamn);
                    intent.putExtra("Role", Role);
                    startActivity(intent);
                }
                else {

                }
            }
            //   alertDialog.setMessage(result);
            //  alertDialog.show();
        }
    }
}