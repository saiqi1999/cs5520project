package com.hs.testfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {

    String frameId;
    TextView title;
    TextView detail;
    EditText date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();

        frameId = intent.getStringExtra("frameId");
        title = findViewById(R.id.plaintext_title);
        detail = findViewById(R.id.plaintext_detail);
        date = findViewById(R.id.editTextDate);

        title.setText(getIntent().getStringExtra("title1"));
        detail.setText(getIntent().getStringExtra("detail"));
        date.setText(getIntent().getStringExtra("date"));
    }

    public void submit(View view) {
        Intent intent = new Intent();
        intent.putExtra("frameId",frameId);
        intent.putExtra("title1",title.getText().toString());
        intent.putExtra("detail",detail.getText().toString());
        intent.putExtra("date",date.getText().toString());
        setResult(0,intent);
        finish();
    }
}