package com.example.secretlocklibrary;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.secretlock.SecretLock;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(this, NextActivity.class);

        if (new SecretLock().getLockValue(getApplicationContext())) {
            startActivity(i);
        }

    }
}
