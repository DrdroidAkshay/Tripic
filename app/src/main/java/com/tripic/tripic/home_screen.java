package com.tripic.tripic;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import static com.tripic.tripic.R.*;

public class home_screen extends AppCompatActivity {

    DrawerLayout drawerLayout;
    android.support.v7.widget.Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_home_screen);
        setuptoolbar();


    }
    private void setuptoolbar(){
        drawerLayout=  findViewById(id.drawerlayout_id);
        toolbar=  findViewById(id.toolbar_id);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout, toolbar, string.app_name, string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
