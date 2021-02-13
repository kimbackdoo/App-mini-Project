package com.example.roulette;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    ImageView iv_roulette;
    float startDegree = 0f;
    float endDegree = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_roulette = (ImageView)findViewById(R.id.roulette);
        iv_roulette.setOnTouchListener(this);
    }

    private VelocityTracker mVelocityTracker = null;
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                } else {
                    mVelocityTracker.clear();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                mVelocityTracker.computeCurrentVelocity(10);

                float velocity_x = mVelocityTracker.getXVelocity();
                float velocity_y = mVelocityTracker.getYVelocity();
                float velocity = (float) Math.sqrt(Math.pow(velocity_x, 2) + Math.pow(velocity_y, 2));

                rotate(velocity);
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                mVelocityTracker.recycle();
                break;
        }
        return true;
    }

    public void rotate (float velocity) {
        int duration = (int)velocity * 5;

        startDegree = endDegree;
        endDegree = startDegree + 360 * velocity * velocity;

        ObjectAnimator object = ObjectAnimator.ofFloat(iv_roulette, "rotation", startDegree, endDegree);
        object.setInterpolator(new AccelerateDecelerateInterpolator());
        object.setDuration(duration);
        object.start();
    }
}