package edu.uta.cse.distributedmeetingscheduler;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;


public class ScheduleMeeting extends ActionBarActivity
{
    EditText titl,startdate,st1,et1,st2,et2,st3,et3,locatn;
  //  FrameLayout fr;
    public TimePicker timepicker1;
    public Button btn;
    public Button nxt;
    //private TextView time;
    private String format = "";
    int year,monthOfYear,dayOfMonth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_meeting);
        titl = (EditText) findViewById(R.id.Title);
      //  fr = (FrameLayout) findViewById(R.id.timeframe);
        startdate = (EditText) findViewById(R.id.startdate);
        timepicker1 = (TimePicker) findViewById(R.id.timePicker1);
        timepicker1.setVisibility(View.INVISIBLE);
        btn = (Button) findViewById(R.id.seteventtime);
        nxt = (Button) findViewById(R.id.next);
        btn.setVisibility(View.INVISIBLE);
        //title = (EditText) findViewById(R.id.title);
        st1 = (EditText)findViewById(R.id.st1);
        et1 = (EditText)findViewById(R.id.et1);
        st2 = (EditText)findViewById(R.id.st2);
        et2 = (EditText)findViewById(R.id.et2);
        st3 = (EditText)findViewById(R.id.st3);
        et3 = (EditText)findViewById(R.id.et3);
        locatn = (EditText)findViewById(R.id.location);
        // Calendar myCalendar = Calendar.getInstance();
        final Calendar myCalendar = Calendar.getInstance();
        int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        int min = myCalendar.get(Calendar.MINUTE);
        //showTime(hour,min);

        // Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth)
            {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                startdate.setText(monthOfYear+"/"+dayOfMonth+"/"+year);
            }

        };
        titl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titl.setText("");
            }
        });
        locatn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
              locatn.setText("");
            }
        });

        btn.setOnClickListener(new View.OnClickListener()
        {

                                   @Override
                                   public void onClick(View v)
                                   {

                                        timepicker1.setVisibility(View.INVISIBLE);
                                       btn.setVisibility(View.INVISIBLE);
                                   }
                               }
        );
        startdate.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DatePickerDialog(ScheduleMeeting.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        st1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btn.setVisibility(View.VISIBLE);
                timepicker1.setVisibility(View.VISIBLE);

                //fr.setVisibility(View.VISIBLE);
                setTime(v, st1);
            }
        });
        et1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   timepicker1.setVisibility(View.VISIBLE);
                   btn.setVisibility(View.VISIBLE);
              //  fr.setVisibility(View.VISIBLE);
                setTime(v,et1);
            }
        });
        st2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setVisibility(View.VISIBLE);
                timepicker1.setVisibility(View.VISIBLE);

              //  fr.setVisibility(View.VISIBLE);
                setTime(v,st2);
            }
        });
        et2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               timepicker1.setVisibility(View.VISIBLE);
               btn.setVisibility(View.VISIBLE);
              //  fr.setVisibility(View.VISIBLE);
                setTime(v,et2);
            }
        });
        st3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepicker1.setVisibility(View.VISIBLE);
                btn.setVisibility(View.VISIBLE);
               // fr.setVisibility(View.VISIBLE);
                setTime(v,st3);
            }
        });
        et3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               timepicker1.setVisibility(View.VISIBLE);
                btn.setVisibility(View.VISIBLE);
                //fr.setVisibility(View.VISIBLE);
                setTime(v,et3);
            }
        });
        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Data to be PASSED TO WEBSERVER HERE
                String e = titl.getText().toString();
                String d = startdate.getText().toString();
                String stt1 = st1.getText().toString();
                String ett1 = et1.getText().toString();
            }
        });
    }
    public void setTime(View view,EditText time)
    {

        int hour = timepicker1.getCurrentHour();
        int min  = timepicker1.getCurrentMinute();
        showTime(hour, min,time);
    }

    public void showTime(int hour, int min,EditText time) {
        if(hour == 0)
        {
            hour += 12;
            format = "AM";
        }
        else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));

}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule_meeting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
