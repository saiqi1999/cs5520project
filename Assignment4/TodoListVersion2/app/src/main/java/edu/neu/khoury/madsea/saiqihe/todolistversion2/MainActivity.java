package edu.neu.khoury.madsea.saiqihe.todolistversion2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.neu.khoury.madsea.saiqihe.todolistversion2.roomdatabaseclasses.TodoNote;

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
        if(data==null)return;
        if(requestCode==1){
            modelView.insert(new TodoNote(
                    data.getStringExtra("title"),
                    data.getStringExtra("detail")));
        }
        else{
            modelView.update(new TodoNote(data.getIntExtra("id",0),
                    data.getStringExtra("title"),
                    data.getStringExtra("detail")));

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