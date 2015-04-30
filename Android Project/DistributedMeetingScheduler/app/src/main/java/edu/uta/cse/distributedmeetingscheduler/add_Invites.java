package edu.uta.cse.distributedmeetingscheduler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Collection;


public class add_Invites extends ActionBarActivity {

    ArrayList<String> values;
    ListView listView;
    String participant="";
    Button addInvitees;
    String requestXML;
    String TAG="DMS";
    Button donebtn;
    ProposeMeetingRequest request;
    ProgressBar mProgressView ;
    private String URLFORMAT = "http://%s/DistributedMeetingSchedulerWebService/DMSWebService/ProposeMeeting/";
    private String URL = "http://dms.ngrok.io/DistributedMeetingSchedulerWebService/DMSWebService/ProposeMeeting/";
    private String response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__invites);
        donebtn = (Button) findViewById(R.id.done);
        requestXML = getIntent().getStringExtra("propose_meeting_request");
        mProgressView = (ProgressBar) findViewById(R.id.login_progress);

        Serializer serializer = new Persister();
        try {
            request = new ProposeMeetingRequest();
            request = serializer.read(ProposeMeetingRequest.class, requestXML);
            //String name = user.getFirstName();

        } catch (Exception ex) {
            String exceptionstring = ex.toString();
            Log.i(TAG, "Async task error: " + exceptionstring);
            exceptionstring.toString();
        }

        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collection<Participant> participants = new ArrayList<Participant>();
               for(String item: values)
               {
                   Participant participant = new Participant();
                   participant.setUserEmailId(item);
                   participants.add(participant);
               }
                request.setParticipants(participants);

                AsyncAddInviteesTask task = new AsyncAddInviteesTask();
                task.execute();
              /*  response =  addProposeMeeting();
                if(response.equals("SUCCESS"))
                {
                    Toast toast = Toast.makeText(add_Invites.this,"Meeting Proposed Successfully",Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(add_Invites.this,"Failed",Toast.LENGTH_LONG);
                    toast.show();
                }*/

            }
        });

        listView = (ListView) findViewById(R.id.add_invitees_list);
        values = new ArrayList<String>();
        values.add("<Add Participants>");
       final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
        addInvitees = (Button) findViewById(R.id.add_invitees);

       addInvitees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();

            }
        });

    }

    private String addProposeMeeting(){
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
    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(add_Invites.this);
        View promptView = layoutInflater.inflate(R.layout.input_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(add_Invites.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        participant =    editText.getText().toString();
                        if(participant!="")
                        {
                            // adapter.add(participant);
                            if(values.contains("<Add Participants>"))
                                values.remove(values.indexOf("<Add Participants>"));

                            values.add(participant);
                            ArrayAdapter adapter =new ArrayAdapter<String>(add_Invites.this, android.R.layout.simple_list_item_1,android.R.id.text1, values);
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                       // Log.w(TAG, "url result " + urlresult);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add__invites, menu);
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

    private class AsyncAddInviteesTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            Log.i(TAG, "doInBackground");

           // if (validated) {
                String result =  addProposeMeeting();
                response = result;
         //   }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");

            mProgressView.setVisibility(View.GONE);
            if(response.equals("SUCCESS"))
            {
                //navigate back to login page
                Toast toast = Toast.makeText(getApplicationContext(),R.string.register_success,Toast.LENGTH_LONG);
                toast.show();
              /*  mFirstNameView.setText("");
                mLastNameView.setText("");
                mEmailView.setText("");
                mPasswordView.setText("");
                mConfirmPasswordView.setText("");*/

                /*Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent);*/

            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.error_occured, Toast.LENGTH_LONG);
                toast.show();

            }


           /* if(response.equals("SUCCESS"))
            {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                // startActivity(intent);
                //response = result;
                response = "";
            }
            else if(response.equals("FAILURE"))
            {

            }*/

        }


        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            mProgressView.setVisibility(View.VISIBLE);

        }


        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
            mProgressView.animate();

        }

    }
}
