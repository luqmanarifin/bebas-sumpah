package com.dancinggrass.prophetaria.bebassumpah;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.math.BigInteger;
import java.util.List;

import elliptic_curve_signature.Constant;
import elliptic_curve_signature.Pair;
import elliptic_curve_signature.Point;

public class MailActivity extends AppCompatActivity
        implements
        OnMailSelectedListener,
        LoginDialogFragment.OnLoginDialogListener {

    String address = null;
    Key key = null;

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

            InboxFragment firstFragment = getInboxFragment();
            if (firstFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, firstFragment).commit();
            }
        }

    }

    private String toClauseString(String clause) {
        return "'" + clause + "'";
    }

    @Override
    public void onLogin(final String address) {
        this.address = address;

        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setWhereClause("address = " + toClauseString(address));
        Backendless.Persistence.of(Key.class).findFirst(new AsyncCallback<Key>() {
            @Override
            public void handleResponse(Key response) {
                if (response == null) {
                    setNewKey();
                }
                else {
                    key = response;
                    changeFragment(getInboxFragment());
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d(MailActivity.class.getSimpleName(), fault.toString());
                if (fault.getCode().equals("1010")) {
                    setNewKey();
                }
            }
        });
    }

    public void setNewKey() {
        Pair<Point, BigInteger> rawKey = Constant.getKey();
        String x = rawKey.first.x.toString();
        String y = rawKey.first.y.toString();
        String start = rawKey.second.toString();

        final Key packedKey = new Key(address, x, y, start);

        Backendless.Persistence.of(Key.class).save(packedKey, new AsyncCallback<Key>() {
            @Override
            public void handleResponse(Key response) {
                key = response;
                changeFragment(getInboxFragment());
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d(MailActivity.class.getSimpleName(), fault.toString());
            }
        });
    }

    public void onStart() {
        super.onStart();
        if (address == null) {
            LoginDialogFragment loginDialogFragment = LoginDialogFragment.newInstance();
            loginDialogFragment.show(getSupportFragmentManager(), "Login Dialog Fragment");
        }
    }

    private InboxFragment getInboxFragment() {
        if (inboxFragment == null && address != null)
            inboxFragment = InboxFragment.newInstance(address);
        return inboxFragment;
    }
    private SentFragment getSentFragment() {
        if (sentFragment == null && address != null)
            sentFragment = SentFragment.newInstance(address);
        return sentFragment;
    }

    private ComposeFragment getComposeFragment() {
        if (composeFragment == null && address != null)
            composeFragment = ComposeFragment.newInstance(address);
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
