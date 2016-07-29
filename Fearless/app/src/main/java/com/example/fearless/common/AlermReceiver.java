package com.example.fearless.common;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.view.View;

import com.example.fearless.fearless.R;

public class AlermReceiver extends BroadcastReceiver{
    public AlermReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //设置点击通知栏得动作为启动另一个广播
        Intent broadCastIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, broadCastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setContentTitle("您有一个邀约活动一小时后开启")
                .setContentText("是否确定参加，否则打开软件提前退出")
                .setTicker("与你相约有通知！")
                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher);
        

        manager.notify(1, builder.build());
//        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification notification = new Notification(R.drawable.ic_launcher, "与你相约软件提醒", System.currentTimeMillis());
//        notification.setLatestEventInfo(context, "您有一个邀约活动一小时后开启", "是否确定参加，否则打开软件提前退出", null);
//        notification.defaults = Notification.DEFAULT_ALL;
//        manager.notify(1, notification);

        Intent i = new Intent(context, NotificationService.class);
        context.stopService(i);

//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Intent i = new Intent(context, NotificationService.class);
//        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
//        alarmManager.cancel(pi);
    }

}
