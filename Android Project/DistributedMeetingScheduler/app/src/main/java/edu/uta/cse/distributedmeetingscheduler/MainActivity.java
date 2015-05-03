package edu.uta.cse.distributedmeetingscheduler;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;


public class MainActivity extends ActionBarActivity {
    User user;
    String userXML;
    Button mProposeMeetingBtn, mScheduleMeetingBtn, mGetNotificationsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userXML = getIntent().getStringExtra("USER_XML");

        mProposeMeetingBtn = (Button) findViewById(R.id.btnProposeMeeting);
        mGetNotificationsBtn = (Button) findViewById(R.id.btnNotifications);
        mScheduleMeetingBtn = (Button) findViewById(R.id.btnScheduleMeeting);
        //open the Propose Meeting Intent
        mProposeMeetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScheduleMeeting.class);
                intent.putExtra("USER_XML", userXML);
                startActivity(intent);

            }
        });
        mGetNotificationsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotificationsActivity.class);
                intent.putExtra("USER_XML", userXML);
                startActivity(intent);

            }
        });

        mScheduleMeetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrganizerListActivity.class);
                intent.putExtra("USER_XML", userXML);
                startActivity(intent);

            }
        });


        try {
            //parse User XML to User Class
            user = deserializeUserXML(userXML);
        } catch (Exception ex) {
            //redirect to login page
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
        }


    }

    protected User deserializeUserXML(String userXML) {
        User user = new User();
        Serializer serializer = new Persister();
        try {

            user = serializer.read(User.class, userXML);


        } catch (Exception ex) {
            String exceptionstring = ex.toString();
            exceptionstring.toString();
        }
        return user;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
