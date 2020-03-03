package com.dmtroncoso.satapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2020, 2, 01, 18, 54);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2020, 2, 01, 21, 01);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Test")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Test class")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "The Test")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "myrows.contactme@gmail.com, jallamasalvarez@gmail.com, jmbarguenolopez@gmail.com, pablorodriguezr2000@gmail.com")
                .putExtra(CalendarContract.Events.HAS_ALARM, true)
                .putExtra(CalendarContract.Reminders.EVENT_ID, CalendarContract.Events._ID)
                .putExtra(CalendarContract.Events.ALLOWED_REMINDERS, "METHOD_DEFAULT")
                .putExtra(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
                .putExtra(CalendarContract.Reminders.MINUTES,5);
        startActivity(intent);
    }
}
