package com.tripic.tripic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Riderdetails extends AppCompatActivity {

    TextView name,fromto,date,time;
    ImageView profilepic;
    String profilename,profilefromto,profiledate,profiletime;
    Button sendrequestbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riderdetails);

        name=findViewById(R.id.profilename_id);
        fromto=findViewById(R.id.profilefromto_id);
        date=findViewById(R.id.profiledate_id);
        time=findViewById(R.id.profiletime_id);
        profilepic=findViewById(R.id.profilepic_id);
        sendrequestbtn=findViewById(R.id.sendrequestbtn_id);

        profilename=getIntent().getStringExtra("profilename");
        profilefromto=getIntent().getStringExtra("profilefromto");
        profiledate=getIntent().getStringExtra("profiledate");
        profiletime=getIntent().getStringExtra("profiletime").trim();

        name.setText(profilename);
        fromto.setText(profilefromto);
        date.setText(profiledate);
        time.setText("Time: "+profiletime);

        sendrequestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Riderdetails.this,"Request Sent",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
