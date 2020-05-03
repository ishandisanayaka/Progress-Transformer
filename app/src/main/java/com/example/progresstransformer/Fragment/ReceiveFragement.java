package com.example.progresstransformer.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.progresstransformer.R;


public class ReceiveFragement extends Fragment  {


    View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView= inflater.inflate(R.layout.fragment_receive,container,false);

        return mView;
    }

}
