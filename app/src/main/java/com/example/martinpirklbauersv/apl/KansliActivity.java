package com.example.martinpirklbauersv.apl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
xml fil för denna aktivitet är "activity_kansli"
Beskriving:
Här har vi Kanslistens startsida där hen ska kunna se Närvaro och annat material.


indata:
man kan inte skriva in någon data.

utdata:
Ingen utdata finns.

*/

public class KansliActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kansli);
    }
}
