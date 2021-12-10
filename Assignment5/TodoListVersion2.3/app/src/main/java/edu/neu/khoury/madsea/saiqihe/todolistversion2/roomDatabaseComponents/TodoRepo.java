package edu.neu.khoury.madsea.saiqihe.todolistversion2.roomDatabaseComponents;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import edu.neu.khoury.madsea.saiqihe.todolistversion2.firebaseComponents.FirebaseSyncFlag;

/**
 * NOTE:
 * ALL user[operate]() method with side effect called by view model
 * ALL [operate]() method without side effect called by sync executor
 */
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
        String s = System.currentTimeMillis() + "";
        t.setCreateTime(s);
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
                    if(note.getFirebaseId()!=null&&!note.getFirebaseId().equals(""))
                    deleteDao.insert(note);
                }
            }
            insertDao.deleteAll();
            dao.deleteAll();
        });
    }

    public void update(TodoNote note) {
        TodoNoteDatabase.executor.execute(() -> {
            dao.update(note);
        });
    }

    /**
     * we are always refreshing local db, which made the id less reliable
     * while we're offline, we will update it in local db and no firebase id will be assigned
     * while we're online, when we first enter edit a note the online one is created
     * what's more, it's sync write back and have a different id,
     * and the original one is sync deleted! which made it not trackable
     * I think I need to keep original id if possible when writing back
     * <p>
     * struggling..
     * <p>
     * solution by create time
     * if the note has firebase id, it is saved on cloud:
     * and we just find the firebaseId remove the one in 3 dbs and insert this into it
     * if the note has no firebase id, it is not saved on cloud:
     * but it could be synced to local with a diff noteId and a firebaseId cuz it's by insertDB
     * we will try to find that one with same create time and copy fields from that one, del that one
     * keep this note id so we can use update(), after that, update insertDB and delDB
     * if we don't find the same create time, it's not synced yet
     * we just update the one we have in mainDB, then find by createTime, update insDB and delDB
     *
     * @param note note with note id(may not correct) to be updated
     */
    public void userUpdate(TodoNote note) {
        syncCache();
        if (note.getCreateTime() == null) return;//illegal added note, leave it

        List<TodoNote> mainList = lists.getValue();

        if (note.getFirebaseId() != null&& !note.getFirebaseId().equals("")) {
            note.setNoteId(null);
            String firebaseId = note.getFirebaseId();
            TodoNoteDatabase.executor.execute(() -> {
                dao.deleteByFirebaseId(firebaseId);
                insertDao.deleteByFirebaseId(firebaseId);//possibly empty delete
                deleteDao.deleteByFirebaseId(firebaseId);//possibly empty delete
                dao.insert(note);
                insertDao.insert(note);
                deleteDao.insert(note);
            });
        } else {
            TodoNoteDatabase.executor.execute(() -> {
                dao.update(note);
                insertDao.update(note);
                deleteDao.update(note);
            });
            //just fail because sync not finished
            /*if(mainList==null)return;
            String createTime = note.getCreateTime();
            TodoNote findNote = null;
            for(TodoNote n : mainList){
                if(n.getCreateTime().equals(createTime)&&n.getFirebaseId()!=null){
                    findNote = n;
                    break;
                }
            }
            if(findNote!=null){
                //findNote: the one synced back, we just use that one and delete this local one
                findNote.setChecked(note.getChecked());
                findNote.setDetail(note.getDetail());
                findNote.setAlarmTime(note.getAlarmTime());
                findNote.setTitle(note.getTitle());
                TodoNote finalFindNote = findNote;
                TodoNoteDatabase.executor.execute(()->{
                    dao.deleteById(note.getNoteId());
                    dao.update(finalFindNote);
                });
                //idk if this will create synchronize problems, TODO
                findNote.setNoteId(null);
                TodoNote finalFindNote1 = findNote;
                TodoNoteDatabase.executor.execute(()->{
                    insertDao.insert(finalFindNote1);
                    deleteDao.insert(finalFindNote1);
                });
            }*/
            /**/
        }
        /*TodoNoteDatabase.executor.execute(() -> {
            dao.update(note);
            deleteDao.insert(note);
            insertDao.insert(note);
        });*/
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
