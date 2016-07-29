package com.example.fearless.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.fearless.fearless.R;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Fearless on 16/3/27.
 */
public class TimeActivity extends Activity {
    @InjectView(R.id.datepicker)
    DatePicker datePicker;
    @InjectView(R.id.timepicker)
    TimePicker timePicker;
    @InjectView(R.id.setTime)
    Button setTime;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private AlertDialog ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_datetime);
        ButterKnife.inject(this);
        Calendar ca = Calendar.getInstance();
        year = ca.get(Calendar.YEAR);
        month = ca.get(Calendar.MONTH);
        day = ca.get(Calendar.DAY_OF_MONTH);
        hour = ca.get(Calendar.HOUR_OF_DAY);
        minute = ca.get(Calendar.MINUTE);

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                TimeActivity.this.year = year;
                TimeActivity.this.month = month+1;
                TimeActivity.this.day = day;
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                TimeActivity.this.hour = hour;
                TimeActivity.this.minute = minute;
            }
        });
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("time", year + "年" + month + "月" + day + "日" + hour + "时" + minute + "分");
                setResult(2, intent);
                finish();
            }
        });
    }
}
