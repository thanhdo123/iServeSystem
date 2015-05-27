package com.i.serve.iservesystem;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.i.serve.iservesystem.application.IServeApplication;


public class LoginActivity extends Activity implements View.OnClickListener{
    private static final String TAG = LoginActivity.class.getSimpleName();

    private Button btnOK;
    private Button btnCancel;
    private EditText inputUsername;
    private IServeApplication iServeApplicationl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iServeApplicationl = (IServeApplication) getApplicationContext();

        inputUsername = (EditText)findViewById(R.id.txtUsername);

        btnOK = (Button)findViewById(R.id.btnLoginOK);
        btnOK.setOnClickListener(this);

        btnCancel = (Button)findViewById(R.id.btnLoginCancel);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnLoginOK:
                Log.d(TAG, "Press OK button");
                //Log.d(TAG, iServeApplicationl.writeAndWaitReply(inputUsername.getText().toString()));
                intent = new Intent(this, TableActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLoginCancel:
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                break;
        }
    }
}
