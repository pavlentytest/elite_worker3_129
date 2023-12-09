package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    private OneTimeWorkRequest workRequest, workRequest2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Data data = new Data.Builder().putInt("key2",123123123).build();
        workRequest = new OneTimeWorkRequest.Builder(MyWork.class)
                .setInputData(data)
                .build();
        workRequest2 = new OneTimeWorkRequest.Builder(MyWork.class)
                .build();
        List<OneTimeWorkRequest> list = new ArrayList<>();
        // параллельно
        WorkManager.getInstance(this).enqueue(list);
        // последовательный
        WorkManager.getInstance(this).beginWith(list).enqueue();
        WorkManager.getInstance(this).beginWith(workRequest).then(workRequest2).enqueue();


        WorkManager.getInstance(this).enqueue(workRequest);
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId()).observe(
                this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        Log.d("RRR","State = " + workInfo.getState());
                        Log.d("RRR", "key="+workInfo.getOutputData().getString("key"));
                    }
                }
        );
    }
}