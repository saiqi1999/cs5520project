package edu.neu.khoury.madsea.saiqihe.todolistversion2.Workers;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import edu.neu.khoury.madsea.saiqihe.todolistversion2.TodoNote;
import edu.neu.khoury.madsea.saiqihe.todolistversion2.TodoRepo;

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
        String date = inputData.getString("date");
        assert title != null;
        TodoNote note = new TodoNote(title,detail);
        repo.insert(note);
        return Result.success();
    }
}
