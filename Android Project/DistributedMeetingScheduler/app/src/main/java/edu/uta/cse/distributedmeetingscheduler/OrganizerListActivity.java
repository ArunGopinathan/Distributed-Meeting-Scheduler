package edu.uta.cse.distributedmeetingscheduler;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class OrganizerListActivity extends ActionBarActivity {

    String userXML;
    User user;
    String URL = "http://dms.ngrok.io/DistributedMeetingSchedulerWebService/DMSWebService/GetOrganizerMeetings/";
    ProgressBar mProgressView;
    ListView listView;
    ArrayList<String> values = new ArrayList<String>();

    String organizerResponse = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_list);

        userXML = getIntent().getStringExtra("USER_XML");
        mProgressView = (ProgressBar) findViewById(R.id.login_progress);

        listView = (ListView) findViewById(R.id.organizerMeeting);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                String[] values = item.split("-");
                String meetingId = values[0];
                Log.w("DMS",item);
                Log.w("DMS",meetingId);

                Intent intent = new Intent(getApplicationContext(),OrganizerFinalMeetingActivity.class);
                intent.putExtra("USER_XML",userXML);
                intent.putExtra("meeting_id",meetingId);
                startActivity(intent);
            }
        });
        Serializer serializer = new Persister();
        try {
            user = serializer.read(User.class, userXML);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //  values.add("Select ")
        AsyncTaskGetOrganizer task = new AsyncTaskGetOrganizer();
        task.execute();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_organizer_list, menu);
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

    public String getOrganizerMeetings() {
        String response = "";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = URL + user.getMavEmail();
        Log.w("DMS", url);
        HttpGet getRequest = new HttpGet(url);
        try {

            HttpResponse httpResponse = httpClient.execute(getRequest);

            HttpEntity entity = httpResponse.getEntity();
            Log.w("AMS-S", httpResponse.getStatusLine().toString());

            response = EntityUtils.toString(entity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return response;
    }


    public class AsyncTaskGetOrganizer extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            String response = getOrganizerMeetings();
            organizerResponse = response;
            Log.w("DMS", organizerResponse);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //  super.onPostExecute(aVoid);
            mProgressView.setVisibility(View.GONE);
            OrganizerMeetingResponse response = new OrganizerMeetingResponse();
            Serializer serializer = new Persister();
            Writer writer = new StringWriter();
            try {
                response = serializer.read(OrganizerMeetingResponse.class, organizerResponse);
                for (Notification n : response.getNotifications()) {
                    values.add(n.getMeetingId() + "-" + n.getMeetingName());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(OrganizerListActivity.this, android.R.layout.simple_list_item_1, values);
                listView.setAdapter(arrayAdapter);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //super.onProgressUpdate(values);
            mProgressView.animate();
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            mProgressView.setVisibility(View.VISIBLE);
        }
    }
}
