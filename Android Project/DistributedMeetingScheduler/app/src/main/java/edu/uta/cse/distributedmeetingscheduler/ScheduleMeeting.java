package edu.uta.cse.distributedmeetingscheduler;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;


public class ScheduleMeeting extends ActionBarActivity {

    EditText titl, startdate,  st3, et3, locatn,meetAgenda;
    TextView st1,et1,st2, et2;
    //  FrameLayout fr;
    public TimePicker timepicker1;
    public Button btn, setst1;
    public Button nxt;
    public int Hour;
    public int Minutes;
    String userXML;
    String TAG="DMS";
    User user;
    //private TextView time;
    private String format = "";
    int year, monthOfYear, dayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_meeting);


        userXML = getIntent().getStringExtra("USER_XML");

        Serializer serializer = new Persister();
        try {
            user = new User();
            user = serializer.read(User.class, userXML);
            //String name = user.getFirstName();

        } catch (Exception ex) {
            String exceptionstring = ex.toString();
            Log.i(TAG, "Async task error: " + exceptionstring);
            exceptionstring.toString();
        }
        titl = (EditText) findViewById(R.id.Title);
        //  fr = (FrameLayout) findViewById(R.id.timeframe);
        startdate = (EditText) findViewById(R.id.startdate);
        timepicker1 = (TimePicker) findViewById(R.id.timePicker1);
        timepicker1.setVisibility(View.INVISIBLE);
        btn = (Button) findViewById(R.id.seteventtime);
        nxt = (Button) findViewById(R.id.next);
        btn.setVisibility(View.INVISIBLE);

        //title = (EditText) findViewById(R.id.title);
        st1 = (TextView) findViewById(R.id.st1);
        et1 = (TextView) findViewById(R.id.et1);
      st2 = (TextView) findViewById(R.id.st2);
        et2 = (TextView) findViewById(R.id.et2);

        meetAgenda = (EditText)findViewById(R.id.meetAgenda);
      /*  st3 = (EditText) findViewById(R.id.st3);
        et3 = (EditText) findViewById(R.id.et3);*/
        locatn = (EditText) findViewById(R.id.location);
        // Calendar myCalendar = Calendar.getInstance();
        final Calendar myCalendar = Calendar.getInstance();
        int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        int min = myCalendar.get(Calendar.MINUTE);
        //   timepicker1.setIs24HourView(true);
        //showTime(hour,min);

      /*  timepicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Hour = (hourOfDay);
                Minutes = (minute);
                timepicker1.setCurrentHour(hourOfDay);
                timepicker1.setCurrentMinute(minute);
            }
        });*/
        // Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
               // startdate.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
                startdate.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }

        };


        btn.setOnClickListener(new View.OnClickListener() {

                                   @Override
                                   public void onClick(View v) {

                                       timepicker1.setVisibility(View.INVISIBLE);
                                       Hour = timepicker1.getCurrentHour();
                                       Minutes = timepicker1.getCurrentMinute();

                                       btn.setVisibility(View.INVISIBLE);
                                   }
                               }
        );
        startdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(ScheduleMeeting.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }

        });

      /*  startdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DatePickerDialog(ScheduleMeeting.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/

      /*  st1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //fr.setVisibility(View.VISIBLE);
                setTime(v, st1);
            }
        });*/

        /*st1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  setTime fromTime = new setTime(st1, getApplicationContext());
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog tpd = new TimePickerDialog(ScheduleMeeting.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                st1.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                tpd.show();
               /* Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getApplicationContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        st1.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }

        });*/
        /*st1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    btn.setVisibility(View.VISIBLE);
                    timepicker1.setVisibility(View.VISIBLE);
                    setTime(v, st1);
                }
                else {
                    setTime(v, st1);
                }
            }
        });*/
      /*  et1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepicker1.setVisibility(View.VISIBLE);
                btn.setVisibility(View.VISIBLE);
                //  fr.setVisibility(View.VISIBLE);
                setTime(v, et1);
            }
        });*/
      /*  st2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setVisibility(View.VISIBLE);
                timepicker1.setVisibility(View.VISIBLE);

                //  fr.setVisibility(View.VISIBLE);
                setTime(v, st2);
            }
        });

        st2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setTime(v, st2);
            }
        });
        et2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepicker1.setVisibility(View.VISIBLE);
                btn.setVisibility(View.VISIBLE);
                //  fr.setVisibility(View.VISIBLE);
                setTime(v, et2);
            }
        });
        st3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepicker1.setVisibility(View.VISIBLE);
                btn.setVisibility(View.VISIBLE);
                // fr.setVisibility(View.VISIBLE);
                setTime(v, st3);
            }
        });
        et3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                timepicker1.setVisibility(View.VISIBLE);
                btn.setVisibility(View.VISIBLE);
                //fr.setVisibility(View.VISIBLE);
                setTime(v, et3);

            }
        });*/
        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Data to be PASSED TO WEBSERVER HERE
                String e = titl.getText().toString();
                String d = startdate.getText().toString();
                String stt1 = st1.getText().toString();
                String ett1 = et1.getText().toString();
                String loc =locatn.getText().toString();

               String stt2 = st2.getText().toString();
                String ett2 = et2.getText().toString();

                ProposeMeetingRequest request = new ProposeMeetingRequest();
                request.setMeetingName(e);
                request.setMeetingLocation(loc);
                request.setUserEmailId(user.getMavEmail());
                request.setMeetingAgenda(meetAgenda.getText().toString());
                Collection<MeetingDate> meetingDates = new ArrayList<MeetingDate>();

                MeetingDate date = new MeetingDate();
                date.setMeetDate(d);

                Collection<MeetingTime> meetingTimes = new ArrayList<MeetingTime>();
                MeetingTime time = new MeetingTime();
                time.setMeetingStartTime(stt1);
                time.setMeetingEndTime(ett1);
                meetingTimes.add(time);

                //added second time
                MeetingTime time2 = new MeetingTime();
                time2.setMeetingEndTime(ett2);
                time2.setMeetingStartTime(stt2);
                meetingTimes.add(time2);

                date.setMeetingTimes(meetingTimes);

                meetingDates.add(date);
                request.setMeetingDates(meetingDates);
                request.setParticipants(new ArrayList<Participant>());

                String xml = "";
                Writer writer = new StringWriter();
                Serializer serializer = new Persister();
                try {
                    serializer.write(request, writer);
                    Log.w("DMS",writer.toString());

                    xml = writer.toString();
                } catch (Exception ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }



                Intent i = new Intent(getApplicationContext(), add_Invites.class);
                i.putExtra("propose_meeting_request",xml);

                startActivity(i);
            }
        });

    }

    public void onStartTime(View v) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ScheduleMeeting.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                st1.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }
    public void onStartTime2(View v) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ScheduleMeeting.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                st2.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }
    public void onEndTime(View v) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ScheduleMeeting.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                et1.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }
    public void onEndTime2(View v) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ScheduleMeeting.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                et2.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }




    public void setTime(View view, EditText time) {

        //   int hour = timepicker1.getCurrentHour();
        int hour = timepicker1.getCurrentHour();
        int min = timepicker1.getCurrentMinute();
        showTime(hour, min, time);
    }

    public void showTime(int hour, int min, EditText time) {
       /* if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }*/
        time.setText(new StringBuilder().append(hour).append(" : ").append(min)
        );

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
