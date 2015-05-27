package com.i.serve.iservesystem;

import android.app.Activity;
import android.content.Intent;
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
                iServeApplication.setupConnection(inputHost.getText().toString(), Integer.parseInt(inputPort.getText().toString()));
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
