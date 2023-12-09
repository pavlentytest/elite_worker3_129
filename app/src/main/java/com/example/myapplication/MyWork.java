package com.example.myapplication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWork extends Worker {
    public MyWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        int y = workerParams.getInputData().getInt("key2",0);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Data data = new Data.Builder()
                .putString("key","Hello from Worker!")
                .build();
        return Result.success(data);
    }
}
