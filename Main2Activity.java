package com.btree.giulia.myapplication2.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.btree.giulia.myapplication2.R;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ImageView im=(ImageView) findViewById(R.id.ima);
        im.setImageResource(R.drawable.addfolder);
    }
}
