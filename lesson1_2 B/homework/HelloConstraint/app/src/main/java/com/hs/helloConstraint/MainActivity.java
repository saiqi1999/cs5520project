package com.hs.helloConstraint;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private int mCount = 0;
    private TextView mShowCount;
    private float textSize = 0;
    private float smallTextSize = 0;
    private final int textUnit = TypedValue.COMPLEX_UNIT_PX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);
        textSize = mShowCount.getTextSize();
        smallTextSize = textSize/2;
        //textUnit = mShowCount.getTextSizeUnit(); require API30, just assumed 'sp'
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this,R.string.toast_message,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public void countUp(View view) {
        if(mCount % 2 == 0)view.setBackgroundColor(Color.parseColor("#FF77FF"));
        else view.setBackgroundColor(Color.parseColor("#7777FF"));
        TextView x = (TextView) findViewById(R.id.button_zero);
        x.setBackgroundColor(Color.RED);
        mCount++;
        if(mShowCount!=null){
            if(mCount>9)mShowCount.setTextSize(textUnit,smallTextSize);
            mShowCount.setText(Integer.toString(mCount));
        }

    }

    public void toZero(View view) {
        view.setBackgroundColor(Color.GRAY);
        mCount = 0;
        if(mShowCount!=null){
            mShowCount.setTextSize(textUnit,textSize);
            mShowCount.setText(Integer.toString(mCount));
        }
    }
}