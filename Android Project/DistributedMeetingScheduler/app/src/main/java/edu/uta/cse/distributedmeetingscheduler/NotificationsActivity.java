package edu.uta.cse.distributedmeetingscheduler;

import android.app.ActionBar;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NotificationsActivity extends ActionBarActivity {

    ListView listView;
    ArrayList<String> values = new ArrayList<String>();
    ArrayList<String> list = new ArrayList<String>();
    ProgressBar mProgressView;
    String userXML, requestXML;
    String responseXML;
    NotificationsResponse response;
    User user;
    String TAG = "DMS";
    private String URLFORMAT = "http://%s/DistributedMeetingSchedulerWebService/DMSWebService/GetNotifications/";
    private String URL = "http://dms.ngrok.io/DistributedMeetingSchedulerWebService/DMSWebService/GetNotifications/";
    GetNotificationRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

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

        mProgressView = (ProgressBar) findViewById(R.id.login_progress);
        listView = (ListView) findViewById(R.id.meetingInvitesList);

      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              long meetingId = parent.getItemIdAtPosition(position);
             // parent.()
              Log.w("DMS",meetingId+"");
              //get the item thats selected and open the Meeting Request Accept / Reject page
              Intent intent = new Intent(getApplicationContext(),MeetingRequestActivity.class);
              intent.putExtra("notification_xml",responseXML);
              intent.putExtra("meeting_id",meetingId+"");

              intent.putExtra("user_xml",userXML);
                startActivity(intent);
          }
      });
      //  values.add("0,<No Invitations>");

      /*  TextView dummy = new TextView(this);
        dummy.setText("<No Invitations>");
        dummy.setTag("0");
        listView.addView(dummy);*/

       /* values = new HashMap<String,String>();
        values.put("0","<No Invitations>");*/

        request = new GetNotificationRequest();
        request.setUserEmailId(user.getMavEmail());

        list = new ArrayList<String>();
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                R.layout.list_layout, values,list);

        listView.setAdapter(adapter);

        NotificationsAsyncTask task = new NotificationsAsyncTask();
        task.execute();

    }

    public String getNotifications() {
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {

            //  String url = "http://192.168.0.6:8080/AMSWebServices/AMSService/Authenticate/"+userName+"/"+password;
            String url = URL;
            Log.w(TAG, "url=" + url);
            HttpPost postRequest = new HttpPost(url);

            //requestXML =	Writer writer = new StringWriter();
            Writer writer = new StringWriter();
            Serializer serializer = new Persister();
            try {
                serializer.write(request, writer);
                requestXML = writer.toString();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

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
        return result;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notifications, menu);
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

    private class NotificationsAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            responseXML = getNotifications();
            Log.w(TAG, responseXML);
            Serializer serializer = new Persister();
            try {
                response = serializer.read(NotificationsResponse.class, responseXML);
                for (Notification n : response.getNotifications()) {
                    String EventName = n.getMeetingName();
                    String MeetingId = n.getMeetingId();
                    if (values.contains("0,<No Invitations>"))
                        values.remove("0,<No Invitations>");


                    String entry = MeetingId + "," + EventName;
                    values.add(entry);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgressView.setVisibility(View.GONE);
            StableArrayAdapter adapter = new StableArrayAdapter(NotificationsActivity.this, R.layout.list_layout, values,list);
            listView.setAdapter(adapter);
           adapter.notifyDataSetChanged();

        }

        @Override
        protected void onPreExecute() {
            mProgressView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            mProgressView.animate();
        }
    }


}
