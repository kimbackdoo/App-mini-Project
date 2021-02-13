package com.example.videoalbum;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class Video extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);

        setTitle("동영상 재생");

        Intent it = getIntent();
        String tag = it.getStringExtra("it_tag");

        TextView title = (TextView)findViewById(R.id.title);
        VideoView videoview = (VideoView)findViewById(R.id.videoview);
        MediaController mc = new MediaController(this);

        Resources res = getResources();

        int stringId;
        String myKey;

        stringId = res.getIdentifier("title" + tag, "string", getPackageName());
        myKey = res.getString(stringId);
        title.setText(myKey);

        stringId = res.getIdentifier("video" + tag, "string", getPackageName());
        myKey = res.getString(stringId);
        int id_video = res.getIdentifier(myKey, "raw", getPackageName());
        Uri uri = Uri.parse("android.resource://com.example.videoalbum/" + id_video);
        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setVisibility(View.VISIBLE);

        //videoview.setMediaController(mc); // 미디어 컨트롤러 주석 처리했습니다!!

        Button button = (Button)findViewById(R.id.button);
    }
    private int currentPosition;
    public void clickButton(View v) { finish(); }
    public void stopVideo(View v) {
        int id = v.getId();
        VideoView videoview = (VideoView)findViewById(id);
        if (videoview.isPlaying())
            videoview.pause();
        else
            videoview.start();
    }
}
