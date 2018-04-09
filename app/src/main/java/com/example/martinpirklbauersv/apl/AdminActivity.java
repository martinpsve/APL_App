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

public class AdminActivity extends AppCompatActivity {

    private DrawerLayout myDrawerLayout;

    private TextView AnvandarID;
    private TextView RoleID;
    private TextView AnvändarnID, AnvändarRoll;
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