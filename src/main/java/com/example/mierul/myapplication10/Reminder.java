package com.example.mierul.myapplication10;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Reminder extends AppCompatActivity implements OnDateSelectedListener, OnMonthChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    //    @Bind(R.id.calendarView)
    private MaterialCalendarView widget;

    //    @Bind(R.id.textView)
    private TextView textView;

    //private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_calendar);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.setLogo(R.drawable.icon_title);
        widget = (MaterialCalendarView)findViewById(R.id.calendarView);
        widget.setDateTextAppearance(Color.WHITE);

        if (widget != null) {
            widget.setOnDateChangedListener(this);
            widget.setOnMonthChangedListener(this);
        }

        //Setup initial text

        textView = (TextView)findViewById(R.id.textView);

        textView.setText(getSelectedDatesString());
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        textView.setText(getSelectedDatesString());
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
    }

    private String getSelectedDatesString() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }

        String string = String.valueOf(date.getCalendar().getTimeInMillis());
        Log.d("AMIERUL",string);
        //time = date.getCalendar().getTimeInMillis(); /**this one pass time needed for scheduled method**/
        return FORMATTER.format(date.getDate());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case R.id.action_save_reminder:
                scheduleNotification();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void scheduleNotification(){

        Intent notificationIntent = new Intent(this,NotificationPublisher.class);
        notificationIntent.putExtra("notifId",0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,notificationIntent,0);

        /** Set kan mase kat sini **/
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,1);

        /**Nanti tukar time**/

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

    }
}
