package com.tripic.tripic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static com.tripic.tripic.R.*;

public class home_screen extends AppCompatActivity {

    DrawerLayout drawerLayout;
    android.support.v7.widget.Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    MenuItem myaccount,bookyourseats,payments,Referandearn,knowyourrides,about,settings,logout;
    RelativeLayout relativeLayout;
    LinearLayout bottomtabs;
    Animation slidedowntop,slideuptop,slidedownbottom,slideupbottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_homescreen);
        setuptoolbar();

        navigationView=findViewById(id.navigationview_id);
        Menu menu=navigationView.getMenu();
        myaccount= menu.findItem(id.myaccount_id);
        bookyourseats=menu.findItem(id.Booking_id);
        payments=menu.findItem(id.Payment_id);
        Referandearn=menu.findItem(id.Refer_id);
        knowyourrides=menu.findItem(id.Knowride_id);
        about=menu.findItem(id.About_id);
        settings=menu.findItem(id.settings_id);
        logout=menu.findItem(id.logout_id);
        bottomtabs =findViewById(id.bottomtabs_id);
        toolbar=  findViewById(id.toolbar_id);
        relativeLayout=findViewById(id.relativelayout_id);

        slideuptop= AnimationUtils.loadAnimation(this,R.anim.slideuptop);
        slidedowntop= AnimationUtils.loadAnimation(this, anim.slidedowntop);
        slideupbottom= AnimationUtils.loadAnimation(this, anim.slideupbottom);
        slidedownbottom= AnimationUtils.loadAnimation(this, anim.slidedownbottom);

        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (toolbar.isShown()) {
//                        toolbar.setVisibility(View.INVISIBLE);
//                        bottomtabs.setVisibility(View.INVISIBLE);
                        toolbar.startAnimation(slideuptop);
                        toolbar.setVisibility(View.INVISIBLE);
                        bottomtabs.startAnimation(slidedownbottom);
                        bottomtabs.setVisibility(View.INVISIBLE);
                    } else {
                        toolbar.startAnimation(slidedowntop);
                        toolbar.setVisibility(View.VISIBLE);
                        bottomtabs.startAnimation(slideupbottom);
                        bottomtabs.setVisibility(View.VISIBLE);
                    }
                    return true;
                } else return false;
            }
        });

        myaccount.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent=new Intent(home_screen.this,MyAccount.class);
                startActivity(intent);
                return true;
            }
        });
        bookyourseats.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent=new Intent(home_screen.this,BookYourSeats.class);
                startActivity(intent);
                return true;
            }
        });
        payments.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent=new Intent(home_screen.this,Payments.class);
                startActivity(intent);
                return true;
            }
        });
        Referandearn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent=new Intent(home_screen.this,ReferAndEarn.class);
                startActivity(intent);
                return true;
            }
        });
        knowyourrides.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent=new Intent(home_screen.this,KnowYourRides.class);
                startActivity(intent);
                return true;
            }
        });
        about.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent=new Intent(home_screen.this,About.class);
                startActivity(intent);
                return true;
            }
        });
        settings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent=new Intent(home_screen.this,Settings.class);
                startActivity(intent);
                return true;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return true;
            }
        });
    }

    private void setuptoolbar(){
        drawerLayout=  findViewById(id.drawerlayout_id);
        toolbar=  findViewById(id.toolbar_id);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout, toolbar, string.app_name, string.app_name);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
