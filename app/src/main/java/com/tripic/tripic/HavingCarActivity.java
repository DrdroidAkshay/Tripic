package com.tripic.tripic;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;

public class HavingCarActivity extends AppCompatActivity{

    EditText from,to;
    TextView date,time;
    Button submitbtn;
    String status;
    String details;
    TimePickerDialog timePickerDialog;
    int mYear,mMonth,mDay;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_having_car);

        from=findViewById(R.id.from_id);
        to=findViewById(R.id.to_id);
        date=findViewById(R.id.date_id);
        time=findViewById(R.id.time_id);
        submitbtn=findViewById(R.id.havingcarbookingbtn_id);


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickdate();


            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    timePickerDialog=new TimePickerDialog(HavingCarActivity.this,(timepicker,hourofday,minutes)->{
                        time.setText(hourofday + ":" + minutes);
                    }, 0,0,false
                            );
                    timePickerDialog.show();
            }

        });
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               createRide();

            }
        });
    }
    private void createRide(){
        SharedPreferences sharedPreferences= getSharedPreferences("userdetails", 0);

        String username=sharedPreferences.getString("username","");
        String userphone=sharedPreferences.getString("userphone","");
        String ridefrom=from.getText().toString().trim();
        String rideto=to.getText().toString().trim();
        String ridedate=date.getText().toString().trim();
        String ridetime=time.getText().toString().trim();

        username=username.replace(" ","+");
        userphone=userphone.replace(" ","+");
        ridefrom=ridefrom.replace(" ","+");
        rideto=rideto.replace(" ","+");
        ridedate=ridedate.replace(" ","+");
        ridetime=ridetime.replace(" ","+");
        create(username,userphone,ridefrom,rideto,ridedate,ridetime);
    }
    private void create(final String username,final String userphone,final String ridefrom, final String rideto, final String ridedate, final  String ridetime){
        final String urlsuffix="?username="+username+"&userphone="+userphone+"&ridefrom="+ridefrom+"&rideto="+rideto+"&ridedate="+ridedate+"&ridetime="+ridetime;
        class CreateRide extends AsyncTask<String,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                Log.i("000000","fghhhhjj");
                super.onPreExecute();
                loading=ProgressDialog.show(HavingCarActivity.this,"Please wait",null,true,true);
            }
            @Override
            protected String doInBackground(String... params) {
                URL url = null;

                try {
                    Log.i("000000","fghhhhjj");
                    url = new URL("http://fullmoonfilms.000webhostapp.com/createride.php?username="+username+"&userphone="+userphone+"&ridefrom="+ridefrom+"&rideto="+rideto+"&ridedate="+ridedate+"&ridetime="+ridetime);
                } catch (MalformedURLException e) {
                    Log.i("wwwwwww","fghhhhjj");
                    e.printStackTrace();
                }
                HttpURLConnection urlConnection = null;
                try {
                    Log.i("aaaaaa","fghhhhjj");
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("yyyyyyy","fghhhhjj");
                }
                try {
                    Log.i("dddddd","fghhhhjj");
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
                    String line="";
                    line=bufferedReader.readLine();
                    JSONArray jsonArray= new JSONArray(line);
                    JSONObject jsonObject= (JSONObject) jsonArray.get(0);
                    Log.i("mmmmmmmm","fghhhhjj");
                    status= (String) jsonObject.get("status");
                    details= (String) jsonObject.get("details");

                } catch (IOException e) {
                    Log.i("vvvvvvv","fghhhhjj");
                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.i("kkkkkk","fghhhhjj");
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
                Log.i("aaaaaa","fghhhhjj");
                if (status.equals("success")){
                    Toast.makeText(getApplicationContext(), details,
                            Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(HavingCarActivity.this,showridesforcreateride.class);
                    Log.i("aaaaaaaaaaaaaaaaaa","bbbbbbb");
                    Log.i("bbbbbbbbbbbbbbb",rideto);
                    SharedPreferences sharedPreferences= getSharedPreferences("userdetails", 0);
                    Log.i("aaaaaaaaaaaaaaaaaa",rideto);
                    SharedPreferences.Editor editor;
                    editor=sharedPreferences.edit();
                    editor.putString("rideto",rideto);
                    editor.putString("ridefrom",ridefrom);
                    editor.putString("ridedate",ridedate);
                    editor.putString("ridetime",ridetime);
                    editor.commit();
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), details,
                            Toast.LENGTH_LONG).show();
                }
            }


        }
        CreateRide createRide=new CreateRide();
        createRide.execute(urlsuffix);
    }


    public void pickdate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        date.setText(MONTHS[monthOfYear]+" "+dayOfMonth + ", " + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
