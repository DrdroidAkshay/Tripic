package com.tripic.tripic;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class showridesforcreateride extends AppCompatActivity {

    private String URL="";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    String username,userphone,ridedate,ridefrom,rideto,ridetime;
    String url;
    private List<Listitem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showridesforcreateride);
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

        Log.i("llllllllllllllllll",name);
        url = "http://fullmoonfilms.000webhostapp.com/showlistforcreateride.php?username="+name+"&userphone="+phone+"&ridedate="+date+"&ridetime="+time+"&rideto="+to+"&ridefrom="+from;

        loaddata();
//        loadRecyclerViewData();
    }
    private void loaddata(){
        ProgressDialog loading=ProgressDialog.show(showridesforcreateride.this,"Please wait",null,true,true);
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
    private void loadRecyclerViewData(){
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
        load(name,phone,date,time,to,from);
    }
    private void load(final String name,final String phone, final String date, final String time, final String to, final String from){
        final String urlsuffix="?username="+name+"&userphone="+phone+"&ridedate="+date+"&ridetime="+time+"&rideto="+to+"&ridefrom="+from;
        class Loginuser extends AsyncTask<String,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading=ProgressDialog.show(showridesforcreateride.this,"Please wait",null,true,true);
            }
            @Override
            protected String doInBackground(String... params) {
                java.net.URL url = null;
                try {
                    Log.i("llllllllllllllllll",name);
                    url = new URL("http://fullmoonfilms.000webhostapp.com/showlistforcreateride.php?username="+name+"&userphone="+phone+"&ridedate="+date+"&ridetime="+time+"&rideto="+to+"&ridefrom="+from);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Log.i("aaaaaaaa","bbbbbbbbb");
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    Log.i("ffffffff","aaaaaaaa");
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
                    Log.i("rrrrrrrrrrrrrr","ssssssss");
                    String line="";
                    Log.i("kkkkkkkkk","aaaaaaaa");
                    line=bufferedReader.readLine();
                    Log.i("ssssssssss","aaaaaaaa");
                    JSONArray jsonArray= new JSONArray(line);
//                    JSONObject jsonObject= (JSONObject) jsonArray.get(0);
//                    username= (String) jsonObject.get("username");
//                    userphone= (String) jsonObject.get("userphone");
//                    rideto= (String) jsonObject.get("rideto");
//                    ridefrom= (String) jsonObject.get("ridefrom");
//                    ridedate= (String) jsonObject.get("ridedate");
//                    ridetime= (String) jsonObject.get("ridetime");
                    Log.i("pppppppppp0","aaaaaaaa");
                            for (int i=0;i<jsonArray.length();i++){
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

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                Intent intent=new Intent(login.this,home_screen.class);
//                startActivity(intent);
                loading.dismiss();
//                if (!userphone.equals(phone)){
//
//
//                }
//                else {
//
//                }
            }


        }
        Loginuser loginuseruser=new Loginuser();
        loginuseruser.execute(urlsuffix);
    }

}
