package com.tripic.tripic;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Riderdetails extends AppCompatActivity {

    TextView name,fromto,date,time;
    ImageView profilepic;
    String profilename,profilephone,profilefromto,profiledate,profiletime,username,userphone;
    Button sendrequestbtn;
    String url;
    String status,details;
    String loadimageurl;

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
        profilename=profilename.replace(" ","+");
        profilephone=getIntent().getStringExtra("profilephone");
        profilefromto=getIntent().getStringExtra("profilefromto");
        profiledate=getIntent().getStringExtra("profiledate");
        profiletime=getIntent().getStringExtra("profiletime").trim();

        name.setText(profilename);
        fromto.setText(profilefromto);
        date.setText(profiledate);
        time.setText("Time: "+profiletime);

        SharedPreferences sharedPreferences= getSharedPreferences("userdetails", 0);
        username=sharedPreferences.getString("username","");
        username=username.replace(" ","+");
        userphone=sharedPreferences.getString("userphone","");
        userphone=userphone.replace(" ","+");

        profilephone = profilephone.replace(" ", "+");
        loadimageurl = "http://fullmoonfilms.000webhostapp.com/Userprofilepics/" + profilephone + ".jpeg";
        loadimages(loadimageurl, profilepic);

        sendrequestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                url = "http://fullmoonfilms.000webhostapp.com/requeststatus.php?username1="+ username+"&userphone1="+userphone+"&username2="+profilename +"&userphone2="+profilephone;
                sendRequest();
            }
        });

    }
    private void sendRequest(){
        ProgressDialog loading=ProgressDialog.show(Riderdetails.this,"Please wait",null,true,true);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                                JSONObject o=jsonArray.getJSONObject(0);
                                      status=  o.getString("status");
                                      details=  o.getString("details");
                            Toast.makeText(Riderdetails.this,details,
                                    Toast.LENGTH_LONG).show();
                            } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void loadimages(String loadimageurl, ImageView profilepic) {

        Picasso.with(Riderdetails.this).load(loadimageurl).error(R.drawable.profilepic).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(profilepic, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });


    }
}
