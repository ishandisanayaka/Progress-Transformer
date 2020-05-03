package com.example.progresstransformer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.progresstransformer.Database.DBHelper;
import com.example.progresstransformer.Fragment.HomeFragement;
import com.example.progresstransformer.Fragment.ReceiveFragement;
import com.example.progresstransformer.Fragment.VideoFragment;
import com.example.progresstransformer.VideoLoder.Constant;
import com.example.progresstransformer.VideoLoder.Method;
import com.example.progresstransformer.VideoLoder.RecyclerViewAdapter;
import com.example.progresstransformer.VideoLoder.StorageUtil;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.net.URI;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public static long progress1;
    private static DBHelper dbHelper;

    private DrawerLayout drawer;
    private File directory;
    private String[] allPath;

    private boolean permission;
    private File storage;
    private String[] storagePaths;
    private Uri fileUri;
    private RecyclerViewAdapter recyclerViewAdapter;
    public static Context contextOfApplication;

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoFragment.setMainActivity(this);

        contextOfApplication=getApplicationContext();
        dbHelper=new DBHelper(this);

        checkStorageAccessPermission();
        //load data here
        storagePaths = StorageUtil.getStorageDirectories(this);

        for (String path : storagePaths) {
            storage = new File(path);
            Method.load_Directory_Files(storage);
        }


//        allPath= StorageUtil.getStorageDirectories(this);
//        for (String path:allPath){
//            directory=new File(path);
//            Method.load_Directory_Files(directory);
//        }

        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragement()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragement()).commit();
                break;
            case R.id.nav_train:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ReceiveFragement()).commit();
                break;
            case R.id.nav_about:
                Toast.makeText(this,"About",Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void checkStorageAccessPermission() {
        //ContextCompat use to retrieve resources. It provide uniform interface to access resources.
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                new androidx.appcompat.app.AlertDialog.Builder(this)
                        .setTitle("Permission Needed")
                        .setMessage("This permission is needed to access media file in your phone")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        1);
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            // Do nothing. Because if permission is already granted then files will be accessed/loaded in splash_screen_activity
        }
    }


}
