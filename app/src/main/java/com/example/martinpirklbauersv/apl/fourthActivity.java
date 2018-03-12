package com.example.martinpirklbauersv.apl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class fourthActivity extends AppCompatActivity {
    private TextView AnvandarID;
    private TextView RoleID;
    private TextView Förnamn;
    private TextView  Efternamn;
    private LinearLayout ImButlayout;
    String Anvandarnamn;
    String Role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        AnvandarID = (TextView) findViewById(R.id.användarID);
        String s = getIntent().getStringExtra("AnvandarID");
        AnvandarID.setText(s);

        RoleID = (TextView) findViewById(R.id.text2);
        String r = getIntent().getStringExtra("Role");
        RoleID.setText(r);

        Förnamn = (TextView) findViewById(R.id.textView2);
        Efternamn = (TextView) findViewById(R.id.textView4);

        ImButlayout = (LinearLayout) findViewById(R.id.UptLL);

    }
    public void onClickClose(View view) {
        finish();
    }

    public void userGetdata(View view) {
        Anvandarnamn = AnvandarID.getText().toString();

        String method = "hämtadata";
        fourthActivity.GetDataActivity GetDataActivity = new fourthActivity.GetDataActivity(this);
        GetDataActivity.execute(method, Anvandarnamn, Role);
    }

    public void userReg(View view)
    {
        startActivity(new Intent(this,RegUsersActivity.class));
    }

    public void RmUsers(View view)
    {
        startActivity(new Intent(this,RemoveUsersActivity.class));
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

            String login_url = "http://10.0.2.2/APL-APP/APL_PHP/APL_AdminListUsers.php";
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

            StringBuilder b=new StringBuilder();
            StringBuilder c=new StringBuilder();

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

                String sAnvadarID = null;
                try {
                    sAnvadarID = obj.getString("AnvandarID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                final String finalSEfternamn = sEfternamn;
                final String finalSFörnamn = sFörnamn;
                final String finalSAnvadarID = sAnvadarID;

                ImageView UptButton = new ImageView(fourthActivity.this);
                UptButton.setImageResource(R.drawable.ic_launcher_background);
                UptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(ctx, UppdateUsersActivity.class);
                        intent.putExtra("Fnamn", finalSFörnamn);
                        intent.putExtra("Enamn", finalSEfternamn);
                        intent.putExtra("AnvandarID", finalSAnvadarID);
                        startActivity(intent);
                    }
                });

                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
                UptButton.setPadding(0,16,0,0);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);


                ImButlayout.addView(UptButton, lp);

                b.append(sFörnamn + "\n");

                c.append(sEfternamn + "\n");

            }

            Förnamn.setText(b.toString());
            Efternamn.setText(c.toString());

        }
    }

}
