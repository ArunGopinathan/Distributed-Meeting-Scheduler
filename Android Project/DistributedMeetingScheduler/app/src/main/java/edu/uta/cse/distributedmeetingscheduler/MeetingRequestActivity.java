package edu.uta.cse.distributedmeetingscheduler;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;


public class MeetingRequestActivity extends ActionBarActivity {

    String notificationXML = "";
    String meetingId = "";
    NotificationsResponse notifications;
    String TAG="";
    EditText mMeetName,mMeetAgenda,mMeetLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_request);
        notificationXML = getIntent().getStringExtra("notification_xml");
        meetingId = getIntent().getStringExtra("meeting_id");



        Serializer serializer = new Persister();
        try {
            notifications = new NotificationsResponse();
            notifications = serializer.read(NotificationsResponse.class, notificationXML);
            //String name = user.getFirstName();

        } catch (Exception ex) {
            String exceptionstring = ex.toString();
            Log.i(TAG, "Async task error: " + exceptionstring);
            exceptionstring.toString();
        }

        mMeetName = (EditText) findViewById(R.id.eventName);
        mMeetAgenda = (EditText) findViewById(R.id.meetAgenda);
        mMeetLocation = (EditText) findViewById(R.id.meetLocation);

       for(Notification n : notifications.getNotifications())
       {
            if(n.getMeetingId().equals(meetingId))
            {
                mMeetName.setText(n.getMeetingName());
                mMeetAgenda.setText(n.getMeetingAgenda());
                mMeetLocation.setText(n.getMeetingLocation());
            }
       }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meeting_request, menu);
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
