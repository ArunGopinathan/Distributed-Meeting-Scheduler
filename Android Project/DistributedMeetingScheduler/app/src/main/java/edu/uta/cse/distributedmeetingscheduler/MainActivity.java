package edu.uta.cse.distributedmeetingscheduler;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;


public class MainActivity extends ActionBarActivity {
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String userXML = getIntent().getStringExtra("USER_XML");
        try {
            //parse User XML to User Class
            user = deserializeUserXML(userXML);
        }
        catch(Exception ex)
        {
            //redirect to login page
            Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
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
