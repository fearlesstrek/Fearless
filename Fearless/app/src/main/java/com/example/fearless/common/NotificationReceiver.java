package com.example.fearless.common;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.fearless.fearless.FearlessActivity;

public class NotificationReceiver extends BroadcastReceiver {
    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent contextIntent = new Intent(Intent.ACTION_MAIN);
        contextIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        contextIntent.setClass(context, FearlessActivity.class);
        contextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        context.startActivity(contextIntent);

    }
}
