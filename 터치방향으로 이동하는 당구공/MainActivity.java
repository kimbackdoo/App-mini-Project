package com.example.billiardball2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    BilliardBall2 ball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        ball = new BilliardBall2(this);
        setContentView(ball);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                if(x > ball.cx)
                    ball.dir_x = 1;
                else
                    ball.dir_x = -1;
                if(y > ball.cy)
                    ball.dir_y = 1;
                else
                    ball.dir_y = -1;

                ball.dx = Math.abs(ball.cx - x) / 10;
                ball.dy = Math.abs(ball.cy - y) / 10;
                break;
        }
        return false;
    }
}
