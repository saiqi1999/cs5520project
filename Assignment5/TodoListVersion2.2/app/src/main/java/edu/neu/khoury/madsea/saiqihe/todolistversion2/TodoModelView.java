package edu.neu.khoury.madsea.saiqihe.todolistversion2;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.neu.khoury.madsea.saiqihe.todolistversion2.Workers.InsertWorker;
import edu.neu.khoury.madsea.saiqihe.todolistversion2.Workers.TimerWorker;

public class TodoModelView extends AndroidViewModel {
    private TodoRepo repo;
    private final LiveData<List<TodoNote>> items;
    private WorkManager m;
    private MutableLiveData<Integer> hour = new MutableLiveData<>();
    private MutableLiveData<Integer> minute = new MutableLiveData<>();

    public TodoModelView(@NonNull Application application) {
        super(application);
        repo = new TodoRepo(application);
        items = repo.select();
        m = WorkManager.getInstance();
    }
    public void insert(TodoNote note){
        repo.insert(note);
    }
    public void startInsert(TodoNote note){
        Data.Builder builder = new Data.Builder();
        builder.putString("title",note.getTitle());
        builder.putString("detail",note.getDetail());
        OneTimeWorkRequest.Builder workBuilder = new OneTimeWorkRequest.Builder(InsertWorker.class);
        workBuilder.setInputData(builder.build());
        m.enqueue(workBuilder.build());
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setTime(int hour, int minute){
        LocalTime t = LocalTime.now();
        int h = hour-t.getHour();
        int m = minute-t.getMinute();
        if(m<0){
            h-=1;
            m+=60;
        }
        if(h<0){
            h+=24;
        }
        this.hour.setValue(h);
        this.minute.setValue(m);
    }

    public LiveData<Integer> getHour() {
        return hour;
    }

    public LiveData<Integer> getMinute(){
        return minute;
    }

}
