package com.example.progresstransformer.PlayVideo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.progresstransformer.Database.DBHelper;
import com.example.progresstransformer.R;
import com.example.progresstransformer.VideoLoder.Constant;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerVideo extends AppCompatActivity {
    VideoView videoView;
    MediaController mediaController;
    private  DBHelper dbHelper;
    private Uri urlOfVideo;
    private int lastPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper=new DBHelper(this);

        setContentView(R.layout.activity_player_video);
        videoView=(VideoView)findViewById(R.id.videoView);
        mediaController=new MediaController(this);
        Intent intent=getIntent();
        int positionOfArray=intent.getIntExtra("uri",0);
        urlOfVideo = Uri.fromFile(Constant.allMediaList.get(positionOfArray));
        if (!Constant.allSendToDB.contains(String.valueOf(urlOfVideo))) {
            lastPosition = videoView.getCurrentPosition();
            dbHelper.insertData(Constant.allMediaList.get(positionOfArray).getName(), String.valueOf(urlOfVideo), String.valueOf(lastPosition));
            Constant.allSendToDB.add(String.valueOf(urlOfVideo));
        }else{
            ArrayList<HashMap<String, String>> progressAttay=dbHelper.getvideoData(String.valueOf(urlOfVideo));
            lastPosition=Integer.parseInt(progressAttay.get(0).get("progress"));
        }


        videoView.setVideoURI(urlOfVideo);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);


        //ArrayList<HashMap<String, String>> progressAttay=dbHelper.getvideoData(String.valueOf(urlOfVideo));

        videoView.seekTo(lastPosition);
        //videoView.start();





    }
    @Override
    protected void onStop() {
        videoView.pause();
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onPause() {
        lastPosition=videoView.getCurrentPosition();
        String urlOfVideoString=String.valueOf(urlOfVideo);
        dbHelper.updateProgress(String.valueOf(lastPosition),urlOfVideoString);



        //dbHelper.insertContact(urlOfVideoString, String.valueOf(lastPosition));
        //dbHelper.updateContact(urlOfVideoString,100);
        //Cursor res=dbHelper.getCurrentPosition(String.valueOf(urlOfVideo));
       /// String a=res.getString(1);
      //  Log.e("aa", a);
       // Log.e("aa", urlOfVideoString);

        super.onPause();
    }
}
