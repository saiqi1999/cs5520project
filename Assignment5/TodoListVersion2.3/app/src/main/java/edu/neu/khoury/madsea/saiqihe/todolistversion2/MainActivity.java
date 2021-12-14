package edu.neu.khoury.madsea.saiqihe.todolistversion2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.neu.khoury.madsea.saiqihe.todolistversion2.roomDatabaseComponents.TodoNote;

//TODO: UI design, make insert user friendly and beautiful
//TODO: add a confirm fragment before deleting all the notes
public class MainActivity extends AppCompatActivity {
    private TodoModelView modelView;
    private TodoNoteAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modelView = new ViewModelProvider(this).get(TodoModelView.class);
        //no sync
        //modelView.syncLocal();
        //observe
        RecyclerView viewInMain = findViewById(R.id.recycle_hold);
        myAdapter = new TodoNoteAdapter(new TodoNoteAdapter.NoteDiff(), this);
        viewInMain.setAdapter(myAdapter);
        viewInMain.setLayoutManager(new LinearLayoutManager(this));
        modelView.selectInsert().observe(this, items->{});
        modelView.selectDelete().observe(this, items->{});
        modelView.select().observe(this, items -> {
            myAdapter.submitList(items);
        });

        //add
        FloatingActionButton button = findViewById(R.id.float_action_button);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, InsertAlarmclockActivity.class);
            startActivityForResult(intent, 1);
        });

        //del
        FloatingActionButton delButton = findViewById(R.id.float_del_button);
        delButton.setOnClickListener(view -> {
            ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment();
            confirmDialogFragment.setModelView(modelView);
            confirmDialogFragment.show(getSupportFragmentManager(),"tag");
        });


    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //sync
        //first upload delete/insert note to cloud, empty these, then download all notes from cloud
        modelView.syncCloud();
        modelView.syncLocal();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 1) {
                TodoNote t = new TodoNote(
                        data.getStringExtra("title"),
                        data.getStringExtra("detail"));
                t.setAlarmTime(data.getStringExtra("alarm_time"));
                t.setChecked(data.getStringExtra("checked"));
                modelView.insert(t);
            } else {
                TodoNote t = new TodoNote(
                        data.getIntExtra("id", 0),
                        data.getStringExtra("title"),
                        data.getStringExtra("detail"));
                t.setAlarmTime(data.getStringExtra("alarm_time"));
                t.setChecked(data.getStringExtra("checked"));
                t.setFirebaseId(data.getStringExtra("firebaseId"));
                t.setCreateTime(data.getStringExtra("createTime"));
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
        modelView.select().observe(this, items -> {
            myAdapter.submitList(items);
        });
    }

    public void delete(TodoNote note) {
        modelView.delete();
    }

    public void update(TodoNote note) {
        Intent intent = new Intent(MainActivity.this, InsertAlarmclockActivity.class);
        intent.putExtra("id", note.getNoteId().toString());
        for (TodoNote n : modelView.select().getValue()) {
            if (n.getNoteId().equals(note.getNoteId())) {
                intent.putExtra("title", n.getTitle());
                intent.putExtra("detail", n.getDetail());
                intent.putExtra("alarm_time", n.getAlarmTime());
                intent.putExtra("checked", n.getChecked());
                intent.putExtra("firebaseId", n.getFirebaseId());
                intent.putExtra("createTime", n.getCreateTime());
            }
        }
        startActivityForResult(intent, 2);
    }

    public void silentUpdate(TodoNote t) {
        for (TodoNote n : modelView.select().getValue()) {
            if (n.getNoteId().equals(t.getNoteId())) {
                t.setTitle(n.getTitle());
                t.setDetail(n.getDetail());
                t.setAlarmTime(n.getAlarmTime());
            }
        }
        modelView.update(t);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}