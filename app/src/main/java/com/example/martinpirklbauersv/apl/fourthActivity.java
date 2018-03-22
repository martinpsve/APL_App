package com.example.martinpirklbauersv.apl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Objects;

public class fourthActivity extends AppCompatActivity {
    private TextView AnvandarID;
    private TextView RoleID;
    private TextView  Efternamn;
    private LinearLayout RmButlayout;
    private LinearLayout IDButLayout;
    String Anvandarnamn;
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

        //Förnamn = (TextView) findViewById(R.id.textView2);
        Efternamn = (TextView) findViewById(R.id.textView4);


        RmButlayout = (LinearLayout) findViewById(R.id.RMLL);
        IDButLayout = (LinearLayout) findViewById(R.id.IDLL);

        Anvandarnamn = AnvandarID.getText().toString();

        String method = "hämtadata";
        fourthActivity.GetDataActivity GetDataActivity = new fourthActivity.GetDataActivity(this);
        GetDataActivity.execute(method, Anvandarnamn);

    }

    @Override
    public void onRestart(){

        super.onRestart();
        Anvandarnamn = AnvandarID.getText().toString();
        IDButLayout.removeAllViewsInLayout();
        String method = "hämtadata";
        fourthActivity.GetDataActivity GetDataActivity = new fourthActivity.GetDataActivity(this);
        GetDataActivity.execute(method, Anvandarnamn);

    }

    public void onClickClose(View view) {
        finish();
    }

    public void userReg(View view)
    {
        startActivity(new Intent(this,RegUsersActivity.class));
    }

    public void APLReguser(View view)
    {
        startActivity(new Intent(this,APLRegUsers.class));
    }

    public void UserNarvaro(View view) {
        startActivity(new Intent(this,CalenderActivity.class));
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
                    Log.d("fel", sFörnamn);

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


                final String finalSAnvadarID = sAnvadarID;

                ImageView RMButton = new ImageView(fourthActivity.this);
                RMButton.setImageResource(R.drawable.ic_launcher_background);

                RMButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(ctx, RemoveUsersActivity.class);
                        intent.putExtra("AnvandarID", finalSAnvadarID);
                        startActivity(intent);
                    }
                });


                    Button IDButton = new Button(fourthActivity.this);
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
                    IDButton.setText(sFörnamn);




                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());

                RMButton.setPadding(0,16,0,0);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);


                RmButlayout.addView(RMButton, lp);

              //  b.append(sFörnamn + "\n");
                c.append(sEfternamn + "\n");

            }

            //Förnamn.setText(b.toString());
            Efternamn.setText(c.toString());

        }

    }

}