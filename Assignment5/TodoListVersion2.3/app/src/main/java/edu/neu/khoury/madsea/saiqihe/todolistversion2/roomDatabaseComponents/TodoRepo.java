package edu.neu.khoury.madsea.saiqihe.todolistversion2.roomDatabaseComponents;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import edu.neu.khoury.madsea.saiqihe.todolistversion2.firebaseComponents.FirebaseSyncFlag;

public class TodoRepo {

    private TodoNoteDao dao;
    private TodoNoteDao insertDao;
    private TodoNoteDao deleteDao;
    private LiveData<List<TodoNote>> lists;
    private LiveData<List<TodoNote>> listsInsert;
    private LiveData<List<TodoNote>> listsDelete;

    public TodoRepo(Application app) {
        TodoNoteDatabase db = TodoNoteDatabase.getInstance(app);
        TodoNoteInsertDatabase dbInsert = TodoNoteInsertDatabase.getInstance(app);
        TodoNoteDeleteDatabase dbDelete = TodoNoteDeleteDatabase.getInstance(app);
        dao = db.dao();
        insertDao = dbInsert.dao();
        deleteDao = dbDelete.dao();
        lists = dao.selectAll();
        listsInsert = insertDao.selectAll();
        listsDelete = deleteDao.selectAll();
    }

    public void insert(TodoNote t) {
        TodoNoteDatabase.executor.execute(() -> {
            dao.insert(t);
        });
    }

    public void userInsert(TodoNote t) {
        syncCache();
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
            dao.deleteAll();
        });
    }

    public void userDelete() {
        syncCache();
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
        });
    }

    public void userUpdate(TodoNote note) {
        syncCache();
        TodoNoteDatabase.executor.execute(() -> {
            dao.update(note);
            deleteDao.insert(note);
            insertDao.insert(note);
        });
    }

    public void cleanCache() {
        TodoNoteDatabase.executor.execute(() -> {
            insertDao.deleteAll();
            deleteDao.deleteAll();
        });
    }

    public void syncCache() {
        //if synced just delete them, if not, keep editing cache
        if (FirebaseSyncFlag.getInstance().isFlag()) {//has sync to cloud
            cleanCache();
            FirebaseSyncFlag.getInstance().setFlag(false);
        }
    }

    //put on a thread avoid race condition
    public void deleteAndInsert(ArrayList<TodoNote> saved) {
        TodoNoteDatabase.executor.execute(() -> {
            dao.deleteAll();
            for (TodoNote note : saved) {
                dao.insert(note);
            }
        });
    }
}
