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
import android.text.style.UpdateLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.ArrayList;
import java.util.Objects;

public class fourthActivity extends AppCompatActivity {

    private DrawerLayout myDrawerLayout;

    private TextView AnvandarID;
    private TextView RoleID;
    private TextView AnvändarnID, AnvändarRoll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

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

                        case R.id.nav_startpage:
                            Intent fourthActivity = new Intent(fourthActivity.this,fourthActivity.class);
                            startActivity(fourthActivity);
                            break;

                        case R.id. nav_setNarvaro:
                            Intent CalenderActivity = new Intent(fourthActivity.this,CalenderActivity.class);
                            startActivity(CalenderActivity);
                            break;

                        case R.id.nav_Narvaro:
                            Intent CalenderReadActivity = new Intent(fourthActivity.this,CalenderReadActivity.class);
                            startActivity(CalenderReadActivity);
                            break;

                        case R.id.nav_regUsersApl:
                            Intent  APLRegUsers = new Intent(fourthActivity.this, APLRegUsers.class);
                            startActivity( APLRegUsers);
                         break;

                        case R.id.nav_regUsers:
                            Intent  RegUsersActivity = new Intent(fourthActivity.this, RegUsersActivity.class);
                            startActivity( RegUsersActivity);
                            break;

                        case R.id.nav_listUsers:
                            Intent  userListActivity = new Intent(fourthActivity.this, userListActivity.class);
                            startActivity( userListActivity);
                            break;

                        case R.id.    navl_logOut:
                            Intent  MainActivity = new Intent(fourthActivity.this, MainActivity.class);
                            startActivity( MainActivity);
                            break;
                    }


                                menuItem.setChecked(true);

                                myDrawerLayout.closeDrawers();

                                return true;
                            }
                        });

      /*  Menu menuItem = navView.getMenu();
        menuItem.findItem(R.id.nav_startpage).setVisible(false);
*/
        AnvandarID = (TextView) findViewById(R.id.användarID);
        String s = getIntent().getStringExtra("AnvandarID");
        AnvandarID.setText(s);

        RoleID = (TextView) findViewById(R.id.text2);
        String r = getIntent().getStringExtra("Role");
        RoleID.setText(r);

        //ANVÄNDARNAMN OCH ROLL I DRAWERN
        View nameView = navView.getHeaderView(0);

        AnvändarnID = (TextView) nameView.findViewById(R.id.userName);
        AnvändarnID.setText(s);

        AnvändarRoll = (TextView) nameView.findViewById(R.id.userRoll);
        AnvändarRoll.setText(r);

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


    @Override
    public void onRestart(){

        super.onRestart();

    }

}