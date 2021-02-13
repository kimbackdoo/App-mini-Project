package com.example.stepcounter2;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView tv_sensor;
    SensorManager sm;
    Sensor sensor_step_detector;

    int steps = 0;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);

        tv_sensor = (TextView) findViewById(R.id.sensor);
        tv_sensor.setText("0");

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor_step_detector = sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sm.registerListener(this, sensor_step_detector, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged (Sensor sensor ,int accuracy) {
    }

    @Override
    public void onSensorChanged (SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_STEP_DETECTOR:
                tv_sensor.setText("" + (++steps));
                break;
        }
    }

    public void start (View v) {
        String name = (String) btn.getText();

        if (name.equals("시작")) {
            btn.setText("종료");
            steps = 0;
            tv_sensor.setText("" + steps);
        } else if (name.equals("종료"))
            btn.setText("시작");
    }
}