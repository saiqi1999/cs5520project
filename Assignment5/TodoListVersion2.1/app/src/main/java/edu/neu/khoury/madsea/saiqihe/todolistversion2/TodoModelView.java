package edu.neu.khoury.madsea.saiqihe.todolistversion2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.neu.khoury.madsea.saiqihe.todolistversion2.Workers.TimerWorker;

public class TodoModelView extends AndroidViewModel {
    private TodoRepo repo;
    private final LiveData<List<TodoNote>> items;
    private WorkManager m;

    public TodoModelView(@NonNull Application application) {
        super(application);
        repo = new TodoRepo(application);
        items = repo.select();
        m = WorkManager.getInstance();
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
    public void startTimer(String titleS,String detailS, int time){
        OneTimeWorkRequest.Builder TimerBuilder = new OneTimeWorkRequest.Builder(TimerWorker.class);
        Data.Builder dBuilder = new Data.Builder();
        dBuilder.putString("info_title",titleS);
        dBuilder.putString("info_detail",detailS);
        TimerBuilder.setInputData(dBuilder.build());
        WorkContinuation c = m.beginUniqueWork(titleS,
                ExistingWorkPolicy.REPLACE,
                TimerBuilder.setInitialDelay(time, TimeUnit.SECONDS).build());
        c.enqueue();

    }
}
