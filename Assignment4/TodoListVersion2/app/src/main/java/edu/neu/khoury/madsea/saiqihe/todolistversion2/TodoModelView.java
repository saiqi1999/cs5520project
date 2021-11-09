package edu.neu.khoury.madsea.saiqihe.todolistversion2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.neu.khoury.madsea.saiqihe.todolistversion2.roomdatabaseclasses.TodoNote;
import edu.neu.khoury.madsea.saiqihe.todolistversion2.roomdatabaseclasses.TodoRepo;

public class TodoModelView extends AndroidViewModel {
    private TodoRepo repo;
    private final LiveData<List<TodoNote>> items;

    public TodoModelView(@NonNull Application application) {
        super(application);
        repo = new TodoRepo(application);
        items = repo.select();

    }
    public void insert(TodoNote note){
        repo.insert(note);
    }
    public void delete(){
        repo.delete();
    }
    public LiveData<List<TodoNote>> select(){
        return items;
    }

    public void update(TodoNote note) {
        repo.update(note);
    }
}
