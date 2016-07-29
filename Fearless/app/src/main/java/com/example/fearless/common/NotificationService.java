package com.example.fearless.common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.example.fearless.invite.ipresenter.IThirdFragmentPresenter;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NotificationService extends Service {

    public NotificationService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String inviteTime = intent.getStringExtra("inviteTime");
        Calendar timeForRel =Constant.getCalendarByInintData(inviteTime);
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.MONTH, timeForRel.get(timeForRel.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, timeForRel.get(timeForRel.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, timeForRel.get(timeForRel.HOUR_OF_DAY)-1);
        calendar.set(Calendar.MINUTE,timeForRel.get(timeForRel.MINUTE));

        Log.i("wxy---service--calendarTime", "" + calendar.getTime());


        Date timeLong = calendar.getTime();
        Date curDate = new Date(System.currentTimeMillis());
        long diff = timeLong.getTime() - curDate.getTime();
        if (diff > 0) {
            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent i = new Intent(this, AlermReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
            manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, AlermReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.cancel(pi);
    }
}
