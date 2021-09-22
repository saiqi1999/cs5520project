package com.hs.lesson2_1codechallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String text = getIntent().getStringExtra("msg");
        TextView tView = findViewById(R.id.target);
        tView.setText(text);
    }
}