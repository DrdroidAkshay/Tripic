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

public class NotificationDetailActivity extends AppCompatActivity {

    ImageView profilepic;
    TextView name, phone, requeststatus, contactmethod;
    Button acceptrequestbtn, cancelrequestbtn;
    String profilename, profilephone, status;
    String accepturl, cancelurl;
    String username, userphone;
    String result, details;
    String loadimageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        profilepic = findViewById(R.id.profilepic_id);
        name = findViewById(R.id.profilename_id);
        phone = findViewById(R.id.profilephone_id);
        requeststatus = findViewById(R.id.requeststatus_id);
        contactmethod = findViewById(R.id.contactmethod_id);
        acceptrequestbtn = findViewById(R.id.acceptrequestbtn_id);
        cancelrequestbtn = findViewById(R.id.cancelrequestbtn_id);

        profilename = getIntent().getStringExtra("requestername");
        profilephone = getIntent().getStringExtra("requesterphone");
        status = getIntent().getStringExtra("requeststatus");

        SharedPreferences sharedPreferences = getSharedPreferences("userdetails", 0);
        username = sharedPreferences.getString("username", "");
        userphone = sharedPreferences.getString("userphone", "");
        username = username.replace(" ", "+");
        userphone = userphone.replace(" ", "+");
        name.setText(profilename);
        phone.setText(profilephone);
        if (status.equals("accepted")) {
            acceptrequestbtn.setVisibility(View.GONE);
            cancelrequestbtn.setVisibility(View.GONE);
        } else {
            requeststatus.setVisibility(View.GONE);
            contactmethod.setVisibility(View.GONE);
        }


        acceptrequestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilename = profilename.replace(" ", "+");
                profilephone = profilephone.replace(" ", "+");
                accepturl = "http://fullmoonfilms.000webhostapp.com/acceptrequest.php?username1=" + profilename + "&userphone1=" + profilephone + "&username2=" + username + "&userphone2=" + userphone;
                acceptRequest();
            }
        });
        cancelrequestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilename = profilename.replace(" ", "+");
                profilephone = profilephone.replace(" ", "+");
                cancelurl = "http://fullmoonfilms.000webhostapp.com/cancelrequest.php?username1=" + profilename + "&userphone1=" + profilephone + "&username2=" + username + "&userphone2=" + userphone;
                cancelRequest();
            }
        });
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilename = profilename.replace(" ", "+");
                loadimageurl = "http://fullmoonfilms.000webhostapp.com/Userprofilepics/" + profilename + ".jpeg";
                loadimages(loadimageurl, profilepic);
            }
        });

    }

    private void acceptRequest() {
        ProgressDialog loading = ProgressDialog.show(NotificationDetailActivity.this, "Please wait", null, true, true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, accepturl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject o = jsonArray.getJSONObject(0);
                            result = o.getString("status");
                            details = o.getString("details");
                            Toast.makeText(NotificationDetailActivity.this, details,
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void cancelRequest() {
        ProgressDialog loading = ProgressDialog.show(NotificationDetailActivity.this, "Please wait", null, true, true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, cancelurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject o = jsonArray.getJSONObject(0);
                            result = o.getString("status");
                            details = o.getString("details");
                            Toast.makeText(NotificationDetailActivity.this, details,
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void loadimages(String loadimageurl, ImageView profilepic) {

        Picasso.with(NotificationDetailActivity.this).load(loadimageurl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(profilepic, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });


    }
}
