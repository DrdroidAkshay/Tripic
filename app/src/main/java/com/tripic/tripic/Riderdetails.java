package com.tripic.tripic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Riderdetails extends AppCompatActivity {

    TextView name,fromto,date,time;
    LinearLayout profilepiclayout;
    String profilename,profilefromto,profiledate,profiletime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riderdetails);

        name=findViewById(R.id.profilename_id);
        fromto=findViewById(R.id.profilefromto_id);
        date=findViewById(R.id.profiledate_id);
        time=findViewById(R.id.profiletime_id);
        profilepiclayout=findViewById(R.id.profilepic_id);
        profilename=getIntent().getStringExtra("profilename");
        profilefromto=getIntent().getStringExtra("profilefromto");
        profiledate=getIntent().getStringExtra("profiledate");
        profiletime=getIntent().getStringExtra("profiletime").trim();
        name.setText(profilename);
        fromto.setText(profilefromto);
        date.setText(profiledate);
        time.setText(profiletime);
    }
}
