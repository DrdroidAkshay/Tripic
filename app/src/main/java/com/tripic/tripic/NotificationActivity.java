package com.tripic.tripic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    String url,url2;
    private List<Notificationitem> listItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    String username,userphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView=findViewById(R.id.notificationrecyclerview_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        listItems = new ArrayList<Notificationitem>();

        SharedPreferences sharedPreferences = getSharedPreferences("userdetails", 0);
        username = sharedPreferences.getString("username", "");
        userphone = sharedPreferences.getString("userphone", "");
        username = username.replace(" ", "+");
        userphone = userphone.replace(" ", "+");
        url = "http://fullmoonfilms.000webhostapp.com/notificationmanagement.php?username=" + username+"&userphone="+userphone;
        url2 = "http://fullmoonfilms.000webhostapp.com/acceptnotificationmanagement.php?username=" + username+"&userphone="+userphone;

        notificationcheck();
        acceptnotificationcheck();
    }

    private void notificationcheck() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            Log.i("lllllllllll","ttttttttt");
                            for (int i=0;i<jsonArray.length();i++){
                                Log.i("wwwwwwwww","ttttttttt");
                                JSONObject o=jsonArray.getJSONObject(i);
                                Notificationitem item= new Notificationitem(
                                        o.getString("requestername"),
                                        o.getString("requesterphone"),
                                        o.getString("requeststatus")
                                );
                                listItems.add(item);
                            }
                            Log.i("jjjjjjjj","ttttttttt");

                            adapter=new NotificationAdapter(listItems,getApplicationContext());
                            Log.i("00000000","aaaaaaaa");
                            recyclerView.setAdapter(adapter);

                        } catch (Exception e) {
                            e.printStackTrace();
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
    private void acceptnotificationcheck() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            Log.i("lllllllllll","ttttttttt");
                            for (int i=0;i<jsonArray.length();i++){
                                Log.i("wwwwwwwww","ttttttttt");
                                JSONObject o=jsonArray.getJSONObject(i);
                                Notificationitem item= new Notificationitem(
                                        o.getString("requestername"),
                                        o.getString("requesterphone"),
                                        o.getString("requeststatus")
                                        );
                                listItems.add(item);
                            }
                            Log.i("jjjjjjjj","ttttttttt");

                            adapter=new NotificationAdapter(listItems,getApplicationContext());
                            Log.i("00000000","aaaaaaaa");
                            recyclerView.setAdapter(adapter);

                        } catch (Exception e) {
                            e.printStackTrace();
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
}
