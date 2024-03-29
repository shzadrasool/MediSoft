package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.myapplication.adapters.AdapterCommonMedi;
import com.google.android.material.navigation.NavigationView;

public class navigation_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    TextView name, contact;
    String user_name, user_con, uid;
    private static View view;

    int arraySize;
    ImageButton mImageBtn;
    TextView mCountTv;
    MenuItem mCartIconMenuItem;
    Context mContext = navigation_activity.this;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        AdapterCommonMedi.listofId.clear();


        initViews();

        if (!isConnectedToInternet(navigation_activity.this)) {
            Toast.makeText(this, "Can't connect to Internet!", Toast.LENGTH_SHORT).show();
            // Snackbar.make(context, "Can't connect to Internet!", Snackbar.LENGTH_LONG).show();
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        mCartIconMenuItem = menu.findItem(R.id.cart_count_menu_item);

        View actionView = mCartIconMenuItem.getActionView();

        if (actionView != null) {
            mCountTv = actionView.findViewById(R.id.count_tv_layout);
            mImageBtn = actionView.findViewById(R.id.image_btn_layout);
        }

        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Image Button is Clicked!", Toast.LENGTH_LONG).show();
            }
        });


        AdapterCommonMedi.listofId.clear();
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                arraySize = AdapterCommonMedi.listofId.size();
                mCountTv.setText(String.valueOf(arraySize));

                handler.postDelayed(this, 100);
            }

        };
        handler.post(run);


        return super.onCreateOptionsMenu(menu);
    }


    private boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void initViews() {


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        name = headerView.findViewById(R.id.tv_name);

        contact = headerView.findViewById(R.id.tv_contact);


        UserShared userShared = new UserShared(navigation_activity.this);
        uid = userShared.getUser_uid();
        user_name = userShared.getName();
        user_con = userShared.getContact();

        Toast.makeText(mContext, "uid is " + uid, Toast.LENGTH_SHORT).show();


        name.setText(user_name);
        contact.setText(user_con);


    }

    public void LogOut(View view) {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(navigation_activity.this);
        builder.setTitle("Confirm:");
        builder.setMessage("Are you sure you want to Log Out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                new UserShared(navigation_activity.this).removerUser();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {

            super.onBackPressed();
            AdapterCommonMedi.listofId.clear();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(navigation_activity.this, My_Profile.class);
            startActivity(intent);


        } else if (id == R.id.nav_logout) {
            LogOut(view);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
