package edu.neu.khoury.madsea.saiqihe.todolistversion2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.neu.khoury.madsea.saiqihe.todolistversion2.RoomComponents.TodoNote;
//TODO: UI design, make insert user friendly and beautiful
//TODO: add a confirm fragment before deleting all the notes
public class MainActivity extends AppCompatActivity{
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
                TodoNote t =new TodoNote(
                        data.getStringExtra("title"),
                        data.getStringExtra("detail"));
                t.setAlarmTime(data.getStringExtra("alarm_time"));
                t.setChecked(data.getStringExtra("checked"));
                modelView.insert(t);
            } else {
                TodoNote t =new TodoNote(
                        data.getIntExtra("id", 0),
                        data.getStringExtra("title"),
                        data.getStringExtra("detail"));
                t.setAlarmTime(data.getStringExtra("alarm_time"));
                t.setChecked(data.getStringExtra("checked"));
                modelView.update(t);
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

    public void delete(TodoNote note) {
        modelView.delete();
    }

    public void update(TodoNote note) {
        Intent intent = new Intent(MainActivity.this,InsertActivity.class);
        intent.putExtra("id",note.getNoteId().toString());
        for(TodoNote n : modelView.select().getValue()){
            if(n.getNoteId()==note.getNoteId()){
                intent.putExtra("title",n.getTitle());
                intent.putExtra("detail",n.getDetail());
                intent.putExtra("alarm_time",n.getAlarmTime());
                intent.putExtra("checked",n.getChecked());
            }
        }
        startActivityForResult(intent,2);
    }

    public void silentUpdate(TodoNote t) {
        for(TodoNote n : modelView.select().getValue()){
            if(n.getNoteId().equals(t.getNoteId())){
                t.setTitle(n.getTitle());
                t.setDetail(n.getDetail());
                t.setAlarmTime(n.getAlarmTime());
            }
        }
        modelView.update(t);
    }
}