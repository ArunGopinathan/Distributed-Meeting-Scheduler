package edu.uta.cse.distributedmeetingscheduler;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;
import java.io.Writer;


public class MeetingRequestActivity extends ActionBarActivity {

    String notificationXML = "", userXML = "";
    User user;
    String meetingId = "";
    NotificationsResponse notifications;
    String response1 = "", response2 = "";
    String TAG = "";
    EditText mMeetName, mMeetAgenda, mMeetLocation, mMeetDate, mMeetTime1, mMeetTime2;
    Button bAccept1, bReject1, bAccept2, bReject2;
    private String URLFORMAT = "http://%s/DistributedMeetingSchedulerWebService/DMSWebService/SaveUserResponse/";
    private String URL = "http://dms.ngrok.io/DistributedMeetingSchedulerWebService/DMSWebService/SaveUserResponse/";
    private String time1Response = "", time2Response = "";
    private ProgressBar mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_request);
        notificationXML = getIntent().getStringExtra("notification_xml");
        meetingId = getIntent().getStringExtra("meeting_id");
        userXML = getIntent().getStringExtra("user_xml");
        mProgressView = (ProgressBar) findViewById(R.id.login_progress);
        Serializer serializer = new Persister();
        try {
            notifications = new NotificationsResponse();
            notifications = serializer.read(NotificationsResponse.class, notificationXML);
            user = serializer.read(User.class, userXML);
            //String name = user.getFirstName();

        } catch (Exception ex) {
            String exceptionstring = ex.toString();
            Log.i(TAG, "Async task error: " + exceptionstring);
            exceptionstring.toString();
        }

        mMeetName = (EditText) findViewById(R.id.eventName);
        mMeetAgenda = (EditText) findViewById(R.id.meetAgenda);
        mMeetLocation = (EditText) findViewById(R.id.meetLocation);
        mMeetDate = (EditText) findViewById(R.id.meetDate);
        mMeetTime1 = (EditText) findViewById(R.id.meetTime1);
        mMeetTime2 = (EditText) findViewById(R.id.meetTime2);

        bAccept1 = (Button) findViewById(R.id.accept1);
        bAccept2 = (Button) findViewById(R.id.accept2);
        bReject1 = (Button) findViewById(R.id.reject1);
        bReject2 = (Button) findViewById(R.id.reject2);

        bAccept1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time1Response = "YES";
                AsyncUpdate1Task task = new AsyncUpdate1Task();
                task.execute();
            }
        });

        bReject1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time1Response = "NO";
                AsyncUpdate1Task task = new AsyncUpdate1Task();
                task.execute();

            }
        });

        bAccept2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time2Response = "YES";
                AsyncUpdate2Task task = new AsyncUpdate2Task();
                task.execute();
            }
        });

        bReject2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time2Response = "NO";
                AsyncUpdate2Task task = new AsyncUpdate2Task();
                task.execute();

            }
        });

        for (Notification n : notifications.getNotifications()) {
            if (n.getMeetingId().equals(meetingId)) {
                mMeetName.setText(n.getMeetingName());
                mMeetAgenda.setText(n.getMeetingAgenda());
                mMeetLocation.setText(n.getMeetingLocation());

                for (MeetingDate date : n.getMeetingDates()) {
                    mMeetDate.setText(date.getMeetDate());

                    int count = 0;
                    for (MeetingTime time : date.getMeetingTimes()) {
                        count++;
                        String time1 = time.getMeetingStartTime() + "-" + time.getMeetingEndTime();
                        if (count == 1) {
                            mMeetTime1.setText(time1);
                        }
                        if (count == 2) {
                            mMeetTime2.setText(time1);
                        }
                    }
                }
            }
        }

    }

    public String SaveResponse(String response, String meetStartTime, String meetEndTime) {

        SaveUserResponseRequest request = new SaveUserResponseRequest();
        request.setMeetingId(meetingId);
        request.setMeetDate(mMeetDate.getText().toString());
        request.setParticipantEmail(user.getMavEmail());

        request.setMeetStartTime(meetStartTime);
        request.setMeetEndTime(meetEndTime);
        request.setUserResponse(response);

        Writer writer = new StringWriter();
        Serializer serializer = new Persister();
        String result = "";
        try {
            serializer.write(request, writer);
            String requestXML = writer.toString();


            DefaultHttpClient httpclient = new DefaultHttpClient();
            try {

                //  String url = "http://192.168.0.6:8080/AMSWebServices/AMSService/Authenticate/"+userName+"/"+password;
                String url = URL;
                Log.w(TAG, "url=" + url);
                HttpPost postRequest = new HttpPost(url);

                // String userXML = getUserXml(user);
                postRequest.addHeader("Content-Type", "application/xml");

                StringEntity postentity = new StringEntity(requestXML, "UTF-8");
                postentity.setContentType("application/xml");

                postRequest.setEntity(postentity);


                //  postRequest.setEntity(new BasicHttpEntity(nameValuePair));
                //  HttpGet getRequest = new HttpGet(url);
                //Log.w("AMS-S",getRequest.toString());
                HttpResponse httpResponse = httpclient.execute(postRequest);

                HttpEntity entity = httpResponse.getEntity();
                Log.w("AMS-S", httpResponse.getStatusLine().toString());


                //Log.w("AMS",entity.toString());
                if (entity != null) {
                    result = EntityUtils.toString(entity);

                    //  userXML = result;
                    Log.w("AMS-S", "Entity : " + result);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // When HttpClient instance is no longer needed,
                // shut down the connection manager to ensure
                // immediate deallocation of all system resources
                httpclient.getConnectionManager().shutdown();
            }
            //return result;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

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

    private class AsyncUpdate1Task extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            mProgressView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            mProgressView.animate();
        }

        @Override
        protected Void doInBackground(String... params) {

            String[] time = mMeetTime1.getText().toString().split("-");
            response1 = SaveResponse(time1Response, time[0], time[1]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            mProgressView.setVisibility(View.GONE);
            if (!response1.equals("")) {
                if (response1.equals("SUCCESS")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Response Saved Successfully", Toast.LENGTH_LONG);
                   toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    response1 = "";
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    response1 = "";
                }
            }
        }
    }

    private class AsyncUpdate2Task extends AsyncTask<String, Void, Void> {


        @Override
        protected void onPreExecute() {
            mProgressView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            mProgressView.animate();
        }


        @Override
        protected Void doInBackground(String... params) {
            String[] time = mMeetTime2.getText().toString().split("-");
            response2 = SaveResponse(time2Response, time[0], time[1]);
            //return null;


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgressView.setVisibility(View.GONE);
            //mProgressView.setVisibility(View.GONE);
            if (!response2.equals("")) {
                if (response2.equals("SUCCESS")) {
                    Toast toast = Toast.makeText(MeetingRequestActivity.this, "Response Saved Successfully", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    response2 = "";
                } else {
                    Toast toast = Toast.makeText(MeetingRequestActivity.this, "Failed", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    response2 = "";
                }
            }
        }
    }
}
