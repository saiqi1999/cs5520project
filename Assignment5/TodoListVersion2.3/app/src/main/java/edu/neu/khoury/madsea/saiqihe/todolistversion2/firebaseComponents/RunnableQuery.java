package edu.neu.khoury.madsea.saiqihe.todolistversion2.firebaseComponents;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import edu.neu.khoury.madsea.saiqihe.todolistversion2.roomDatabaseComponents.TodoNote;

public class RunnableQuery implements Runnable{
    private Task<QuerySnapshot> task;
    private ArrayList<TodoNote> saved;
    public RunnableQuery() {
    }

    public Task<QuerySnapshot> getTask() {
        return task;
    }

    public ArrayList<TodoNote> getSaved() {
        return saved;
    }

    public void setSaved(ArrayList<TodoNote> saved) {
        this.saved = saved;
    }

    public void setTask(Task<QuerySnapshot> task) {
        this.task = task;
    }

    @Override
    public void run() {
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot q : task.getResult()) {
                Log.d("query result", q.getId() + q.getData());//auto to string for hashmap
                TodoNote todoNote = new TodoNote();
                todoNote.setFirebaseId(q.getId());
                HashMap<String, Object> map = (HashMap<String, Object>) q.getData();
                for (String s : map.keySet()) {
                    if (s.equals("title")) todoNote.setTitle((String) map.get(s));
                    if (s.equals("detail")) todoNote.setDetail((String) map.get(s));
                    if (s.equals("checked")) todoNote.setChecked((String) map.get(s));
                    if (s.equals("alarmTime")) todoNote.setAlarmTime((String) map.get(s));
                }
                saved.add(todoNote);
            }
        } else Log.w("query result", "query failed");

    }
}
