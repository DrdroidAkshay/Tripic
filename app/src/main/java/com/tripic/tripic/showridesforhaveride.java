package com.tripic.tripic;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class showridesforhaveride extends AppCompatActivity {
    private String URL="";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    String username,userphone,ridedate,ridefrom,rideto,ridetime;
    String url;
    private List<Listitem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showridesforhaveride);

        recyclerView=findViewById(R.id.recyclerview_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        listItems = new ArrayList<>();

        SharedPreferences sharedPreferences= getSharedPreferences("userdetails", 0);
        String name=sharedPreferences.getString("username","");
        String phone=sharedPreferences.getString("userphone","");
        String date=sharedPreferences.getString("ridedate","");
        String time=sharedPreferences.getString("ridetime","");
        String to=sharedPreferences.getString("rideto","");
        String from=sharedPreferences.getString("ridefrom","");
        name=name.replace(" ","+");
        date=date.replace(" ","+");
        time=time.replace(" ","+");
        phone=phone.replace(" ","+");
        to=to.replace(" ","+");
        from=from.replace(" ","+");


        url = "http://fullmoonfilms.000webhostapp.com/showlistforhaveride.php?username="+name+"&userphone="+phone+"&ridedate="+date+"&ridetime="+time+"&rideto="+to+"&ridefrom="+from;

        loaddata();
    }
    private void loaddata(){
        ProgressDialog loading=ProgressDialog.show(showridesforhaveride.this,"Please wait",null,true,true);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try {

                            Log.i("ccccccc","ttttttttt");
                            JSONArray jsonArray=new JSONArray(response);
                            Log.i("lllllllllll","ttttttttt");
                            for (int i=0;i<jsonArray.length();i++){
                                Log.i("wwwwwwwww","ttttttttt");
                                JSONObject o=jsonArray.getJSONObject(i);
                                Listitem item= new Listitem(
                                        o.getString("username"),
                                        o.getString("userphone"),
                                        o.getString("ridefrom"),
                                        o.getString("rideto"),
                                        o.getString("ridedate"),
                                        o.getString("ridetime"));
                                listItems.add(item);
                            }
                            Log.i("jjjjjjjj","ttttttttt");

                            adapter=new MyAdapter(listItems,getApplicationContext());
                            Log.i("00000000","aaaaaaaa");
                            recyclerView.setAdapter(adapter);
                            Log.i("vvvvvvv","lllllllll");
                        } catch (JSONException e) {
                            e.printStackTrace();
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
}
