package com.example1.harshit.event;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.Toast;

/**
 * Created by Harshit on 10/14/2016.
 */
public class DateSettings implements DatePickerDialog.OnDateSetListener {

    Context context;
    static String  st1;
    public DateSettings(Context context)
    {
        this.context=context;

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

        Toast.makeText(context,"Selected Date :"+dayOfMonth+" / "+monthOfYear+" / "+year,Toast.LENGTH_LONG).show();
                st1= dayOfMonth+"/"+monthOfYear+"/"+year ;

    }
}
