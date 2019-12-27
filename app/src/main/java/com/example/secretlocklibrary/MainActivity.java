package com.example.secretlocklibrary;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.secretlock.SecretLock;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();
    private int interval = 1000;
    Button button;
    TextView textView;
    LinearLayout ll;

    final SecretLock secretLock = new SecretLock();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.text);
        ll = findViewById(R.id.layout);

        startRepeatingTask();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secretLock.openSettings(MainActivity.this);
            }
        });


    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            if (secretLock.getLockValue(getApplicationContext())) {

                /// From here you can go to a new activity if lock is open

                button.setVisibility(View.VISIBLE);
                textView.setText("Unlocked");
                ll.setBackgroundColor(Color.BLUE);
            } else {
                button.setVisibility(View.INVISIBLE);
                textView.setText("Locked");
                ll.setBackgroundColor(Color.RED);

            }
            handler.postDelayed(mStatusChecker, interval);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }


    private void startRepeatingTask() {
        mStatusChecker.run();
    }

    private void stopRepeatingTask() {
        handler.removeCallbacks(mStatusChecker);
    }
}
