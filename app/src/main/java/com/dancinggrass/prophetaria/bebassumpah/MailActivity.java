package com.dancinggrass.prophetaria.bebassumpah;

import android.os.Bundle;
import android.provider.Telephony;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.backendless.Backendless;

public class MailActivity extends AppCompatActivity implements OnMailSelectedListener {

    InboxFragment inboxFragment = null;
    SentFragment sentFragment = null;
    ComposeFragment composeFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);


        // Initialize backendless
        String appVersion = "v1";
        Backendless.initApp(this,
                getResources().getString(R.string.app_id),
                getResources().getString(R.string.android_key),
                appVersion);


        Button inbox = (Button)findViewById(R.id.button_inbox);
        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(getInboxFragment());
            }
        });
        Button sent = (Button)findViewById(R.id.button_sent);
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(getSentFragment());
            }
        });
        Button compose = (Button)findViewById(R.id.button_compose);
        compose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(getComposeFragment());
            }
        });

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, getInboxFragment()).commit();
        }
    }

    private InboxFragment getInboxFragment() {
        if (inboxFragment == null)
            inboxFragment = InboxFragment.newInstance("from@gmail.com");
        return inboxFragment;
    }
    private SentFragment getSentFragment() {
        if (sentFragment == null)
            sentFragment = SentFragment.newInstance("from@gmail.com");
        return sentFragment;
    }

    private ComposeFragment getComposeFragment() {
        if (composeFragment == null)
            composeFragment = ComposeFragment.newInstance("from@gmail.com");
        return composeFragment;
    }



    @Override
    public void onMailSelected(Mail mail) {

    }

    void changeFragment(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inbox, menu);
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
