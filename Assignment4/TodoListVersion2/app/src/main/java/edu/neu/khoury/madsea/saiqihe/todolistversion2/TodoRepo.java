package edu.neu.khoury.madsea.saiqihe.todolistversion2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepo {

    private TodoNoteDao dao;
    private LiveData<List<TodoNote>> lists;

    TodoRepo(Application app){
        TodoNoteDatabase db = TodoNoteDatabase.getInstance(app);
        dao = db.dao();
        lists = dao.selectAll();
    }
    public void insert(TodoNote t){
        /*if(t.getNoteId()==null){
            t.setNoteId(t.hashCode());
        }*/
        TodoNoteDatabase.executor.execute(()->{
            dao.insert(t);
        });
    }
    public LiveData<List<TodoNote>> select(){
        return lists;
    }
    public void delete(){
        TodoNoteDatabase.executor.execute(()->{
            dao.deleteAll();
        });
    }

    public void update(TodoNote note) {
        TodoNoteDatabase.executor.execute(()->{
            dao.update(note);
        });
    }
}
