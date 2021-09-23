package com.hs.lesson2_3codechallenge;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import lesson2_3codechallenge.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eText = findViewById(R.id.shop_name_edittext);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TextView tView = findViewById(R.id.textView1);
        if(tView.getText()!=null)outState.putString("tView1",tView.getText().toString());
        tView = findViewById(R.id.textView2);
        if(tView.getText()!=null)outState.putString("tView2",tView.getText().toString());
        tView = findViewById(R.id.textView3);
        if(tView.getText()!=null)outState.putString("tView3",tView.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TextView tView = findViewById(R.id.textView1);
        tView.setText(savedInstanceState.getString("tView1"));
        tView = findViewById(R.id.textView2);
        tView.setText(savedInstanceState.getString("tView2"));
        tView = findViewById(R.id.textView3);
        tView.setText(savedInstanceState.getString("tView3"));
    }

    public void goShopping(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView tView = findViewById(R.id.textView1);
        //System.out.println(tView.getText()+"1");
        if((tView.getText().equals(""))){
            tView.setText(data.getStringExtra("choose"));
            return;
        }
        tView = findViewById(R.id.textView2);
        if((tView.getText().equals(""))){
            tView.setText(data.getStringExtra("choose"));
            return;
        }
        tView = findViewById(R.id.textView3);
        if((tView.getText().equals(""))){
            tView.setText(data.getStringExtra("choose"));
            return;
        }
    }

    EditText eText;
    public void findShop(View view) {
        String s = eText.getText().toString();
        Uri uri = Uri.parse("geo:0,0?q="+s);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }else{
            Log.e("CodeChallenge2_3","cant handle");
        }
    }
}