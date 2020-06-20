package com.tripic.tripic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splashscreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        sharedPreferences = getSharedPreferences("userdetails", 0);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                String loginstatus=sharedPreferences.getString("loginstatus","");
                Log.i("aaaaaaaaaaaaaaa",loginstatus);
                if (loginstatus.equals("true")) {
                    Intent mainIntent = new Intent(splashscreen.this, home_screen.class);
                    splashscreen.this.startActivity(mainIntent);
                }
                else{
                    Intent mainIntent = new Intent(splashscreen.this, registration.class);
                    splashscreen.this.startActivity(mainIntent);
                }
                splashscreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
        ImageView image = (ImageView)findViewById(R.id.logo1);
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade);
        image.startAnimation(animation1);
    }

}
