package com.hs.hellotoast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
        TextView tView = findViewById(R.id.textView);
        tView.setText(getString(R.string.hello)+getIntent().getStringExtra("toast"));
    }
}