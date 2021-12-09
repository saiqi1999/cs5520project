package edu.neu.khoury.madsea.saiqihe.todolistversion2.firebaseComponents;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import edu.neu.khoury.madsea.saiqihe.todolistversion2.roomDatabaseComponents.TodoNote;
import edu.neu.khoury.madsea.saiqihe.todolistversion2.roomDatabaseComponents.TodoRepo;

public class FirebaseExecutor {
    private static final String NOTE_COLLECTION = "TodoNote";
    private final FirebaseFirestore firebaseFirestore;
    private ArrayList<TodoNote> firebaseFetch;
    private TodoRepo localRepo;

    public FirebaseExecutor(Application application, TodoRepo repo) {
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        localRepo = repo;
    }

    /**
     * insert note into firebase fire store
     *
     * @param note target
     */
    public void insertNote(TodoNote note) {
        firebaseFirestore.collection(NOTE_COLLECTION)
                .add(note)
                .addOnSuccessListener(new AfterInputSuccessListener())
                .addOnFailureListener(new AfterInputFailureListener());
    }

    /**
     * update cloud
     *
     * @param note the updated state
     */
    public void updateNote(TodoNote note) {
        if (note.getFirebaseId() == null) return;
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", note.getTitle());
        map.put("detail", note.getDetail());
        map.put("noteId", note.getNoteId());
        map.put("checked", note.getChecked());
        map.put("createTime",note.getCreateTime());
        map.put("firebaseId",note.getFirebaseId());
        map.put("alarmTime", note.getAlarmTime());
        firebaseFirestore.collection(NOTE_COLLECTION)
                .document(note.getFirebaseId())
                .set(map)
                .addOnFailureListener(new AfterInputFailureListener());
    }

    /**
     * delete cloud
     *
     * @param firebaseId with firebase id to be deleted
     */
    public void deleteNoteById(String firebaseId) {
        if (firebaseId == null) return;
        firebaseFirestore.collection(NOTE_COLLECTION).document(firebaseId).delete();
    }


    /**
     * when coming back, sync local database with cloud
     * got the repo and do it
     */
    public void syncLocal() {
        AfterQueryListener afterQueryListener = new AfterQueryListener();
        afterQueryListener.setLocalRepo(localRepo);
        firebaseFirestore.collection(NOTE_COLLECTION)
                .get()
                .addOnCompleteListener(afterQueryListener);
    }

    public void syncFirebase() {
        List<TodoNote> insertList = localRepo.selectInsert().getValue();
        List<TodoNote> deleteList = localRepo.selectDelete().getValue();
        if(insertList==null||deleteList==null)return;
        for (TodoNote note : deleteList) {
            if (note.getFirebaseId() != null) {
                deleteNoteById(note.getFirebaseId());
            }
        }
        for (TodoNote note : insertList){
            if(note.getFirebaseId()!=null){
                updateNote(note);
            }
            else{
                //here we have a diff note id
                insertNote(note);
            }
        }

    }

}
