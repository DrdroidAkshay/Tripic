package com.tripic.tripic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button createaccount;
        createaccount=findViewById(R.id.createaccountbtn_id);
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"you clicked on mobile",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(login.this,home_screen.class);
                startActivity(intent);

            }
        });
//        Button mobile,facebook,google;
//        mobile=findViewById(R.id.using_mobile);
//        facebook=findViewById(R.id.facebook);
//        google=findViewById(R.id.google);
//
//
//
//        mobile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(getApplicationContext(),"you clicked on mobile",Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(login.this,home_screen.class);
//                startActivity(intent);
//
//            }
//        });
//
//
//
//
//
//        facebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(getApplicationContext(),"you clicked on facebook",Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//
//
//
//        google.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(getApplicationContext(),"you clicked on google",Toast.LENGTH_SHORT).show();
//
//            }
//        });



    }
}
