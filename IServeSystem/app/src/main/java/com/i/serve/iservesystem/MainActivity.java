package com.i.serve.iservesystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.i.serve.iservesystem.application.IServeApplication;


public class MainActivity extends Activity {
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean isSet=false;
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if(settings != null){
            String ip = settings.getString("config_ip", null);
            String port = settings.getString("config_port", null);
            String timeout = settings.getString("config_timeout", null);
            if(ip != null && port != null && timeout != null &&
                    !ip.equals("") && !port.equals("") && !timeout.equals("")){
                isSet = true;
                IServeApplication iServeApplication = (IServeApplication) getApplicationContext();
                iServeApplication.setupConnection(ip, Integer.parseInt(port),
                        Integer.parseInt(timeout));
            }
        }
        Intent intent;
        if (!isSet) {
            intent = new Intent(this, ConfigurationActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
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
