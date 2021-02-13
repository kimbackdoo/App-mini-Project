package com.example.narrator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech tts;
    TextView tv;
    Intent intent;
    SpeechRecognizer mRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, 1);
        }

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(recognitionListener);

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int amStreamMusicMaxVol = am.getStreamMaxVolume(am.STREAM_MUSIC);
        am.setStreamVolume(am.STREAM_MUSIC, amStreamMusicMaxVol, 0);
    }

    private RecognitionListener recognitionListener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
        }

        @Override
        public void onBeginningOfSpeech() {
        }

        @Override
        public void onRmsChanged(float v) {
        }

        @Override
        public void onBufferReceived(byte[] bytes) {
        }

        @Override
        public void onEndOfSpeech() {
        }

        @Override
        public void onError(int error) {
        }

        @Override
        public void onResults(Bundle bundle) {
            ArrayList<String> mResult = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String[] rs = new String[mResult.size()];
            mResult.toArray(rs);

            Resources res = getResources();
            if(rs[0].equals("별 헤는 밤")) {
                int id_poem = res.getIdentifier("poem1", "string", getPackageName());
                String body = res.getString(id_poem);

                tv.setText(body);
                tts.speak(body, TextToSpeech.QUEUE_FLUSH, null);
            } else if (rs[0].equals("가지 않는 길")) {
                int id_poem = res.getIdentifier("poem2", "string", getPackageName());
                String body = res.getString(id_poem);

                tv.setText(body);
                tts.speak(body, TextToSpeech.QUEUE_FLUSH, null);
            } else {
                tv.setText("해당 시가 존재하지 않습니다!");
            }
        }

        @Override
        public void onPartialResults(Bundle bundle) {
        }

        @Override
        public void onEvent(int event, Bundle bundle) {
        }
    };

    public void recognizeVoice (View view) {
       tv.setText("");

       if(tts.isSpeaking())
           tts.stop();

        tts.speak("시의 제목을 말하세요", TextToSpeech.QUEUE_FLUSH, null);
        mRecognizer.startListening(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }
}
