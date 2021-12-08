package edu.neu.khoury.madsea.saiqihe.todolistversion2.roomDatabaseComponents;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepo {

    private TodoNoteDao dao;
    private TodoNoteDao insertDao;
    private TodoNoteDao deleteDao;
    private LiveData<List<TodoNote>> lists;
    private LiveData<List<TodoNote>> listsInsert;
    private LiveData<List<TodoNote>> listsDelete;

    public TodoRepo(Application app) {
        TodoNoteDatabase db = TodoNoteDatabase.getInstance(app);
        dao = db.dao();
        insertDao = TodoNoteInsertDatabase.getInstance(app).dao();
        deleteDao = TodoNoteDeleteDatabase.getInstance(app).dao();
        lists = dao.selectAll();
        listsInsert = insertDao.selectAll();
        listsDelete = deleteDao.selectAll();
    }

    public void insert(TodoNote t) {
        TodoNoteDatabase.executor.execute(() -> {
            dao.insert(t);
            insertDao.insert(t);
        });
    }

    public LiveData<List<TodoNote>> select() {
        return lists;
    }
    public LiveData<List<TodoNote>> selectInsert() {
        return listsInsert;
    }
    public LiveData<List<TodoNote>> selectDelete() {
        return listsDelete;
    }

    public void delete() {
        TodoNoteDatabase.executor.execute(() -> {
            if (lists.getValue() != null) {
                for (TodoNote note : lists.getValue()) {
                    deleteDao.insert(note);
                }
            }
            dao.deleteAll();
        });
    }

    public void update(TodoNote note) {
        TodoNoteDatabase.executor.execute(() -> {
            dao.update(note);
            deleteDao.insert(note);
            insertDao.insert(note);
        });
    }

}
