package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private TextView tv;
    private Button btn;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn = findViewById(R.id.button2);
        tv = findViewById(R.id.textView2);
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                // получим сообщение из другого потока
                int x = msg.getData().getInt("key");
                tv.setText("Number N: "+x);
            }
        };
        btn.setOnClickListener(v -> new Thread(this::doCount).start());
    }
    public void doCount() {
        for(int i=1;i<=20;i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt("key", i);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    }


}