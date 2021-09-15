package com.hs.helloworld;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //the code of function "onCreate()" seems to be changed and setContentView() is outside
        //assume I should put those Log sentences just there.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity","Hello World and the debug log");
        Log.v("MainActivity","the v log");
        Log.i("MainActivity","the I log");
        Log.e("MainActivity","the E log");
        Log.w("MainActivity","the W log");
    }
}