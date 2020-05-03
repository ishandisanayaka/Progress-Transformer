package com.example.progresstransformer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.progresstransformer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;




public class HomeFragement extends Fragment {
    View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView= inflater.inflate(R.layout.fragment_home,container,false);
        BottomNavigationView bottomNavigationView=mView.findViewById(R.id.bottom_nevigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);
        getFragmentManager().beginTransaction().replace(R.id.fragment_contaner_home,new VideoFragment()).commit();

        return mView;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListner=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment=null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_video:
                            //artActivity(new Intent(getActivity().getApplicationContext(), VideoBottomNevigation.class));

                            selectedFragment=new VideoFragment();
                            break;
                        case R.id.nav_audio:
                            selectedFragment=new AudioFragment();
                            break;
                        case R.id.nav_pdf:
                            selectedFragment=new PdfFragment();
                            break;
                    }
                    getFragmentManager().beginTransaction().replace(R.id.fragment_contaner_home,selectedFragment).commit();
                    return true;
                }
            };


    }

