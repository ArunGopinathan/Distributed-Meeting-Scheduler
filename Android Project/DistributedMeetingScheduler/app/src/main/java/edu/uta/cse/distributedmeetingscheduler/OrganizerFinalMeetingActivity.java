package edu.uta.cse.distributedmeetingscheduler;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.Collection;


public class OrganizerFinalMeetingActivity extends ActionBarActivity {

    String meetingId = "";
    ProgressBar mProgressView;
    String finalMeetingResponseXML;
    EditText mMeetName, mMeetAgenda, mMeetLocation, mMeetDate, mMeetTime1;
    FinalMeetingResponse finalMeetingResponse;
    String URL = "http://dms.ngrok.io/DistributedMeetingSchedulerWebService/DMSWebService/getFinalMeeting/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_final_meeting);

        meetingId = getIntent().getStringExtra("meeting_id");
        mProgressView = (ProgressBar) findViewById(R.id.login_progress);

        mMeetName = (EditText) findViewById(R.id.eventName);
        mMeetAgenda = (EditText) findViewById(R.id.meetAgenda);
        mMeetLocation = (EditText) findViewById(R.id.meetLocation);
        mMeetDate = (EditText) findViewById(R.id.meetDate);
        mMeetTime1 = (EditText) findViewById(R.id.meetTime1);

        AsyncGetFinalMeeting task = new AsyncGetFinalMeeting();
        task.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_organizer_final_meeting, menu);
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

    public String getFinalMeetings() {
        String result = "";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(URL + meetingId);

        try {
            HttpResponse response = httpClient.execute(getRequest);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
            Log.w("DMS", result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public class AsyncGetFinalMeeting extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            finalMeetingResponseXML = getFinalMeetings();
            Serializer serializer = new Persister();
            try {
                finalMeetingResponse = serializer.read(FinalMeetingResponse.class, finalMeetingResponseXML);
                Log.w("TAG",finalMeetingResponseXML);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            mProgressView.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressView.setVisibility(View.GONE);
            mMeetName.setText(finalMeetingResponse.getNotification().getMeetingName());
            mMeetAgenda.setText(finalMeetingResponse.getNotification().getMeetingAgenda());
            mMeetLocation.setText(finalMeetingResponse.getNotification().getMeetingLocation());

          Collection<MeetingDate> meetingDates = finalMeetingResponse.getNotification().getMeetingDates();
            for(MeetingDate meetingDate : meetingDates)
            {
                    mMeetDate.setText(meetingDate.getMeetDate());
                Collection<MeetingTime> times = meetingDate.getMeetingTimes();
                for(MeetingTime time : times)
                {
                    String meetingTime = time.getMeetingStartTime()+"-"+time.getMeetingEndTime();
                    mMeetTime1.setText(meetingTime);
                }
            }


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //  super.onProgressUpdate(values);
            mProgressView.animate();
        }
    }
}
