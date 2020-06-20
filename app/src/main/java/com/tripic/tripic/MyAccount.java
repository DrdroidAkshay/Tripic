package com.tripic.tripic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyAccount extends AppCompatActivity {

    CircularImageView profilepic,profilepicchangebtn;
    TextView myaccountname,myaccountphone;
    String name,phone,status,details,profileimage;
    private  static final int gallerypic=1;
    Bitmap bitmap;
    String image;
    private String uploadurl;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog loading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        profilepic=findViewById(R.id.img_profile);
        profilepicchangebtn=findViewById(R.id.img_plus);
        myaccountname=findViewById(R.id.myaccountname_id);
        myaccountphone=findViewById(R.id.myaccountphone_id);

        sharedPreferences = getSharedPreferences("userdetails", 0);
        name = sharedPreferences.getString("username", "");
        phone = sharedPreferences.getString("userphone", "");
        profileimage = sharedPreferences.getString("profileimage", "");
        if (!profileimage.equalsIgnoreCase("")){
            byte[] b = Base64.decode(profileimage, Base64.DEFAULT);
            Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
            profilepic.setImageBitmap(bm);
        }
        String myname=name.replace("+", " ");
        myaccountname.setText(myname);
        myaccountphone.setText(phone);
        name=name.replace(" ", "+");
        String loadimageurl = "http://fullmoonfilms.000webhostapp.com/Userprofilepics/" + phone + ".jpeg";
        loadimages(loadimageurl, profilepic);

        Log.i("aaaaaaaaaa","aaaaaaaaaaaa");
        uploadurl = "http://fullmoonfilms.000webhostapp.com/uploadprofilepic.php?name="+name+"&image="+image+"&phone="+phone;


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
                    Log.i("image",image);
                    Log.i("xxxxxxxx","aaaaaaaaaaaa");
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
    private void uploadimage(final String name, final String image) {
//        loading=ProgressDialog.show(MyAccount.this,"Please wait",null,true,true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("iiiiiiiiiii", "iiiiiiiiii");

                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject o = jsonArray.getJSONObject(0);
                            status = o.getString("status");
                            details = o.getString("details");
                            Toast.makeText(MyAccount.this, details,
                                    Toast.LENGTH_LONG).show();
                            if (status.equals("success")){
//                                loading.dismiss();
                                editor=sharedPreferences.edit();
                                editor.putString("profileimage",image);
                                editor.commit();
                            }
                        } catch (JSONException ex) {
                            Log.i("jjjjjjjjjj", "jjjjjjjjjj");

                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("kkkkkkkkkk", String.valueOf(error));


                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("image", image);
                params.put("phone", phone);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private String imagetostring( Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,30,byteArrayOutputStream);
        byte[] imgbytes=byteArrayOutputStream.toByteArray();
        Log.i("lllllll","llllllll");

        return Base64.encodeToString(imgbytes,Base64.DEFAULT);

    }
    private void loadimages(String loadimageurl, ImageView profilepic) {

        Picasso.with(MyAccount.this).load(loadimageurl).error(R.drawable.profilepic).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(profilepic, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });


    }

}
