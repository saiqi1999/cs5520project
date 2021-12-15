package edu.neu.khoury.madsea.saiqihe.todolistversion2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import edu.neu.khoury.madsea.saiqihe.todolistversion2.roomDatabaseComponents.TodoNote;

//TODO: UI design, make insert user friendly and beautiful
//TODO: add a confirm fragment before deleting all the notes
//todo: try separate alarming and timer by set alarm time "null", separate acts from layouts
public class MainActivity extends AppCompatActivity {
    private TodoModelView modelView;
    private TodoNoteAdapter myAdapter;
    private TodoNoteAdapter myAdapter2;
    RecyclerView viewInMain;
    RecyclerView viewInMain2;
    private String currentPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modelView = new ViewModelProvider(this).get(TodoModelView.class);
        //no sync

        //observe
        viewInMain = findViewById(R.id.recycle_hold);
        viewInMain2 = findViewById(R.id.recycle_hold2);
        myAdapter = new TodoNoteAdapter(new TodoNoteAdapter.NoteDiff(), this);
        myAdapter2 = new TodoNoteAdapter(new TodoNoteAdapter.NoteDiff(), this);
        viewInMain.setAdapter(myAdapter);
        viewInMain2.setAdapter(myAdapter2);
        viewInMain.setLayoutManager(new LinearLayoutManager(this));
        viewInMain2.setLayoutManager(new LinearLayoutManager(this));
        modelView.selectInsert().observe(this, items -> {
        });
        modelView.selectDelete().observe(this, items -> {
        });
        modelView.select().observe(this, items -> {
        });
        modelView.select("alarms").observe(this, items -> {
            myAdapter.submitList(sortByCreateTime(items));
        });
        modelView.select("timers").observe(this, items -> {
            myAdapter2.submitList(sortByCreateTime(items));
        });
        switchUiToAlarm();

        //add
        FloatingActionButton button = findViewById(R.id.float_action_button);
        button.setOnClickListener(view -> {
            if (currentPage.equals("alarms")) {
                Intent intent = new Intent(MainActivity.this, InsertAlarmclockActivity.class);
                startActivityForResult(intent, 1);
            } else {
                Intent intent = new Intent(MainActivity.this, InsertTimerActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        //del
        FloatingActionButton delButton = findViewById(R.id.float_del_button);
        delButton.setOnClickListener(view -> {
            ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment();
            confirmDialogFragment.setModelView(modelView);
            confirmDialogFragment.show(getSupportFragmentManager(), "tag");
        });

        //switch
        Button toTimersButton = findViewById(R.id.to_timers);
        Button toAlarmsButton = findViewById(R.id.to_alarm_clocks);
        toTimersButton.setOnClickListener((view) -> {
            switchUiToTimer();
        });
        toAlarmsButton.setOnClickListener((view) -> {
            switchUiToAlarm();
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
            if (data.getStringExtra("alarm_time") != null &&
                    data.getStringExtra("alarm_time").equals("null-null")) {
                switchUiToTimer();
            } else {
                switchUiToAlarm();
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
        //listener recover
        Button toTimersButton = findViewById(R.id.to_timers);
        Button toAlarmsButton = findViewById(R.id.to_alarm_clocks);
        if (currentPage.equals("timers")) {
            switchUiToTimer();
        } else {
            switchUiToAlarm();
        }
        toTimersButton.setOnClickListener((view) -> {
            switchUiToTimer();
        });
        toAlarmsButton.setOnClickListener((view) -> {
            switchUiToAlarm();
        });
    }

    public void delete(TodoNote note) {
        modelView.delete();
    }

    //todo: don't know why alarms just lose their time data and got invisible, need to save it
    public void update(TodoNote note) {
        Intent intent = null;//note id could change
        for (TodoNote n : modelView.select().getValue()) {
            if (n.getCreateTime().equals(note.getCreateTime())) {
                if (n.getAlarmTime() == null) {
                    intent = new Intent(MainActivity.this, InsertAlarmclockActivity.class);
                } else if (n.getAlarmTime().equals("null-null")) {//timer
                    intent = new Intent(MainActivity.this, InsertTimerActivity.class);
                } else {//alarm clock
                    intent = new Intent(MainActivity.this, InsertAlarmclockActivity.class);
                }
                intent.putExtra("id", note.getNoteId().toString());
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

    private void switchUiToTimer() {
        Button toTimersButton = findViewById(R.id.to_timers);
        Button toAlarmsButton = findViewById(R.id.to_alarm_clocks);
        currentPage = "timers";
        toTimersButton.setBackgroundColor(Color.GRAY);
        toAlarmsButton.setBackgroundColor(Color.WHITE);
        viewInMain.setVisibility(View.INVISIBLE);
        viewInMain2.setVisibility(View.VISIBLE);
    }

    private void switchUiToAlarm() {
        Button toTimersButton = findViewById(R.id.to_timers);
        Button toAlarmsButton = findViewById(R.id.to_alarm_clocks);
        currentPage = "alarms";
        toTimersButton.setBackgroundColor(Color.WHITE);
        toAlarmsButton.setBackgroundColor(Color.GRAY);
        viewInMain2.setVisibility(View.INVISIBLE);
        viewInMain.setVisibility(View.VISIBLE);
    }

    private List<TodoNote> sortByCreateTime(List<TodoNote> items) {
        HashMap<String,TodoNote> map = new HashMap<>();
        ArrayList<Long> times = new ArrayList<>();
        for(TodoNote note : items){
            times.add(Long.parseLong(note.getCreateTime()));
            map.put(note.getCreateTime(),note);
        }
        Collections.sort(times);
        ArrayList<TodoNote> rst = new ArrayList<>();
        for(Long l : times){
            rst.add(map.get(l.toString()));
        }
        return rst;
    }
}