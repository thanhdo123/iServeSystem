package com.i.serve.iservesystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.i.serve.iservesystem.application.IServeApplication;
import com.i.serve.iservesystem.socket.ClientConnectionManager;
import com.i.serve.iservesystem.socket.IConnectionManager;

import java.io.IOException;
import java.util.Locale;


public class ConfigurationActivity extends Activity implements View.OnClickListener{

    public static final String PREFS_NAME = "MyPrefsFile";

    private Button btnTiepTuc;
    private EditText inputHost;
    private EditText inputPort;
    private EditText inputTimeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        inputHost = (EditText)findViewById(R.id.txtConfigMayChu);
        inputPort = (EditText)findViewById(R.id.txtConfigCongKetNoi);
        inputTimeOut = (EditText)findViewById(R.id.txtConfigThoiGianCho);
        btnTiepTuc = (Button)findViewById(R.id.btnConfigTiepTuc);
        btnTiepTuc.setOnClickListener(this);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if(settings != null){
            String ip = settings.getString("config_ip", null);
            String port = settings.getString("config_port", null);
            String timeout = settings.getString("config_timeout", null);
            if(ip != null && !ip.equals("")){
                inputHost.setText(ip);
            }

            if(port != null && !port.equals("")){
                inputPort.setText(port);
            }

            if(timeout != null && !timeout.equals("")){
                inputTimeOut.setText(timeout);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuration, menu);
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
            case R.id.btnConfigTiepTuc:
                IServeApplication iServeApplication = (IServeApplication) getApplicationContext();
                iServeApplication.setupConnection(inputHost.getText().toString(), Integer.parseInt(inputPort.getText().toString()),
                        Integer.parseInt(inputTimeOut.getText().toString()));
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("config_ip", inputHost.getText().toString());
                editor.putString("config_port", inputPort.getText().toString());
                editor.putString("config_timeout", inputTimeOut.getText().toString());
                // Commit the edits!
                editor.commit();

                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
