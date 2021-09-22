package com.hs.lesson2_2codechallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void addFruit(View view) {
        int id = view.getId();
        Intent re = new Intent();
        if(id==R.id.button2)re.putExtra("choose","apple");
        if(id==R.id.button3)re.putExtra("choose","orange");
        if(id==R.id.button4)re.putExtra("choose","banana");
        setResult(RESULT_OK,re);
        finish();
    }
}