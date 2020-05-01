package com.tripic.tripic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

        myaccountname.setText(name);
        myaccountphone.setText(phone);


        profilepicchangebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),resultUri);
                    profilepic.setImageBitmap(bitmap);
                    image=imagetostring(bitmap);
                    uploadurl="http://fullmoonfilms.000webhostapp.com/uploadprofilepic.php?name="+name+"profilepic&image="+image;
                    uploadimage();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
    private void uploadimage(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, uploadurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            JSONObject o=jsonArray.getJSONObject(0);
                            status=  o.getString("status");
                            details=  o.getString("details");
                            Toast.makeText(MyAccount.this,details,
                                    Toast.LENGTH_LONG).show();
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params=new HashMap<>();
//                params.put("name",name+"profilepic");
//                params.put("image",imagetostring(bitmap));
//                return params;
//            }
//        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private String imagetostring( Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgbytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgbytes,Base64.DEFAULT);
    }

}
