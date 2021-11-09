package edu.neu.khoury.madsea.saiqihe.todolistversion2.Workers;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import edu.neu.khoury.madsea.saiqihe.todolistversion2.RoomComponents.TodoNote;
import edu.neu.khoury.madsea.saiqihe.todolistversion2.RoomComponents.TodoRepo;

public class InsertWorker extends Worker {
    private TodoRepo repo;
    public InsertWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        repo = new TodoRepo((Application) getApplicationContext());
    }

    @NonNull
    @Override
    public Result doWork() {
        Data inputData = getInputData();
        String title = inputData.getString("title");
        String detail = inputData.getString("detail");
        assert title != null;
        TodoNote note = new TodoNote(title,detail);
        repo.insert(note);
        return Result.success();
    }
}
