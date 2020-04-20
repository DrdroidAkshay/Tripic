package com.tripic.tripic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class registration extends AppCompatActivity {

    Button createaccount;
    EditText name, phone, password;
    TextView login;
    String status;
    String details;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final String REGISTER_URL="http://Fullmoonfilms.000webhostapp.com/registration.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        createaccount=findViewById(R.id.createaccountbtn_id);
        name=findViewById(R.id.name_id);
        phone=findViewById(R.id.phone_id);
        password=findViewById(R.id.password_id);
        login=findViewById(R.id.login_id);
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();


            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(registration.this, com.tripic.tripic.login.class);
                startActivity(intent);
            }
        });

    }
    private void registerUser(){
        String username=name.getText().toString().trim();
        String userphone=phone.getText().toString().trim();
        String userpassword=password.getText().toString().trim();
        username=username.replace(" ","+");
        userphone=userphone.replace(" ","+");
        userpassword=userpassword.replace(" ","+");
        register(username,userphone,userpassword);
    }
    private void register(final String username, final String userphone, final String userpassword){
        final String urlsuffix="?username="+username+"&userphone="+userphone+"&userpassword="+userpassword;
        class Registeruser extends AsyncTask<String,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading=ProgressDialog.show(registration.this,"Please wait",null,true,true);
            }
            @Override
            protected String doInBackground(String... params) {
                URL url = null;
                try {
                    url = new URL("http://fullmoonfilms.000webhostapp.com/registration.php?username="+username+"&userphone="+userphone+"&userpassword="+userpassword);
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
                    details= (String) jsonObject.get("details");

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
                loading.dismiss();
                if (status.equals("success")){
                    Toast.makeText(getApplicationContext(), details,
                            Toast.LENGTH_LONG).show();

                    sharedPreferences = getSharedPreferences("userdetails", 0);
                    editor=sharedPreferences.edit();
                    editor.putString("username",username);
                    editor.putString("userphone",userphone);
                    editor.putString("userpassword",userpassword);
                    editor.commit();
                    Intent intent=new Intent(registration.this,home_screen.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), details,
                            Toast.LENGTH_LONG).show();
                }
            }


        }
        Registeruser registeruser=new Registeruser();
        registeruser.execute(urlsuffix);
    }
}
