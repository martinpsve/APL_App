package com.example.martinpirklbauersv.apl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/*

Beskriving:
Här har vi en funktion som ska vara till för att tabort användare

indata:
ingen indata möjlig.

utdata:
ingen utdata möjlig.

*/

public class RemoveUsersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_removeusers);
    }

    public void onClickClose(View view) {finish();}




}
