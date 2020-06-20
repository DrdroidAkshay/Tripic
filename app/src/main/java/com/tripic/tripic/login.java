package com.tripic.tripic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class login extends AppCompatActivity {

    EditText phone,password;
    Button loginbtn;
    String Json_string;
    String status;
    String username;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor=sharedPreferences.edit();

        phone=findViewById(R.id.loginphone_id);
        password=findViewById(R.id.loginpassword_id);
        loginbtn=findViewById(R.id.loginbtn_id);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }
    private void loginUser(){
        String userphone=phone.getText().toString().trim();
        String userpassword=password.getText().toString().trim();
        userphone=userphone.replace(" ","+");
        userpassword=userpassword.replace(" ","+");
        login(userphone,userpassword);
    }
    private void login(final String userphone, final String userpassword){
        final String urlsuffix="?userphone="+userphone+"&userpassword="+userpassword;
        class Loginuser extends AsyncTask<String,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading=ProgressDialog.show(login.this,"Please wait",null,true,true);
            }
            @Override
            protected String doInBackground(String... params) {
                URL url = null;
                try {
                    url = new URL("http://fullmoonfilms.000webhostapp.com/login.php?userphone="+userphone+"&userpassword="+userpassword);
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
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
                    String line="";
                    line=bufferedReader.readLine();
                    JSONArray jsonArray= new JSONArray(line);
                    JSONObject jsonObject= (JSONObject) jsonArray.get(0);
                    status= (String) jsonObject.get("status");
                    username= (String) jsonObject.get("name");


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
                if (status.equals("success")){

                    sharedPreferences = getSharedPreferences("userdetails", 0);
                    editor=sharedPreferences.edit();
                    editor.putString("loginstatus","true");
                    editor.putString("username",username);
                    editor.putString("userphone",userphone);
                    editor.commit();
                    Intent intent=new Intent(login.this,home_screen.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Incorrect Details",
                            Toast.LENGTH_LONG).show();
                }
            }


        }
        Loginuser loginuseruser=new Loginuser();
        loginuseruser.execute(urlsuffix);
    }
}
