package com.example.poetry;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.poetry.R.*;

public class Poem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.poem);

        TextView tv_title = (TextView) findViewById(id.title);
        TextView tv_author = (TextView) findViewById(id.author);
        TextView tv_body = (TextView) findViewById(id.body);

        Intent it = getIntent();
        String tag = it.getStringExtra("it_tag");

        Resources res = getResources();

        int id_title = res.getIdentifier("title" + tag, "string", getPackageName());
        String title = res.getString(id_title);
        tv_title.setText(title);

        int id_author = res.getIdentifier("author" + tag, "string", getPackageName());
        String author = res.getString(id_author);
        tv_author.setText(author);

        int id_body = res.getIdentifier("body" + tag, "string", getPackageName());
        String body = res.getString(id_body);
        tv_body.setText(body);
    }

    public void closePoem(View v) {
        finish();
    }
}