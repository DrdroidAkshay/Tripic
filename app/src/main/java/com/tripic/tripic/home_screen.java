package com.tripic.tripic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import static com.tripic.tripic.R.*;

public class home_screen extends AppCompatActivity {

    DrawerLayout drawerLayout;
    android.support.v7.widget.Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    MenuItem myaccount,bookyourseats,payments,Referandearn,knowyourrides,about,settings,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_home_screen);
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
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
