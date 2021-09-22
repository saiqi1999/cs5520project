package com.hs.lesson2_1codechallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this,MainActivity2.class);
    }

    public void buttonOneClicked(View view) {
        String text = "Message for button one";
        intent.putExtra("msg",text);
        startActivity(intent);
    }

    public void buttonTwoClicked(View view) {
        String text = "Message for button two";
        intent.putExtra("msg",text);
        startActivity(intent);
    }

    public void buttonThreeClicked(View view) {
        String text = "Message for button three";
        intent.putExtra("msg",text);
        startActivity(intent);
    }
}