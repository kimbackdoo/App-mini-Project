package com.example.poetry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import static com.example.poetry.R.*;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
    }

    public void displayPoem(View v){
        int id = v.getId();
        LinearLayout layout = (LinearLayout) v.findViewById(id);
        String tag = (String) layout.getTag();

        //Toast.makeText(this, "클릭한 아이템 : " + tag, Toast.LENGTH_LONG).show();
        Intent it = new Intent(this, Poem.class);
        it.putExtra("it_tag", tag);
        startActivity(it);
    }
}

