package edu.uta.cse.distributedmeetingscheduler;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


public class ADD_Invites extends ActionBarActivity {

    LinearLayout containerLayout;
    static int totalEditTexts = 0;
    Button button;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__invites);
        containerLayout = (LinearLayout)findViewById(R.id.linear1);
        button = (Button)findViewById(R.id.add_invitees);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalEditTexts++;
                if (totalEditTexts > 100)
                    return;
                EditText editText = new EditText(getBaseContext());
                containerLayout.addView(editText);
                editText.setGravity(Gravity.RIGHT);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) editText.getLayoutParams();
                layoutParams.width = LinearLayout.LayoutParams.FILL_PARENT;
                layoutParams.setMargins(23, 34, 0, 0);
                // RelativeLayout.LayoutParams()
                editText.setLayoutParams(layoutParams);
                //if you want to identify the created editTexts, set a tag, like below
                editText.setTag("EditText" + totalEditTexts);
            }
        });
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
}



