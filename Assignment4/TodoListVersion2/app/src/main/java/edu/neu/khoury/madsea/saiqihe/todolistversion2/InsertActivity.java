package edu.neu.khoury.madsea.saiqihe.todolistversion2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InsertActivity extends AppCompatActivity {
    private Button button;
    private EditText title;
    private EditText detail;
    private TextView idv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        button = findViewById(R.id.insert_submit_button);
        title = findViewById(R.id.text_title);
        detail = findViewById(R.id.text_detail);
        idv = findViewById(R.id.text_id);
        Intent inputIntent = getIntent();
        if(inputIntent.getStringExtra("title")!=null){
            title.setText(inputIntent.getStringExtra("title"));
            detail.setText(inputIntent.getStringExtra("detail"));
            idv.setText(inputIntent.getStringExtra("id"));
        }
        button.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra("id",Integer.parseInt(idv.getText().toString()));
            intent.putExtra("title",title.getText().toString());
            intent.putExtra("detail",detail.getText().toString());
            setResult(RESULT_OK,intent);
            finish();
        });
    }
}