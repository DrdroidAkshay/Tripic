package com.tripic.tripic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class NotHavingCarActivity extends AppCompatActivity {

    EditText from,to,date;
    Button submitbtn;
    String status;
    String details;
//    ListView listView;
//    ArrayList<String> arrayList;
//    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_having_car);

        from=findViewById(R.id.from_id);
        to=findViewById(R.id.to_id);
        date=findViewById(R.id.date_id);
        submitbtn=findViewById(R.id.havingcarbookingbtn_id);
//        listView=findViewById(R.id.listview_id);
//
//        arrayList=new ArrayList<String>();
//
//            arrayList.add("Available Rides will appear Here:" );
//
//        arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
//        listView.setAdapter(arrayAdapter);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                arrayList.add(from.getText().toString());
//
//                createRide();
                Intent intent=new Intent(NotHavingCarActivity.this,showridesforhaveride.class);
                startActivity(intent);
            }
        });
    }
    private void createRide(){
        String ridefrom=from.getText().toString().trim();
        String rideto=to.getText().toString().trim();
        String ridedate=date.getText().toString().trim();
        create(ridefrom,rideto,ridedate);
    }
    private void create(final String ridefrom, final String rideto, final String ridedate){
        final String urlsuffix="?ridefrom="+ridefrom+"&rideto="+rideto+"&ridedate="+ridedate;
        class CreateRide extends AsyncTask<String,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading=ProgressDialog.show(NotHavingCarActivity.this,"Please wait",null,true,true);
            }
            @Override
            protected String doInBackground(String... params) {
                URL url = null;
                try {
                    url = new URL("http://fullmoonfilms.000webhostapp.com/haveride.php?ridefrom="+ridefrom+"&rideto="+rideto+"&ridedate="+ridedate);
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
                    Intent intent=new Intent(NotHavingCarActivity.this,showridesforhaveride.class);
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
}

