package com.example.macqurarieinterns.Function;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class MyIntent {
    public static void moveActivity(final AppCompatActivity appCompatActivity, final Class appCompatActivity1) {
        Intent intent = new Intent(appCompatActivity.getApplicationContext(), appCompatActivity1);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        appCompatActivity.startActivity(intent);
        appCompatActivity.finish();
    }
}
