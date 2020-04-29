package com.tripic.tripic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

public class MyAccount extends AppCompatActivity {

    CircularImageView profilepic,profilepicchangebtn;
    TextView myaccountname,myaccountphone;
    String name,phone;
    private  static final int gallerypic=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        profilepic=findViewById(R.id.img_profile);
        profilepicchangebtn=findViewById(R.id.img_plus);
        myaccountname=findViewById(R.id.myaccountname_id);
        myaccountphone=findViewById(R.id.myaccountphone_id);

        SharedPreferences sharedPreferences = getSharedPreferences("userdetails", 0);
        name = sharedPreferences.getString("username", "");
        phone = sharedPreferences.getString("userphone", "");

        myaccountname.setText(name);
        myaccountphone.setText(phone);

        profilepicchangebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryintent=new Intent();
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent,gallerypic);
            }
        });
    }
}
