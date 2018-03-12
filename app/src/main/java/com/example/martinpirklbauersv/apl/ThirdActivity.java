package com.example.martinpirklbauersv.apl;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class ThirdActivity extends AppCompatActivity {

    private TextView AnvandarID;
    private TextView RoleID;
    private TextView arbetsplats;
    private TextView mail;
    String Anvandarnamn;
    String Role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        AnvandarID = (TextView) findViewById(R.id.användarID);
        String s = getIntent().getStringExtra("AnvandarID");
        AnvandarID.setText(s);

        RoleID = (TextView) findViewById(R.id.text2);
        String r = getIntent().getStringExtra("Role");
        RoleID.setText(r);

        arbetsplats = (TextView) findViewById(R.id.textView2);
        mail = (TextView) findViewById(R.id.textView4);
    }

    public void onClickClose(View view) {
        finish();
    }
    public void userGetdata(View view) {
        Anvandarnamn = AnvandarID.getText().toString();

        String method = "hämtadata";
        ThirdActivity.GetDataActivity GetDataActivity = new ThirdActivity.GetDataActivity(this);
        GetDataActivity.execute(method, Anvandarnamn, Role);


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

            String login_url = "http://10.0.2.2/APL-APP/APL_PHP/APL_afterLogin.php";
            String method = params[0];
            if (method.equals("hämtadata")) {

                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("Anvandarnamn", "UTF-8") + "=" + URLEncoder.encode(Anvandarnamn, "UTF-8");
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

                String arbetsplatsnamn = null;
                try {
                    arbetsplatsnamn = obj.getString("Namn");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String Mail = null;
                try {
                    Mail = obj.getString("Email");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                arbetsplats.setText(arbetsplatsnamn);
                mail.setText(Mail);


            }

        }
    }

}