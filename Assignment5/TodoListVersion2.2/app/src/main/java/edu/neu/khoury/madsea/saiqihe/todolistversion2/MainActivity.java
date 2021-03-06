package edu.neu.khoury.madsea.saiqihe.todolistversion2;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements Someinterface{
    private TodoModelView modelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //observe
        RecyclerView viewInMain = findViewById(R.id.recycle_hold);
        final TodoNoteAdapter myAdapter = new TodoNoteAdapter(new TodoNoteAdapter.NoteDiff(),this);
        viewInMain.setAdapter(myAdapter);
        viewInMain.setLayoutManager(new LinearLayoutManager(this));
        modelView = new ViewModelProvider(this).get(TodoModelView.class);
        modelView.select().observe(this, items -> {
            myAdapter.submitList(items);
        });

        //add
        FloatingActionButton button = findViewById(R.id.float_action_button);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,InsertActivity.class);
            startActivityForResult(intent,1);
        });

        //del
        FloatingActionButton delButton = findViewById(R.id.float_del_button);
        delButton.setOnClickListener(view -> {
            modelView.delete();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 1) {
                modelView.startInsert(new TodoNote(
                        data.getStringExtra("title"),
                        data.getStringExtra("detail")));
            } else {
                modelView.update(new TodoNote(data.getIntExtra("id", 0),
                        data.getStringExtra("title"),
                        data.getStringExtra("detail")));
            }
            try {
                String titleS = data.getStringExtra("title");
                String detailS = data.getStringExtra("detail");
                String timeS = data.getStringExtra("timer");
                int time = Integer.parseInt(timeS);
                modelView.startTimer(titleS, detailS, time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(TodoNote note) {
        modelView.delete();
    }

    @Override
    public void update(TodoNote note) {
        Intent intent = new Intent(MainActivity.this,InsertActivity.class);
        intent.putExtra("id",note.getNoteId().toString());
        intent.putExtra("title",note.getTitle());
        intent.putExtra("detail",note.getDetail());
        startActivityForResult(intent,2);
    }
}