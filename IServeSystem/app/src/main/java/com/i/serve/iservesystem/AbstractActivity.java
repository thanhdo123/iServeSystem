package com.i.serve.iservesystem;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.i.serve.iservesystem.application.IServeApplication;


public class AbstractActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_abstract, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            IServeApplication iServeApplication = (IServeApplication) getApplicationContext();
            iServeApplication.setIsLogin(false);
            intent = new Intent(this, ConfigurationActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_logout) {
            IServeApplication iServeApplication = (IServeApplication) getApplicationContext();
            iServeApplication.setIsLogin(false);
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
