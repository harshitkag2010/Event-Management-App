package com.example1.harshit.event;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import android.view.LayoutInflater;

public class PostEvent extends AppCompatActivity {

    private  EditText TitleEt,VenueEt,DetailsEt,hoursEt,minutesEt,timeEt;
    private Button button1,button;

    private Long mSelectedUnixTimeStamp;
    private String eventTitle, eventLocation, eventDetails;
    private int durationHours, durationMinutes, durationTotalMinutes;

    private List<Long> freeTimes;

    private Spinner spinTime;
    private static final String URL="http://manral10.net16.net/retriveEvent.php?title=";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_event);

        TitleEt = (EditText) findViewById(R.id.TitleEt);
         VenueEt = (EditText) findViewById(R.id.VenueEt);
         DetailsEt = (EditText) findViewById(R.id.DetailsEt);
         hoursEt  = (EditText) findViewById(R.id.hoursEt);
         minutesEt  = (EditText) findViewById(R.id.minutesEt);
       // timeEt      = (EditText) findViewById(R.id.timeEt);
//        button1     =(Button) findViewById(R.id.button1);


//        spinTime = (Spinner) findViewById(R.id.spinTime);


        final Button CreateBt=(Button) findViewById(R.id.CreateBt);

        CreateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateFields()) {

                    final String title = TitleEt.getText().toString();
                    final String venue = VenueEt.getText().toString();
                    final String details = DetailsEt.getText().toString();
                    final int hours = Integer.parseInt(hoursEt.getText().toString());
                    final int minutes = Integer.parseInt(minutesEt.getText().toString());
         //           final String time = timeEt.getText().toString();

             //       final String setTime = button1.getText().toString();

                    final String setTime = TimeSettings.st.toString();
                    final String setDate = DateSettings.st1.toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(PostEvent.this);
                                    builder.setMessage("Creation successfull")
                                            .setNegativeButton("", null)
                                            .create()
                                            .show();
                                    Intent intent = new Intent(PostEvent.this, MainActivity.class);
                                    PostEvent.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(PostEvent.this);
                                    builder.setMessage("Creation Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    };

                    CreateRequest createRequest = new CreateRequest(title, venue, details, hours, minutes, setTime,setDate, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(PostEvent.this);
                    queue.add(createRequest);

                }
            }
        });

    }


    private boolean validateFields() {

        eventTitle = TitleEt.getText().toString();
        if (eventTitle.isEmpty()) {
            Toast.makeText(this, "Please Fill in Title", Toast.LENGTH_SHORT).show();
            return false;
        }

        eventLocation = VenueEt.getText().toString();
        if (eventLocation.isEmpty()) {
            Toast.makeText(this, "Please Fill in Location", Toast.LENGTH_SHORT).show();
            return false;
        }

        eventDetails = DetailsEt.getText().toString();
        if (eventDetails.isEmpty()) {
            Toast.makeText(this, "Please Fill in Event Details", Toast.LENGTH_SHORT).show();
            return false;
        }

        calcDuration();

        if (durationTotalMinutes <= 0) {
            Toast.makeText(this, "Please Choose a suitable duration", Toast.LENGTH_SHORT).show();
            return false;
        }

       /* if (mSelectedUnixTimeStamp == null) {
            Toast.makeText(this, "Please Choose a suitable Time", Toast.LENGTH_SHORT).show();
            return false;
        }

        */


        // TODO fix this after dynamic fill
//        mSelectedUnixTimeStamp = mSelectedTimeStamp;
        return true;
    }
    private void calcDuration() {
        String durationHoursStr = hoursEt.getText().toString();
        durationHours = durationHoursStr.isEmpty() ? 0 : Integer.parseInt(durationHoursStr);

        String durationMinutesStr = minutesEt.getText().toString();
        durationMinutes = durationMinutesStr.isEmpty() ? 0 : Integer.parseInt(durationMinutesStr);

        durationTotalMinutes = durationMinutes + durationHours * 60;
    }


    private void fillFreeTimesSpinner(List<FreeTime> freeTimesList) {
//        this.freeTimes = freeTimesList;
        this.freeTimes = new ArrayList<>();
        DateManager dateManager = new DateManager();

        ArrayList<String> arraySpinner = new ArrayList<>();
        for (FreeTime freeTime : freeTimesList) {
//            arraySpinner.add(freeTime.getTimeString());
//            if (arraySpinner.size() > 15) {
//                break;
//            }
            for (int i = 0; i < freeTime.getRepetitionsCount() && i < 4; i++) {
                Long timestamp = freeTime.getStartTimeStamp() + i * durationTotalMinutes * 60;
                this.freeTimes.add(timestamp);
                dateManager.setTimeStamp(timestamp);
                arraySpinner.add(dateManager.getReadableDayDateTimeString());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, arraySpinner);
        spinTime.setAdapter(adapter);
        spinTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DateManager dateManager = new DateManager();

                PostEvent.this.mSelectedUnixTimeStamp =
                        PostEvent.this.freeTimes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    public void onClick(View view)
    {
        Intent i=new Intent(this,PostEvent.class);
                startActivity(i);
    }
    public void setDate(View view)
    {
        PickerDialogs pickerDialogs= new PickerDialogs();

        pickerDialogs.show(getSupportFragmentManager(),"date_picker");
    }

    public void showTime(View view)
    {
        DialogHandler dialogHandler= new DialogHandler();
        dialogHandler.show(getSupportFragmentManager(), "time_picker");

    }


}
