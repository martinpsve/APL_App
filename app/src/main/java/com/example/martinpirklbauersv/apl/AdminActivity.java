package com.example.martinpirklbauersv.apl;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


/*
xml fil för denna aktivitet är "activity_admin"
Beskriving:
Här har vi Admins startsida där hen kommer åt de olika funktionerna via
en Drawer.

De olika funktionerna:
Lista användare
Registrera användare
koppla användare med apl-period
Rapportera närvaro på elever


indata:
ingen indata möjlig.

utdata:
Admins Användarnamn och Roll Bekräftelse skriv ut på skärmen.
Denna Utdata skickas vidare ifrån LoginActivity

*/

public class AdminActivity extends AppCompatActivity {

    private DrawerLayout myDrawerLayout;

    private TextView AnvändarID, AnvändarRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

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
                            Intent CalenderActivity = new Intent(AdminActivity.this,SetNarvaroActivity.class);
                            startActivity(CalenderActivity);
                            break;

                        case R.id.nav_Narvaro:
                            Intent CalenderReadActivity = new Intent(AdminActivity.this,ListNarvaroActivity.class);
                            startActivity(CalenderReadActivity);
                            break;

                        case R.id.nav_regUsersApl:
                            Intent  APLRegUsers = new Intent(AdminActivity.this, ConnectUsersToAPLActivity.class);
                            startActivity( APLRegUsers);
                         break;

                        case R.id.nav_regUsers:
                            Intent  RegUsersActivity = new Intent(AdminActivity.this, RegisterUsersActivity.class);
                            startActivity( RegUsersActivity);
                            break;

                        case R.id.nav_listUsers:
                            Intent  userListActivity = new Intent(AdminActivity.this, ListUsersActivity.class);
                            startActivity( userListActivity);
                            break;

                        case R.id.    navl_logOut:
                            Intent  MainActivity = new Intent(AdminActivity.this, LoginActivity.class);
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