package edu.neu.khoury.madsea.saiqihe.todolistversion2.Workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.time.LocalDateTime;

import edu.neu.khoury.madsea.saiqihe.todolistversion2.R;

public class TimerWorker extends Worker {
    public TimerWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context applicationContext = getApplicationContext();
        String title_message = getInputData().getString("info_title");
        String detail_message = getInputData().getString("info_detail");

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(applicationContext, "MY_CHANNEL")
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle("TIMER:"+title_message)
                .setContentText(detail_message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[0]);

        // Show the notification
        NotificationManagerCompat.from(applicationContext).notify(1, builder.build());
        return Result.success();
    }
}
