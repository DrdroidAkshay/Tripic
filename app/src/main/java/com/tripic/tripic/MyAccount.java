package com.tripic.tripic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyAccount extends AppCompatActivity {

    CircularImageView profilepic,profilepicchangebtn;
    TextView myaccountname,myaccountphone;
    String name,phone,status,details;
    private  static final int gallerypic=1;
    Bitmap bitmap;
    String image;
    private String uploadurl;



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
        name=name.replace(" ", "+");
        myaccountname.setText(name);
        myaccountphone.setText(phone);
        Log.i("aaaaaaaaaa","aaaaaaaaaaaa");
        uploadurl = "http://fullmoonfilms.000webhostapp.com/uploadprofilepic.php?name="+name+"profilepic&image="+image;


        profilepicchangebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("bbbbbbbbbbb","bbbbbbbbbb");

                Intent galleryintent=new Intent();
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent,gallerypic);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==gallerypic && resultCode == RESULT_OK && data!=null){
            Uri imageuri=data.getData();
            Log.i("ccccccccccc","cccccccccc");


//            CropImage.activity()
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setAspectRatio(1,1)
//                    .start(this);

            CropImage.activity(imageuri)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Log.i("dddddddddd","ddddddddddd");

            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.i("eeeeeeeee","eeeeeeeeee");

                try {
                    Log.i("zzzzzzzzz","aaaaaaaaaaaa");

                    bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),resultUri);
                    Log.i("yyyyyyyy","aaaaaaaaaaaa");

                    profilepic.setImageBitmap(bitmap);
                    image=imagetostring(bitmap);
                    Log.i("xxxxxxxx","aaaaaaaaaaaa");

                    uploadurl="http://fullmoonfilms.000webhostapp.com/uploadprofilepic.php?name="+name+"profilepic&image="+image;

                    Log.i("ffffffffffff","fffffffffff");

                    uploadimage(name,image);
                } catch (IOException e) {
                    Log.i("gggggggg",String.valueOf(e));

                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Log.i("hhhhhhhhhhh","hhhhhhhhhh");

                Exception error = result.getError();
            }
        }

    }
//    private void uploadimage(){
//        StringRequest stringRequest=new StringRequest(Request.Method.GET, uploadurl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            Log.i("iiiiiiiiiii","iiiiiiiiii");
//
//                            JSONArray jsonArray=new JSONArray(response);
//                            JSONObject o=jsonArray.getJSONObject(0);
//                            status=  o.getString("status");
//                            details=  o.getString("details");
//                            Toast.makeText(MyAccount.this,details,
//                                    Toast.LENGTH_LONG).show();
//                        } catch (JSONException ex) {
//                            Log.i("jjjjjjjjjj","jjjjjjjjjj");
//
//                            ex.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener(){
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i("kkkkkkkkkk",String.valueOf(error));
//
//
//                    }
//                });
////        {
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////                Map<String,String> params=new HashMap<>();
////                params.put("name",name+"profilepic");
////                params.put("image",imagetostring(bitmap));
////                return params;
////            }
////        };
//        RequestQueue requestQueue= Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//    }

private void uploadimage(final String name, final String image){
    class Upload extends AsyncTask<String,Void,String> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading=ProgressDialog.show(MyAccount.this,"Please wait",null,true,true);
        }
        @Override
        protected String doInBackground(String... params) {
            Log.i("mmmmmm","mmmmmm");
            URL url = null;
            try {
                url = new URL("http://fullmoonfilms.000webhostapp.com/uploadprofilepic.php?name="+name+"profilepic&image="+bitmap);
                Log.i("nnnnnn","nnnnnnn");
            } catch (MalformedURLException e) {
                Log.i("lllllll",String.valueOf(e));
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                Log.i("oooooooo","ppppppp");
            } catch (IOException e) {
                Log.i("sssssss",String.valueOf(e));
                e.printStackTrace();
            }
            try {
                Log.i("pppppp","pppppp");
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Log.i("lllllll","llllllll");
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
                Log.i("mmmmmm","llllllll");
                String line="";
                Log.i("nnnnnnn","llllllll");
                line=bufferedReader.readLine();
                Log.i("lllllll","llllllll");
                JSONArray jsonArray= new JSONArray(line);
                Log.i("qqqqqq","qqqqqq");
                JSONObject jsonObject= (JSONObject) jsonArray.get(0);
                status= (String) jsonObject.get("status");
                details= (String) jsonObject.get("details");
                Log.i("rrrrrr","rrrrrr");

            } catch (IOException e) {
                Log.i("pppppp",String.valueOf(e));
                e.printStackTrace();
            } catch (JSONException e) {
                Log.i("error",String.valueOf(e));
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

        }


    }
    Upload registeruser=new Upload();
    registeruser.execute(uploadurl);
}
    private String imagetostring( Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgbytes=byteArrayOutputStream.toByteArray();
        Log.i("lllllll","llllllll");

        return Base64.encodeToString(imgbytes,Base64.DEFAULT);

    }

}
