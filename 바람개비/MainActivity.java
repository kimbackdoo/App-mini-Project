package com.example.pinwheel;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    ImageView iv_pinwheel;
    SensorManager sm;
    Sensor sensor_linear_acceleration;
    ObjectAnimator object = new ObjectAnimator();
    double timestamp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_pinwheel = (ImageView)findViewById(R.id.iv_pinwheel);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor_linear_acceleration = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }


    @Override
    protected void onResume() {
        super.onResume();

        sm.registerListener(this, sensor_linear_acceleration, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sm.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        switch(event.sensor.getType()) {
            case Sensor.TYPE_LINEAR_ACCELERATION:

                double dt = (event.timestamp - timestamp) / 1000000000; // 센서 감지 시간 간격, 나노 시간을 초 단위로 변환

                // 센서 감지 시간 간격이 1초 되면 회전 변경
                if (dt > 1) {
                    double magnitude = Math.sqrt(Math.pow(event.values[0], 2) + Math.pow(event.values[1], 2) + Math.pow(event.values[2], 2));

                    double degree_start = iv_pinwheel.getRotation();
                    double degree_end   = degree_start + magnitude * 1000;

                    object.cancel();
                    object = ObjectAnimator.ofFloat(iv_pinwheel, "rotation", (float)degree_start, (float)degree_end);
                    object.setInterpolator(new LinearInterpolator());
                    object.setDuration(1000);
                    object.start();

                    timestamp = event.timestamp;
                }
                break;
        }
    }
}